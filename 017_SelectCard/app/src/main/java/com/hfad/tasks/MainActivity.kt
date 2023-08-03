package com.hfad.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * clicking on Recycle View, it will navigate to edit fragment
 * logic:
 *
 * new technics:
 *
 * new technic points:
 * viewModel.navigateToTask.observe
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}