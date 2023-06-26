package com.bizerba.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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