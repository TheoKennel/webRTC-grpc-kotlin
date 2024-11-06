package com.poc.webrtc.grpc.common.peer.common

import android.content.Context
import android.os.Build
import com.poc.webrtc.grpc.common.peer.audio.AudioP2P
import com.poc.webrtc.grpc.common.peer.video.VideoP2PUtils
import kotlinx.coroutines.CoroutineScope
import org.webrtc.IceCandidate
import org.webrtc.Logging
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import org.webrtc.RtpTransceiver
import org.webrtc.audio.JavaAudioDeviceModule

class StreamPeerConnectionFactory(
    private val context: Context,
) {
    val rtcConfig =
        PeerConnection.RTCConfiguration(emptyList()).apply {
            sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN
        }

    /**
     * Factory that builds all the connections based on the extensive configuration provided under
     * the hood.
     */
    val factory: PeerConnectionFactory by lazy {
        PeerConnectionFactory.initialize(
            PeerConnectionFactory.InitializationOptions
                .builder(context)
                .setInjectableLogger({ message, severity, label ->
                    setupFactoryLogsOptions(message, severity, label)
                }, Logging.Severity.LS_VERBOSE)
                .createInitializationOptions(),
        )

        PeerConnectionFactory
            .builder()
            .setVideoDecoderFactory(VideoP2PUtils.videoDecoderFactory)
            .setVideoEncoderFactory(VideoP2PUtils.videoEncoderFactory)
            .setAudioDeviceModule(
                JavaAudioDeviceModule
                    .builder(context)
                    .setUseHardwareAcousticEchoCanceler(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    .setUseHardwareNoiseSuppressor(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    .setAudioRecordErrorCallback(AudioP2P.setupErrorCallback())
                    .setAudioTrackErrorCallback(AudioP2P.setupTrackErrorCallback())
                    .setAudioRecordStateCallback(AudioP2P.setupStateCallback())
                    .setAudioTrackStateCallback(AudioP2P.setupTrackStateCallback())
                    .createAudioDeviceModule()
                    .also {
                        it.setMicrophoneMute(false)
                        it.setSpeakerMute(false)
                    },
            ).createPeerConnectionFactory()
    }

    private fun setupFactoryLogsOptions(
        message: String,
        severity: Logging.Severity,
        label: String,
    ) {
        when (severity) {
            Logging.Severity.LS_VERBOSE -> {
                println { "[onLogMessage] label: $label, message: $message" }
            }

            Logging.Severity.LS_INFO -> {
                println { "[onLogMessage] label: $label, message: $message" }
            }

            Logging.Severity.LS_WARNING -> {
                println { "[onLogMessage] label: $label, message: $message" }
            }

            Logging.Severity.LS_ERROR -> {
                println { "[onLogMessage] label: $label, message: $message" }
            }

            Logging.Severity.LS_NONE -> {
                println { "[onLogMessage] label: $label, message: $message" }
            }
        }
    }

    /**
     * Builds a [StreamPeerConnection] that wraps the WebRTC [PeerConnection] and exposes several
     * helpful handlers.
     *
     * @param coroutineScope Scope used for asynchronous operations.
     * @param configuration The [PeerConnection.RTCConfiguration] used to set up the connection.
     * @param type The type of connection, either a subscriber of a publisher.
     * @param mediaConstraints Constraints used for audio and video tracks in the connection.
     * @param onStreamAdded Handler when a new [MediaStream] gets added.
     * @param onNegotiationNeeded Handler when there's a new negotiation.
     * @param onIceCandidateRequest Handler whenever we receive [IceCandidate]s.
     * @return [StreamPeerConnection] That's fully set up and can be observed and used to send and
     * receive tracks.
     */

    fun makePeerConnection(
        coroutineScope: CoroutineScope,
        configuration: PeerConnection.RTCConfiguration,
        type: StreamPeerType,
        mediaConstraints: MediaConstraints,
        onStreamAdded: ((MediaStream) -> Unit)? = null,
        onNegotiationNeeded: ((StreamPeerConnection, StreamPeerType) -> Unit)? = null,
        onIceCandidateRequest: ((IceCandidate, StreamPeerType) -> Unit)? = null,
        onVideoTrack: ((RtpTransceiver?) -> Unit)? = null,
    ): StreamPeerConnection {
        val peerConnection =
            StreamPeerConnection(
                coroutineScope = coroutineScope,
                type = type,
                mediaConstraints = mediaConstraints,
                onStreamAdded = onStreamAdded,
                onNegotiationNeeded = onNegotiationNeeded,
                onIceCandidate = onIceCandidateRequest,
                onVideoTrack = onVideoTrack,
            )
        val connection =
            makePeerConnectionInternal(
                configuration = configuration,
                observer = peerConnection,
            )
        return peerConnection.apply { initialize(connection) }
    }

    /**
     * Builds a [PeerConnection] internally that connects to the server and is able to send and
     * receive tracks.
     *
     * @param configuration The [PeerConnection.RTCConfiguration] used to set up the connection.
     * @param observer Handler used to observe different states of the connection.
     * @return [PeerConnection] that's fully set up.
     */
    private fun makePeerConnectionInternal(
        configuration: PeerConnection.RTCConfiguration,
        observer: PeerConnection.Observer?,
    ): PeerConnection =
        requireNotNull(
            factory.createPeerConnection(
                configuration,
                observer,
            ),
        )
}
