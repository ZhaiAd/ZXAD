package com.zhaixin.advert.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heart.weather.R;
import com.zhaixin.ZXAD;
import com.zhaixin.advert.BannerAd;
import com.zhaixin.advert.FullScreenAd;
import com.zhaixin.advert.InterstitialAd;
import com.zhaixin.advert.Platform;
import com.zhaixin.advert.RewardVideoAd;
import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.AdViewListener;
import com.zhaixin.listener.VideoPlayListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Menu mMenu;

    private FrameLayout mContent;

    private boolean debug, bd, ks, ylh, csj, zx, bz, zy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContent = findViewById(R.id.content);
        TextView mTvTeamVersion = findViewById(R.id.mTvTeamVersion);
        mTvTeamVersion.setText(getString(R.string.textTeamVersion, ZXAD.getVersion()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(!item.isChecked());
        int id = item.getItemId();
        if (id == R.id.debug) {
            debug = item.isChecked();
            bd = ks = ylh = csj = zx = bz = zy = debug;
            mMenu.findItem(R.id.bd).setChecked(bd).setVisible(debug);
            mMenu.findItem(R.id.ks).setChecked(ks).setVisible(debug);
            mMenu.findItem(R.id.ylh).setChecked(ylh).setVisible(debug);
            mMenu.findItem(R.id.csj).setChecked(csj).setVisible(debug);
            mMenu.findItem(R.id.zx).setChecked(zx).setVisible(debug);
            mMenu.findItem(R.id.bz).setChecked(bz).setVisible(debug);
            mMenu.findItem(R.id.zy).setChecked(zy).setVisible(debug);
        } else {
            if (id == R.id.bd) {
                bd = item.isChecked();
            }
            if (id == R.id.ks) {
                ks = item.isChecked();
            }
            if (id == R.id.ylh) {
                ylh = item.isChecked();
            }
            if (id == R.id.csj) {
                csj = item.isChecked();
            }
            if (id == R.id.zx) {
                zx = item.isChecked();
            }
            if (id == R.id.bz) {
                bz = item.isChecked();
            }
            if (id == R.id.zy) {
                zy = item.isChecked();
            }
            if (!bd && !ks && !ylh && !csj && !zx && !bz && !zy) {
                debug = false;
                mMenu.findItem(R.id.debug).setChecked(debug);
                mMenu.findItem(R.id.bd).setChecked(bd).setVisible(debug);
                mMenu.findItem(R.id.ks).setChecked(ks).setVisible(debug);
                mMenu.findItem(R.id.ylh).setChecked(ylh).setVisible(debug);
                mMenu.findItem(R.id.csj).setChecked(csj).setVisible(debug);
                mMenu.findItem(R.id.zx).setChecked(zx).setVisible(debug);
                mMenu.findItem(R.id.bz).setChecked(bz).setVisible(debug);
                mMenu.findItem(R.id.zy).setChecked(zy).setVisible(debug);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Platform[] getDebugPlatforms() {
        ArrayList<Platform> list = new ArrayList<>();
        if (bd) list.add(Platform.BD);
        if (ks) list.add(Platform.KS);
        if (ylh) list.add(Platform.YLH);
        if (csj) list.add(Platform.CSJ);
        if (zx) list.add(Platform.ZX);
        if (bz) list.add(Platform.BZ);
        if (zy) list.add(Platform.BZ);
        if (zy) list.add(Platform.ZY);

        Platform[] platforms = new Platform[list.size()];
        list.toArray(platforms);
        return platforms;
    }

    // 激励视频
    public void rewardAd(View view) {
        RewardVideoAd ad = new RewardVideoAd("2919646015");
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
        FullScreenAd ad = new FullScreenAd("2021839469");
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
        InterstitialAd ad = new InterstitialAd("2102886533");
        ad.enableDebug();
        ad.setAdLoadListener(new AdLoadListener() {
            @Override
            public void onLoad() {
                ad.show(MainActivity.this);
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
        if (debug) intent.putExtra("platforms", getDebugPlatforms());
        startActivity(intent);
    }

    // 横幅广告
    public void bannerAd(View view) {

        BannerAd ad = new BannerAd("2208423313");

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

        // 设置 Banner 广告尺寸
        ad.load(MainActivity.this, mContent.getWidth(), 0);

    }

    // 信息流(模板)
    public void feedAd(View view) {
        Intent intent = new Intent(this, FeedListActivity.class);
        if (debug) intent.putExtra("platforms", getDebugPlatforms());
        startActivity(intent);
    }


}