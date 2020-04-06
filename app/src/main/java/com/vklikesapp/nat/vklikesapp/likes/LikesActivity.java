package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.common.utils.UiHelper;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.LikesPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.view.LikesView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LikesActivity extends MvpAppCompatActivity implements LikesView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.info)
    ImageView info;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBarTitle)
    TextView progressBarTitle;
    @BindView(R.id.adView)
    AdView adView;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LikesActivity.class));
    }

    @InjectPresenter
    LikesPresenter likesPresenter;

    private LikesAdapter likesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        ButterKnife.bind(this);

        MyApplication.getApplicationComponent().inject(this);
        likesPresenter.init();

        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("14254AD3E1ABD887329C75222D8C3E26").
                build();
        adView.loadAd(adRequest);
    }

    @Override
    public void updateAdapter(List<Profile> profiles) {
        if (likesAdapter == null) {
            likesAdapter = new LikesAdapter(this, profiles);
            recyclerView.setAdapter(likesAdapter);
        } else {
            likesAdapter.updateAdapter(profiles);
        }
    }

    @Override
    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
        progressBarTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
        progressBarTitle.setVisibility(View.GONE);
    }

    @Override
    public void setProgressValue(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void setProgressMax(int progressMax) {
        progressBar.setMax(progressMax);
    }

    @OnClick({R.id.toolbar_title, R.id.info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info:

                UiHelper.seeAlertDialogJoinVkGroup(this,
                        getString(R.string.title_alert_join_vk_group),
                        getString(R.string.text_alert_join_vk_group),
                        getString(R.string.yes),
                        getString(R.string.no)
                );

                break;
        }
    }
}

