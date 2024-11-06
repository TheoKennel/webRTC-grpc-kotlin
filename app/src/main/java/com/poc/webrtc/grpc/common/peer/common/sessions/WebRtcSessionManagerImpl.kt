package com.poc.webrtc.grpc.common.peer.common.sessions

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.content.getSystemService
import com.poc.webrtc.grpc.common.audio.AudioFocusHandler
import com.poc.webrtc.grpc.common.audio.AudioSwitch
import com.poc.webrtc.grpc.common.peer.audio.AudioP2P
import com.poc.webrtc.grpc.common.peer.common.SignalingClient
import com.poc.webrtc.grpc.common.peer.common.StreamPeerConnection
import com.poc.webrtc.grpc.common.peer.common.StreamPeerConnectionFactory
import com.poc.webrtc.grpc.common.peer.common.StreamPeerType
import grpc.SignalingCommand
import io.getstream.webrtc.android.ktx.stringify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.webrtc.AudioTrack
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.MediaStreamTrack
import org.webrtc.SessionDescription
import org.webrtc.VideoTrack
import java.util.UUID

private const val ICE_SEPARATOR = '$'

val LocalWebRtcSessionManager: ProvidableCompositionLocal<WebRtcSessionManager> =
    staticCompositionLocalOf { error("WebRtcSessionManager was not initialized!") }

class WebRtcSessionManagerImpl(
    private val context: Context,
    override val signalingClient: SignalingClient,
    override val peerConnectionFactory: StreamPeerConnectionFactory,
) : WebRtcSessionManager {
    private val sessionManagerScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    // used to send remote video track to the sender
    private val _remoteVideoSinkFlow = MutableSharedFlow<VideoTrack>()
    override val remoteVideoSinkFlow: SharedFlow<VideoTrack> = _remoteVideoSinkFlow

    // declaring video constraints and setting OfferToReceiveVideo to true
    // this step is mandatory to create valid offer and answer
    private val mediaConstraints =
        MediaConstraints().apply {
            mandatory.addAll(
                listOf(
                    MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"),
                    MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"),
                ),
            )
        }

    /** Audio properties */

    private val audioHandler: AudioSwitch by lazy {
        AudioSwitch(audioFocusHandler)
    }

    private val audioFocusHandler by lazy {
        AudioFocusHandler(context)
    }

    private val audioManager by lazy {
        context.getSystemService<AudioManager>()
    }

    private val audioSource by lazy {
        AudioP2P.makeAudioSource(peerConnectionFactory.factory, AudioP2P.buildAudioConstraints())
    }

    private val localAudioTrack: AudioTrack by lazy {
        AudioP2P.makeAudioTrack(
            peerConnectionFactory.factory,
            source = audioSource,
            trackId = "Audio${UUID.randomUUID()}",
        )
    }

    private var offer: String? = null

    fun peerConnectionAudio(
        isSubscriber: Boolean,
        serialReceiver: String,
    ): StreamPeerConnection {
        val type =
            when (isSubscriber) {
                true -> StreamPeerType.SUBSCRIBER
                false -> StreamPeerType.PUBLISHER
            }
        return peerConnectionFactory.makePeerConnection(
            coroutineScope = sessionManagerScope,
            configuration = peerConnectionFactory.rtcConfig,
            type = type,
            mediaConstraints = mediaConstraints,
            onIceCandidateRequest = { iceCandidate, _ ->
                signalingClient.sendCommand(
                    SignalingCommand.ICE,
                    "${iceCandidate.sdpMid}$ICE_SEPARATOR" + "${iceCandidate.sdpMLineIndex}$ICE_SEPARATOR${iceCandidate.sdp}",
                    serialReceiver,
                )
            },
        )
    }

    fun peerConnectionVideo(
        isSubscriber: Boolean,
        serialReceiver: String,
    ): StreamPeerConnection {
        val type =
            when (isSubscriber) {
                true -> StreamPeerType.SUBSCRIBER
                false -> StreamPeerType.PUBLISHER
            }
        return peerConnectionFactory.makePeerConnection(
            coroutineScope = sessionManagerScope,
            configuration = peerConnectionFactory.rtcConfig,
            type = type,
            mediaConstraints = mediaConstraints,
            onIceCandidateRequest = { iceCandidate, _ ->
                signalingClient.sendCommand(
                    SignalingCommand.ICE,
                    "${iceCandidate.sdpMid}$ICE_SEPARATOR" + "${iceCandidate.sdpMLineIndex}$ICE_SEPARATOR${iceCandidate.sdp}",
                    serialReceiver,
                )
            },
            onVideoTrack = { rtpTransceiver ->
                val track = rtpTransceiver?.receiver?.track() ?: return@makePeerConnection
                if (track.kind() == MediaStreamTrack.VIDEO_TRACK_KIND) {
                    val videoTrack = track as VideoTrack
                    sessionManagerScope.launch {
                        _remoteVideoSinkFlow.emit(videoTrack)
                    }
                }
            },
        )
    }

    fun initializeSignalingCommandFlow(peerConnection: StreamPeerConnection) {
        sessionManagerScope.launch {
            signalingClient.signalingCommandFlow.collect { commandToValue ->
                when (commandToValue.first) {
                    SignalingCommand.OFFER -> handleOffer(commandToValue.second)
                    SignalingCommand.ANSWER ->
                        handleAnswer(
                            commandToValue.second,
                            peerConnection,
                        )

                    SignalingCommand.ICE -> handleIce(commandToValue.second, peerConnection)
                    else -> Unit
                }
            }
        }
    }

    override fun enableMicrophone(enabled: Boolean) {
        audioManager?.isMicrophoneMute = !enabled
    }

    override fun disconnect() {
        // dispose audio & video tracks.
        remoteVideoSinkFlow.replayCache.forEach { videoTrack ->
            videoTrack.dispose()
        }
        localAudioTrack.dispose()

        // dispose audio handler and video capturer.
        audioHandler.stop()
//        videoCapturer.stopCapture()
//        videoCapturer.dispose()

        // dispose signaling clients and socket.
        signalingClient.dispose()
    }

    private suspend fun sendOffer(
        peerConnection: StreamPeerConnection,
        serialReceiver: String,
    ) {
        val offer = peerConnection.createOffer().getOrThrow()
        val result = peerConnection.setLocalDescription(offer)
        result.onSuccess {
            signalingClient.sendCommand(SignalingCommand.OFFER, offer.description, serialReceiver)
        }
        println { "[SDP] send offer: ${offer.stringify()}" }
    }

    private suspend fun sendAnswer(
        peerConnection: StreamPeerConnection,
        serialReceiver: String,
    ) {
        peerConnection.setRemoteDescription(
            SessionDescription(SessionDescription.Type.OFFER, offer),
        )
        val answer = peerConnection.createAnswer().getOrThrow()
        val result = peerConnection.setLocalDescription(answer)
        result.onSuccess {
            signalingClient.sendCommand(SignalingCommand.ANSWER, answer.description, serialReceiver)
        }
        println { "[SDP] send answer: ${answer.stringify()}" }
    }

    private fun handleOffer(sdp: String) {
        println { "[SDP] handle offer: $sdp" }
        offer = sdp
    }

    private suspend fun handleAnswer(
        sdp: String,
        peerConnection: StreamPeerConnection,
    ) {
        println { "[SDP] handle answer: $sdp" }
        peerConnection.setRemoteDescription(
            SessionDescription(SessionDescription.Type.ANSWER, sdp),
        )
    }

    private suspend fun handleIce(
        iceMessage: String,
        peerConnection: StreamPeerConnection,
    ) {
        val iceArray = iceMessage.split(ICE_SEPARATOR)
        peerConnection.addIceCandidate(
            IceCandidate(
                iceArray[0],
                iceArray[1].toInt(),
                iceArray[2],
            ),
        )
    }

    private fun setupAudio() {
        println { "[setupAudio] #p2p; no args" }
        audioHandler.start()
        audioManager?.mode = AudioManager.MODE_IN_COMMUNICATION
        val devices = audioManager?.availableCommunicationDevices ?: return
        val deviceType = AudioDeviceInfo.TYPE_BUILTIN_SPEAKER

        val device = devices.firstOrNull { it.type == deviceType } ?: return

        val isCommunicationDeviceSet = audioManager?.setCommunicationDevice(device)
        println { "[setupAudio] #p2p; isCommunicationDeviceSet: $isCommunicationDeviceSet" }
    }
}
