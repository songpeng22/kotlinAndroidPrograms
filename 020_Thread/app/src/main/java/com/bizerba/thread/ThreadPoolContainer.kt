package com.bizerba.thread

import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class ThreadPoolContainer {
    private val TAG:String = "ThreadPoolContainer"
    fun run(){
        //thread pool
        //tasks are infrequent or not performance-critical:
        Log.v(TAG,"\nsingle thread pool:")
        val workerPool: ExecutorService = Executors.newSingleThreadExecutor()
        // Later, in some method
        // Do some in-thread CPU-intensive work
        Log.v(TAG,"single thread pool:I am working hard")
        workerPool.submit {
            Thread.sleep(100) // Imitate slow IO
            Log.v(TAG,"single thread pool:I am reporting on the progress")
        }
        Log.v(TAG,"single thread pool:Meanwhile I continue to work")

        //A scheduled thread pool will execute a task (or several tasks) with a given delay or at a fixed rate:
        Log.v(TAG,"\nsingle thread pool working in fixed rate:")
        val counter = AtomicInteger(0)
        val worker = Executors.newSingleThreadScheduledExecutor()
        worker.scheduleAtFixedRate({ Log.v(TAG,"scheduleAtFixedRate() - work in fixed rate - index:${counter.incrementAndGet()}.") }, 0, 5000, TimeUnit.MILLISECONDS)

        //It will try to reuse existing threads for submitted tasks but will create up to Integer.MAX_VALUE threads if needed.
        Log.v(TAG,"\ncached thread pool:")
        val workerPoolOfCached: ExecutorService = Executors.newCachedThreadPool()
        Log.v(TAG,"cached thread pool:I am working hard")
        workerPool.submit {
            Thread.sleep(100) // Imitate slow IO
            Log.v(TAG,"cached thread pool:I am reporting on the progress")
        }
        Log.v(TAG,"cached thread pool:Meanwhile I continue to work")

        //One of the most common modifications for the ThreadFactory is to change the name given to the threads in the thread pool so that it complies with the logging conventions of the team or company
        Log.v(TAG,"\nfixed thread pool:")
        val worker2 = Executors.newFixedThreadPool(4, object : ThreadFactory {
            private val counter = AtomicInteger(0)
            override fun newThread(r: Runnable): Thread {
                Log.v(TAG,"newFixedThreadPool::newThread()")
                return Thread(null, r, "panda-thread-${counter.incrementAndGet()}")
            }
        })
        worker2.submit {
            Thread.sleep(100) // Imitate slow IO
            Log.v(TAG,"fixed thread pool:I am reporting on the progress")
        }
        Log.v(TAG,"fixed thread pool:Meanwhile I continue to work")

        worker.shutdown()
        worker2.shutdown()
    }
}