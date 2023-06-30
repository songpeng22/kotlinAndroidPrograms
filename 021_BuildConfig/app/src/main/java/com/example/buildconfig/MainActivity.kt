package com.example.buildconfig

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    //TAG
    var TAG_PENG = "peng:MainActivity"
    private var strLog = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onTest()
    }

    fun onTest(){
        Log.v(TAG_PENG, "MainActivity::onTest().")
        strLog = String.format("Build.ID:%s.", Build.ID);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("Build.VERSION.SDK_INT:%d.", Build.VERSION.SDK_INT);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("BuildConfig.VERSION_CODE:%d.", BuildConfig.VERSION_CODE);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("BuildConfig.VERSION_NAME:%s.", BuildConfig.VERSION_NAME);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("BuildConfig.BUILD_TYPE:%s.", BuildConfig.BUILD_TYPE);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("BuildConfig.FLAVOR:%s.", BuildConfig.FLAVOR);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("BuildConfig.flavorTAG:%s.", BuildConfig.flavorTAG);
        Log.v(TAG_PENG, strLog)
        strLog = String.format("BuildConfig.BUILD_NUMBER:%s.", BuildConfig.BUILD_NUMBER);
        Log.v(TAG_PENG, strLog)
        // format the build date
        val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
        val buildDate: String = dateFormat.format(Date(BuildConfig.BUILD_DATE))
        strLog = String.format("BuildConfig.BUILD_DATE:%s.", buildDate);
        Log.v(TAG_PENG, strLog)

        Log.v("TAG","VERSION.RELEASE {" + Build.VERSION.RELEASE + "}");
        Log.v("TAG","\nVERSION.INCREMENTAL {" + Build.VERSION.INCREMENTAL + "}");
        Log.v("TAG","\nVERSION.SDK {" + Build.VERSION.SDK + "}");
        Log.v("TAG","\nBOARD {" + Build.BOARD + "}");
        Log.v("TAG","\nBRAND {" + Build.BRAND + "}");
        Log.v("TAG","\nDEVICE {" + Build.DEVICE + "}");
        Log.v("TAG","\nFINGERPRINT {" + Build.FINGERPRINT + "}");
        Log.v("TAG","\nHOST {" + Build.HOST + "}");
        Log.v("TAG","\nID {" + Build.ID + "}");

        // do something depending whether this is a productive build
        if (BuildConfig.IS_PRODUCTION) {

        } else {

        }

    }
}