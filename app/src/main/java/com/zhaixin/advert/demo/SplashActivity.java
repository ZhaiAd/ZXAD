package com.zhaixin.advert.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.heart.weather.R;
import com.zhaixin.advert.SplashAd;
import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.AdViewListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

public class SplashActivity extends AppCompatActivity {

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

        SplashAd ad = new SplashAd("2629995460");
        ad.enableDebug();
        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(content);
            }

            @Override
            public void onNoAd(int code, String message) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
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
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
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
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ad.load(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adClose) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

}
