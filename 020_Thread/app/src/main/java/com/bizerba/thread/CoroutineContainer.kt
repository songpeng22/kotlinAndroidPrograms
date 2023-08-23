package com.bizerba.thread

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*

class CoroutineContainer {
    private val TAG:String = "CoroutineContainer"
    fun run(){
        //Coroutine - runBlocking
        //because we have used Dispatchers.Default which launches it in the GlobalScope.
        Log.v(TAG,"\nrunBlocking launch:")
        runBlocking {
            val job = launch(Dispatchers.Default) {
                Log.v(TAG,"Coroutine ->-> ${Thread.currentThread()} has run.")
            }
            //
            launch { // context of the parent, main runBlocking coroutine
                Log.v(TAG,"main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
                Log.v(TAG,"main runBlocking: After delay in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
                Log.v(TAG,"Unconfined            : I'm working in thread ${Thread.currentThread().name}")
                delay(500)
                //resume on another thread
                Log.v(TAG,"Unconfined      : After delay in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
                Log.v(TAG,"Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
                Log.v(TAG,"newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
        //Dispatcher
        Log.v(TAG,"\nGlobalScope.launch:")
        GlobalScope.launch(Dispatchers.Main) {
            Log.v(TAG,"GlobalScope.launch::Dispatchers.Main::I'm working in thread ${Thread.currentThread().name}")
        }

        //Coroutine - GlobalScope
        Log.v(TAG,"\nGlobalScope.launch job:")
        val job = GlobalScope.launch { 	// creates a new coroutine and continues
            Log.v(TAG,"Coroutine ->-> GlobalScope has run.")
        }

        //Instead of launching coroutines in the GlobalScope, just like we usually do with threads (threads are always global),
        // we can launch coroutines in the specific scope of the operation we are performing
        Log.v(TAG,"\nrunBlocking launch:")
        runBlocking {
            val job = launch {
                Log.v(TAG,"${Thread.currentThread()} has run.")
            }
        }

        //async - deferred object builder
        Log.v(TAG,"\nCoroutineScope async:")
        val deferredCoroutineScope = CoroutineScope(Dispatchers.Default).async {
            return@async "CoroutineScope::async ->-> ${Thread.currentThread()} has run."
        }
        CoroutineScope(Dispatchers.Default).launch {
            Log.v(TAG,"CoroutineScope(Dispatchers.Default).launch()::I'm working in thread ${Thread.currentThread().name}")
            deferredCoroutineScope.await()
            Log.v(TAG,"GlobalScope.launch::Dispatchers.Main::")
            withContext(Dispatchers.Main){
                Log.v(TAG,"withContext(Dispatchers.Main)::I'm working in thread ${Thread.currentThread().name}")
            }
        }

        val deferred = GlobalScope.async(Dispatchers.Unconfined, CoroutineStart.LAZY) {
            Log.v(TAG,"GlobalScope::async ->-> ${Thread.currentThread()} has run.")
            return@async "any result"
        }
        Log.v(TAG,"async - GlobalScope.async.")
        GlobalScope.launch {
            val deferredResult = deferred.await()
            println("deferredResult:${deferredResult}")
        }


        //Channel
        runBlocking<Unit> {
            val channel = Channel<String>()
            launch {
                println("A1 sending...")
                channel.send("A1")
                println("A1 sent")
                delay(2000)
                channel.send("A2")
                println("A2 sent")
                println("A done")
            }
            launch {
                println("B1 sending...")
                channel.send("B1")
                println("B1 sent")
                println("B done")
            }
            launch {
                repeat(3) {
                    delay(2000)
                    println("receiving...")
                    val x = channel.receive()
                    println("${x} received")
                }
            }
        }
    }
}