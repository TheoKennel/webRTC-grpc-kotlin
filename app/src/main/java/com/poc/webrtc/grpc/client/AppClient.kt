package com.poc.webrtc.grpc.client

import com.poc.webrtc.grpc.common.SignalManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppClient : SignalManager() {
    private val job = CoroutineScope(Dispatchers.IO)

    init {
        job.launch {
        }
    }

    private suspend fun connect() {
    }
}
