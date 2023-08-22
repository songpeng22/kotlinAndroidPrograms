package com.bizerba.thread

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log


class ThreadContainer {
    private val TAG:String = "ThreadContainer"
    fun run(){
        //new thread
        Log.v(TAG,"\nnew thread:")
        val thread = Thread {
            Log.v(TAG,"Thread ->-> ${Thread.currentThread()} has run.")
        }
        thread.start()

        //extends thread
        Log.v(TAG,"\nextends Thread class:")
        class SimpleThread: Thread() {
            public override fun run() {
                Log.v(TAG,"Thread ->-> override thread has run.")
            }
        }
        val threadSimple = SimpleThread()
        threadSimple.start()

        //Looper Thread
        class LooperThread : Thread() {
            var mHandler: Handler? = null
            private var TAG:String = "MyHandlerThread"
            override fun run() {
                Log.v(TAG,"LooperThread::run() + prepare........................")
                Looper.prepare()
                Log.v(TAG,"LooperThread::run() - prepare........................")
                mHandler = object : Handler(Looper.myLooper()!!) {
                    override fun handleMessage(msg: Message) {
                        // process incoming messages here
                        Log.v(TAG,"LooperThread::handleMessage()........................")
                    }
                }
                Log.v(TAG,"LooperThread::run() + loop.")
                Looper.loop()
                Log.v(TAG,"LooperThread::run() - loop.")
            }
        }
        val looperThread = LooperThread()
        looperThread.start()
        val message = Message.obtain(looperThread.mHandler)
        message.what = MESSAGE_NEW_LABEL_ARRIVED
        message.obj = "msg to HandlerThread V1."
        looperThread.mHandler?.sendMessage(message)

        //new runnable thread
        Log.v(TAG,"\nextends Runnable and run it in thread:")
        class SimpleRunnable: Runnable {
            public override fun run() {
                Log.v(TAG,"Thread ->-> runnable thread has run.")
            }
        }
        val threadWithRunnable = Thread(SimpleRunnable())
        threadWithRunnable.start()
    }
}