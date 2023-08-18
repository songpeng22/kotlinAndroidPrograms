package com.bizerba.thread

import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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