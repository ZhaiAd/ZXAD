package com.zhaixin.advert.demo;

import android.app.Application;

import com.anythink.core.api.ATSDK;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ATSDK.init(this, "a6822aaa94b631", "af5f12e3386e175fb763df9596f7578d4");

        ATSDK.start();

        ATSDK.integrationChecking(this);

        ATSDK.setNetworkLogDebug(true);
    }
}
