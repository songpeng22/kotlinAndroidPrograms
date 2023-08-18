package com.bizerba.thread

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var mainActivityString:String = "main activity string"
    fun StringArrived(){
        Log.v("MainActivity","string arrived.")
    }

    //Handler -> WeakReference to the outer class
    private val weakReferenceOfOuterClass = WeakReference<MainActivity>(this)
    class MessageHandler(private val outerClass: WeakReference<MainActivity>) : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.v("MainActivity","msg:${msg.what},obj:${msg.obj}")
            outerClass.get()?.mainActivityString = "main activity string..."
            outerClass.get()?.StringArrived()
        }
    }
    private val messageHandler = MessageHandler(weakReferenceOfOuterClass)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/**/
        //thread function
        println("\nthread function:")
        thread(start = true){
            println("thread function: ->-> run thread function.")
            Thread.sleep(100)

            Handler(Looper.getMainLooper()).post {
                Log.v("thread", "PackService::onServiceConnected()::thread(),Thread is ->-> ${Thread.currentThread()}...............................")
            }
        }

        //threads
        thread(start = true){
            ThreadContainer().run()
        }

        //handler communicate from thread
        println("\nthread communicate from thread:")
        thread(start = true){
            val handlerContainer = HandlerContainer()
            handlerContainer.setHandler(messageHandler)
            handlerContainer.run()
        }

        //HandlerThread
        //The main advantage of using HandlerThread over a regular Thread is that it simplifies the process of communicating between threads.
        println("\nHandlerThread:")
        var strLog = String.format("MainActivity -> Thread::id:%d,name:%s", Thread.currentThread().id, Thread.currentThread().name)
        Log.d("HandlerThread", strLog)
        val handlerThread = HandlerThread("HandlerThread->NamedThread")
        handlerThread.start()
        //get looper from HandlerThread to Handler
        val handler = object : Handler(handlerThread.looper){
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    else -> {
                        Log.v("HandlerThread","msg:${msg.what},obj:${msg.obj}")
                        //send msg back to MainActivity - failed, because here do not have handler of MainActivity
                        val handlerMain = Handler(Looper.getMainLooper())
                        val message = Message.obtain(handlerMain)
                        message.what = MESSAGE_NEW_LABEL_ARRIVED
                        message.obj = "msg from HandlerThread"
                        handlerMain.sendMessage(message)
                    }
                }
            }
        }
        val interval:Long = 1000
        var r = object : Runnable {
            override fun run() {
                var strLog = String.format("Thread::id:%d,name:%s", Thread.currentThread().id, Thread.currentThread().name)
                Log.d("HandlerThread", strLog)
                handler.postDelayed(this, interval)
            }
        }
        handler.postDelayed(r, interval)
        //send msg to handler thread
        val message = Message.obtain(handler)
        message.what = MESSAGE_NEW_LABEL_ARRIVED
        message.obj = "msg to HandlerThread."
        handler.sendMessage(message)
/* */
        //thread pools
        thread(start = true){
            ThreadPoolContainer().run()
        }

        //coroutines
        thread(start = true){
            CoroutineContainer().run()
        }

    }
}