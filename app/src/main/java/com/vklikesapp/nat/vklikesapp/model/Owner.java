package com.vklikesapp.nat.vklikesapp.model;


import com.vk.sdk.api.model.Identifiable;

public interface Owner extends Identifiable {

    String getFullName();
    String getPhoto();
}
