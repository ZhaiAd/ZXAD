package com.zhaixin.advert.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.splashad.api.ATSplashAd;
import com.anythink.splashad.api.ATSplashAdExtraInfo;
import com.anythink.splashad.api.ATSplashAdListener;
import com.heart.weather.R;
import com.zhaixin.advert.SplashAd;
import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.AdViewListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

public class SplashActivity extends AppCompatActivity {

    private ATSplashAd mAdvert;

    private FrameLayout content;

    private boolean adClose = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        content = findViewById(R.id.content);

        initTk();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adClose) {
            toMain();
        }
    }

    private void initTk() {
        mAdvert = new ATSplashAd(SplashActivity.this, "b6822aadea2299", new ATSplashAdListener() {
            @Override
            public void onAdLoaded(boolean b) {

                mAdvert.show(SplashActivity.this, content);
            }

            @Override
            public void onAdLoadTimeout() {
                toMain();

            }

            @Override
            public void onNoAdError(AdError adError) {
                toMain();
            }

            @Override
            public void onAdShow(ATAdInfo atAdInfo) {

            }

            @Override
            public void onAdClick(ATAdInfo atAdInfo) {

            }

            @Override
            public void onAdDismiss(ATAdInfo atAdInfo, ATSplashAdExtraInfo atSplashAdExtraInfo) {
                toMain();
            }
        }, 5000);

        mAdvert.loadAd();
    }

    private void initZx() {
        SplashAd ad = new SplashAd("2629995460");
        ad.enableDebug();

        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(content);
            }

            @Override
            public void onNoAd(int code, String message) {
                toMain();
            }
        });
        ad.setAdViewListener(new AdViewListener() {
            @Override
            public void onShow() {
            }

            @Override
            public void onClose() {
                if (!getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                    adClose = true;
                    return;
                }
                toMain();
            }

            @Override
            public void onClick() {
            }

            @Override
            public void onReward() {
            }

            @Override
            public void onResourceError() {
                if (!SplashActivity.this.isFinishing()) {
                    toMain();
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    startActivity(intent);
//                    finish();
                }
            }
        });
        ad.load(this);

    }

    private void toMain(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
