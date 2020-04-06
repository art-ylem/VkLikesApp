package com.bs.vklibrary;

import android.content.Context;
import android.support.annotation.RawRes;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sergey on 22/01/18.
 */

public class JsonParser {

    public static class Group{

        @SerializedName("id")
        public String id;

        @SerializedName("sex")
        public String sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

    public static List<Group> parceRoute(Context context){
        String json = readJsonFromFile(context, R.raw.groups);

        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Group>>(){}.getType();
        List<Group> boxSearchCollection = gson.fromJson(json, collectionType);
        return boxSearchCollection;
    }

    private static String readJsonFromFile (Context context, @RawRes int rawRes)
    {
        InputStream raw = context.getResources().openRawResource(rawRes);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try
        {
            i = raw.read();
            while (i != -1)
            {
                byteArrayOutputStream.write(i);
                i = raw.read();
            }
            raw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return byteArrayOutputStream.toString();

    }

}
