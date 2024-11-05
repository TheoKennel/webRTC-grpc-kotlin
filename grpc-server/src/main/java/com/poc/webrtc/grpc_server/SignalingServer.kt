package com.poc.webrtc.grpc_server

import grpc.SignalingMessage
import grpc.SignalingServiceGrpcKt
import io.grpc.Server
import io.grpc.ServerBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class SignalingServer(
    private val port: Int,
) {
    private val server: Server =
        ServerBuilder
            .forPort(port)
            .addService(SignalingService())
            .build()

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
        private val clients = ConcurrentHashMap<String, MutableSharedFlow<SignalingMessage>>()

        override fun signal(requests: Flow<SignalingMessage>): Flow<SignalingMessage> =
            channelFlow {
                val outgoingMessages = MutableSharedFlow<SignalingMessage>()
                var clientId: String? = null

                val requestJob =
                    launch {
                        requests.collect { message ->
                            if (clientId == null) {
                                clientId = message.senderId
                                clients[clientId!!] = outgoingMessages
                            }

                            clients[message.targetId]?.emit(message)
                        }
                    }

                val outgoingJob =
                    launch {
                        outgoingMessages.collect { message ->
                            send(message)
                        }
                    }

                invokeOnClose {
                    clientId?.let { clients.remove(it) }
                    requestJob.cancel()
                    outgoingJob.cancel()
                }
            }
    }
}

fun main() {
    val server = SignalingServer(8080)
    server.start()
    server.blockUntilShutdown()
}
