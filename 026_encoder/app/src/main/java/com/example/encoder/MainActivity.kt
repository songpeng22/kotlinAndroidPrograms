package com.example.encoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//byte array to decimal tring
@ExperimentalUnsignedTypes // just to make it clear that the experimental unsigned types are used
fun ByteArray.toDecimalString() = asUByteArray().joinToString(" ") { it.toString(10).padStart(2, '0') }
//byte array to hex string
fun ByteArray.toHexString() = joinToString("") { "%02x ".format(it) }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val byteArray = byteArrayOf(0x48, 101, 108, 108, 111)
        println("byteArray:${byteArray.toHexString()}")
        val encoded:ByteArray = android.util.Base64.encode(byteArray,android.util.Base64.DEFAULT)
        println("encoded:${encoded.toHexString()}")
        val decoded = android.util.Base64.decode(encoded,android.util.Base64.DEFAULT)
        println("decoded:${decoded.toHexString()}")
        println("decoded decimal:${decoded.toDecimalString()}")
        for (i in 0 until decoded.size) {
            System.out.print(decoded.get(i))
            System.out.print(" ")
        }
        System.out.print("\n")
        System.out.flush()
    }
}