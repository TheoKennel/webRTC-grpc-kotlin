package com.poc.webrtc.grpc.common.peer.common.sessions

import com.poc.webrtc.grpc.common.peer.common.SignalingClient
import com.poc.webrtc.grpc.common.peer.common.StreamPeerConnectionFactory
import kotlinx.coroutines.flow.SharedFlow
import org.webrtc.VideoTrack

interface WebRtcSessionManager {
    val signalingClient: SignalingClient

    val peerConnectionFactory: StreamPeerConnectionFactory

    val remoteVideoSinkFlow: SharedFlow<VideoTrack>

    fun enableMicrophone(enabled: Boolean)

    fun disconnect()
}
