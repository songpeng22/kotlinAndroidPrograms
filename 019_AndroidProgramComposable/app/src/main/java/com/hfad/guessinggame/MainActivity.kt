package com.hfad.guessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
 * what this project do:
 * interact compose with view
 * logic:
 * activity_main.xml -> navigation/nav_graph.xml -> app:startDestination="@id/gameFragment" -> GameFragment
 * new technics:
 * put viewModel into Compose View
 * new technic points:
 * GameFragmentContent(viewModel)
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}