package com.vklikesapp.nat.vklikesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vklikesapp.nat.vklikesapp.model.attachment.ApiAttachment;
import com.vklikesapp.nat.vklikesapp.model.countable.Likes;
import com.vklikesapp.nat.vklikesapp.model.countable.Reposts;

import java.util.ArrayList;

public class CommentItem {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("from_id")
    @Expose
    public int senderId;

    public String senderName;

    public String senderPhoto;

    @SerializedName("date")
    @Expose
    public int date;
    @SerializedName("text")
    @Expose
    public String text;

    @SerializedName("attachments")
    @Expose
    public ArrayList<ApiAttachment> attachments = new ArrayList<>();

    public String attachmentsString;

    @SerializedName("likes")
    @Expose
    public Likes likes;


    @SerializedName("reposts")
    @Expose
    public Reposts reposts;

    public boolean isFromTopic = false;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return senderId;
    }



    public Integer getDate() {
        return date;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getDisplayText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<ApiAttachment> getAttachments() {
        return attachments;
    }




    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Reposts getReposts() {
        return reposts;
    }

    public void setReposts(Reposts reposts) {
        this.reposts = reposts;
    }


    public String getDisplayAttachmentsString() {
        return attachmentsString;
    }





    public void setAttachmentsString(String attachmentsString) {
        this.attachmentsString = attachmentsString;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderPhoto() {
        return senderPhoto;
    }



    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public String getPhoto() {
        return senderPhoto;
    }

    public void setIsFromTopic(boolean isTopic) {
        this.isFromTopic = isTopic;
    }
}