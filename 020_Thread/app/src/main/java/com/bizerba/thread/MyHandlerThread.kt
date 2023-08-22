package com.bizerba.thread

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

class MyHandlerThread(name:String) : HandlerThread(name){
    var handler:Handler? = null
    private var TAG:String = "MyHandlerThread"

    override fun run() {
        Log.v(TAG,"MyHandlerThread::run() +run........................")
        super.run()
        Log.v(TAG,"MyHandlerThread::run() -run........................")
    }

    fun prepareHandler():Handler?{
        Log.v(TAG,"MyHandlerThread::prepareHandler().")
        handler = object : Handler(looper){
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    else -> {
                        Log.v("MyHandlerThread","msg:${msg.what},obj:${msg.obj}")
                        //send msg back to MainActivity - failed, because here do not have handler of MainActivity
                        val replyMessenger: Messenger = msg.replyTo
                        val messageV1 = Message.obtain()
                        messageV1.what = MESSAGE_NEW_LABEL_ARRIVED
                        messageV1.obj = "msg from MyHandlerThread v1"
                        replyMessenger.send(messageV1)
                        //failed received in MainActivity
                        val mainHandler = Handler(Looper.getMainLooper())
                        val messageV2 = Message.obtain()
                        messageV2.what = MESSAGE_NEW_LABEL_ARRIVED
                        messageV2.obj = "msg from MyHandlerThread v2"
                        mainHandler.sendMessage(messageV2)
                    }
                }
            }
        }
        return handler
    }

    override fun onLooperPrepared(){
        Log.v(TAG,"MyHandlerThread::onLooperPrepared() +onLooperPrepared........................")
        super.onLooperPrepared()
        Log.v(TAG,"MyHandlerThread::onLooperPrepared() -onLooperPrepared........................")
//        handler = object : Handler(looper){
//            override fun handleMessage(msg: Message) {
//                when (msg.what) {
//                    else -> {
//                        Log.v("MyHandlerThread","msg:${msg.what},obj:${msg.obj}")
//                        //send msg back to MainActivity - failed, because here do not have handler of MainActivity
//                        val replyMessenger: Messenger = msg.replyTo
//                        val messageV1 = Message.obtain()
//                        messageV1.what = MESSAGE_NEW_LABEL_ARRIVED
//                        messageV1.obj = "msg from MyHandlerThread v1"
//                        replyMessenger.send(messageV1)
//                        //failed received in MainActivity
//                        val mainHandler = Handler(Looper.getMainLooper())
//                        val messageV2 = Message.obtain()
//                        messageV2.what = MESSAGE_NEW_LABEL_ARRIVED
//                        messageV2.obj = "msg from MyHandlerThread v2"
//                        mainHandler.sendMessage(messageV2)
//                    }
//                }
//            }
//        }
    }
}