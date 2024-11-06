package com.poc.webrtc.grpc_server

import com.google.protobuf.Empty
import grpc.Client
import grpc.ClientList
import grpc.SignalingMessage
import grpc.SignalingServiceGrpcKt
import io.grpc.Server
import io.grpc.ServerBuilder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class SignalingServer(
    private val port: Int,
) {
    private val server: Server = ServerBuilder.forPort(port).addService(SignalingService()).build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down")
                this@SignalingServer.stop()
            },
        )
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private fun stop() {
        server.shutdown()
    }

    internal class SignalingService : SignalingServiceGrpcKt.SignalingServiceCoroutineImplBase() {
        private val clients = MutableSharedFlow<List<Client>>()
        private val connectedClients = ArrayList<Client>()
        private val signalingFlow = ConcurrentHashMap<String, MutableSharedFlow<SignalingMessage>>()

        override suspend fun registerClient(request: Client): Empty {
            connectedClients.add(request)
            clients.emit(connectedClients)
            println("A new client is register in server ! : $request")
            return Empty.getDefaultInstance()
        }

        override fun getConnectedClient(requests: Flow<Empty>): Flow<ClientList> =
            channelFlow {
                val requestJob =
                    launch {
                        requests.collect {
                            send(
                                ClientList.newBuilder().addAllClients(connectedClients).build(),
                            )
                        }
                    }

                val clientListJob =
                    launch {
                        clients.collect { updatedClientList ->
                            send(
                                ClientList.newBuilder().addAllClients(updatedClientList).build(),
                            )
                            println("Emit there is a new client in list clients : $clients and connected clients : $connectedClients")
                        }
                    }

                awaitClose {
                    requestJob.cancel()
                    clientListJob.cancel()
                }
            }

        override fun forwardMessage(requests: Flow<SignalingMessage>): Flow<SignalingMessage> =

            channelFlow {
                var senderSerial: String? = null
                val requestJob =
                    launch {
                        requests.collect { message ->
                            senderSerial = message.senderSerial
                            val targetSerial = message.serialReceiver
                            if (message.serialReceiver != "ALL") {
                                signalingFlow[targetSerial]?.emit(message)
                            } else {
                                signalingFlow.forEach { (id, flow) ->
                                    if (id != senderSerial) {
                                        flow.emit(message)
                                    }
                                }
                            }
                        }
                    }

                awaitClose {
                    signalingFlow.remove(senderSerial)
                    requestJob.cancel()
                }
            }
    }
}

fun main() {
    val server = SignalingServer(8080)
    server.start()
    server.blockUntilShutdown()
}
