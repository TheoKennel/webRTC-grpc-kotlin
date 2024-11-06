package com.poc.webrtc.grpc.common.audio

internal class AudioSwitch(
    private val audioFocusHandler: AudioFocusHandler,
) {
    internal enum class State {
        STARTED,
        ACTIVATED,
        STOPPED,
    }

    private var state: State = State.STOPPED

    /**
     * Starts listening for audio device changes and calls the [listener] upon each change.
     * **Note:** When audio device listening is no longer needed, [AudioSwitch.stop] should be
     * called in order to prevent a memory leak.
     */
    fun start() {
        println("[start] state: $state")
        when (state) {
            State.STOPPED -> {
                state = State.STARTED
                activate()
            }
            else -> {}
        }
    }

    /**
     * Stops listening for audio device changes if [AudioSwitch.start] has already been
     * invoked. [AudioSwitch.deactivate] will also get called if a device has been activated
     * with [AudioSwitch.activate].
     */
    fun stop() {
        println { "[stop] state: $state" }
        when (state) {
            State.ACTIVATED -> {
                deactivate()
                state = State.STOPPED
            }

            State.STARTED -> {
                state = State.STOPPED
            }

            State.STOPPED -> {}
        }
    }

    /**
     * Performs audio routing and unmuting on the selected device from
     * [AudioSwitch.selectDevice]. Audio focus is also acquired for the client application.
     * **Note:** [AudioSwitch.deactivate] should be invoked to restore the prior audio
     * state.
     */
    private fun activate() {
        println { "[activate] state: $state" }
        when (state) {
            State.STARTED -> {
                audioFocusHandler.cacheAudioState()

                // Always set mute to false for WebRTC
                audioFocusHandler.mute(false)
                audioFocusHandler.setAudioFocus()
                state = State.ACTIVATED
            }
            State.ACTIVATED -> {}
            State.STOPPED -> throw IllegalStateException()
        }
    }

    /**
     * Restores the audio state prior to calling [AudioSwitch.activate] and removes
     * audio focus from the client application.
     */
    private fun deactivate() {
        println { "[deactivate] state: $state" }
        when (state) {
            State.ACTIVATED -> {
                // Restore stored audio state
                audioFocusHandler.restoreAudioState()
                state = State.STARTED
            }

            State.STARTED, State.STOPPED -> {}
        }
    }
}
