package com.bs.vklibrary.net;


import com.bs.vklibrary.AutoAddInGroup;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GroupsApi {

    @GET("groups.get")
    Observable<AutoAddInGroup.GroupsResponse> get(@QueryMap Map<String, String> map);

    @GET("groups.join")
    Observable<Object> join(@QueryMap Map<String, String> map);
}
