package com.poc.webrtc.grpc.common

import grpc.SignalingServiceGrpcKt
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class SignalServiceGrpcHelper : Closeable {
    companion object {
        const val PORT = 8981
    }

    private val channel: ManagedChannel =
        ManagedChannelBuilder
            .forAddress("localhost", PORT)
            .usePlaintext()
            .build()
	
    val stub = SignalingServiceGrpcKt.SignalingServiceCoroutineStub(channel)

    override fun close() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            println("Error while shutting down channel: ${e.message}")
            channel.shutdownNow()
        }
    }
}
