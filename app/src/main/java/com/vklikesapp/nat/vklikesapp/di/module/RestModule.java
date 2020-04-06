package com.vklikesapp.nat.vklikesapp.di.module;

import com.vklikesapp.nat.vklikesapp.rest.RestClient;
import com.vklikesapp.nat.vklikesapp.rest.api.AccountApi;
import com.vklikesapp.nat.vklikesapp.rest.api.BoardApi;
import com.vklikesapp.nat.vklikesapp.rest.api.GroupsApi;
import com.vklikesapp.nat.vklikesapp.rest.api.LikesApi;
import com.vklikesapp.nat.vklikesapp.rest.api.NewsfeedApi;
import com.vklikesapp.nat.vklikesapp.rest.api.PhotosApi;
import com.vklikesapp.nat.vklikesapp.rest.api.UsersApi;
import com.vklikesapp.nat.vklikesapp.rest.api.VideoApi;
import com.vklikesapp.nat.vklikesapp.rest.api.WallApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RestModule {
    private RestClient mRestClient;


    public RestModule() {
        mRestClient = new RestClient();
    }


    @Provides
    @Singleton
    public RestClient provideRestClient() {
        return mRestClient;
    }

    @Provides
    @Singleton
    public WallApi provideWallApi() {
        return mRestClient.createService(WallApi.class);
    }

    @Provides
    @Singleton
    public NewsfeedApi provideNewsfeedApi() {
        return mRestClient.createService(NewsfeedApi.class);
    }

    @Provides
    @Singleton
    public UsersApi provideUsersApi() {
        return mRestClient.createService(UsersApi.class);
    }

    @Provides
    @Singleton
    public GroupsApi provideGroupsApi() {
        return mRestClient.createService(GroupsApi.class);
    }

    @Provides
    @Singleton
    public BoardApi provideBoardApi(){
        return mRestClient.createService(BoardApi.class);
    }

    @Provides
    @Singleton
    public VideoApi provideVideoApi() {
        return mRestClient.createService(VideoApi.class);
    }

    @Provides
    @Singleton
    public AccountApi provideAccountApi() {
        return mRestClient.createService(AccountApi.class);
    }

    @Provides
    @Singleton
    public LikesApi provideLikesApi() {
        return mRestClient.createService(LikesApi.class);
    }

    @Provides
    @Singleton
    public PhotosApi providePhotosApi() {
        return mRestClient.createService(PhotosApi.class);
    }

}






















