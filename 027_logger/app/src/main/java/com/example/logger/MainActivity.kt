package com.example.logger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.util.Calendar

fun android.util.Log.log2File(string:String):Unit{
    val logger = LoggerFactory.getLogger(MainActivity::class.java)
    logger.debug(string, "Hello world!!!!!!!!!!!!!!")
}

class MainActivity : AppCompatActivity() {
    private val TAG:String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cache
        val cacheDir:String = applicationInfo.dataDir + File.separator + "cache" + File.separator
        Log.v("TAG",cacheDir)
        //prepareAssets()

        //logcat
        Log.v("TAG","hello Log.v........................")

        /*
         * implementation("org.slf4j:jul-to-slf4j:1.7.28")
         * implementation("org.slf4j:slf4j-api:1.7.28")
         * implementation("org.slf4j:slf4j-simple:1.7.28")
         * */
//        SLF4JBridgeHandler.removeHandlersForRootLogger()
//        SLF4JBridgeHandler.install()
//        val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
//        logger.info("Hello world.")

        /*
         * implementation 'org.slf4j:slf4j-api:1.7.25'
         * implementation 'com.github.tony19:logback-android:2.0.0'
         * <file>/data/user/0/com.example.logger/cache/testFile.log</file>
         * */
//        SLF4JBridgeHandler.removeHandlersForRootLogger();
//        SLF4JBridgeHandler.install();
        val logger = LoggerFactory.getLogger(MainActivity::class.java)
        //logger format
        logger.debug("logger.debug:{},time{}", "debug...", Calendar.getInstance().getTime())
        logger.info("logger.info:{}", "info...")
        logger.warn("logger.warn:{}", "warn...")
        logger.error("logger.error:{}", "error!!!")
        //no format
        logger.debug("logger.debug:" + "time:" + Calendar.getInstance().getTime())

    }


    fun transmitAssetsFiles(assetFilePath: String?, fileName: String?) {
        val dir = File(applicationInfo.dataDir + File.separator + "assets" + File.separator)
        var doSave = true
        if (!dir.exists()) {
            doSave = dir.mkdirs()
        }
        val filePath = File(dir, fileName)
        //Log.d(TAG, filePath.getAbsolutePath());
        try {
            val assetManager = assets
            val inputStream = assetManager.open(assetFilePath!!)
            Utils.createFile(filePath.absolutePath, inputStream)
            inputStream.close()
        } catch (e: IOException) {
            Log.e(TAG, e.message!!)
        }
    }

    fun prepareAssets() {
        Log.d(TAG, "+MainActivity::prepareAssets().................................1")

        transmitAssetsFiles("logback.xml", "logback.xml")

        Log.d(TAG, "-MainActivity::prepareAssets().................................1")
    }
}