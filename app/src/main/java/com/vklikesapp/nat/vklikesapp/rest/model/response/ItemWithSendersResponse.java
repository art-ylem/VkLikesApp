package com.vklikesapp.nat.vklikesapp.rest.model.response;


import android.util.Log;

import com.vklikesapp.nat.vklikesapp.model.Group;
import com.vklikesapp.nat.vklikesapp.model.Owner;
import com.vklikesapp.nat.vklikesapp.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ItemWithSendersResponse<T> extends BaseItemResponse<T> {
    private List<Profile> profiles = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    private List<Profile> getProfiles() {
        return profiles;
    }

    private List<Group> getGroups() {
        return groups;
    }

    private List<Owner> getAllSenders() {
        List<Owner> all = new ArrayList<>();
        all.addAll(getProfiles());
        all.addAll(getGroups());
        return all;
    }

    public Owner getSender(int id) {
        for (Owner owner : getAllSenders()) {
            int a = owner.getId();
            int b = Math.abs(id);
            if (a == b) {
                return owner;
            }
        }
        Log.e("FFFFFF", id + "");
        return null;
    }
}
