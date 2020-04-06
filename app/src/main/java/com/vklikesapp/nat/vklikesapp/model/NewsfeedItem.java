
package com.vklikesapp.nat.vklikesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vklikesapp.nat.vklikesapp.model.attachment.ApiAttachment;
import com.vklikesapp.nat.vklikesapp.model.countable.AudioGroup;
import com.vklikesapp.nat.vklikesapp.model.countable.Comments;
import com.vklikesapp.nat.vklikesapp.model.countable.Likes;
import com.vklikesapp.nat.vklikesapp.model.countable.PhotosGroup;
import com.vklikesapp.nat.vklikesapp.model.countable.Reposts;
import com.vklikesapp.nat.vklikesapp.model.countable.VideoGroup;

import java.util.ArrayList;

public class NewsfeedItem {

    private String attachmentsString;
    private String senderName;
    private String senderPhoto;

    @SerializedName("post_id")
    @Expose
    private int id;
    @SerializedName("source_id")
    @Expose
    private int sourceId;
    @SerializedName("owner_id")
    @Expose
    private int ownerId;
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("marked_as_ads")
    @Expose
    private int markedAsAds;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("can_pin")
    @Expose
    private int canPin;
    @SerializedName("attachments")
    @Expose
    private ArrayList<ApiAttachment> attachments = new ArrayList<>();

    @SerializedName("copy_history")
    @Expose
    private ArrayList<NewsfeedItem> copyHistory = new ArrayList<>();

    @SerializedName("post_source")
    @Expose
    private PostSource postSource = new PostSource();
    @SerializedName("comments")
    @Expose
    private Comments comments = new Comments();
    @SerializedName("likes")
    @Expose
    private Likes likes = new Likes();
    @SerializedName("video")
    @Expose
    private VideoGroup videoGroup = new VideoGroup();
    @SerializedName("audio")
    @Expose
    private AudioGroup audioGroup = new AudioGroup();
    @SerializedName("photos")
    @Expose
    private PhotosGroup photosGroup = new PhotosGroup();
    @SerializedName("reposts")
    @Expose
    private Reposts reposts = new Reposts();
    @SerializedName("views")
    @Expose
    private Views views = new Views();

    public String getAttachmentsString() {
        return attachmentsString;
    }

    public void setAttachmentsString(String attachmentsString) {
        this.attachmentsString = attachmentsString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMarkedAsAds() {
        return markedAsAds;
    }

    public void setMarkedAsAds(int markedAsAds) {
        this.markedAsAds = markedAsAds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCanPin() {
        return canPin;
    }

    public void setCanPin(int canPin) {
        this.canPin = canPin;
    }

    public ArrayList<ApiAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<ApiAttachment> attachments) {
        this.attachments = attachments;
    }

    public PostSource getPostSource() {
        return postSource;
    }

    public void setPostSource(PostSource postSource) {
        this.postSource = postSource;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Likes getLikes() {
        return likes;
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

    public Views getViews() {
        return views;
    }

    public void setViews(Views views) {
        this.views = views;
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

    public boolean haveSharedRepost() {
        return copyHistory.size() > 0;
    }

    public NewsfeedItem getSharedRepost() {
        if (haveSharedRepost()) {
            return copyHistory.get(0);
        }
        return null;
    }

    public VideoGroup getVideoGroup() {
        return videoGroup;
    }

    public void setVideoGroup(VideoGroup videoGroup) {
        this.videoGroup = videoGroup;
    }

    public AudioGroup getAudioGroup() {
        return audioGroup;
    }

    public void setAudioGroup(AudioGroup audioGroup) {
        this.audioGroup = audioGroup;
    }

    public PhotosGroup getPhotosGroup() {
        return photosGroup;
    }

    public void setPhotosGroup(PhotosGroup photosGroup) {
        this.photosGroup = photosGroup;
    }
}






