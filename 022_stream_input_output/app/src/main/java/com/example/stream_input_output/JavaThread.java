package com.example.stream_input_output;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.*;

import com.google.common.io.ByteStreams;

public class JavaThread extends Thread {
    @Override
    public void run(){
        Log.v("JavaThread","run().");

        /*
         * ByteArray -> inputStream -> outputStream -> file
         */
        String content = "Hello World\n";
        //repeat need change from VERSION_1_8 to
        //sourceCompatibility JavaVersion.VERSION_11
        //targetCompatibility JavaVersion.VERSION_11
        //from jvmTarget = '1.8'
        //to jvmTarget = '11'
        String repeatContent = content.repeat(3);
        File file1 = null;
        try {
            file1 = File.createTempFile("Java", ".txt");
            // check if the file is created
            if (file1.exists()) {
                // the file is created
                // as the function returned true
                System.out.println("Temp File created: "
                        + file1.getName());
            }
            else {

                // display the file cannot be created
                // as the function returned false
                System.out.println("Temp File cannot be created: "
                        + file1.getName());
            }
        }
        catch (Exception e) {
            // display the error message
            System.err.println(e);
        }
        Log.v("MainActivity", "Java:file1.name:" + file1.getName());
        Log.v("MainActivity", "Java:file1.path:" + file1.getPath());

        //ByteArray -> inputStream
        InputStream inputStream = new ByteArrayInputStream(repeatContent.getBytes());
        ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
        try {
            ByteStreams.copy(inputStream, targetStream);
            OutputStream fileStream = new FileOutputStream(file1);
            //write to FileOutputStream
            targetStream.writeTo(fileStream);
        }
        catch (Exception e) {
            // display the error message
            System.err.println(e);
        }

    }
}
