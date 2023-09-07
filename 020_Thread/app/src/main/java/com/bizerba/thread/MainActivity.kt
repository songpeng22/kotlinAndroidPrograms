package com.bizerba.thread

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.ref.WeakReference
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    var mainActivityString:String = "main activity string"
    fun StringArrived(){
        Log.v("MainActivity","string arrived.")
    }

    //Handler
    class MessageHandler() : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.v("MainActivity","msg:${msg.what},obj:${msg.obj}")

            super.handleMessage(msg)
        }
    }
    val messageHandler = MessageHandler()
    val mainActivityMessenger: Messenger = Messenger(messageHandler)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
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
                        val replyMessenger:Messenger = msg.replyTo
                        val messageV1 = Message.obtain()
                        messageV1.what = MESSAGE_NEW_LABEL_ARRIVED
                        messageV1.obj = "msg from HandlerThread v1"
                        replyMessenger.send(messageV1)
                        //failed received in MainActivity
                        val mainHandler = Handler(Looper.getMainLooper())
                        val messageV2 = Message.obtain()
                        messageV2.what = MESSAGE_NEW_LABEL_ARRIVED
                        messageV2.obj = "msg from HandlerThread v2"
                        mainHandler.sendMessage(messageV2)
                    }
                }
            }
        }
        val interval:Long = 5000
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
        message.obj = "msg to HandlerThread V1."
        message.replyTo = mainActivityMessenger
        handler.sendMessage(message)

        //MyHandlerThread
        println("\nMyHandlerThread:")
        val myHandlerThread = MyHandlerThread("MyHandlerThread->NamedThread")
        myHandlerThread.start()
        //send msg to handler thread
        //val myHandler = Handler(myHandlerThread.looper)
        val myHandler = myHandlerThread.prepareHandler()
        //val myHandler = myHandlerThread.handler
        val message2 = Message.obtain(myHandler)
        message2.what = MESSAGE_NEW_LABEL_ARRIVED
        message2.obj = "msg to MyHandlerThread V1."
        message2.replyTo = mainActivityMessenger
        myHandler?.sendMessage(message2)


        //thread pools
        thread(start = true){
            ThreadPoolContainer().run()
        }

        //coroutines
        println("\ncoroutines:")
        System.setProperty("kotlinx.coroutines.debug", "on" )
        thread(start = true){
            CoroutineContainer().run()
        }
*/
/**/
        //coroutines
        println("\nMyCoroutineCope:")
        var supervisorJob = SupervisorJob()
        //val contextOf = CoroutineName("DataManager")
        val exceptionHandler = CoroutineExceptionHandler{ context, error ->
            Log.v(TAG, "${context[CoroutineName]}:CoroutineExceptionHandler:${error.message}")
        }
        val scopeOf:CoroutineScope = CoroutineScope(Dispatchers.IO + supervisorJob + exceptionHandler)
//        Log.v(TAG, "dispatcher is ${scopeOf.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher}")
        //scopeOf.ensureActive()
        var dataScope = DataScope(supervisorJob)
        var dataManager = DataManager()
        var childJobOfDataScope = dataScope.launch(CoroutineName("DataManager")) {
//            try {
                Log.v(TAG, "${coroutineContext[CoroutineName]} dispatched by ${coroutineContext[ContinuationInterceptor] as CoroutineDispatcher} is running...")

                dataScope.apply {
                    Log.v(TAG,"DataManager apply:${name}.")
                    name = "DataManagerEx"
                    Log.v(TAG,"DataManager apply:${name}.")
                }.apply {
                    Log.v(TAG,"DataManager apply..")
                }
                dataScope.runOnce()
                Log.v(TAG,"+dataScope.isActive:${dataScope.isActive}...")
                while(dataScope.isActive){
                    dataScope.run()
                    dataManager.getData()
                }
                Log.v(TAG,"-dataScope.isActive:${dataScope.isActive}...")

                throw error("launch1")
//            }
//            catch (ex:CancellationException){
//                Log.v(TAG,"CancellationException caught:${ex.message}")
//            }
//            catch (ex:Exception){
//                Log.v(TAG,"Exception caught:${ex.message}")
//            }

        }
        childJobOfDataScope.invokeOnCompletion {
            Log.v(TAG, "jobOf ${(childJobOfDataScope as CoroutineScope).coroutineContext[CoroutineName].toString()} is completed.")
        }
        //
        var jobOfParent = scopeOf.launch(CoroutineName("Parent")) {
            Log.v(TAG, "${coroutineContext[CoroutineName]} is running...")
            throw error("launch2")
        }
        jobOfParent.cancel()
        jobOfParent.invokeOnCompletion {
            Log.v(TAG, "jobOf ${(jobOfParent as CoroutineScope).coroutineContext[CoroutineName].toString()} is completed.")
        }
        //
        var jobOfMyCoroutineScopeEx = scopeOf.launch(CoroutineName("MyCoroutineScopeEx")) {
            //class extends CoroutineScope
            MyCoroutineScopeEx()
            throw error("launch3")
        }
        jobOfMyCoroutineScopeEx.cancel()
        //iterate
        for (child in supervisorJob.children){
            println("child is ${(child as CoroutineScope).coroutineContext[CoroutineName].toString() }")
        }
        //cancel
        runBlocking {
            launch {
                delay(10)
                Log.v(TAG,"+jobOfDataManager is cancelling...")
                childJobOfDataScope.cancel()
                Log.v(TAG,"-jobOfDataManager is cancelling...")
            }
        }
        childJobOfDataScope.invokeOnCompletion {
            Log.v(TAG,"jobOfDataManager completed.")
        }
    }
}