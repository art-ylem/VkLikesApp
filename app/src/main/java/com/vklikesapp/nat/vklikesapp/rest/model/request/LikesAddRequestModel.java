package com.vklikesapp.nat.vklikesapp.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.Map;

/**
 * Created by user on 14.08.2017.
 */

public class LikesAddRequestModel extends BaseRequestModel {

    @SerializedName(VKApiConst.OWNER_ID)
    int ownerId;

    @SerializedName(VKApiConst.TYPE)
    int itemId;

    @SerializedName(VKApiConst.TYPE)
    String type = "photo";

    public LikesAddRequestModel(int ownerId, int itemId) {
        this.ownerId = ownerId;
        this.itemId = itemId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }


    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(VKApiConst.OWNER_ID, String.valueOf(getOwnerId()));
        map.put(VKApiConst.ITEM_ID, String.valueOf(itemId));
        map.put(VKApiConst.TYPE, type);
    }
}
