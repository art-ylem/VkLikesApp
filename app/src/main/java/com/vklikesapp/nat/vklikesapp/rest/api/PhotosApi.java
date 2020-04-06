package com.vklikesapp.nat.vklikesapp.rest.api;


import com.vklikesapp.nat.vklikesapp.rest.model.response.PhotosResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PhotosApi {
    @GET(ApiMethods.PHOTOS_GET_ALL)
    Observable<PhotosResponse> getAll(@QueryMap Map<String, String> map);
}
