package com.orioninc.tr.tech.catapult.models

import org.webrtc.*

interface RtcCallBack {
    fun offer(sessionDescription: SessionDescription?, id: String)
    fun answer(sessionDescription: SessionDescription?, id: String)
    fun iceData(data: IceCandidate?, id: String)
    fun stream(stream: MediaStream?, id: String)
}
