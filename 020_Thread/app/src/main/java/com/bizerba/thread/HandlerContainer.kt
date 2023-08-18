package com.bizerba.thread

import android.os.Handler

class HandlerContainer {
    private val TAG:String = "HandlerContainer"
    //handler
    private var handlerOuter: Handler? = null
    fun setHandler(handler: Handler){
        handlerOuter = handler
    }
    fun run(){
        Thread.sleep(1000)

        handlerOuter?.obtainMessage(MESSAGE_NEW_LABEL_ARRIVED, "xml path")?.sendToTarget()
    }
}