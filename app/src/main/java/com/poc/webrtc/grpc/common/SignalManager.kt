package com.poc.webrtc.grpc.common

abstract class SignalManager {
    protected val helper: SignalServiceGrpcHelper = SignalServiceGrpcHelper()

    fun close() = run { helper.close() }
}
