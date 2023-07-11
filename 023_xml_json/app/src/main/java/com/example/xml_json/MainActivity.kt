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
         * json object normal creation
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
        * org.json
        * create json object from string
        * */
        val jsonObject2:JSONObject = JSONObject(jsonText)
        println("jsonObject2:Name:${jsonObject2.get("Name")}")
        println("jsonObject2:Active:${jsonObject2.get("Active")}")

        /*
         * Jackson
         * */
        //read json to tree
        val mapper = ObjectMapper()
        var actualJsonObj:JsonNode = mapper.readTree(jsonText)
        println("actualObj:Name:${actualJsonObj.get("Name")}")
        println("actualObj:Active:${actualJsonObj.get("Active")}")

        //read xml to tree: failed, no error, but result of getName is null
        //implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3'
        //success at
//        implementation 'com.fasterxml.jackson.core:jackson-core:2.14.2'
//        implementation 'com.fasterxml.jackson.core:jackson-databind:2.14.2'
//        implementation 'com.fasterxml.jackson.core:jackson-annotations:2.14.2'
//        implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2'
//        implementation 'com.fasterxml.woodstox:woodstox-core:6.5.0'
//        implementation 'javax.xml.stream:stax-api:1.0-2'
        val xml:String = ("<?xml version='1.0'?><Root><Name>Robert</Name><Active>true</Active></Root>")
        val xmlFactory = XmlFactory.builder()
            .xmlInputFactory(WstxInputFactory())
            .xmlOutputFactory(WstxOutputFactory())
            .build()
        val xmlMapper:XmlMapper = XmlMapper.builder(xmlFactory).build()
        var actualXmlObj = xmlMapper.readTree(xml)
        println("actualXmlObj:Name:${actualXmlObj.get("Name")}")
        println("actualXmlObj:Active:${actualXmlObj.get("Active")}")   //result is null

        //read xml to tree
        //java.lang.NoSuchMethodError: No static method newFactory(Ljava/lang/String;Ljava/lang/ClassLoader;)
        // Ljavax/xml/stream/XMLInputFactory; in class Ljavax/xml/stream/XMLInputFactory;
        // or its super classes (declaration of 'javax.xml.stream.XMLInputFactory'
        //fix may be here: https://stackoverflow.com/questions/31360025/using-jackson-dataformat-xml-on-android
        //but will not touch the fix for now
//        val xml2:String = ("<?xml version='1.0'?><Root><Name>Robert</Name><Active>true</Active></Root>")
//        val xmlMapper2:XmlMapper = XmlMapper()
//        var actualXmlObj2 = xmlMapper2.readTree(xml2)
//        println("actualXmlObj2:Name:${actualXmlObj2.get("Name")}")
//        println("actualXmlObj2:Active:${actualXmlObj2.get("Active")}")
    }
}