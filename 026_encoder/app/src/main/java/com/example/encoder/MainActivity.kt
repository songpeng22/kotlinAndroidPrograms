package com.example.encoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.system.measureNanoTime

//byte array to decimal tring
@ExperimentalUnsignedTypes // just to make it clear that the experimental unsigned types are used
fun ByteArray.toDecimalString() = asUByteArray().joinToString(" ") { it.toString(10).padStart(2, '0') }
//byte array to hex string
fun ByteArray.toHexString() = joinToString("") { "%02x ".format(it) }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
         * Base64
         * */
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


        //Kotlin hashCode
        var elapsedNano = measureNanoTime {
            println(String.format("Kotlin hashcode:%x","knowledgefactory.net".hashCode()))
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
        //MD5
        elapsedNano = measureNanoTime {
            KnowledgeFactoryMD5.main(arrayOf())
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
        //SHA1
        elapsedNano = measureNanoTime {
            KnowledgeFactorySHA1.main(arrayOf())
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
        //SHA256
        elapsedNano = measureNanoTime {
            KnowledgeFactorySHA256.main(arrayOf())
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
        //SHA384
        elapsedNano = measureNanoTime {
            KnowledgeFactorySHA384.main(arrayOf())
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
        //SHA512
        elapsedNano = measureNanoTime {
            KnowledgeFactorySHA512.main(arrayOf())
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
        //PBKDF2
        elapsedNano = measureNanoTime {
            KnowledgeFactoryPBKDF2.main(arrayOf())
        }
        println("elapsedNano:${elapsedNano/1000000}.${elapsedNano%1000000}ms")
    }
}