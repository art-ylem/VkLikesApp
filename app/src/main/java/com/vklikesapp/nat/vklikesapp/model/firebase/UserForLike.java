package com.vklikesapp.nat.vklikesapp.model.firebase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sergey on 01/12/17.
 */

public class UserForLike {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("count")
    @Expose
    private int count;
}
