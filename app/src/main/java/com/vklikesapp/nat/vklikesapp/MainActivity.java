package com.vklikesapp.nat.vklikesapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.crashlytics.android.Crashlytics;
import com.vklikesapp.nat.vklikesapp.likes.MutualLikesActivity;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.MainPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.view.MainView;
import com.vklikesapp.nat.vklikesapp.rest.api.AccountApi;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mPresenter;

    @Inject
    AccountApi mAccountApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        MyApplication.getApplicationComponent().inject(this);

        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", fingerprints[0]);
        clipboard.setPrimaryClip(clip);

        mPresenter.checkAuth();
    }

    @Override
    public void startSignIn() {
        VKSdk.login(this, ApiConstants.DEFAULT_LOGIN_SCOPE);
    }

    @Override
    public void signedIn() {

    }

    @Override
    public void showCurrentUser(Profile profile) {
        MutualLikesActivity.startActivity(MainActivity.this);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                mPresenter.checkAuth();
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void startActivityFromDrawer(Class<?> act) {
        startActivity(new Intent(MainActivity.this, act));
    }
}
















