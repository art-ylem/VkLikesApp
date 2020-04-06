package com.vklikesapp.nat.vklikesapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Base64;

import com.crashlytics.android.Crashlytics;
import com.vk.sdk.VKSdk;
import com.vklikesapp.nat.vklikesapp.di.component.ApplicationComponent;
import com.vklikesapp.nat.vklikesapp.di.component.DaggerApplicationComponent;
import com.vklikesapp.nat.vklikesapp.di.module.ApplicationModule;

import org.solovyev.android.checkout.Billing;

import javax.annotation.Nonnull;

import io.fabric.sdk.android.Fabric;


public class MyApplication extends Application {


    private static MyApplication sInstance;
    private static ApplicationComponent sApplicationComponent;
    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnIrD6aIxDi6tUAssjyQjAmMb5Eccptv/bEe7UbBEVZ6Moqyge6SClui7WbDBwWAL2mkY12Lg1uk3ZF3CKwf+dyZISoKgn2cCG46lHwdR5slDARnmwljUQwPMooGkJdRYL/RYB1RlU8N7r/Cr+/3Ljp+O4p8Jk6ki73zGO1wIcLiBd2Kd5rOtvaAWMwjFIhPXYfcHU91qPP4PQ8ioF76VhfArfkvEcyyzmwsHEPUCdUBFj9P1Jsyiv9kO4Yeinwwm4abZVApzHsthXFsm74G3PTzmLmNTVqB8KXiROuuNDzO6UeKL2yIqFajQS3nBt6O/9vsY/2qKuWZZQ0fsFxCK2wIDAQAB";

    private final Billing mBilling = new Billing(this, new Billing.DefaultConfiguration() {
        @Override
        public String getPublicKey() {
            return publicKey;
//            return decrypt(publicKey, "konovalonatalia4@gmail.com");
        }
    });

    public MyApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        initComponent();

        VKSdk.initialize(this);
    }

    private void initComponent() {
        sApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication get() {
        return sInstance;
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }

    public Billing getBilling() {
        return mBilling;
    }

    @Nonnull
    static String decrypt(@Nonnull String message, @Nonnull String salt) {
        return xor(new String(Base64.decode(message, 0)), salt);
    }

    @Nonnull
    private static String xor(@Nonnull String message, @Nonnull String salt) {
        final char[] m = message.toCharArray();
        final int ml = m.length;

        final char[] s = salt.toCharArray();
        final int sl = s.length;

        final char[] res = new char[ml];
        for (int i = 0; i < ml; i++) {
            res[i] = (char) (m[i] ^ s[i % sl]);
        }
        return new String(res);
    }
}
