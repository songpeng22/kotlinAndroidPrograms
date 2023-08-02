package com.hfad.secretmessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * transfer message from fragment to fragment
 * logic:
 * input message -> click button -> transfer
 * new technics:
 * argument -> EncryptFragmentArgs
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}