package com.example.kotlinaidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import com.example.androidaidllibrary.Field
import com.example.androidaidllibrary.ILabelManager
import com.example.androidaidllibrary.Label
import java.util.concurrent.CopyOnWriteArrayList

class LabelManagerService : Service() {

//    var mFields = mutableListOf<Field>(Field("31","46",0,0,12,12,"5","1"))
//    var mLabel: Label = Label(mFields)
//    var mLabelList:MutableList<Label> = mutableListOf(mLabel)
    var mLabelList: CopyOnWriteArrayList<Label> = CopyOnWriteArrayList<Label>()

    override fun onCreate() {
        super.onCreate()
    }

    private val mBinder = object: ILabelManager.Stub(){
        override fun getLabelList(): MutableList<Label> {
            return mLabelList
        }
        override fun addLabel(label: Label) {
            mLabelList.add(label)
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}