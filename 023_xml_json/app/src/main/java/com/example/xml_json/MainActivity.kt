package com.example.xml_json

import android.R.xml
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.xml.JSONObject
import org.xml.XML


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * org.json
         * */
        val jsonObject:JSONObject = JSONObject()
        jsonObject.put("Name", "Robert")
        jsonObject.put("ID", 1)
        jsonObject.put("Fees", 1000.21)
        jsonObject.put("Active", true)
        jsonObject.put("Details", JSONObject.NULL)

        //Convert a JSONObject to XML
        val xmlText:String = XML.toString(jsonObject)
        println("xml:")
        println(xmlText)

        //Convert an XML to JSONObject
        val jsonText:String = XML.toJSONObject(xmlText).toString()
        println("json:")
        println(jsonText)

        /*
         * Jackson
         * */
        val mapper = ObjectMapper()
        val actualObj:JsonNode = mapper.readTree(jsonText)
        println("actualObj:Name:${actualObj.get("Name")}")
        println("actualObj:Active:${actualObj.get("Active")}")

    }
}