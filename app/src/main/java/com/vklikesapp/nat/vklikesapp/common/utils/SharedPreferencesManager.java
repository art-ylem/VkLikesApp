package com.vklikesapp.nat.vklikesapp.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.model.Profile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by d.melehov on 19.10.16.
 */

public class SharedPreferencesManager {

    @Inject
    Context context;

    private static final String SHARED_PREFERENCES_FILE_COMMON = "SHARED_PREFERENCES_FILE_COMMON";
    private static final String STORED_FRIENDS_ID = "STORED_FRIENDS_ID";
    private static final String USER_GROUP_SET = "USER_GROUP_SET";
    private static final String USER_PROFILE = "USER_PROFILE";
    private static final String IS_APP_ISTALLED = "IS_APP_ISTALLED";
    private static final String IS_CONTAIN_AD= "IS_CONTAIN_AD";
    private static final String SCORE = "SCORE";
    private static final String PHOTO_ID = "PHOTO_ID";
    private static final String PAYMENTABLE = "PAYMENTABLE";
    private static final String URL = "URL";

    public SharedPreferencesManager() {
        MyApplication.getApplicationComponent().inject(this);
    }

    /**
     * Получение профиля
     */
    public  Observable<Profile> getUserProfileObservable() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String storedHashMapString = shPref.getString(USER_PROFILE, "");
        if (!storedHashMapString.isEmpty()) {
            Type type = new TypeToken<Profile>() {
            }.getType();
            Observable.just(new Gson().fromJson(storedHashMapString, type));
        }

        return null;
    }
    /**
     * Получение профиля
     */
    public  Profile getUserProfile() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String storedHashMapString = shPref.getString(USER_PROFILE, "");
        if (!storedHashMapString.isEmpty()) {
            Type type = new TypeToken<Profile>() {
            }.getType();
            return  new Gson().fromJson(storedHashMapString, type);
        }

        return null;
    }

    /**
     * save UserProfile profile
     */
    public void setUserProfile(Profile userProfile) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String productBasketString = new Gson().toJson(userProfile);
        shPref.edit()
                .putString(USER_PROFILE, productBasketString)
                .apply();
    }


    /**
     * Сохранение лайкнутых ids
     */
    public void addLikedId(int id) {
        HashMap<Integer, Integer> getStoredFriendsId = getStoredFriendsId();
        if (getStoredFriendsId == null) {
            getStoredFriendsId = new HashMap<>();
        }
        getStoredFriendsId.put(id,id);
        setStoredFriendsId(getStoredFriendsId);
    }

    /**
     * Сохранение лайкнутых ids
     */
    public void setStoredFriendsId(HashMap<Integer, Integer> users) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String usersString = new Gson().toJson(users);
        shPref.edit()
                .putString(STORED_FRIENDS_ID, usersString)
                .apply();
    }

    /**
     * Получение лайкнутых ids
     */
    public HashMap<Integer, Integer> getStoredFriendsId() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String storedHashMapString = shPref.getString(STORED_FRIENDS_ID, "");
        if (!storedHashMapString.isEmpty()) {
            Type type = new TypeToken<HashMap<Integer, Integer>>() {
            }.getType();
            return new Gson().fromJson(storedHashMapString, type);
        } else {
            return null;
        }
    }

    /**
     * Список групп куда вступал
     */
    public Set<String> getUserIvitedGroup() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        Set<String> set = shPref.getStringSet(USER_GROUP_SET, new HashSet<String>());
        return set;
    }

    /**
     * save UserProfile profile
     */
    public void setUserIvitedGroup(String groupId) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);

        Set<String> set = getUserIvitedGroup();
        set.add(groupId);

        shPref.edit()
                .putStringSet(USER_GROUP_SET, set)
                .apply();
    }




    /**
     * Изменение балла
     */
    public void changeScore(int change) {
        int sum = change + getScore();

        if (sum < 0) sum = 0;

        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        shPref.edit()
                .putInt(SCORE, sum)
                .apply();
    }

    /**
     * Сколько баллов
     */
    public int getScore() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        int score = shPref.getInt(SCORE, 0);
        return score;
    }



    /**
     * Сколько баллов
     */
    public boolean isAppInstalled() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        boolean score = shPref.getBoolean(IS_APP_ISTALLED, false);
        return score;
    }

    /**
     * Сколько баллов
     */
    public void setIsAppInstalled() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        shPref.edit()
                .putBoolean(IS_APP_ISTALLED, true)
                .apply();
    }



    /**
     * Сколько баллов
     */
    public boolean isContainAd() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        boolean score = shPref.getBoolean(IS_CONTAIN_AD, true);
        return score;
    }

    /**
     * Сколько баллов
     */
    public void setContainAd() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        shPref.edit()
                .putBoolean(IS_CONTAIN_AD, false)
                .apply();
    }



    /**
     * Изменение фото
     */
    public void setPhotoId(int change) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        shPref.edit()
                .putInt(PHOTO_ID, change)
                .apply();
    }

    /**
     * Сколько фото
     */
    public int getPhotoId() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        int id = shPref.getInt(PHOTO_ID, 0);
        return id;
    }



    /**
     * Возможность оплаты
     */
    public void setPaymentable(int change) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        shPref.edit()
                .putInt(PAYMENTABLE, change)
                .apply();
    }

    public int getPaymentable() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        int id = shPref.getInt(PAYMENTABLE, 0);
        return id;
    }



    /**
     * УРЛ для загрузки
     */
    public void setUrl(String url) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        shPref.edit()
                .putString(URL, url)
                .apply();
    }

    public String getUrl() {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String url = shPref.getString(URL, "");
        return url;
    }

}
