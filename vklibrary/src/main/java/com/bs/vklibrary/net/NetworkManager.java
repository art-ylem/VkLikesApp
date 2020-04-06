package com.bs.vklibrary.net;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetworkManager {

    @Inject
    Context mContext;

    private static final String TAG = "NetworkManager";

    public NetworkManager(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return ((networkInfo != null && networkInfo.isConnected()));
    }

    public Callable<Boolean> isVkReachableCallable() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    if (!isOnline()) {
                        return false;
                    }

                    URL url = new URL("https://api.vk.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(2000);
                    urlc.connect();

                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        };
    }

    public Observable<Boolean> getNetworkObservable() {
        return Observable.fromCallable(isVkReachableCallable());
    }
}
















