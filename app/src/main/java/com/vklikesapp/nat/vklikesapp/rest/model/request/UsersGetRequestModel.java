package com.vklikesapp.nat.vklikesapp.rest.model.request;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.vklikesapp.nat.vklikesapp.ApiConstants;
import com.vk.sdk.api.VKApiConst;

import java.util.ArrayList;
import java.util.Map;

public class UsersGetRequestModel extends BaseRequestModel{

    @SerializedName(VKApiConst.USER_IDS)
    ArrayList<String> userIds = new ArrayList<>();

    @SerializedName(VKApiConst.FIELDS)
    String fields = ApiConstants.DEFAULT_USER_FIELDS;

    public UsersGetRequestModel(String userId) {
        this.userIds.add(userId);
    }

    public UsersGetRequestModel(String userId, String fields) {
        this.userIds.add(userId);
        this.fields = fields;
    }

    public UsersGetRequestModel(ArrayList<String> userId) {
        this.userIds.addAll(userId);
    }

    public UsersGetRequestModel(ArrayList<String> userId, String fields) {
        this.userIds.addAll(userId);
        this.fields = fields;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(VKApiConst.USER_IDS, TextUtils.join(",", userIds));
        map.put(VKApiConst.FIELDS, getFields());
    }
}
