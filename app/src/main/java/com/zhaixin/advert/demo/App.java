package com.zhaixin.advert.demo;

import android.app.Application;

import com.anythink.core.api.ATSDK;
import com.lockin.loock.R;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ATSDK.init(this, getString(R.string.appid_zx), getString(R.string.appkey_zx));
        ATSDK.start();
        ATSDK.integrationChecking(this);
        ATSDK.setNetworkLogDebug(true);
    }
}
