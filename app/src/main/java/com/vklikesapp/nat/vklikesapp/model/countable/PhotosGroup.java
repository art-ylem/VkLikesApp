package com.vklikesapp.nat.vklikesapp.model.countable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vklikesapp.nat.vklikesapp.model.attachment.Photo;

import java.util.ArrayList;

/**
 * Created by sergey on 10/11/17.
 */

public class PhotosGroup {

    @SerializedName("count")
    @Expose
    public Integer count;

    @SerializedName("items")
    @Expose
    public ArrayList<Photo> items = null;

}
