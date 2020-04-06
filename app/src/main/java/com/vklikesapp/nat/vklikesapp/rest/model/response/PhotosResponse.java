package com.vklikesapp.nat.vklikesapp.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosResponse {

    @SerializedName("response")
    @Expose
    public Response response;

    public static class Response {

        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;
    }

    public static class Item {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("album_id")
        @Expose
        public Integer albumId;
        @SerializedName("owner_id")
        @Expose
        public Integer ownerId;
        @SerializedName("photo_75")
        @Expose
        public String photo75;
        @SerializedName("photo_130")
        @Expose
        public String photo130;
        @SerializedName("photo_604")
        @Expose
        public String photo604;
        @SerializedName("photo_807")
        @Expose
        public String photo807;
        @SerializedName("photo_1280")
        @Expose
        public String photo1280;
        @SerializedName("width")
        @Expose
        public Integer width;
        @SerializedName("height")
        @Expose
        public Integer height;
        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("date")
        @Expose
        public Integer date;
        @SerializedName("post_id")
        @Expose
        public Integer postId;
        @SerializedName("photo_2560")
        @Expose
        public String photo2560;

    }
}