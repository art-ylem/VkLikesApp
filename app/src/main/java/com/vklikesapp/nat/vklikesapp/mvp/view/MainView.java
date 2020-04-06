package com.vklikesapp.nat.vklikesapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.vklikesapp.nat.vklikesapp.model.Profile;


public interface MainView extends MvpView {
    void startSignIn();
    void signedIn();
    void showCurrentUser(Profile profile);
    void startActivityFromDrawer(Class<?> act);
}
