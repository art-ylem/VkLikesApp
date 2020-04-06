package com.vklikesapp.nat.vklikesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vklikesapp.nat.vklikesapp.model.attachment.Photo;

/**
 * Created by sergey on 02/12/17.
 */

public class CropProfile {

    @SerializedName("photo")
    @Expose
    Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}
