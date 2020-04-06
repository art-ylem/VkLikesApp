
package com.vklikesapp.nat.vklikesapp.model.countable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments{

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("can_post")
    @Expose
    private int canPost;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCanPost() {
        return canPost;
    }

    public void setCanPost(int canPost) {
        this.canPost = canPost;
    }

}
