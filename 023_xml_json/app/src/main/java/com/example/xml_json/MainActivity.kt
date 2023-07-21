package com.example.xml_json

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ctc.wstx.stax.WstxInputFactory
import com.ctc.wstx.stax.WstxOutputFactory
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.JSONObject
import org.xml.XML
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.xml.parsers.DocumentBuilderFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val xmlTreeStr:String = """<categories>
                                <category>abc
                                    <category>cde
                                        <item>someid_1</item>
                                        <item>someid_2</item>
                                        <item>someid_3</item>
                                        <item>someid_4</item>
                                    </category>
                                </category>
                                <category>xyz
                                   <category>zwd
                                      <category>hgw
                                         <item>someid_5</item>
                                      </category>
                                   </category>
                                </category>
                             </categories>"""

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
        //
        //read json to tree
        //
        val mapper = ObjectMapper()
        var actualJsonObj:JsonNode = mapper.readTree(jsonText)
        println("actualObj:Name:${actualJsonObj.get("Name")}")
        println("actualObj:Active:${actualJsonObj.get("Active")}")
        println("actualObj:toPrettyString:${actualJsonObj.toPrettyString()}")
        println("actualObj:tree view:${org.json.JSONObject(jsonText).toString(2)}")
        val subNode1 = actualJsonObj.at("/Name")
        if(subNode1 != null && !subNode1.isEmpty){
            val subNodeStr = mapper.writeValueAsString(subNode1)
            println("actualObj:tree view:${org.json.JSONObject(subNodeStr).toString(2)}")
        }
        else if(subNode1 == null){
            println("subNode is null")
        }
        else if(subNode1.isEmpty){
            println("subNode is empty:${subNode1}")
        }
        val subNode2 = actualJsonObj.path("Name")
        println("subNode2 is:${subNode2.toPrettyString()}")

        //
        //read xml to tree: failed, no error, but result of getName is null
        //
        //implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3'
        //success at
//        implementation 'com.fasterxml.jackson.core:jackson-core:2.14.2'
//        implementation 'com.fasterxml.jackson.core:jackson-databind:2.14.2'
//        implementation 'com.fasterxml.jackson.core:jackson-annotations:2.14.2'
//        implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2'
//        implementation 'com.fasterxml.woodstox:woodstox-core:6.5.0'
//        implementation 'javax.xml.stream:stax-api:1.0-2'
        val xml:String = ("<?xml version='1.0'?><dataroot><Name type=\"human\">Robert</Name><Active>true</Active><Name>Robin</Name><Active>false</Active></dataroot>")
        val xmlFactory = XmlFactory.builder()
            .xmlInputFactory(WstxInputFactory())
            .xmlOutputFactory(WstxOutputFactory())
            .build()
        val xmlMapper:XmlMapper = XmlMapper.builder(xmlFactory).build()
        var actualXmlObj = xmlMapper.readTree(xml)
        //xml tree view
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        var prettyXmlStr = xmlMapper.writeValueAsString(actualXmlObj)
        println("Jackson:xml tree view:${prettyXmlStr}")
        //Jackson:xml to json
        val jsonMapper:ObjectMapper = ObjectMapper()
        val jsonStr:String = jsonMapper.writeValueAsString(actualXmlObj)
        println("Jackson:xml to json:${jsonStr}")
        //json tree view
        println("actualXmlObj:tree view:${org.json.JSONObject(jsonStr).toString(2)}")
        println("actualXmlObj:Name:${actualXmlObj.get("Name")}")
        println("actualXmlObj:Active:${actualXmlObj.get("Active")}")

        //
        //read xml InputStream to tree
        //but with the same tag, only first tag is identified by object
        //
        val xml2:String = ("<?xml version='1.0'?>" +
                "<Root>" +
                    "<Layout>" +
                        "<Field ID=\"31\" TYPE=\"6\"></Field>" +
                        "<Field ID=\"32\" TYPE=\"6\"></Field>" +
                        "<VARLIST>" +
                            "<VARIABLE VARID=\"913\" FIELDTYPE=\"13\"></VARIABLE>" +
                            "<VARIABLE VARID=\"217\" FIELDTYPE=\"11\"></VARIABLE>" +
                        "</VARLIST>" +
                    "</Layout>" +
                "</Root>")
        val inputStream: InputStream =
            ByteArrayInputStream(xml2.toByteArray(StandardCharsets.UTF_8))
        val xmlFactory2 = XmlFactory.builder()
            .xmlInputFactory(WstxInputFactory())
            .xmlOutputFactory(WstxOutputFactory())
            .build()
        val xmlMapper2:XmlMapper = XmlMapper.builder(xmlFactory2).build()
        var actualXmlObj2 = xmlMapper.readTree(inputStream)
        actualXmlObj2.forEachIndexed  { index, node -> println("index:${index},node:${node},isObject:${node.isObject},isArray:${node.isArray},isValueNode:${node.isValueNode}") }
        //print xml path - /Layout
        var xmlBranchPath:String = "/Layout"
        var subNode = actualXmlObj2.at(xmlBranchPath)
        println("Jackson:subNode:${xmlBranchPath}:${subNode}")
        subNode.forEachIndexed  { index, node -> println("index:${index},node:${node},isObject:${node.isObject},isArray:${node.isArray},isValueNode:${node.isValueNode}") }
        //print xml path - /Field
        xmlBranchPath = "/Layout/Field"
        subNode = actualXmlObj2.at(xmlBranchPath)
        println("Jackson:subNode:${xmlBranchPath}:${subNode},isObject:${subNode.isObject},isArray:${subNode.isArray},isValueNode:${subNode.isValueNode}")
        subNode.forEachIndexed  { index, node -> println("index:${index},node:${node},isObject:${node.isObject},isArray:${node.isArray},isValueNode:${node.isValueNode}") }
        //check fake node
        xmlBranchPath = "/LAYOUT/NOTHING"
        var nodeOfNothing = actualXmlObj2.at(xmlBranchPath)
        println("nodeOfNothing.nodeType:${nodeOfNothing.nodeType}")

        //read xml to tree
        //java.lang.NoSuchMethodError: No static method newFactory(Ljava/lang/String;Ljava/lang/ClassLoader;)
        // Ljavax/xml/stream/XMLInputFactory; in class Ljavax/xml/stream/XMLInputFactory;
        // or its super classes (declaration of 'javax.xml.stream.XMLInputFactory'
        //fix may be here: https://stackoverflow.com/questions/31360025/using-jackson-dataformat-xml-on-android
        //but will not touch the fix for now
//        val xml3:String = ("<?xml version='1.0'?><Root><Name>Robert</Name><Active>true</Active></Root>")
//        val xmlMapper2:XmlMapper = XmlMapper()
//        var actualXmlObj3 = xmlMapper2.readTree(xml3)
//        println("actualXmlObj3:Name:${actualXmlObj3.get("Name")}")
//        println("actualXmlObj3:Active:${actualXmlObj3.get("Active")}")

        //iterate root
        val xmlFactory3 = XmlFactory.builder()
            .xmlInputFactory(WstxInputFactory())
            .xmlOutputFactory(WstxOutputFactory())
            .build()
        val xmlMapper3:XmlMapper = XmlMapper.builder(xmlFactory3).build()
        var actualXmlObj3 = xmlMapper3.readTree(xmlTreeStr)
        actualXmlObj3.forEachIndexed  { index, node -> println("index:${index},node:${node}") }

        /*
         * Dom parser
         * */
        //parse tag with same name
        val f:File = File(createTempFile().toString())
        f.writeText(xmlTreeStr)
        val dbf = DocumentBuilderFactory.newInstance()
        val db = dbf.newDocumentBuilder()
        val doc: Document = db.parse(f)
        val root: Element = doc.getDocumentElement()
        val nodeList: NodeList = doc.getElementsByTagName("item")
        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i) // this is node under track info

            println("node name:${node.nodeName},value:${node.textContent}")
        }
    }
}