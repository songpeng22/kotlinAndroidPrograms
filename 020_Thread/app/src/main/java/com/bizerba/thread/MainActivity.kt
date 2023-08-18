package com.bizerba.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        //thread pools
        thread(start = true){
            ThreadPoolContainer().run()
        }

        //coroutines
        thread(start = true){
            CoroutineContainer().run()
        }
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

    var mainActivityString:String = "main activity string"
    fun StringArrived(){
        Log.v("MainActivity","string arrived.")
    }
}