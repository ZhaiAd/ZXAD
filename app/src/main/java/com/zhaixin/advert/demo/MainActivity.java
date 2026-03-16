package com.zhaixin.advert.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lockin.loock.R;
import com.zhaixin.ZXAD;
import com.zhaixin.advert.BannerAd;
import com.zhaixin.advert.FullScreenAd;
import com.zhaixin.advert.InterstitialAd;
import com.zhaixin.advert.RewardVideoAd;
import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.AdViewListener;
import com.zhaixin.listener.VideoPlayListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mContent = findViewById(R.id.content);
        TextView mTvTeamVersion = findViewById(R.id.mTvTeamVersion);
        mTvTeamVersion.setText(getString(R.string.textTeamVersion, ZXAD.getVersion()));

    }

    // 激励视频
    public void rewardAd(View view) {
        RewardVideoAd ad = new RewardVideoAd(getString(R.string.posid_reward));
        ad.enableDebug();
        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(MainActivity.this);
            }

            @Override
            public void onNoAd(int code, String message) {
            }
        });
        ad.setVideoPlayListener(new VideoPlayListener() {
            @Override
            public void onPlayStart() {
            }

            @Override
            public void onPlaySkip() {
            }

            @Override
            public void onPlayFinish() {
            }
        });
        ad.setAdViewListener(new AdViewListener() {
            @Override
            public void onShow() {
            }

            @Override
            public void onClose() {
            }

            @Override
            public void onClick() {
            }

            @Override
            public void onReward() {
                Toast.makeText(MainActivity.this, "奖励已获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResourceError() {
            }
        });
        ad.load(MainActivity.this);
    }

    // 全屏视频
    public void fullScreenAd(View view) {
        FullScreenAd ad = new FullScreenAd(getString(R.string.posid_fullscreen));
        ad.enableDebug();
        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(MainActivity.this);
            }

            @Override
            public void onNoAd(int code, String message) {
                Toast.makeText(MainActivity.this, "无广告", Toast.LENGTH_SHORT).show();
            }
        });
        ad.load(MainActivity.this);
    }

    // 插屏广告
    public void interstitialAd(View view) {
        InterstitialAd ad = new InterstitialAd(getString(R.string.posid_interstitial));
        ad.enableDebug();
        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(MainActivity.this);
                ad.biddingFailed("CHUANSHANJIA",3000);
            }

            @Override
            public void onNoAd(int code, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        ad.load(MainActivity.this);
    }

    // 开屏广告
    public void splashAd(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    // 横幅广告
    public void bannerAd(View view) {
        BannerAd ad = new BannerAd(getString(R.string.posid_banner));
        ad.enableDebug();
        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(mContent);
            }

            @Override
            public void onNoAd(int code, String message) {
                Toast.makeText(MainActivity.this, "无广告", Toast.LENGTH_SHORT).show();
            }
        });
        ad.load(MainActivity.this, mContent.getWidth(), 0);
    }

    // 信息流(模板)
    public void feedAd(View view) {
        Intent intent = new Intent(this, FeedListActivity.class);
        startActivity(intent);
    }


}