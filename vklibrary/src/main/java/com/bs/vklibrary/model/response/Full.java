package com.bs.vklibrary.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 11.08.2017.
 */

public class Full<T> {
    @SerializedName("response")
    @Expose
    public T response;

}
