package com.hfad.guessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * use View Model and live data in Guessing game
 * logic:
 * activity_main -> nav_graph && NavHostFragment -> app:startDestination="@id/gameFragment"
 * new technics:
 * live data -> list of String like data which is life cycle controllable by owner
 * new technic points:
 * implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}