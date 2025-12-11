package com.zhaixin.advert.demo;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATBiddingResult;
import com.anythink.core.api.ATInitMediation;
import com.anythink.core.api.ErrorCode;
import com.anythink.splashad.unitgroup.api.CustomSplashAdapter;
import com.zhaixin.ZXAD;
import com.zhaixin.advert.SplashAd;
import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.AdViewListener;

import java.util.Map;

/*
 *  项目名:   ZXAD
 *  包名:     com.zhaixin.advert.demo
 *  文件名:   ZXSplashAdapter
 *  创建时间:  2025/12/10
 *  创建者:   Hao
 *  描述:
 */
public class ZXSplashAdapter extends CustomSplashAdapter implements AdViewListener, AdLoadListener {

    final String TAG = ZXSplashAdapter.class.getSimpleName();

    String mPayload;

    boolean isC2SBidding = false;

    private String mAppId;

    private String mUnitId;

    private boolean isReady;

    private SplashAd splashAD;

    private void startLoadAd(final Context context, Map<String, Object> serverExtra) {
        splashAD = new SplashAd(mUnitId);
        splashAD.setAdLoadListener(this);
        splashAD.setAdViewListener(this);
    }

    private void initRequestParams(Map<String, Object> serverExtra, Map<String, Object> localExtra) {
        mAppId = ATInitMediation.getStringFromMap(serverExtra, "appid");
        Log.d("Hao",mAppId);
        mUnitId = ATInitMediation.getStringFromMap(serverExtra, "unit_id");
        Log.d("Hao",mUnitId);
        mPayload = ATInitMediation.getStringFromMap(serverExtra, "payload");

        isReady = false;
    }

    @Override
    public void show(Activity activity, ViewGroup viewGroup) {
        if (viewGroup == null) {
            if (mImpressionListener != null) {
                mDismissType = ATAdConst.DISMISS_TYPE.SHOWFAILED;
                mImpressionListener.onSplashAdShowFail(ErrorCode.getErrorCode(ErrorCode.adShowError, "", "Container is null"));
                mImpressionListener.onSplashAdDismiss();
            }
            return;
        }

        if (splashAD != null) {
            viewGroup.post(() -> splashAD.show(viewGroup));
        }

    }

    @Override
    public void loadCustomNetworkAd(Context context, Map<String, Object> serverExtra, final Map<String, Object> localExtra) {
        initRequestParams(serverExtra, localExtra);

        if (TextUtils.isEmpty(mAppId) || TextUtils.isEmpty(mUnitId)) {
            notifyATLoadFail("", "ZXAD appid or unitId is empty.");
            return;
        }

        ZXAD.init(context, mUnitId);
    }

    @Override
    public void destory() {
        splashAD = null;
    }

    @Override
    public boolean isAdReady() {
        return isReady;
    }

    @Override
    public String getNetworkPlacementId() {
        return mUnitId;
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
    public void onShow() {
        if (mImpressionListener != null) {
            mImpressionListener.onSplashAdShow();
        }
    }

    @Override
    public void onClose() {
        if (mImpressionListener != null) {
            mImpressionListener.onSplashAdDismiss();
        }
    }

    @Override
    public void onClick() {
        if (mImpressionListener != null) {
            mImpressionListener.onSplashAdClicked();
        }
    }

    @Override
    public void onReward() {

    }

    @Override
    public void onResourceError() {
    }

    @Override
    public void onLoad() {
        isReady = true;
        if (isC2SBidding) {
            if (mBiddingListener != null) {
                if (splashAD != null) {
                    double price = splashAD.getEcpm();
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
    public void onNoAd(int code, String message) {
        if (message != null) {
            notifyATLoadFail(code + "", message);
            //if gdt splash show fail,will call this
            if (mImpressionListener != null) {
                Log.e(TAG, "ZXAD Splash show fail:[errorCode:" +code + ",errorMsg:" + message + "]");
                mDismissType = ATAdConst.DISMISS_TYPE.SHOWFAILED;
                mImpressionListener.onSplashAdShowFail(ErrorCode.getErrorCode(ErrorCode.adShowError, "" + code, message));
                mImpressionListener.onSplashAdDismiss();
            }
        } else {
            notifyATLoadFail("", "ZXAD Splash show fail");
            //if gdt splash show fail,will call this
            if (mImpressionListener != null) {
                mImpressionListener.onSplashAdShowFail(ErrorCode.getErrorCode(ErrorCode.adShowError, "", "ZXAD Splash show fail"));
                mImpressionListener.onSplashAdDismiss();
            }
        }
    }
}
