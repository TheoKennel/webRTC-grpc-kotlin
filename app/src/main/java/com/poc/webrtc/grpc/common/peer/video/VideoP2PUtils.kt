package com.poc.webrtc.grpc.common.peer.video

import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.EglBase
import org.webrtc.HardwareVideoEncoderFactory
import org.webrtc.PeerConnectionFactory
import org.webrtc.SimulcastVideoEncoderFactory
import org.webrtc.SoftwareVideoEncoderFactory
import org.webrtc.VideoSource
import org.webrtc.VideoTrack

object VideoP2PUtils {
    val videoDecoderFactory by lazy {
        DefaultVideoDecoderFactory(
            eglBaseContext,
        )
    }

    val videoEncoderFactory by lazy {
        val hardwareEncoder = HardwareVideoEncoderFactory(eglBaseContext, true, true)
        SimulcastVideoEncoderFactory(hardwareEncoder, SoftwareVideoEncoderFactory())
    }

    private val eglBaseContext: EglBase.Context by lazy {
        EglBase.create().eglBaseContext
    }

    internal fun makeVideoSource(
        factory: PeerConnectionFactory,
        isScreencast: Boolean,
    ): VideoSource = factory.createVideoSource(isScreencast)

    internal fun makeVideoTrack(
        factory: PeerConnectionFactory,
        source: VideoSource,
        trackId: String,
    ): VideoTrack = factory.createVideoTrack(trackId, source)
}
