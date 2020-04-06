package com.vklikesapp.nat.vklikesapp.di.component;

import com.vklikesapp.nat.vklikesapp.MainActivity;
import com.vklikesapp.nat.vklikesapp.common.manager.NetworkManager;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.di.module.ApplicationModule;
import com.vklikesapp.nat.vklikesapp.di.module.ManagerModule;
import com.vklikesapp.nat.vklikesapp.di.module.RestModule;
import com.vklikesapp.nat.vklikesapp.likes.LikesActivity;
import com.vklikesapp.nat.vklikesapp.likes.MutualLikesActivity;
import com.vklikesapp.nat.vklikesapp.likes.PayActivity;
import com.vklikesapp.nat.vklikesapp.likes.SelectPhotoActivity;
import com.vklikesapp.nat.vklikesapp.likes.UsersActivity;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.LikesPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.MainPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.MutualLikesPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.UsersPresenter;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(
        modules = {ApplicationModule.class, RestModule.class, ManagerModule.class})
public interface ApplicationComponent {

    //activities
    void inject(MutualLikesPresenter activity);
    void inject(MutualLikesActivity activity);
    void inject(SelectPhotoActivity activity);
    void inject(PayActivity activity);

    void inject(LikesPresenter activity);
    void inject(LikesActivity activity);

    void inject(UsersPresenter activity);
    void inject(UsersActivity activity);

    void inject(MainActivity activity);
    void inject(NetworkManager networkManager);
    void inject(MainPresenter networkManager);

    void inject(SharedPreferencesManager sharedPreferencesManager);
}
