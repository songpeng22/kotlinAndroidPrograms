package com.example.kotlinaidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.concurrent.atomic.AtomicReference
import com.example.kotlinaidl.ILabelManager

class LabelManagerService : Service() {


    private var mFields = mutableListOf<Field>(Field("31","46",0,0,12,12,"5","1"))
    private var mLabel:Label = Label(mFields)
    private var mLabelList:MutableList<Label> = mutableListOf(mLabel)

    override fun onCreate() {
        super.onCreate()

    }

    private val mBinder = object: ILabelManager.Stub(){
        override fun getLabelList(): MutableList<Label> {
            return mLabelList
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}