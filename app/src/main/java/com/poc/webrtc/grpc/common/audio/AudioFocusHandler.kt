package com.poc.webrtc.grpc.common.audio

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager

internal class AudioFocusHandler(
    private val context: Context,
    private val audioFocusRequestWrapper: AudioFocusRequestWrapper = AudioFocusRequestWrapper(),
) {
    private var audioRequest: AudioFocusRequest? = null
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val audioFocusChangeListener =
        AudioManager.OnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> {
                    audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_UNMUTE,
                        0,
                    )
                }

                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        0,
                    )
                }

                AudioManager.AUDIOFOCUS_LOSS -> {
                    audioManager.adjustStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_MUTE,
                        0,
                    )
                }
            }
        }

    fun setAudioFocus() {
        audioRequest = audioFocusRequestWrapper.buildRequest(audioFocusChangeListener)
        audioRequest?.let {
            audioManager.requestAudioFocus(it)
        }

        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
    }

    fun releaseAudioFocus() {
        audioRequest?.let {
            audioManager.abandonAudioFocusRequest(it)
        }
    }
}
