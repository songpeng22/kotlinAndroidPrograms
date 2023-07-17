package com.example.guava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Declaring the Table
        val table: Table<String, String, String> = HashBasedTable.create()
        table.put("74", "4", "header text")
        println(table["74", "4"].toString())

    }
}