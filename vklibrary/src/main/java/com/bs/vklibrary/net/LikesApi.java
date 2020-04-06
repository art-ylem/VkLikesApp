package com.bs.vklibrary.net;


import com.bs.vklibrary.model.response.LikesResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface LikesApi {

    @GET(ApiMethods.LIKES_GET_LIST)
    Observable<LikesResponse> get(@QueryMap Map<String, String> map);

    @GET(ApiMethods.LIKES_ADD)
    Observable<LikesResponse> add(@QueryMap Map<String, String> map);
}
