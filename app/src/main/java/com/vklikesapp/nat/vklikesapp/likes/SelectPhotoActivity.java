package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.rest.api.PhotosApi;
import com.vklikesapp.nat.vklikesapp.rest.model.request.PhotosGetRequestModel;
import com.vklikesapp.nat.vklikesapp.rest.model.response.PhotosResponse;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectPhotoActivity extends AppCompatActivity {

    @Inject
    PhotosApi photosApi;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ready_button)
    AppCompatTextView readyButton;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SelectPhotoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        MyApplication.getApplicationComponent().inject(this);
        ButterKnife.bind(this);
        photosRequest();
    }

    private void photosRequest() {
        photosApi.getAll(new PhotosGetRequestModel(sharedPreferencesManager.getUserProfile().getId()).toMap())
                .filter(photosResponse -> photosResponse != null)
                .filter(photosResponse -> photosResponse.response != null)
                .filter(photosResponse -> photosResponse.response.items != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(photosResponse -> loadPhotos(photosResponse.response.items))
                .subscribe(photosResponse -> {
                        },
                        error -> error.printStackTrace());
    }


    private void loadPhotos(List<PhotosResponse.Item> items) {
        SelectPhotoAdapter selectPhotoAdapter = new SelectPhotoAdapter(this, items, sharedPreferencesManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(selectPhotoAdapter);
    }

    @OnClick(R.id.ready_button)
    public void onViewClicked() {
        onBackPressed();
    }
}
