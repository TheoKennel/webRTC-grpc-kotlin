package com.poc.webrtc.grpc.common.peer.common

import com.poc.webrtc.grpc.common.peer.common.utils.stringify
import io.getstream.webrtc.android.ktx.addRtcIceCandidate
import io.getstream.webrtc.android.ktx.createSessionDescription
import io.getstream.webrtc.android.ktx.stringify
import io.getstream.webrtc.android.ktx.suspendSdpObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.webrtc.CandidatePairChangeEvent
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.IceCandidateErrorEvent
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.MediaStreamTrack
import org.webrtc.PeerConnection
import org.webrtc.RTCStatsReport
import org.webrtc.RtpReceiver
import org.webrtc.RtpTransceiver
import org.webrtc.SessionDescription

class StreamPeerConnection(
    private val coroutineScope: CoroutineScope,
    private val type: StreamPeerType,
    private val mediaConstraints: MediaConstraints,
    private val onStreamAdded: ((MediaStream) -> Unit)?,
    private val onNegotiationNeeded: ((StreamPeerConnection, StreamPeerType) -> Unit)?,
    private val onIceCandidate: ((IceCandidate, StreamPeerType) -> Unit)?,
) : PeerConnection.Observer {
    private lateinit var connection: PeerConnection

    private val typeTag = type.stringify()

    private val pendingIceMutex = Mutex()
    private val pendingIceCandidates = mutableListOf<IceCandidate>()

    /**
     * Used to manage the stats observation lifecycle.
     */
    private var statsJob: Job? = null

    /**
     * Contains stats events for observation.
     */
    private val statsFlow: MutableStateFlow<RTCStatsReport?> = MutableStateFlow(null)

    init {
        println { "<init> #p2p; #$typeTag; mediaConstraints: $mediaConstraints" }
    }

    fun initialize(peerConnection: PeerConnection) {
        println { "[initialize] #p2p; #$typeTag; peerConnection: $peerConnection" }
        this.connection = peerConnection
    }

    /**
     * Used to create an offer whenever there's a negotiation that we need to process on the
     * publisher side.
     *
     * @return [Result] wrapper of the [SessionDescription] for the publisher.
     */
    suspend fun createOffer(): Result<SessionDescription> {
        println { "[createOffer] #p2p; #$typeTag; no args" }
        return createSessionDescription { connection.createOffer(it, mediaConstraints) }
    }

    /**
     * Used to create an answer whenever there's a subscriber offer.
     *
     * @return [Result] wrapper of the [SessionDescription] for the subscriber.
     */
    suspend fun createAnswer(): Result<SessionDescription> {
        println { "[createAnswer] #p2p; #$typeTag; no args" }
        return createSessionDescription { connection.createAnswer(it, mediaConstraints) }
    }

    /**
     * Used to set up the SDP on underlying connections and to add [pendingIceCandidates] to the
     * connection for listening.
     *
     * @param sessionDescription That contains the remote SDP.
     * @return An empty [Result], if the operation has been successful or not.
     */
    suspend fun setRemoteDescription(sessionDescription: SessionDescription): Result<Unit> {
        println {
            "[setRemoteDescription] #p2p; #$typeTag; " +
                "answerSdp: ${sessionDescription.stringify()}"
        }
        return suspendSdpObserver {
            connection.setRemoteDescription(
                it,
                SessionDescription(
                    sessionDescription.type,
                    sessionDescription.description.mungeCodecs(),
                ),
            )
        }.also {
            pendingIceMutex.withLock {
                pendingIceCandidates.forEach { iceCandidate ->
                    println {
                        "[setRemoteDescription] #p2p; #subscriber; " +
                            "pendingRtcIceCandidate: $iceCandidate"
                    }
                    connection.addRtcIceCandidate(iceCandidate)
                }
                pendingIceCandidates.clear()
            }
        }
    }

    /**
     * Sets the local description for a connection either for the subscriber or publisher based on
     * the flow.
     *
     * @param sessionDescription That contains the subscriber or publisher SDP.
     * @return An empty [Result], if the operation has been successful or not.
     */
    suspend fun setLocalDescription(sessionDescription: SessionDescription): Result<Unit> {
        val sdp =
            SessionDescription(
                sessionDescription.type,
                sessionDescription.description.mungeCodecs(),
            )
        println {
            "[setLocalDescription]; #$typeTag; " +
                "offerSdp: ${sessionDescription.stringify()}"
        }
        return suspendSdpObserver { connection.setLocalDescription(it, sdp) }
    }

    /**
     * Adds an [IceCandidate] to the underlying [connection] if it's already been set up, or stores
     * it for later consumption.
     *
     * @param iceCandidate To process and add to the connection.
     * @return An empty [Result], if the operation has been successful or not.
     */
    suspend fun addIceCandidate(iceCandidate: IceCandidate): Result<Unit> {
        if (connection.remoteDescription == null) {
            println {
                "[addIceCandidate] #p2p; #$typeTag;" +
                    " postponed (no remoteDescription): $iceCandidate"
            }
            pendingIceMutex.withLock {
                pendingIceCandidates.add(iceCandidate)
            }
            return Result.failure(RuntimeException("RemoteDescription is not set"))
        }
        return connection.addRtcIceCandidate(iceCandidate).also {
            println { "[addIceCandidate] #p2p; #$typeTag; completed: $it" }
        }
    }

    /**
     * Triggered whenever there's a new [RtcIceCandidate] for the call. Used to update our tracks
     * and subscriptions.
     *
     * @param candidate The new candidate.
     */
    override fun onIceCandidate(candidate: IceCandidate?) {
        println { "[onIceCandidate] #p2p; #$typeTag; candidate: $candidate" }
        if (candidate == null) return

        onIceCandidate?.invoke(candidate, type)
    }

    /**
     * Triggered whenever there's a new [MediaStream] that was added to the connection.
     *
     * @param stream The stream that contains audio or video.
     */
    override fun onAddStream(stream: MediaStream?) {
        println { "[onAddStream] #p2p; #$typeTag; stream: $stream" }
        if (stream != null) {
            onStreamAdded?.invoke(stream)
        }
    }

    /**
     * Triggered whenever there's a new [MediaStream] or [MediaStreamTrack] that's been added
     * to the call. It contains all audio and video tracks for a given session.
     *
     * @param receiver The receiver of tracks.
     * @param mediaStreams The streams that were added containing their appropriate tracks.
     */
    override fun onAddTrack(
        receiver: RtpReceiver?,
        mediaStreams: Array<out MediaStream>?,
    ) {
        println { "[onAddTrack] #p2p; #$typeTag; receiver: $receiver, mediaStreams: $mediaStreams" }
        mediaStreams?.forEach { mediaStream ->
            println { "[onAddTrack] #p2p; #$typeTag; mediaStream: $mediaStream" }
            mediaStream.audioTracks?.forEach { remoteAudioTrack ->
                remoteAudioTrack.setEnabled(true)
            }
            onStreamAdded?.invoke(mediaStream)
        }
    }

    /**
     * Triggered whenever there's a new negotiation needed for the active [PeerConnection].
     */
    override fun onRenegotiationNeeded() {
        println { "[onRenegotiationNeeded] #p2p; #$typeTag; no args" }
        onNegotiationNeeded?.invoke(this, type)
    }

    override fun onRemoveStream(stream: MediaStream?) {}

    /**
     * Triggered when the connection state changes.  Used to start and stop the stats observing.
     *
     * @param newState The new state of the [PeerConnection].
     */
    override fun onIceConnectionChange(newState: PeerConnection.IceConnectionState?) {
        println { "[onIceConnectionChange] #p2p; #$typeTag; newState: $newState" }
        when (newState) {
            PeerConnection.IceConnectionState.CLOSED,
            PeerConnection.IceConnectionState.FAILED,
            PeerConnection.IceConnectionState.DISCONNECTED,
            -> statsJob?.cancel()

            PeerConnection.IceConnectionState.CONNECTED -> statsJob = observeStats()
            else -> Unit
        }
    }

    /**
     * Observes the local connection stats and emits it to [statsFlow] that users can consume.
     */
    private fun observeStats() =
        coroutineScope.launch {
            while (isActive) {
                delay(10_000L)
                connection.getStats {
                    println { "[observeStats] #p2p; #$typeTag; stats: $it" }
                    statsFlow.value = it
                }
            }
        }

    override fun onTrack(transceiver: RtpTransceiver?) {
        println { "[onTrack] #p2p; #$typeTag; transceiver: $transceiver" }
    }

    /**
     * Domain - [PeerConnection] and [PeerConnection.Observer] related callbacks.
     */
    override fun onRemoveTrack(receiver: RtpReceiver?) {
        println { "[onRemoveTrack] #p2p; #$typeTag; receiver: $receiver" }
    }

    override fun onSignalingChange(newState: PeerConnection.SignalingState?) {
        println { "[onSignalingChange] #p2p; #$typeTag; newState: $newState" }
    }

    override fun onIceConnectionReceivingChange(receiving: Boolean) {
        println { "[onIceConnectionReceivingChange] #p2p; #$typeTag; receiving: $receiving" }
    }

    override fun onIceGatheringChange(newState: PeerConnection.IceGatheringState?) {
        println { "[onIceGatheringChange] #p2p; #$typeTag; newState: $newState" }
    }

    override fun onIceCandidatesRemoved(iceCandidates: Array<out org.webrtc.IceCandidate>?) {
        println { "[onIceCandidatesRemoved] #p2p; #$typeTag; iceCandidates: $iceCandidates" }
    }

    override fun onIceCandidateError(event: IceCandidateErrorEvent?) {
        println { "[onIceCandidateError] #p2p; #$typeTag; event: ${event?.stringify()}" }
    }

    override fun onConnectionChange(newState: PeerConnection.PeerConnectionState?) {
        println { "[onConnectionChange] #p2p; #$typeTag; newState: $newState" }
    }

    override fun onSelectedCandidatePairChanged(event: CandidatePairChangeEvent?) {
        println { "[onSelectedCandidatePairChanged] #p2p; #$typeTag; event: $event" }
    }

    override fun onDataChannel(channel: DataChannel?): Unit = Unit

    private fun String.mungeCodecs(): String =
        this
            .replace("vp9", "VP9")
            .replace("vp8", "VP8")
            .replace("h264", "H264")
}
