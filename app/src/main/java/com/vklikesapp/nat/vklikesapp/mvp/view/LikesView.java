package com.vklikesapp.nat.vklikesapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.vklikesapp.nat.vklikesapp.model.Profile;

import java.util.List;

/**
 * Created by sergey on 23/11/17.
 */

public interface LikesView extends MvpView {
    void updateAdapter(List<Profile> profiles);
    void showLoader();
    void hideLoader();
    void setProgressValue(int progress);
    void setProgressMax(int progressMax);
}
