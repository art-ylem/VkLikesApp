package com.vklikesapp.nat.vklikesapp.rest.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 11.08.2017.
 */

public class BaseItemResponse <T> {
    public Integer count;
    public List<T> items = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public List<T> getItems() {
        return items;
    }
}