package com.bizerba.thread

import android.util.Log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

data class Data(var data:String = "data")

interface DataGetter {
    fun init() {
        getData()
    }
    fun getData(): Data{
        return Data()
    }
}

class DataManager : DataGetter{

}

class DataScope(var parentJob:Job) : CoroutineScope {
    //coroutine related
    override val coroutineContext: CoroutineContext
        get() = CoroutineName("DataScope") + Dispatchers.IO + parentJob
    //TAG
    private val TAG:String = "DataScope"
    //
    var name:String = "DataScope"
    var dataManager = DataManager()

    suspend fun runOnce(){
        Log.v(TAG,"DataManager::run():job name:${coroutineContext[CoroutineName]}.")
        Log.v(TAG,"DataManager::run():${Thread.currentThread().name}.")
        Log.v(TAG,"DataManager::run():name:${name}.")
    }
    suspend fun run(){
        Log.v(TAG,"DataManager::run()::loop,${coroutineContext[CoroutineName]!!},isActive:${isActive}}.")
        delay(2)
    }

    fun cancelParent(){

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