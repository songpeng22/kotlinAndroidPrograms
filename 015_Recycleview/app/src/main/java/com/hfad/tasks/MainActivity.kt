package com.hfad.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * read database and show data in RecyclerView
 * logic:
 * activity_main.xml -> FragmentContainerView -> TasksFragment -> RecyclerView
 * new technics:
 * Recycle View
 * new technic points:
 * implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
 * implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}