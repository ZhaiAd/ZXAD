package com.zhaixin.advert.takuadapter;

import android.app.Activity;
import android.content.Context;

import com.anythink.core.api.ATBiddingListener;
import com.anythink.core.api.ATInitMediation;
import com.anythink.interstitial.unitgroup.api.CustomInterstitialAdapter;
import com.zhaixin.advert.BannerAd;
import com.zhaixin.advert.InterstitialAd;
import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.AdViewListener;

import java.util.Map;

/*
 *  项目名:   ZXAD
 *  包名:     com.zhaixin.advert.takuadapter
 *  文件名:   ZXInterstitialAdapter
 *  创建时间:  2025/12/11
 *  创建者:   Hao
 *  描述:
 */
public class ZXInterstitialAdapter extends CustomInterstitialAdapter implements AdLoadListener, AdViewListener {

    boolean isC2SBidding = false;

    private String mAppId;

    private String mPosId;

    private boolean isReady;

    private InterstitialAd interstitialAd;

    @Override
    public void show(Activity activity) {

    }

    @Override
    public void loadCustomNetworkAd(Context context, Map<String, Object> map, Map<String, Object> map1) {

    }

    @Override
    public boolean isAdReady() {
        return false;
    }

    @Override
    public String getNetworkPlacementId() {
        return null;
    }

    @Override
    public String getNetworkSDKVersion() {
        return null;
    }

    @Override
    public String getNetworkName() {
        return null;
    }

    @Override
    public boolean startBiddingRequest(Context context, Map<String, Object> serverExtra, Map<String, Object> localExtra, ATBiddingListener biddingListener) {
        isC2SBidding = true;
        loadCustomNetworkAd(context, serverExtra, localExtra);
        return true;
    }


    @Override
    public void onLoad() {

    }

    @Override
    public void onNoAd(int code, String message) {

    }

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

    }

    @Override
    public void onResourceError() {

    }

    private void initRequestParams(Map<String, Object> serverExtra) {

        mAppId = ATInitMediation.getStringFromMap(serverExtra, "appid");

        mPosId = ATInitMediation.getStringFromMap(serverExtra, "slot_id");

        isReady = false;
    }

    private void startLoadAd(final Context context) {

        if (!(context instanceof Activity)) {
            notifyATLoadFail("", "ZXAD: Context must be Activity for splash ad");
            return;
        }

        interstitialAd = new InterstitialAd(mPosId);

        interstitialAd.enableDebug();

        interstitialAd.setAdLoadListener(this);

        interstitialAd.setAdViewListener(this);

        interstitialAd.load((Activity) context);


    }
}
