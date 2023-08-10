package com.bizerba.thread

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlin.concurrent.thread
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //thread function
        thread(start = true){
            Thread.sleep(100)
            println("Thread ->-> run thread function.")

            Handler(Looper.getMainLooper()).post {
                Log.v("thread", "PackService::onServiceConnected()::thread(),Thread is ->-> ${Thread.currentThread()}...............................")
            }
        }

        //new thread
        val thread = Thread {
            println("Thread ->-> ${Thread.currentThread()} has run.")
        }
        thread.start()

        //override thread
        class SimpleThread: Thread() {
            public override fun run() {
                println("Thread ->-> override thread has run.")
            }
        }
        val threadSimple = SimpleThread()
        threadSimple.start()

        //new runnable thread
        class SimpleRunnable: Runnable {
            public override fun run() {
                println("Thread ->-> runnable thread has run.")
            }
        }
        val threadWithRunnable = Thread(SimpleRunnable())
        threadWithRunnable.start()

        //Coroutine - runBlocking
        //because we have used Dispatchers.Default which launches it in the GlobalScope.
        runBlocking {
            val job = launch(Dispatchers.Default) {
                println("Coroutine ->-> ${Thread.currentThread()} has run.")
            }
            //
            launch { // context of the parent, main runBlocking coroutine
                println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
                delay(1000)
                println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
                println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
                delay(500)
                //resume on another thread
                println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
                println("Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
        //Dispatcher
        GlobalScope.launch(Dispatchers.Main) {
            println("GlobalScope.launch::Dispatchers.Main::I'm working in thread ${Thread.currentThread().name}")
        }

        //Coroutine - GlobalScope
        val job = GlobalScope.launch { 	// creates a new coroutine and continues
            println("Coroutine ->-> GlobalScope has run.")
        }

        //Instead of launching coroutines in the GlobalScope, just like we usually do with threads (threads are always global),
        // we can launch coroutines in the specific scope of the operation we are performing
        runBlocking {
            val job = launch {
                println("${Thread.currentThread()} has run.")
            }
        }

        //async
        CoroutineScope(Dispatchers.Default).async {
            return@async "async ->-> ${Thread.currentThread()} has run."
        }

        val deferred = GlobalScope.async(Dispatchers.Unconfined, CoroutineStart.LAZY) {
            println("async ->-> ${Thread.currentThread()} has run.")
        }


    }
}