package com.poc.webrtc.grpc.common.audio

import org.webrtc.MediaConstraints

object AudioMediaConstraints {
    fun buildAudioConstraints(): MediaConstraints {
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
