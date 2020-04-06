package com.bs.vklibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by d.melehov on 19.10.16.
 */

public class SharedPreferencesManager {

    Context context;

    private static final String SHARED_PREFERENCES_FILE_COMMON = "SHARED_PREFERENCES_FILE_COMMON";
    private static final String STORED_FRIENDS_ID = "STORED_FRIENDS_ID";
    private static final String USER_GROUP_SET = "USER_GROUP_SET";
    private static final String USER_PROFILE = "USER_PROFILE";

    public SharedPreferencesManager(Context context) {
        this.context = context;
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
     * Сохранение лайкнутых ids
     */
    public void addLikedId(int id) {
        HashMap<Integer, Integer> getStoredFriendsId = getStoredLikesId();
        if (getStoredFriendsId == null) {
            getStoredFriendsId = new HashMap<>();
        }
        getStoredFriendsId.put(id,id);
        setStoredLikesId(getStoredFriendsId);
    }

    /**
     * Сохранение лайкнутых ids
     */
    public void setStoredLikesId(HashMap<Integer, Integer> users) {
        SharedPreferences shPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_COMMON, Context.MODE_PRIVATE);
        String usersString = new Gson().toJson(users);
        shPref.edit()
                .putString(STORED_FRIENDS_ID, usersString)
                .apply();
    }


    /**
     * Получение лайкнутых ids
     */
    public HashMap<Integer, Integer> getStoredLikesId() {
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

}
