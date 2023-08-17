package com.example.kotlinbundle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var strBuf = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * set data into intent
         * */
        // creating a intent
        val intent = Intent()
        // creating a bundle object
        val bundleOut = Bundle()
        //string
        bundleOut.putString("bundle_string", "bundle string")
        intent.putExtra("intent_string", "intent string")
        //int
        bundleOut.putInt("int", 1)
        intent.putExtra("intent_int", 2)
        //parcelable
        val student = Student(1, "Jack", "grade 1")
        bundleOut.putParcelable("parcelable", student)
        intent.putExtra("intent_parcelable", student)
        //Serializable
        val mySerializableObject = MySerializableObject(1,"Geek","India")
        bundleOut.putSerializable("bundle object", mySerializableObject as Serializable)
        intent.putExtra("intent object", mySerializableObject as Serializable)
        //
        intent.putExtra("DATA", bundleOut)

        /*
         * get data into intent
         * */
        val bundleIn = intent.getBundleExtra("DATA")
        //get string
        //get string
        var str = bundleIn!!.getString("bundle_string")
        strBuf = String.format("bundle_string:%s", str)
        Log.v(TAG, str!!)
        str = intent.getStringExtra("intent_string")
        strBuf = String.format("intent_string:%s", str)
        Log.v(TAG, str!!)
        //get int
        //get int
        val intValue = bundleIn!!.getInt("int")
        strBuf = String.format("bundle int:%d", intValue)
        Log.v(TAG, strBuf)
        val intentIntIn = intent.getIntExtra("intent_int", 0)
        strBuf = String.format("intent_int:%d", intentIntIn)
        Log.v(TAG, strBuf)
        //get parcelable
        //get parcelable
        val studentIn: Student? = bundleIn?.getParcelable("parcelable")
        strBuf = String.format(
            "bundle parcelable:id:%d,name:%s",
            studentIn?.id,
            studentIn?.name
        )
        Log.v(TAG, strBuf)
        val studentInIntent: Student? = intent?.getParcelableExtra("intent_parcelable")
        strBuf = String.format("bundle parcelable:id:%d,name:%s", studentInIntent?.id, studentInIntent?.name)
        Log.v(TAG, strBuf)
        //get Serializable
        //get Serializable
        val mySerializableObjectIn = bundleIn!!.getSerializable("bundle object") as MySerializableObject?
        strBuf = String.format("bundle serializable:id:%d,name:%s", mySerializableObjectIn?.id, mySerializableObjectIn?.name)
        Log.v(TAG, strBuf)
        val mySerializableObjectInIntent =
            intent.getSerializableExtra("intent object") as MySerializableObject?
        strBuf = String.format("intent serializable:id:%d,name:%s", mySerializableObjectInIntent?.id, mySerializableObjectInIntent?.name
        )
        Log.v(TAG, strBuf)
    }
}