package com.vklikesapp.nat.vklikesapp.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vklikesapp.nat.vklikesapp.CurrentUser;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.common.manager.NetworkManager;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.view.UsersView;
import com.vklikesapp.nat.vklikesapp.rest.api.LikesApi;
import com.vklikesapp.nat.vklikesapp.rest.model.request.LikesGetRequestModel;
import com.vklikesapp.nat.vklikesapp.rest.model.response.LikesResponse;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class UsersPresenter extends MvpPresenter<UsersView> {

    private int ownerId;
    private int postId;
    private final static String TAG = "UserPresenter";
    @Inject
    LikesApi likesApi;

    @Inject
    NetworkManager networkManager;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    public UsersPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    public void changeScore(int score){
        sharedPreferencesManager.changeScore(score);
    }

    public void check(){
        networkManager.getNetworkObservable()
                .flatMap(aBoolean -> {
                    return likesApi.get(new LikesGetRequestModel(ownerId, postId).toMap())
                            .doOnNext(this::countLikes)
                            .observeOn(AndroidSchedulers.mainThread())
                            .observeOn(Schedulers.io());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                }, error -> error.printStackTrace());
    }

    public void emptyList(){
        getViewState().isLikeDone(ownerId);
    }

    public void likesOrder(int amount){
        Log.d(TAG, "likesOrder: ");
        Profile currentUser = sharedPreferencesManager.getUserProfile();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users").child(String.valueOf(currentUser.getId()));


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Long> stringLongHashMap = new HashMap<>();

                long count;
                if (dataSnapshot.getValue() == null){
                    count = Long.valueOf(amount);
                }
                else {
                    HashMap<String, Long> dataFromSrever = (HashMap<String, Long>) dataSnapshot.getValue();
                    count = dataFromSrever.get("count") + amount;
                }

                if (sharedPreferencesManager.getPhotoId() <= 0){
                    getViewState().showToast("Для оформления закакза выберите Ваше фото");
                    return;
                }

                stringLongHashMap.put("count", count);
                stringLongHashMap.put("photo", (long) sharedPreferencesManager.getPhotoId());

                ref.setValue(stringLongHashMap);
                getViewState().showToast("Заказ на лайки оформлен");
                changeScore(-1);
                getViewState().finishActivity();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                getViewState().showToast("Произошла ошибка");
            }
        });
    }
    public void likeDone(){
        Log.d(TAG, "likeDone: ");
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users").child(String.valueOf(ownerId));


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                HashMap<String, Long> stringLongHashMap = new HashMap<>();
                if (dataSnapshot.getValue() != null){

                    HashMap<String, Long> dataFromSrever = (HashMap<String, Long>) dataSnapshot.getValue();

                    long count = dataFromSrever.get("count") - 1;
                    stringLongHashMap.put("count", count);
                    stringLongHashMap.put("photo", dataFromSrever.get("photo"));

                    if (count == 0){
                        stringLongHashMap = null;
                    }
                }

                if (stringLongHashMap == null || stringLongHashMap.size() == 0){
                    stringLongHashMap = null;
                }

                ref.setValue(stringLongHashMap);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR","Произошла ошибка");
            }
        });
    }

    public void setId(int ownerId, int postId){
        this.ownerId = ownerId;
        this.postId = postId;
    }


    private boolean checkLikes(LikesResponse likesResponse) {

        int currentUser = Integer.parseInt(CurrentUser.getId());
        for (LikesResponse.LikeItem likeItem : likesResponse.response.items) {
            int id = likeItem.id;

            if (currentUser == id) {
                return true;
            }
        }
        return false;
    }

    private void countLikes(LikesResponse likesResponse) {

        boolean isCorrect = checkLikes(likesResponse);
        if (isCorrect){
            getViewState().isLikeDone(ownerId);
        }
    }

}
























