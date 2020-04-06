package com.vklikesapp.nat.vklikesapp.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.common.manager.NetworkManager;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.model.WallItem;
import com.vklikesapp.nat.vklikesapp.mvp.view.LikesView;
import com.vklikesapp.nat.vklikesapp.rest.api.LikesApi;
import com.vklikesapp.nat.vklikesapp.rest.api.UsersApi;
import com.vklikesapp.nat.vklikesapp.rest.api.WallApi;
import com.vklikesapp.nat.vklikesapp.rest.model.request.LikesGetRequestModel;
import com.vklikesapp.nat.vklikesapp.rest.model.request.UsersGetRequestModel;
import com.vklikesapp.nat.vklikesapp.rest.model.request.WallGetRequestModel;
import com.vklikesapp.nat.vklikesapp.rest.model.response.GetWallResponse;
import com.vklikesapp.nat.vklikesapp.rest.model.response.LikesResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class LikesPresenter extends MvpPresenter<LikesView> {
    private static final int DELAY_TIME = 250;
    private static int MAX_ITEMS = 50;

    @Inject
    WallApi wallApi;

    @Inject
    LikesApi likesApi;

    @Inject
    UsersApi usersApi;

    @Inject
    NetworkManager networkManager;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    HashMap<Integer, Integer> countLikes = new HashMap<>();

    private List<WallItem> likeItems = new ArrayList<>();

    AtomicInteger counter = new AtomicInteger();

    public LikesPresenter() {
        MyApplication.getApplicationComponent().inject(this);
    }

    public void init() {
        counter.set(0);
        getViewState().showLoader();
        onCreateLoadDataObservable();
    }

    public void onCreateLoadDataObservable() {
        networkManager.getNetworkObservable()
                .flatMap(aBoolean -> loadWall())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                }, error -> error.printStackTrace());
    }

    private Observable<GetWallResponse> loadWall(){
        return wallApi.get(new WallGetRequestModel(sharedPreferencesManager.getUserProfile().getId(), MAX_ITEMS).toMap())
                .doOnNext(this::loadLikes);
    }

    private void loadLikes(GetWallResponse getWallResponse){
        likeItems = getWallResponse.response.items;

        if (likeItems.size() < MAX_ITEMS){
            MAX_ITEMS = likeItems.size();
        }
        getViewState().setProgressMax(MAX_ITEMS - 1);
        nextLikeLoading();
    }

    private int position;
    private long lastTime;
    private void nextLikeLoading(){
        if (position < likeItems.size()) {
            long difference = System.currentTimeMillis() - lastTime;
            if (difference < DELAY_TIME) {
                try {
                    Thread.sleep(DELAY_TIME - difference);
                    Log.e("DELAY", "DELAY - " + (DELAY_TIME - difference));
                    lastTime = System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getViewState().setProgressValue(position);
            checkNetworkForLikes(likeItems.get(position));
            position++;
        }
    }

    private void checkNetworkForLikes(WallItem wallItem){
        networkManager.getNetworkObservable()
                .flatMap(aBoolean -> loadLike(wallItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                }, error -> {
                    error.printStackTrace();
                    sleep();
                    checkNetworkForLikes(wallItem);
                });
    }

    private void sleep(){
        try {
            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Observable<LikesResponse> loadLike(WallItem wallItem){
         return likesApi.get(new LikesGetRequestModel(wallItem.getOwnerId(), wallItem.getId()).toMap())
                .doOnNext(likesResponse -> nextLikeLoading())
                .doOnNext(this::countLikes)
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io());
    }


    public void loadUsers(ArrayList<String> userIds) {

        try {
            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        networkManager
                .getNetworkObservable()
                .flatMap(aBoolean ->
                        usersApi.get(new UsersGetRequestModel(userIds).toMap())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext(listFull -> setLikesToUsers(listFull.response))
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                }, error -> error.printStackTrace());
    }

    private void setLikesToUsers(List<Profile> profiles){
        for (Profile profile : profiles) {
            profile.setLikesForYou(countLikes.get(profile.getId()));
        }

        Collections.sort(profiles, (p1, p2) -> {
            if (p1.getLikesForYou() > p2.getLikesForYou()){
                return -1;
            }
            else if (p1.getLikesForYou() < p2.getLikesForYou()){
                return 1;
            }
            return 0;
        });

        getViewState().hideLoader();
        getViewState().updateAdapter(profiles);
    }

    private void countLikes(LikesResponse likesResponse) {

        for (LikesResponse.LikeItem likeItem : likesResponse.response.items) {
            if (countLikes.containsKey(likeItem.id)) {
                countLikes.put(likeItem.id, countLikes.get(likeItem.id) + 1);
            } else {
                countLikes.put(likeItem.id, 1);
            }
        }

        counter.incrementAndGet();

        if (counter.get() >= MAX_ITEMS){
            ArrayList<String> userIds = new ArrayList<>();

            for (Integer key : countLikes.keySet()){
                userIds.add(String.valueOf(key));
            }

            loadUsers(userIds);
        }
    }

}
























