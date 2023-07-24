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


object  Unpack {
    fun init(){

    }
    fun unpackNested(xmlBranchPath:List<String>,jsonNode: JsonNode,level:Int){
        var path:String = xmlBranchPath.get(level)
        println("Unpack::path:${path}")
        var subNode = jsonNode.at(path)
        var levelInner = level + 1
        when(subNode.nodeType){
            JsonNodeType.OBJECT -> println("Unpack::${subNode.toPrettyString()}")
            JsonNodeType.ARRAY -> subNode.forEach{
                println("Unpack::${it.toPrettyString()}")
                if(levelInner < xmlBranchPath.size)
                    unpackNested(xmlBranchPath,it,levelInner)
            }
            else -> {
                println("subNode.nodeType:${subNode.nodeType}")
            }
        }
    }



}

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
        var subNode1 = actualJsonObj.at("/Name")
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
        subNode1 = actualJsonObj.path("Name")
        println("subNode2 is:${subNode1.toPrettyString()}")

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
        println("xml2 begin:")
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
        var actualXmlObj2 = xmlMapper2.readTree(inputStream)
        actualXmlObj2.forEachIndexed  { index, node -> println("index:${index},node:${node},isObject:${node.isObject},isArray:${node.isArray},isValueNode:${node.isValueNode}") }
        //print xml path - /Layout
        var xmlBranchPath:String = "/Layout"
        var subNode2 = actualXmlObj2.at(xmlBranchPath)
        println("Jackson:subNode:${xmlBranchPath}:${subNode2}")
        subNode2.forEachIndexed  { index, node -> println("subNode2::index:${index},node:${node},isObject:${node.isObject},isArray:${node.isArray},isValueNode:${node.isValueNode}") }
        //print xml path - /Field
        xmlBranchPath = "/Layout/Field"
        subNode2 = actualXmlObj2.at(xmlBranchPath)
        println("Jackson:subNode:${xmlBranchPath}:${subNode2},isObject:${subNode2.isObject},isArray:${subNode2.isArray},isValueNode:${subNode2.isValueNode}")
        subNode2.forEachIndexed  { index, node -> println("subNode2::index:${index},node:${node},isObject:${node.isObject},isArray:${node.isArray},isValueNode:${node.isValueNode}") }
        //check fake node
        xmlBranchPath = "/LAYOUT/NOTHING"
        var nodeOfNothing = actualXmlObj2.at(xmlBranchPath)
        //nodeOfNothing.required("abc") //require series will generate exception if element not found
        println("nodeOfNothing.nodeType:${nodeOfNothing.nodeType}")
        //find value
        var nodeList1:List<JsonNode> = actualXmlObj2.findValues("Field")
        println("nodeList1.size:${nodeList1.size}")
        println("nodeList1.count:${nodeList1.count()}")
        nodeList1.forEach{
            println("it.toPrettyString():${it.toPrettyString()}")
        }
        println("xml2 end.")


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

        //complex xml path
        println("xml4 begin:complex xml path:")
        val xml4:String = ("<?xml version='1.0'?>" +
                "<Root>" +
                    "<Layout>" +
                        "<Field ID=\"31\" TYPE=\"6\"></Field>" +
                        "<Field ID=\"32\" TYPE=\"6\"></Field>" +
                        "<VARLIST>" +
                            "<VARIABLE VARID=\"913\" FIELDTYPE=\"13\"></VARIABLE>" +
                            "<VARIABLE VARID=\"217\" FIELDTYPE=\"11\"></VARIABLE>" +
                        "</VARLIST>" +
                    "</Layout>" +
                    "<Layout>" +
                        "<Field ID=\"33\" TYPE=\"6\"></Field>" +
                        "<Field ID=\"34\" TYPE=\"6\"></Field>" +
                        "<VARLIST>" +
                            "<VARIABLE VARID=\"913\" FIELDTYPE=\"13\"></VARIABLE>" +
                            "<VARIABLE VARID=\"217\" FIELDTYPE=\"11\"></VARIABLE>" +
                        "</VARLIST>" +
                    "</Layout>" +
                "</Root>")
        val inputStream4: InputStream =
            ByteArrayInputStream(xml4.toByteArray(StandardCharsets.UTF_8))
        val xmlFactory4 = XmlFactory.builder()
            .xmlInputFactory(WstxInputFactory())
            .xmlOutputFactory(WstxOutputFactory())
            .build()
        val xmlMapper4:XmlMapper = XmlMapper.builder(xmlFactory4).build()
        var actualXmlObj4 = xmlMapper4.readTree(inputStream4)
        xmlBranchPath = "/Layout/Field"
        var subNode4 = actualXmlObj4.at(xmlBranchPath)
        println("complex xml path1::subNode4.nodeType:${subNode4.nodeType}")
        xmlBranchPath = "/Layout"
        subNode4 = actualXmlObj4.at(xmlBranchPath)
        when(subNode4.nodeType){
            JsonNodeType.OBJECT -> println("complex xml path2::${subNode4.toPrettyString()}")
            JsonNodeType.ARRAY -> subNode4.forEach{
                println("complex xml path3::${it.toPrettyString()}")
                xmlBranchPath = "/Field"
                var level2Node = it.at(xmlBranchPath)
                when(level2Node.nodeType){
                    JsonNodeType.OBJECT -> println("complex xml path4::${subNode4.toPrettyString()}")
                    JsonNodeType.ARRAY -> level2Node.forEach{
                        println("complex xml path4::${it.toPrettyString()}")
                    }
                    else -> {
                        println("it.nodeType:${it.nodeType}")
                    }
                }
            }
            else -> {
                println("subNode4.nodeType:${subNode4.nodeType}")
            }
        }
        //inner class to analyse nested path
        var listStr = listOf<String>("/Layout","/Field")
        Unpack.unpackNested(listStr,actualXmlObj4,0)
        println("xml4 end.")

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