package com.zhaixin.advert.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anythink.banner.api.ATBannerListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialListener;
import com.heart.weather.R;
import com.zhaixin.ZXAD;

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

    }

    // 全屏视频
    public void fullScreenAd(View view) {
    }

    // 插屏广告
    public void interstitialAd(View view) {

        ATInterstitial mAdvert = new ATInterstitial(MainActivity.this, "b6822aae04b070");

        mAdvert.setAdListener(new ATInterstitialListener() {
            @Override
            public void onInterstitialAdLoaded() {

                mAdvert.show(MainActivity.this);

            }

            @Override
            public void onInterstitialAdLoadFail(AdError adError) {

            }

            @Override
            public void onInterstitialAdClicked(ATAdInfo atAdInfo) {

            }

            @Override
            public void onInterstitialAdShow(ATAdInfo atAdInfo) {

            }

            @Override
            public void onInterstitialAdClose(ATAdInfo atAdInfo) {

            }

            @Override
            public void onInterstitialAdVideoStart(ATAdInfo atAdInfo) {

            }

            @Override
            public void onInterstitialAdVideoEnd(ATAdInfo atAdInfo) {

            }

            @Override
            public void onInterstitialAdVideoError(AdError adError) {

            }
        });

        mAdvert.load();
    }

    // 开屏广告
    public void splashAd(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    // 横幅广告
    public void bannerAd(View view) {

        ATBannerView bannerView = new ATBannerView(MainActivity.this);

        bannerView.setPlacementId("b6822aadf7d249");

        bannerView.setLayoutParams(new FrameLayout.LayoutParams(mContent.getWidth(), mContent.getHeight()));

        bannerView.setBannerAdListener(new ATBannerListener() {
            @Override
            public void onBannerLoaded() {

                mContent.addView(bannerView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mContent.getLayoutParams().height));

            }

            @Override
            public void onBannerFailed(AdError adError) {

            }

            @Override
            public void onBannerClicked(ATAdInfo atAdInfo) {

            }

            @Override
            public void onBannerShow(ATAdInfo atAdInfo) {

            }

            @Override
            public void onBannerClose(ATAdInfo atAdInfo) {

            }

            @Override
            public void onBannerAutoRefreshed(ATAdInfo atAdInfo) {

            }

            @Override
            public void onBannerAutoRefreshFail(AdError adError) {

            }
        });

        bannerView.loadAd();


    }

    // 信息流(模板)
    public void feedAd(View view) {
//        Intent intent = new Intent(this, FeedListActivity.class);
//        startActivity(intent);
    }


}