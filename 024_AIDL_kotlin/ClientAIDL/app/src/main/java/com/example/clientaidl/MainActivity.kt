package com.example.clientaidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidaidllibrary.Field
import com.example.androidaidllibrary.ILabelManager
import com.example.androidaidllibrary.Label


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var strLog = ""
    private val SERVICE_ACTION = "com.example.kotlinaidl.LabelManagerService"

    private val conn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            //客户端与Service建立连接
            Log.i(TAG, "LabelManagerService::onServiceConnected")
            //pass AIDL as binder directly
            //pass AIDL as binder directly
            val labelManager: ILabelManager = ILabelManager.Stub.asInterface(binder)
            //
            //
            try {
                println("iLabelManager.labelList.size:${labelManager.labelList.size}")
                var newLabel = Label("3")
                labelManager.addLabel(newLabel)
                val label:Label = labelManager.labelList.get(0)
                println("label.id:${label.id}")

//                println("label.fields.size:${label.fields.size}")
//                var field = label.fields.get(0)
//                println("label.field.id:${field.id}")
//                println("label.field.type:${field.type}")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            //客户端与Service失去连接
            Log.i(TAG, "LabelManagerService::onServiceDisconnected")
        }
    }

    protected fun onConnect() {
        val intent = Intent()
        intent.action = SERVICE_ACTION
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val pm = packageManager
        //我们先通过一个隐式的Intent获取可能会被启动的Service的信息
        val info = pm.resolveService(intent, 0)
        if (info != null) {
            //如果ResolveInfo不为空，说明我们能通过上面隐式的Intent找到对应的Service
            //我们可以获取将要启动的Service的package信息以及类型
            val packageName = info.serviceInfo.packageName
            val serviceNmae = info.serviceInfo.name
            strLog = String.format("info.serviceInfo.packageName:%s.", info.serviceInfo.packageName)
            Log.i(TAG, strLog)
            strLog = String.format("info.serviceInfo.name:%s.", info.serviceInfo.name)
            Log.i(TAG, strLog)
            //然后我们需要将根据得到的Service的包名和类名，构建一个ComponentName
            //从而设置intent要启动的具体的组件信息，这样intent就从隐式变成了一个显式的intent
            //之所以大费周折将其从隐式转换为显式intent，是因为从Android 5.0 Lollipop开始，
            //Android不再支持通过通过隐式的intent启动Service，只能通过显式intent的方式启动Service
            //在Android 5.0 Lollipop之前的版本倒是可以通过隐式intent启动Service
            val componentName = ComponentName(packageName, serviceNmae)
            intent.component = componentName
            try {
                Log.i(TAG, "BookManagerService::call bindService")
                bindService(intent, conn, BIND_AUTO_CREATE)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, e.message!!)
            }
        } else {
            strLog = String.format("info is null")
            Log.i(TAG, strLog)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        strLog = String.format("MainActivity::onCreate().")
        Log.i(TAG, strLog)
        onConnect()
    }
}