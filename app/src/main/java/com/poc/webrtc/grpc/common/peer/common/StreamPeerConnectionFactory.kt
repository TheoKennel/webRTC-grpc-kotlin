package com.poc.webrtc.grpc.common.peer.common

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import org.webrtc.AudioSource
import org.webrtc.AudioTrack
import org.webrtc.IceCandidate
import org.webrtc.Logging
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
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
    private val factory by lazy {
        PeerConnectionFactory.initialize(
            PeerConnectionFactory.InitializationOptions
                .builder(context)
                .setInjectableLogger({ message, severity, label ->
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

                        else -> {}
                    }
                }, Logging.Severity.LS_VERBOSE)
                .createInitializationOptions(),
        )

        PeerConnectionFactory
            .builder()
            .setAudioDeviceModule(
                JavaAudioDeviceModule
                    .builder(context)
                    .setUseHardwareAcousticEchoCanceler(true)
                    .setUseHardwareNoiseSuppressor(true)
                    .setAudioRecordErrorCallback(
                        object :
                            JavaAudioDeviceModule.AudioRecordErrorCallback {
                            override fun onWebRtcAudioRecordInitError(p0: String?) {
                                println { "[onWebRtcAudioRecordInitError] $p0" }
                            }

                            override fun onWebRtcAudioRecordStartError(
                                p0: JavaAudioDeviceModule.AudioRecordStartErrorCode?,
                                p1: String?,
                            ) {
                                println { "[onWebRtcAudioRecordInitError] $p1" }
                            }

                            override fun onWebRtcAudioRecordError(p0: String?) {
                                println { "[onWebRtcAudioRecordError] $p0" }
                            }
                        },
                    ).setAudioTrackErrorCallback(
                        object :
                            JavaAudioDeviceModule.AudioTrackErrorCallback {
                            override fun onWebRtcAudioTrackInitError(p0: String?) {
                                println { "[onWebRtcAudioTrackInitError] $p0" }
                            }

                            override fun onWebRtcAudioTrackStartError(
                                p0: JavaAudioDeviceModule.AudioTrackStartErrorCode?,
                                p1: String?,
                            ) {
                                println { "[onWebRtcAudioTrackStartError] $p0" }
                            }

                            override fun onWebRtcAudioTrackError(p0: String?) {
                                println { "[onWebRtcAudioTrackError] $p0" }
                            }
                        },
                    ).setAudioRecordStateCallback(
                        object :
                            JavaAudioDeviceModule.AudioRecordStateCallback {
                            override fun onWebRtcAudioRecordStart() {
                                println { "[onWebRtcAudioRecordStart] no args" }
                            }

                            override fun onWebRtcAudioRecordStop() {
                                println { "[onWebRtcAudioRecordStop] no args" }
                            }
                        },
                    ).setAudioTrackStateCallback(
                        object :
                            JavaAudioDeviceModule.AudioTrackStateCallback {
                            override fun onWebRtcAudioTrackStart() {
                                println { "[onWebRtcAudioTrackStart] no args" }
                            }

                            override fun onWebRtcAudioTrackStop() {
                                println { "[onWebRtcAudioTrackStop] no args" }
                            }
                        },
                    ).createAudioDeviceModule()
                    .also {
                        it.setMicrophoneMute(false)
                        it.setSpeakerMute(false)
                    },
            ).createPeerConnectionFactory()
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
    ): StreamPeerConnection {
        val peerConnection =
            StreamPeerConnection(
                coroutineScope = coroutineScope,
                type = type,
                mediaConstraints = mediaConstraints,
                onStreamAdded = onStreamAdded,
                onNegotiationNeeded = onNegotiationNeeded,
                onIceCandidate = onIceCandidateRequest,
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

    /**
     * Builds an [AudioSource] from the [factory] that can be used for audio sharing.
     *
     * @param constraints The constraints used to change the way the audio behaves.
     * @return [AudioSource] that can be used to build tracks.
     */
    fun makeAudioSource(constraints: MediaConstraints = MediaConstraints()): AudioSource = factory.createAudioSource(constraints)

    /**
     * Builds an [AudioTrack] from the [factory] that can be used for regular video share (camera)
     * or screen sharing.
     *
     * @param source The [AudioSource] used for the track.
     * @param trackId The unique ID for this track.
     * @return [AudioTrack] That represents an audio feed.
     */
    fun makeAudioTrack(
        source: AudioSource,
        trackId: String,
    ): AudioTrack = factory.createAudioTrack(trackId, source)
}
