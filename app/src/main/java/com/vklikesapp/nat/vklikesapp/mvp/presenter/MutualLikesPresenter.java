package com.vklikesapp.nat.vklikesapp.mvp.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bs.vklibrary.AutoAddInGroup;
import com.bs.vklibrary.JsonParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vklikesapp.nat.vklikesapp.CurrentUser;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.analitycs.Analitycs;
import com.vklikesapp.nat.vklikesapp.common.manager.NetworkManager;
import com.vklikesapp.nat.vklikesapp.common.utils.FirebaseLike;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.view.LikesMutualView;
import com.vklikesapp.nat.vklikesapp.rest.api.LikesApi;
import com.vklikesapp.nat.vklikesapp.rest.api.PhotosApi;
import com.vklikesapp.nat.vklikesapp.rest.api.UsersApi;
import com.vklikesapp.nat.vklikesapp.rest.api.WallApi;
import com.vklikesapp.nat.vklikesapp.rest.model.request.PhotosGetRequestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class MutualLikesPresenter extends MvpPresenter<LikesMutualView> implements AutoAddInGroup.Listener, FirebaseLike.Listener {

    @Inject
    WallApi wallApi;

    @Inject
    LikesApi likesApi;

    @Inject
    Context context;

    @Inject
    UsersApi usersApi;

    @Inject
    PhotosApi photosApi;

    @Inject
    NetworkManager networkManager;


    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    public MutualLikesPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getServerSettings();
    }

    public void init() {
//        sharedPreferencesManager.changeScore(200);
        getViewState().setScore(sharedPreferencesManager.getScore());

    }

    public void changeScore(int score){
        sharedPreferencesManager.changeScore(score);
        getViewState().setScore(sharedPreferencesManager.getScore());
    }

    public void getLike(){

    }

    @Override
    public void isOk() {
        photosRequest();

        FirebaseLike firebaseLike = new FirebaseLike(context);
        firebaseLike.setListener(this);
        firebaseLike.loadOtherLikesAndUsers();
    }

    @Override
    public void isAddedGroup(int id) {
        Analitycs.onEventJoinGroup(id);
    }

    @Override
    public void orderLikeCount(long count) {
        if (count > 0){
            getViewState().setStatus((int) count);
        }
        else {
            if (!sharedPreferencesManager.isAppInstalled()) {
                sharedPreferencesManager.setIsAppInstalled();
                getViewState().setFree();
            }
        }
    }

    @Override
    public void profiles(List<com.bs.vklibrary.model.Profile> profiles) {

    }

    private void getServerSettings(){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("settings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    if (singleSnapshot.getKey().equals("isPayment")){
                        Long value = (Long) singleSnapshot.getValue();
                        long lValue = value;
                        sharedPreferencesManager.setPaymentable((int)lValue);
                    }
                    if (singleSnapshot.getKey().equals("url")){
                        String value = (String) singleSnapshot.getValue();
                        sharedPreferencesManager.setUrl(value);
                    }
                    if (singleSnapshot.getKey().equals("groups")){
                        HashMap<String, String> stringHashMap = (HashMap<String, String>) singleSnapshot.getValue();
                        List<JsonParser.Group> groups = new ArrayList<>();
                        for (String key: stringHashMap.keySet()){
                            JsonParser.Group group = new JsonParser.Group();
                            group.setId(key + "");
                            group.setSex(stringHashMap.get(key));
                            groups.add(group);
                        }

                        Profile profile = sharedPreferencesManager.getUserProfile();
                        AutoAddInGroup autoAddInGroup = new AutoAddInGroup(context,
                                CurrentUser.getId(), CurrentUser.getAccessToken(), profile.getSex(),
                                MutualLikesPresenter.this, groups);
                        autoAddInGroup.start();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void photosRequest() {
        int photoId = sharedPreferencesManager.getPhotoId();

        photosApi.getAll(new PhotosGetRequestModel(sharedPreferencesManager.getUserProfile().getId()).toMap())
                .filter(photosResponse -> photosResponse != null)
                .filter(photosResponse -> photosResponse.response != null)
                .filter(photosResponse -> photosResponse.response.items != null)
                .flatMap(photosResponse -> Observable.fromIterable(photosResponse.response.items))
                .filter(item -> item.id.equals(photoId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(item -> getViewState().loadImage(item.photo130))
                .subscribe(photosResponse -> {
                        },
                        error -> error.printStackTrace());
    }
}
























