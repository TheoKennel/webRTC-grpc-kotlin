package com.poc.webrtc.grpc.common.peer.common.utils

import com.poc.webrtc.grpc.common.peer.common.StreamPeerType

fun StreamPeerType.stringify() =
    when (this) {
        StreamPeerType.PUBLISHER -> "publisher"
        StreamPeerType.SUBSCRIBER -> "subscriber"
    }
