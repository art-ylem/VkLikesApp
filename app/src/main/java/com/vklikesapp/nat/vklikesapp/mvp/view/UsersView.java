package com.vklikesapp.nat.vklikesapp.mvp.view;

import com.arellomobile.mvp.MvpView;


public interface UsersView extends MvpView {
    void isLikeDone(int id);
    void showToast(String msg);
    void finishActivity();
}
