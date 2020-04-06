package com.vklikesapp.nat.vklikesapp.rest.api;

import com.vklikesapp.nat.vklikesapp.rest.model.response.GetNewsfeedResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface NewsfeedApi {
    @GET(ApiMethods.NEWSFEED_GET)
    Observable<GetNewsfeedResponse> get(@QueryMap Map<String, String> map);
}
