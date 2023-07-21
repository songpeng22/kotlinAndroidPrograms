package com.example.kotlinaidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG_PENG = "peng:MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this@MainActivity,LabelManagerService::class.java)
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            when(name.className){
                "com.example.kotlinaidl.LabelManagerService" -> {
                    Log.v(TAG_PENG, "LabelManagerService::onServiceConnected().")
                    val iLabelManager:ILabelManager = ILabelManager.Stub.asInterface(binder)
                    println("iLabelManager.labelList.size:${iLabelManager.labelList.size}")
                    val label = iLabelManager.labelList.get(0)
                    println("label.fields.size:${label.fields.size}")
                    var field = label.fields.get(0)
                    println("label.field.id:${field.id}")
                    println("label.field.type:${field.type}")
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            when(name.className){
                "com.example.kotlinaidl.LabelManagerService" -> {
                    Log.v(TAG_PENG, "LabelManagerService::onServiceDisconnected().")
                }
            }
        }
    }
}