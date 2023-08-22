package com.bizerba.thread

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

data class Data(var data:String = "data")

interface MyCoroutineScope: CoroutineScope {
    val job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun init() {
        getData()
    }
    fun getData(): Data{
        return Data()
    }
}

class DataManager : MyCoroutineScope {
    private val TAG:String = "DataManager"
    override val job = Job()

    init {
        launch {
            Log.v(TAG,"DataManager::init().")
            Log.v(TAG,"DataManager::I'm working in thread ${Thread.currentThread().name}")
            init()
        }
    }
}

class MyCoroutineScopeEx : CoroutineScope{
    private val TAG:String = "MyCoroutineScopeEx"

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    init {
        launch {
            Log.v(TAG,"MyCoroutineScopeEx::init().")
            Log.v(TAG,"MyCoroutineScopeEx::I'm working in thread ${Thread.currentThread().name}")
        }
    }
}