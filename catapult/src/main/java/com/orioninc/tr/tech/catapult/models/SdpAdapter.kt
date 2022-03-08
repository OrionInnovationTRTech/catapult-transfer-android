package com.orioninc.tr.tech.catapult.models

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

open class SdpAdapter : SdpObserver {
    override fun onCreateSuccess(pc: SessionDescription?) {
        TODO("Not yet implemented")
    }

    override fun onSetSuccess() {
        TODO("Not yet implemented")
    }

    override fun onCreateFailure(pc: String?) {
        TODO("Not yet implemented")
    }

    override fun onSetFailure(pc: String?) {
        TODO("Not yet implemented")
    }
}
