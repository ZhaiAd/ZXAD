package com.zhaixin.advert.demo;

import android.app.Application;

import com.zhaixin.ZXAD;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ZXAD.init(this, getString(R.string.appid_zx));

    }
}
