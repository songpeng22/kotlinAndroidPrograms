package com.example.stream_input_output

import android.content.Context
import android.util.Log
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Paths

class KotlinThread : Thread() {

    override fun run() {
        super.run()
        Log.v("KotlinThread", "run().")

        /*
         * test 01
         * ByteArray -> inputStream -> outputStream -> file
         */
        val content:String = "Test01\n".repeat(3)
        val file1: File = createTempFile()
        Log.v("MainActivity", "file1.name:${file1.name}")
        Log.v("MainActivity", "file1.path:${file1.path}")
        //ByteArray -> inputStream

        val inputStream = ByteArrayInputStream(content.toByteArray())
        //inputStream -> outputStream
        inputStream.use { input ->
            file1.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        //assertThat(file1).hasContent(content)
        Log.v("MainActivity", "content:${content}")
        //End of ByteArray -> inputStream -> outputStream -> file

        /*
         * test 02
         * inputStream -> file path
         * works on API 26
         */
//        val testStr2:String = "Test02\n".repeat(3)
//        val inputStream2 = ByteArrayInputStream(testStr2.toByteArray())
//        val file2: File = createTempFile()
//        inputStream2.use { input ->
//            Files.copy(input, file2.toPath())
//        }
        //assertThat(File("./copied")).hasContent(content)



        /*
         * test 03
         * InputStream to an OutputStream
         * works on API 33
         * */
//        val content3:String = "Test03\n".repeat(3)
//        val file3 = createTempFile()
//        val inputStream3 = ByteArrayInputStream(content3.toByteArray())
//
//        inputStream3.use { input ->
//            file3.outputStream().use { output ->
//                input.transferTo(output)
//            }
//        }
        //assertThat(file).hasContent(content)

        /*
         * test 04
         * FileOutputStream -> OutputStreamWriter -> BufferedWriter
         * */
        val file4 = createTempFile()//File("foo.out")
        val text4 = "Test04\n".repeat(3)
        FileOutputStream(file4).use { fos ->
            OutputStreamWriter(fos, Charsets.UTF_8).use { osw ->
                BufferedWriter(osw).use { bf -> bf.write(text4) }
            }
        }

        /*
         * test 05
         * openFileOutput -> FileOutputStream
         * */
//        val file5 = createTempFile()
//        val text5 = "Test05\n".repeat(3)
//        val file:String = file5.toString()
//        val fileOutputStream: FileOutputStream
//        try {
//            fileOutputStream = getApplicationContext().openFileOutput(file, Context.MODE_PRIVATE)
//            fileOutputStream.write(text5.toByteArray())
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
    }
}