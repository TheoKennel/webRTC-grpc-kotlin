package com.poc.webrtc.grpc.common

import android.os.Build
import com.poc.webrtc.grpc.common.peer.common.utils.Constant
import grpc.CustomMessageP2P
import grpc.SignalingServiceGrpcKt
import grpc.client
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.io.Closeable
import java.util.concurrent.TimeUnit

class GrpcHelper : Closeable {
    companion object {
        const val PORT = 8981
    }

    private val job: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val _messageFlow = MutableSharedFlow<CustomMessageP2P>()
    val messageFlow: SharedFlow<CustomMessageP2P> = _messageFlow

    init {
        job.launch {
            launch {
                initForwardListener()
            }
            launch {
                initConnection()
            }
        }
    }

    private val channel: ManagedChannel =
        ManagedChannelBuilder
            .forAddress("localhost", PORT)
            .usePlaintext()
            .build()
	
    val stub = SignalingServiceGrpcKt.SignalingServiceCoroutineStub(channel)

    private suspend fun initForwardListener() {
        stub.forwardMessage(emptyFlow()).collect { request ->
            when {
                request.payload.typeUrl.equals(Constant.TYPE_URL_P2P_ANY) -> {
                    val customPayload = CustomMessageP2P.parseFrom(request.payload.value)
                    _messageFlow.emit(customPayload)
                    println("Received message from server into ForwardMessage : $customPayload")
                }
            }
        }
    }

    private suspend fun initConnection() {
        val payload =
            client {
                serial = Build.getSerial()
                app = "evasion" + Constant.IS_CONTROLLER
            }
        stub.registerClient(payload)
    }

    override fun close() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            println("Error while shutting down channel: ${e.message}")
            channel.shutdownNow()
        }
    }
}
