package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bs.vklibrary.model.Profile;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.analitycs.Analitycs;
import com.vklikesapp.nat.vklikesapp.common.utils.FirebaseLike;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.UsersPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.view.UsersView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class UsersActivity extends MvpAppCompatActivity implements UsersView{
    public static int LIKES_FOR_USER = 2;

    @InjectPresenter
    UsersPresenter usersPresenter;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, UsersActivity.class));
    }

    private UsersAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        MyApplication.getApplicationComponent().inject(this);
        adapter = new UsersAdapter(UsersActivity.this, new ArrayList<>(), sharedPreferencesManager.getUrl());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProgressBar progressBar = findViewById(R.id.progressBarUsers);

        recyclerView = findViewById(R.id.recyclerView);

        adapter.setListener(listener);
        recyclerView.setAdapter(adapter);

        showToast(getString(R.string.like_condition));

        FirebaseLike firebaseLike = new FirebaseLike(this);
        firebaseLike.setListener(new FirebaseLike.Listener() {
            @Override
            public void orderLikeCount(long count) {}

            @Override
            public void profiles(List<Profile> profiles) {
                runOnUiThread(() -> {
                    Collections.shuffle(profiles);
                    adapter.updateAdapter(profiles);
                    progressBar.setVisibility(View.GONE);

                    if (adapter.isEmpty()){
                        usersPresenter.likesOrder(LIKES_FOR_USER);
                    }
                });
            }
        });
        firebaseLike.loadOtherLikesAndUsers();
    }

    UsersAdapter.Listener listener = (int ownerId, int userId) -> {
        usersPresenter.setId(ownerId, userId);
    };

    @Override
    public void isLikeDone(int id) {
        usersPresenter.likeDone();
        Analitycs.onEventLikeToUser();

        runOnUiThread(() -> {
            adapter.removeItem(id);
            sharedPreferencesManager.addLikedId(id);
            if (adapter.isEmpty()){
                sharedPreferencesManager.changeScore(-1);
                usersPresenter.likesOrder(LIKES_FOR_USER);
            }
        });
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    protected void onStart() {
        if (adapter.isEmpty()){
            usersPresenter.emptyList();
        }
        else {
            usersPresenter.check();
        }
        super.onStart();
    }
}
