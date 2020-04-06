package com.vklikesapp.nat.vklikesapp.rest.model.response;

/**
 * Created by sergey on 24/11/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LikesResponse {

    @SerializedName("response")
    @Expose
    public Response response;

    public class Response {

        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("items")
        @Expose
        public List<LikeItem> items = null;

    }

    public class LikeItem {

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;

    }

}
