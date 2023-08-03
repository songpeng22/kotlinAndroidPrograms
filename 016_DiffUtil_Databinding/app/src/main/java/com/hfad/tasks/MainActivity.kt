package com.hfad.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * like a virtual list, DiffUtil tells Recycle View where the difference of two list, make it more efficient
 * logic:
 *
 * new technics:
 * DiffUtil
 * new technic points:
 *
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}