package com.vklikesapp.nat.vklikesapp.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.Map;

/**
 * Created by user on 14.08.2017.
 */

public class LikesGetRequestModel extends BaseRequestModel {

    @SerializedName(VKApiConst.OWNER_ID)
    int ownerId;

    @SerializedName(VKApiConst.TYPE)
    int itemId;

    @SerializedName(VKApiConst.EXTENDED)
    int extended = 1;

    @SerializedName(VKApiConst.FILTERS)
    String filter = "likes";

    @SerializedName(VKApiConst.TYPE)
    String type = "photo";

    @SerializedName(VKApiConst.COUNT)
    int count = 1000;

    public LikesGetRequestModel(int ownerId, int itemId) {
        this.ownerId = ownerId;
        this.itemId = itemId;
    }

    public LikesGetRequestModel(int ownerId, int itemId, String filter) {
        this.ownerId = ownerId;
        this.itemId = itemId;
        this.filter = filter;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getExtended() {
        return extended;
    }

    public void setExtended(int extended) {
        this.extended = extended;
    }


    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(VKApiConst.OWNER_ID, String.valueOf(getOwnerId()));
        map.put(VKApiConst.EXTENDED, String.valueOf(getExtended()));
        map.put(VKApiConst.FILTERS, filter);
        map.put(VKApiConst.ITEM_ID, String.valueOf(itemId));
        map.put(VKApiConst.TYPE, type);
    }
}
