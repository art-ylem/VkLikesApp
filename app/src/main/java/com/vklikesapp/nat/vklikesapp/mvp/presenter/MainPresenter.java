package com.vklikesapp.nat.vklikesapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vklikesapp.nat.vklikesapp.CurrentUser;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.common.manager.NetworkManager;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.view.MainView;
import com.vklikesapp.nat.vklikesapp.rest.api.UsersApi;
import com.vklikesapp.nat.vklikesapp.rest.model.request.UsersGetRequestModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    UsersApi mUserApi;

    @Inject
    NetworkManager mNetworkManager;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    public void checkAuth() {
        if (!CurrentUser.isAuthorized()) {
            getViewState().startSignIn();
        } else {
            getCurrentUser();
            getViewState().signedIn();
        }
    }

    public MainPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    public Observable<Profile> getProfileFromNetwork() {
        return mUserApi.get(new UsersGetRequestModel(CurrentUser.getId(), "crop_photo,sex").toMap())
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
                .doOnNext(this::saveProfile);
    }


    public void saveProfile(Profile item) {
        if (sharedPreferencesManager.getPhotoId() <= 0){
            if (item.getCropProfile() != null && item.getCropProfile().getPhoto() != null) {
                sharedPreferencesManager.setPhotoId(item.getCropProfile().getPhoto().getId());
            }
        }
        sharedPreferencesManager.setUserProfile(item);
    }

    private void getCurrentUser() {
        mNetworkManager.getNetworkObservable()
                .flatMap(aBoolean ->  {
                    if (!CurrentUser.isAuthorized()) {
                        getViewState().startSignIn();
                    }
                    return aBoolean
                            ? getProfileFromNetwork()
                            : sharedPreferencesManager.getUserProfileObservable();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    getViewState().showCurrentUser(profile);
                }, error -> {
                    error.printStackTrace();
                });
    }

}
























