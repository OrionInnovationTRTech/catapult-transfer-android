package com.orioninc.tr.tech.catapult.models

import org.webrtc.*

open class PeerConnectionAdapter : PeerConnection.Observer {

    override fun onSignalingChange(pc: PeerConnection.SignalingState?) {
        // Triggered when the SignalingState changes.
    }

    override fun onIceConnectionChange(pc: PeerConnection.IceConnectionState?) {
        // Triggered when the IceConnectionState changes.
    }

    override fun onIceConnectionReceivingChange(pc: Boolean) {
        //
    }

    override fun onIceGatheringChange(pc: PeerConnection.IceGatheringState?) {
        // Triggered when the IceGatheringState changes.
    }

    override fun onIceCandidate(pc: IceCandidate?) {
        // Triggered when a new ICE candidate has been found.
    }

    override fun onIceCandidatesRemoved(pc: Array<out IceCandidate>?) {
        //
    }

    override fun onAddStream(pc: MediaStream?) {
        // Triggered when media is received on a new stream from remote peer.
    }

    override fun onRemoveStream(pc: MediaStream?) {
        // Triggered when a remote peer close a stream.
    }

    override fun onDataChannel(pc: DataChannel?) {
        // Triggered when a remote peer opens a DataChannel.
    }

    override fun onRenegotiationNeeded() {
        // Triggered when renegotiation is necessary.
    }

    override fun onAddTrack(pc: RtpReceiver?, p1: Array<out MediaStream>?) {
        //
    }
}
