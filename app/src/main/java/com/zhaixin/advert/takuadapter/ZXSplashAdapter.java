package com.zhaixin.advert.takuadapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATBiddingListener;
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

    boolean isC2SBidding = false;

    private String mAppId;

    private String mPosId;

    private boolean isReady;

    private SplashAd splashAd;

    @Override
    public void loadCustomNetworkAd(Context context, Map<String, Object> serverExtra, final Map<String, Object> localExtra) {

        initRequestParams(serverExtra);

        if (TextUtils.isEmpty(mAppId) || TextUtils.isEmpty(mPosId)) {
            notifyATLoadFail("", "ZXAD appid or unitId is empty.");
            return;
        }

        ZXAD.init(context, mPosId);

        startLoadAd(context);
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

        if (splashAd != null) {
            viewGroup.post(() -> splashAd.show(viewGroup));
        }

    }

    @Override
    public void destory() {
        if (splashAd !=null)
        splashAd = null;
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
        if (mImpressionListener != null) {
            mImpressionListener.onSplashAdDismiss();
        }
    }

    @Override
    public void onLoad() {

        isReady = true;

        if (isC2SBidding) {
            if (mBiddingListener != null) {
                if (splashAd != null) {
                    double price = splashAd.getEcpm();

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
                Log.e("Hao", "ZXAD Splash show fail:[errorCode:" +code + ",errorMsg:" + message + "]");
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

    @Override
    public boolean startBiddingRequest(Context context, Map<String, Object> serverExtra, Map<String, Object> localExtra, ATBiddingListener biddingListener) {
        isC2SBidding = true;
        loadCustomNetworkAd(context, serverExtra, localExtra);
        return true;
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

        splashAd = new SplashAd(mPosId);

        splashAd.enableDebug();

        splashAd.setAdLoadListener(this);

        splashAd.setAdViewListener(this);

        splashAd.load((Activity) context);
    }
}
