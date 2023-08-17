package com.example.kotlinbundle

import java.io.Serializable

data class MySerializableObject(val id:Int = 1,val name:String = "serial name",val place:String = "place"): Serializable{

}