package com.bs.vklibrary;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by sergey on 21/01/18.
 */

public class FilesUtil {

    public static boolean checkTime(Context context){

        if (isFileExsist(context)){
            String time = readFromFile(context);
            long oldTime = Long.parseLong(time);
            long curTime = System.currentTimeMillis();
            long day = TimeUnit.HOURS.toMillis(8);
            long dif = curTime - oldTime;

            if (dif > day){
                String time2 = System.currentTimeMillis() + "";
                writeToFile(time2, context);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            String time = System.currentTimeMillis() + "";
            writeToFile(time, context);
            return false;
        }
    }

    public static boolean isCorrect(Context context){
//        if (!isFileExsist(context)){
//            return true;
//        }
        return checkTime(context);
    }

    public static boolean isFileExsist(Context context){
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "sys.txt");
        return file.exists();
    }


    private static void writeToFile(String data,Context context) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "sys.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private static String readFromFile(Context context) {

        String ret = "";

        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "sys.txt");
            InputStream inputStream = new FileInputStream(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }


}
