package com.hfad.guessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * use Data Binding in Guessing game
 * logic:
 *
 * new technics:
 * Data Binding
 * new technic points:
 * buildFeatures.dataBinding = true
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}