package com.example.xml_json

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ctc.wstx.stax.WstxInputFactory
import com.ctc.wstx.stax.WstxOutputFactory
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
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
        var actualObj:JsonNode = mapper.readTree(jsonText)
        println("actualObj:Name:${actualObj.get("Name")}")
        println("actualObj:Active:${actualObj.get("Active")}")

        val xml:String = ("<?xml version='1.0'?><Name>Robert</Name><Active>true</Active>")
        val xmlFactory = XmlFactory.builder()
            .xmlInputFactory(WstxInputFactory())
            .xmlOutputFactory(WstxOutputFactory())
            .build()
        val xmlMapper:XmlMapper = XmlMapper.builder(xmlFactory).build()
        var actualXmlObj = xmlMapper.readTree(xml)
        println("actualObj:Name:${actualXmlObj.get("Name")}")       //result is null
        println("actualObj:Active:${actualXmlObj.get("Active")}")   //result is null

    }
}