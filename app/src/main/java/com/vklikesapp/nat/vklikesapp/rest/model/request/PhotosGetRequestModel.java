package com.vklikesapp.nat.vklikesapp.rest.model.request;

import com.google.gson.annotations.SerializedName;
import com.vk.sdk.api.VKApiConst;

import java.util.Map;

/**
 * Created by user on 14.08.2017.
 */

public class PhotosGetRequestModel extends BaseRequestModel {
    @SerializedName(VKApiConst.OWNER_ID)
    int ownerId;

    @SerializedName("skip_hidden")
    int skipHidden = 1;

    @SerializedName(VKApiConst.COUNT)
    int count = 200;

    public PhotosGetRequestModel(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getSkipHidden() {
        return skipHidden;
    }

    public int getCount() {
        return count;
    }

    public int getOwnerId() {
        return ownerId;
    }

    @Override
    public void onMapCreate(Map<String, String> map) {
        map.put(VKApiConst.OWNER_ID, String.valueOf(getOwnerId()));
        map.put(VKApiConst.COUNT, String.valueOf(getCount()));
        map.put("skip_hidden", String.valueOf(getSkipHidden()));
    }
}
