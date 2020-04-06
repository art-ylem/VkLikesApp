package com.vklikesapp.nat.vklikesapp.rest.api;


import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.rest.model.response.Full;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface UsersApi {

    @GET(ApiMethods.USERS_GET)
    Observable<Full<List<Profile>>> get(@QueryMap Map<String, String> map);
}
