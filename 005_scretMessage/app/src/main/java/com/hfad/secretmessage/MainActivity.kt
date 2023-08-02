package com.hfad.secretmessage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * use navigation switching from fragment to fragment
 * logic:
 * setContentView(R.layout.activity_main) -> FragmentContainerView->app:navGraph -> res/navigation/nav_graph.xml
 * default fragment container -> android:name="androidx.navigation.fragment.NavHostFragment"
 * navigation graph -> app:navGraph="@navigation/nav_graph"
 * new technics:
 * Navigation
 * new technics points:
 * implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
 * Navigation -> connect fragment using action
 * Navigation -> findNavController
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}