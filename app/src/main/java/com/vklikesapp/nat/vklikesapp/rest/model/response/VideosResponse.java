package com.vklikesapp.nat.vklikesapp.rest.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vklikesapp.nat.vklikesapp.model.attachment.video.Video;

import java.util.List;

public class VideosResponse {
    public int count;
    @SerializedName("items")
    @Expose
    public List<Video> items;
}