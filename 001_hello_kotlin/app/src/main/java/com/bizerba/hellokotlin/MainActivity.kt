package com.bizerba.hellokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log

/*
 * what this project do:
 * build a hello world project with 'kotlin android' insead of 'java android'
 * */
class MainActivity : AppCompatActivity() {
    private var TAG : String = "Hello_Kotlin"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v(TAG,"Hello Log.v.")
        println("Hello println.")
    }
}