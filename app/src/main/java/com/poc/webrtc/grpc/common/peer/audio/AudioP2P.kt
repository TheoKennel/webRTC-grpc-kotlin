package com.poc.webrtc.grpc.common.peer.audio

import org.webrtc.AudioSource
import org.webrtc.AudioTrack
import org.webrtc.MediaConstraints
import org.webrtc.PeerConnectionFactory
import org.webrtc.audio.JavaAudioDeviceModule

object AudioP2P {
    internal fun setupErrorCallback(): JavaAudioDeviceModule.AudioRecordErrorCallback =
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
        }

    internal fun setupTrackErrorCallback(): JavaAudioDeviceModule.AudioTrackErrorCallback =
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
        }

    internal fun setupStateCallback(): JavaAudioDeviceModule.AudioRecordStateCallback =
        object :
            JavaAudioDeviceModule.AudioRecordStateCallback {
            override fun onWebRtcAudioRecordStart() {
                println { "[onWebRtcAudioRecordStart] no args" }
            }

            override fun onWebRtcAudioRecordStop() {
                println { "[onWebRtcAudioRecordStop] no args" }
            }
        }

    internal fun setupTrackStateCallback(): JavaAudioDeviceModule.AudioTrackStateCallback =
        object :
            JavaAudioDeviceModule.AudioTrackStateCallback {
            override fun onWebRtcAudioTrackStart() {
                println { "[onWebRtcAudioTrackStart] no args" }
            }

            override fun onWebRtcAudioTrackStop() {
                println { "[onWebRtcAudioTrackStop] no args" }
            }
        }

    /**
     * Builds an [AudioSource] from the [factory] that can be used for audio sharing.
     *
     * @param constraints The constraints used to change the way the audio behaves.
     * @return [AudioSource] that can be used to build tracks.
     */
    internal fun makeAudioSource(
        factory: PeerConnectionFactory,
        constraints: MediaConstraints = MediaConstraints(),
    ): AudioSource = factory.createAudioSource(constraints)

    /**
     * Builds an [AudioTrack] from the [factory] that can be used for regular video share (camera)
     * or screen sharing.
     *
     * @param source The [AudioSource] used for the track.
     * @param trackId The unique ID for this track.
     * @return [AudioTrack] That represents an audio feed.
     */
    internal fun makeAudioTrack(
        factory: PeerConnectionFactory,
        source: AudioSource,
        trackId: String,
    ): AudioTrack = factory.createAudioTrack(trackId, source)

    internal fun buildAudioConstraints(): MediaConstraints {
        val mediaConstraints = MediaConstraints()
        val items =
            listOf(
                MediaConstraints.KeyValuePair(
                    "googEchoCancellation",
                    true.toString(),
                ),
                MediaConstraints.KeyValuePair(
                    "googAutoGainControl",
                    true.toString(),
                ),
                MediaConstraints.KeyValuePair(
                    "googHighpassFilter",
                    true.toString(),
                ),
                MediaConstraints.KeyValuePair(
                    "googNoiseSuppression",
                    true.toString(),
                ),
                MediaConstraints.KeyValuePair(
                    "googTypingNoiseDetection",
                    true.toString(),
                ),
            )

        return mediaConstraints.apply {
            with(optional) {
                add(MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"))
                addAll(items)
            }
        }
    }
}
