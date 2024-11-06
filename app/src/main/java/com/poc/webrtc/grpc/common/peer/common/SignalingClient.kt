package com.poc.webrtc.grpc.common.peer.common

import android.os.Build
import com.google.protobuf.Any
import com.poc.webrtc.grpc.common.GrpcHelper
import com.poc.webrtc.grpc.common.peer.common.utils.Constant
import grpc.SignalingCommand
import grpc.customMessageP2P
import grpc.signalingMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SignalingClient {
    private val signalingScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private lateinit var grpcHelper: GrpcHelper

    // session flow to send information about the session state to the subscribers
    private val _sessionStateFlow = MutableStateFlow(WebRTCSessionState.Offline)
    val sessionStateFlow: StateFlow<WebRTCSessionState> = _sessionStateFlow

    // signaling commands to send commands to value pairs to the subscribers
    private val _signalingCommandFlow = MutableSharedFlow<Pair<SignalingCommand, String>>()
    val signalingCommandFlow: SharedFlow<Pair<SignalingCommand, String>> = _signalingCommandFlow

    init {
        signalGrpcListener()
    }

    fun sendCommand(
        signalingCommand: SignalingCommand,
        message: String,
        serialReceiver: String,
    ) {
        val serial = Build.getSerial()

        val customPayload =
            customMessageP2P {
                this.signalingCommand = signalingCommand
                this.message = message
            }

        val request =
            flow {
                val payload =
                    signalingMessage {
                        senderSerial = serial
                        this.serialReceiver = serialReceiver
                        this.payload =
                            Any
                                .newBuilder()
                                .setTypeUrl(Constant.TYPE_URL_P2P_ANY)
                                .setValue(customPayload.toByteString())
                                .build()
                    }
                emit(payload)
            }
        grpcHelper.stub.forwardMessage(request)
    }

    private fun signalGrpcListener() {
        signalingScope.launch {
            grpcHelper.messageFlow.collect { message ->
                when {
                    message.signalingCommand.equals(SignalingCommand.STATE) -> handleStateMessage(message.message)
                    else -> handleSignalingCommand(message.signalingCommand, message.message)
                }
            }
        }
    }

    fun dispose() {
        _sessionStateFlow.value = WebRTCSessionState.Offline
        signalingScope.cancel()
    }

    private suspend fun handleSignalingCommand(
        command: SignalingCommand,
        message: String,
    ) {
        val value = getSeparatedMessage(message)
        println("Received signaling : command : $command value : $value")
        _signalingCommandFlow.emit(command to value)
    }

    private fun handleStateMessage(message: String) {
        val state = getSeparatedMessage(message)
        _sessionStateFlow.value = WebRTCSessionState.valueOf(state)
    }

    private fun getSeparatedMessage(text: String) = text.substringAfter(' ')
}

enum class WebRTCSessionState {
    Active, // Offer and Answer messages has been sent
    Creating, // Creating session, offer has been sent
    Ready, // Both clients available and ready to initiate session
    Impossible, // We have less than two clients connected to the server
    Offline, // unable to connect signaling server
}
