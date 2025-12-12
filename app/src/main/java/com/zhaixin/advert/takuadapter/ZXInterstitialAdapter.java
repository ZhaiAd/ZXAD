package com.zhaixin.advert.takuadapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATBiddingListener;
import com.anythink.core.api.ATBiddingResult;
import com.anythink.core.api.ATInitMediation;
import com.anythink.interstitial.unitgroup.api.CustomInterstitialAdapter;
import com.zhaixin.ZXAD;
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
        interstitialAd.show(activity);
    }

    @Override
    public void loadCustomNetworkAd(Context context, Map<String, Object> serverExtra, Map<String, Object> map1) {
        initRequestParams(serverExtra);

        if (TextUtils.isEmpty(mAppId) || TextUtils.isEmpty(mPosId)) {
            notifyATLoadFail("", "ZXAD appid or unitId is empty.");
            return;
        }

        ZXAD.init(context, mPosId);

        startLoadAd(context);
    }

    @Override
    public boolean isAdReady() {
        return isReady;
    }

    @Override
    public String getNetworkPlacementId() {
        return mPosId;
    }

    @Override
    public String getNetworkSDKVersion() {
        return ZXAD.getVersion();
    }

    @Override
    public String getNetworkName() {
        return "宅心";
    }

    @Override
    public boolean startBiddingRequest(Context context, Map<String, Object> serverExtra, Map<String, Object> localExtra, ATBiddingListener biddingListener) {
        isC2SBidding = true;
        loadCustomNetworkAd(context, serverExtra, localExtra);
        return true;
    }


    @Override
    public void onLoad() {

        isReady = true;

        if (isC2SBidding) {
            if (mBiddingListener != null) {
                if (interstitialAd != null) {
                    double price = interstitialAd.getEcpm();

                    mBiddingListener.onC2SBiddingResultWithCache(ATBiddingResult.success(price, System.currentTimeMillis() + "", null, ATAdConst.CURRENCY.RMB_CENT), null);
                } else {
                    notifyATLoadFail("", "ZXAD: SplashAD had been destroy.");
                }
            }
        } else {
            if (mLoadListener != null) {
                mLoadListener.onAdCacheLoaded();
            }
        }

    }

    @Override
    public void destory() {
        if (interstitialAd != null)
            interstitialAd = null;
    }

    @Override
    public void onNoAd(int code, String message) {
        notifyATLoadFail(String.valueOf(code), message);
    }

    @Override
    public void onShow() {
        if (mImpressListener != null)
            mImpressListener.onInterstitialAdShow();
    }

    @Override
    public void onClose() {
        if (mImpressListener != null)
            mImpressListener.onInterstitialAdClose();
    }

    @Override
    public void onClick() {
        if (mImpressListener != null)
            mImpressListener.onInterstitialAdClicked();
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
