package com.example.logger

import android.content.Context
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object Utils {
    /**
     * Create an output file from raw resources.
     *
     * @param outputFile
     * @param context
     * @param inputRawResources
     * @throws IOException
     */
    @Throws(IOException::class)
    fun createFile(
        outputFile: String?,
        context: Context, inputRawResources: Array<Int>
    ) {
        val outputStream: OutputStream = FileOutputStream(outputFile)
        val resources = context.resources
        val largeBuffer = ByteArray(1024 * 4)
        var totalBytes = 0
        var bytesRead = 0
        for (resource in inputRawResources) {
            val inputStream = resources.openRawResource(resource)
            while (inputStream.read(largeBuffer).also { bytesRead = it } > 0) {
                if (largeBuffer.size == bytesRead) {
                    outputStream.write(largeBuffer)
                } else {
                    val shortBuffer = ByteArray(bytesRead)
                    System.arraycopy(largeBuffer, 0, shortBuffer, 0, bytesRead)
                    outputStream.write(shortBuffer)
                }
                totalBytes += bytesRead
            }
            inputStream.close()
        }
        outputStream.flush()
        outputStream.close()
    }

    /**
     * Create an output file from input stream.
     *
     * @param outputFile
     * @param inputStream
     * @throws IOException
     */
    @Throws(IOException::class)
    fun createFile(
        outputFile: String?,
        inputStream: InputStream
    ) {
        val outputStream: OutputStream = FileOutputStream(outputFile)
        val largeBuffer = ByteArray(1024 * 4)
        var totalBytes = 0
        var bytesRead = 0
        while (inputStream.read(largeBuffer).also { bytesRead = it } > 0) {
            if (largeBuffer.size == bytesRead) {
                outputStream.write(largeBuffer)
            } else {
                val shortBuffer = ByteArray(bytesRead)
                System.arraycopy(largeBuffer, 0, shortBuffer, 0, bytesRead)
                outputStream.write(shortBuffer)
            }
            totalBytes += bytesRead
        }
        outputStream.flush()
        outputStream.close()
    }
}