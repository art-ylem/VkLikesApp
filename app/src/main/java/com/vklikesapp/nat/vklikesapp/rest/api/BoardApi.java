package com.vklikesapp.nat.vklikesapp.rest.api;


import com.vklikesapp.nat.vklikesapp.model.CommentItem;
import com.vklikesapp.nat.vklikesapp.model.Topic;
import com.vklikesapp.nat.vklikesapp.rest.model.response.BaseItemResponse;
import com.vklikesapp.nat.vklikesapp.rest.model.response.Full;
import com.vklikesapp.nat.vklikesapp.rest.model.response.ItemWithSendersResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface BoardApi {
    @GET(ApiMethods.BOARD_GET_TOPICS)
    Observable<Full<BaseItemResponse<Topic>>> getTopics(@QueryMap Map<String, String> map);

    @GET(ApiMethods.BOARD_GET_COMMENTS)
    Observable<Full<ItemWithSendersResponse<CommentItem>>> getComments(@QueryMap Map<String, String> map);
}
