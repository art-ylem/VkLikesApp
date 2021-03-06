
package com.vklikesapp.nat.vklikesapp.model.countable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Likes{

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("user_likes")
    @Expose
    private int userLikes;
    @SerializedName("can_like")
    @Expose
    private int canLike;
    @SerializedName("can_publish")
    @Expose
    private int canPublish;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(int userLikes) {
        this.userLikes = userLikes;
    }

    public int getCanLike() {
        return canLike;
    }

    public void setCanLike(int canLike) {
        this.canLike = canLike;
    }

    public int getCanPublish() {
        return canPublish;
    }

    public void setCanPublish(int canPublish) {
        this.canPublish = canPublish;
    }

    public boolean isUserLikes () {
        return userLikes == 1;
    }

}
