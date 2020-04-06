package com.vklikesapp.nat.vklikesapp.rest.api;


import com.vklikesapp.nat.vklikesapp.model.Group;
import com.vklikesapp.nat.vklikesapp.model.Member;
import com.vklikesapp.nat.vklikesapp.rest.model.response.BaseItemResponse;
import com.vklikesapp.nat.vklikesapp.rest.model.response.Full;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GroupsApi {

    @GET(ApiMethods.GROUPS_GET_MEMBERS)
    Observable<Full<BaseItemResponse<Member>>> getMembers(@QueryMap Map<String, String> map);

    @GET(ApiMethods.GROUPS_GET_BY_ID)
    Observable<Full<List<Group>>> getById(@QueryMap Map<String, String> map);

    @GET(ApiMethods.GROUPS_JOIN)
    Observable<BaseItemResponse<Object>> join(@QueryMap Map<String, String> map);
}
