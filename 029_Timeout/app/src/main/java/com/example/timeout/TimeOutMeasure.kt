package com.example.timeout

import android.util.Log
import java.net.Socket
import java.net.SocketTimeoutException

class TimeOutElement() {
    private var isDebug:Boolean = false
    private var TAG:String = "TimeOutElement"
    private var delay:Long = 100

    fun readWithTimeout(job:(Socket) -> String?,socket: Socket):String?{
        val maxTimeMillis: Long = System.currentTimeMillis() + socket.soTimeout
        var line:String? = null
        while (System.currentTimeMillis() < maxTimeMillis) {
            line = job(socket)
//            println("line:${line}")
            if( line != null)
                return line
            else{
                Thread.sleep(delay)
                if(isDebug) Log.v(TAG,"sleep for ${delay}")
            }
        }
        //Timeout, throw related exception
        throw SocketTimeoutException("socket time out")
    }

    fun doWithTimeout(job:() -> Unit,timeoutMillis:Int = 100,ex:Exception = Exception("time out")){
        val maxTimeMillis: Long = System.currentTimeMillis() + timeoutMillis
        while (System.currentTimeMillis() < maxTimeMillis) {
            job()
            Thread.sleep(delay)
            if(isDebug) Log.v(TAG,"sleep for ${delay}")
        }
        //Timeout
        throw ex
    }
}

object TimeOutMeasure {
    private var map = mutableMapOf<String,TimeOutElement>()
    fun getMap(key:String):TimeOutElement?{
        if(map.containsKey(key))
            return map[key]
        else{
            map.put(key, TimeOutElement())
            return map[key]
        }
        return null
    }
    fun clear(){
        map.clear()
    }
}