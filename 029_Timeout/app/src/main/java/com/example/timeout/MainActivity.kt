package com.example.timeout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.common.base.CharMatcher.`is`
import com.google.common.util.concurrent.SimpleTimeLimiter
import com.google.common.util.concurrent.TimeLimiter
import java.net.SocketTimeoutException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//TimeLimiter Callable
//class Timeout : Callable {
//    override fun call() = 42
//}

class MainActivity : AppCompatActivity() {
    //read timeout - guava is needed
    var executor: ExecutorService = Executors.newCachedThreadPool()
    var timeLimiter: TimeLimiter = SimpleTimeLimiter.create(executor)
    private var readerTimeout:Long = 20

    val doSomething:() -> Unit = {
        val ret:String? = null
        Thread.sleep(200)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            TimeOutMeasure.getMap("test")?.doWithTimeout(doSomething,100)
        }
        catch (ex:Exception){
            Log.e("MainActivity","exception:${ex.message}")
        }

//        while (timeLimiter.callWithTimeout(doSomething, readerTimeout, TimeUnit.SECONDS).also { line = it } != null) {
//
//        }

    }
}