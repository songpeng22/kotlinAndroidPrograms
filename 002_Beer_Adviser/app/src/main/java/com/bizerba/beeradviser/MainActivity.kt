package com.bizerba.beeradviser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextClock
import android.widget.TextView

/*
 * what this project do:
 * build an simple interactive app
 * logic:
 * select beer type from spinner -> click button -> get beer from beer list of listOf -> show string on Text
 * new technics:
 * listOf
 * assign string directly to TextView by 'brands.text = beers'
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val findBeer = findViewById<Button>(R.id.find_beer);
        findBeer.setOnClickListener{
            val beerColor = findViewById<Spinner>(R.id.beer_color);
            val color = beerColor.selectedItem;
            val beerList = getBeers(color.toString())
            val beers = beerList.reduce{str,item -> str + '\n' + item} //wired usage
            val brands = findViewById<TextView>(R.id.brands)
            brands.text = beers
        }
    }

    fun getBeers(color: String): List<String>{
        return when(color){
            "Light" -> listOf("Jail Pale Ale","Lager Lite")
            "Amber" -> listOf("Jack Amber","Red Moose")
            "Brown" -> listOf("Brown Beer Beer","Bock Brownie")
            else -> listOf("Cout Stout", "Dark Daniel")
        }
    }
}