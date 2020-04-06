package com.vklikesapp.nat.vklikesapp.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by sergey on 23/11/17.
 */

public interface LikesMutualView extends MvpView {
    void loadRewardedVideoAd();
    void likesOrder(int amount);
    void showToast(String msg);
    void showLoader();
    void hideLoader();
    void loadImage(String url);
    void setProgressValue(int progress);
    void setProgressMax(int progressMax);

    void setScore(int score);
    void setStatus(int likeAmount);
    void setFree();
    void hideFree();
}
