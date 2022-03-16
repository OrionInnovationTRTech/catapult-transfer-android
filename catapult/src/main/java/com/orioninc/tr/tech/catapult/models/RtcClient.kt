package com.orioninc.tr.tech.catapult.models
import android.content.Context
import android.util.Log
import org.webrtc.*
import org.webrtc.MediaConstraints

class RtcClient{
    private var peerConnectionFactory: PeerConnectionFactory? = null
    var mediaStream: MediaStream? = null
     private var peerConnection : PeerConnection? = null
    private var mDataChannel : DataChannel?=null
    private var mDataChannelObserver : RtcDataChannel?=null
     private val TAG = this.javaClass.name
    private var iceServers = ArrayList<PeerConnection.IceServer>()
    private var call: RtcCallBack? = null
    private var context: Context? = null

    private var peerConnectionAdapter = PeerConnectionAdapter()
    private var sdpAdapter = SdpAdapter()

    var peerConnectionMap: HashMap<String, PeerConnection?>? = null

    private var pcConstraints = MediaConstraints()

    constructor(context: Context) {

        this.context = context
        init(context)
        createOffer("test")
        createAnswer("test")

    }
    // Initialization
    private fun init(app: Context) {
        // create PeerConnectionFactory
        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions
            .builder(app)
            .createInitializationOptions())

        val options = PeerConnectionFactory.Options()

        pcConstraints.mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
        pcConstraints.mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
        pcConstraints.optional.add(MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"))

        peerConnectionFactory = PeerConnectionFactory.builder()
            .setOptions(options)
            .createPeerConnectionFactory()

    }
    fun setCallBack(call: RtcCallBack) {
        this.call = call
    }

    fun call() {
        iceServers.add(PeerConnection.IceServer.builder("stun:stun4.l.google.com:19302").createIceServer())
        println("RtcClient.call")
    }

    fun createOffer(id: String) {
        var peerConnection = getOrCreatePeerConnection(id)
        peerConnection?.createOffer(object : SdpAdapter() {
            override fun onCreateSuccess(sessionDescription: SessionDescription?) {
                super.onCreateSuccess(sessionDescription)
                peerConnection?.setRemoteDescription(SdpAdapter(), sessionDescription)

                call?.offer(sessionDescription, id)
                println("offer create")
            }

            override fun onCreateFailure(pc: String?) {
                super.onCreateFailure(pc)
                print("offer fail")
            }
        }, MediaConstraints())
    }

     fun createAnswer(id: String) {
         // Create answer object
         var peerConnection = getOrCreatePeerConnection(id)
         peerConnection?.createAnswer(object : SdpAdapter() {
             override fun onCreateSuccess(sdp: SessionDescription?) {
                 super.onCreateSuccess(sdp)
                 peerConnection?.setLocalDescription(SdpAdapter(), sdp)
                 call?.answer(sdp, id)
                 println("answer create")
             }

             override fun onCreateFailure(pc: String?) {
                 super.onCreateFailure(pc)
                 println("answer fail"+ pc)
             }

             override fun onSetSuccess() {
                 super.onSetSuccess()
                 println("answer set success")
             }

             override fun onSetFailure(pc: String?) {
                 super.onSetFailure(pc)
                 println("answer set success"+pc)
             }
         }, MediaConstraints())
     }

     @Synchronized
    private fun getOrCreatePeerConnection(socketId: String): PeerConnection? {
        if (peerConnectionMap == null) {
            peerConnectionMap = HashMap()
        }
        peerConnection = peerConnectionMap?.get(socketId)
        if (peerConnection != null) {
            return peerConnection
        }

        var config = PeerConnection.RTCConfiguration(iceServers)

        peerConnection = peerConnectionFactory!!.createPeerConnection(config, pcConstraints, object : PeerConnectionAdapter() {

            override fun onIceCandidate(pc: IceCandidate?) {
                super.onIceCandidate(pc)
                call?.iceData(pc, socketId)
                println("1")
            }

            override fun onAddStream(pc: MediaStream?) {
                super.onAddStream(pc)

                call?.stream(pc, socketId)
            }

            override fun onIceConnectionChange(pc: PeerConnection.IceConnectionState?) {
                super.onIceConnectionChange(pc)
                if (pc == PeerConnection.IceConnectionState.DISCONNECTED) {
                    peerConnectionMap!!.remove(socketId)
                }
            }

            override fun onDataChannel(pc: DataChannel?) {
                super.onDataChannel(pc)
                mDataChannel = pc
                mDataChannel = peerConnection?.createDataChannel(TAG,DataChannel.Init())

                mDataChannel?.registerObserver(mDataChannelObserver)
            }
        })

        peerConnectionMap!![socketId] = peerConnection
        return peerConnection

    }
}
