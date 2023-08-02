package com.hfad.bitsandpizzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * Material themes to show some components usage
 * logic:
 * setContentView(R.layout.activity_main) -> CoordinatorLayout -> OrderFragment
 * new technics:
 * tool bar, scroll collapse
 * checkbox/radio button/chips
 * floating action button
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}