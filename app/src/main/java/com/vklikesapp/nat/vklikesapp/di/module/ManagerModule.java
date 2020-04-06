package com.vklikesapp.nat.vklikesapp.di.module;

import com.vklikesapp.nat.vklikesapp.common.manager.NetworkManager;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ManagerModule {

    @Provides
    @Singleton
    NetworkManager provideNetworkManager() {
        return new NetworkManager();
    }


    @Provides
    @Singleton
    SharedPreferencesManager provideSharedPreferencesManager() {
        return new SharedPreferencesManager();
    }
}
