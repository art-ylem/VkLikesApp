package com.vklikesapp.nat.vklikesapp.likes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.ad.AdDialog;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.common.utils.Utils;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.MutualLikesPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.view.LikesMutualView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MutualLikesActivity extends AdActivity implements LikesMutualView {

    @BindView(R.id.info)
    ImageView info;
    @BindView(R.id.group)
    ImageView group;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.adView)
    AdView adView;
    @BindView(R.id.score)
    TextView scoreText;
    @BindView(R.id.friend_button)
    Button friendButton;
    @BindView(R.id.progressBarLittle)
    ProgressBar progressBarLittle;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.photo_container)
    RelativeLayout photoContainer;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MutualLikesActivity.class));
    }

    @InjectPresenter
    MutualLikesPresenter likesPresenter;

    MutualLikesAdapter mutualLikesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_likes);

        ButterKnife.bind(this);
        MyApplication.getApplicationComponent().inject(this);

        if (sharedPreferencesManager.isContainAd()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }


    @Override
    protected void onStart() {
        init();
        super.onStart();
    }

    private void init() {
        loadPhoto();
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        likesPresenter.init();

                        mutualLikesAdapter = new MutualLikesAdapter(MutualLikesActivity.this, MutualLikesActivity.this, sharedPreferencesManager);
                        recyclerView.setAdapter(mutualLikesAdapter);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MutualLikesActivity.this, "У приложения нет прав, переустановите его", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Toast.makeText(MutualLikesActivity.this, "У приложения нет прав, переустановите его", Toast.LENGTH_SHORT).show();
                    }
                })
                .check();
    }

    private void loadPhoto(){
        Profile profile = sharedPreferencesManager.getUserProfile();

    }


    @Override
    public void loadRewardedVideoAd() {
        if (sharedPreferencesManager.isContainAd()) {
            showLoader();
            showRewardedVideo();
        }
        else {
            showToast("Вы можете просто нажать Получить лайки");
        }
    }

    @Override
    public void likesOrder(int amount) {
        likesPresenter.changeScore(amount);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }


    public void loader() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void addScore() {
        likesPresenter.changeScore(1);
    }

    @Override
    public void loadImage(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(photo);
    }

    @Override
    public void setProgressValue(int progress) {

    }

    @Override
    public void setProgressMax(int progressMax) {

    }

    @Override
    public void setScore(int score) {
        scoreText.setText("" + score);
        if (score < 1) {
            friendButton.setAlpha(0.5f);
            friendButton.setEnabled(false);
        } else {
            friendButton.setAlpha(1);
            friendButton.setEnabled(true);
        }

        if (!sharedPreferencesManager.isContainAd()){
            friendButton.setAlpha(1);
            friendButton.setEnabled(true);
        }
    }

    @Override
    public void setStatus(int likeAmount) {
        if (mutualLikesAdapter != null) {
            mutualLikesAdapter.updateStatus(likeAmount);
        }
    }

    @Override
    public void setFree() {
        if (mutualLikesAdapter != null) {
            mutualLikesAdapter.updateFree();
        }
    }

    @Override
    public void hideFree() {
        if (mutualLikesAdapter != null) {
            mutualLikesAdapter.hideFree();
        }
    }


    @OnClick({R.id.group, R.id.info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info:
                Utils.seeAlertDialogOneButton(
                        this,
                        getString(R.string.title_alert_how_it_works),
                        getString(R.string.how_it_works),
                        getString(R.string.ok));
                break;

            case R.id.group:
                AdDialog adDialog = new AdDialog();
                adDialog.show(getSupportFragmentManager(), "");
                break;
        }
    }




    @OnClick({R.id.photo_container, R.id.friend_button})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.photo_container:
                SelectPhotoActivity.startActivity(this);
                break;
            case R.id.friend_button:
                if (sharedPreferencesManager.getPhotoId() <= 0){
                    showToast("Сначала выберите фото");
                }
                else {
                    UsersActivity.startActivity(this);
                }
                break;
        }
    }
}

