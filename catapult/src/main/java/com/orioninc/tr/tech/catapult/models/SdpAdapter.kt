package com.orioninc.tr.tech.catapult.models

import android.app.Activity
import android.content.Context
import androidx.activity.result.contract.ActivityResultContracts
import com.orioninc.tr.tech.catapult.MainActivity
import org.webrtc.EglBase
import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

open class SdpAdapter : SdpObserver {
    override fun onCreateSuccess(pc: SessionDescription?) {
        print("on create success")
    }

    override fun onSetSuccess() {
        print("on set success")
    }

    override fun onCreateFailure(pc: String?) {
        print("on create fail")
    }

    override fun onSetFailure(pc: String?) {
        print("on set fail")
    }
}
