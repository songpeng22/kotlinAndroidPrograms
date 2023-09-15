package com.bizerba.xlogtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.PathUtils
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.XLog.logLevel
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.file.FilePrinter
import com.elvishew.xlog.printer.file.backup.FileSizeBackupStrategy
import com.elvishew.xlog.printer.file.clean.FileLastModifiedCleanStrategy
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator
import java.io.File

import com.elvishew.xlog.Logger

class MainActivity : AppCompatActivity() {
    //debug
    private var bDebug:Boolean = false
    private var tag:String = "MainActivity"
    private var strLog:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.getInstance().getCacheFolder()

        initXLog()
        XLog.d("default xlog...")
        XLog.tag(tag).d("xlog with tag...")
    }

    var config: LogConfiguration? = null
    private fun initXLog() {
        config = LogConfiguration.Builder()
            .logLevel(LogLevel.ALL)     // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
            .tag("XLOG-TEST")      // Specify TAG, default: "X-LOG"
            .t()                        // Enable thread info, disabled by default
            .st(1)                // Enable stack trace info with depth 2, disabled by default
            .build()
        val androidPrinter: AndroidPrinter = AndroidPrinter()           // Printer that print the log using com.elvishew.xlog.XLog.Log
//        val consolePrinter = ConsolePrinter();               // Printer that print the log to console using System.out
        val filePrinter: FilePrinter = FilePrinter.Builder(MyApplication.getInstance().getCacheFolder())// Specify the path to save log file
                .fileNameGenerator(DateFileNameGenerator())             // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(FileSizeBackupStrategy(1024 * 1024 * 50)) // Default: FileSizeBackupStrategy(1024 * 1024)
                .cleanStrategy(FileLastModifiedCleanStrategy(1000 * 60 * 60 * 24 * 7)) // Default: NeverCleanStrategy(), 7天
                .build()
        XLog.init( // Initialize XLog
            config,  // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
            androidPrinter,  // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
//            consolePrinter,
            filePrinter
        )
    }
}