package com.zhaixin.advert.demo;

import com.zhaixin.listener.AdLoadListener;
import com.zhaixin.listener.FeedLoadListener;

import java.util.List;

/**
 * 广告辅助工具类
 */
public class AdHelper {

    /**
     * 创建默认的加载监听器
     * @param onLoad 加载成功回调
     */
    public static AdLoadListener createLoadListener(Runnable onLoad) {
        return new AdLoadListener() {
            @Override
            public void onLoad() {
                if (onLoad != null) {
                    onLoad.run();
                }
            }

            @Override
            public void onNoAd(int code, String message) {
                // 默认空实现
            }
        };
    }

    /**
     * 创建信息流广告加载监听器
     * @param onLoad 加载成功回调
     * @param onNoAd 无广告回调
     */
    public static FeedLoadListener createFeedLoadListener(
            FeedLoadCallback onLoad, FeedLoadCallback onNoAd) {
        return new FeedLoadListener() {
            @Override
            public void onLoad(List list) {
                if (onLoad != null) {
                    onLoad.onLoad(list);
                }
            }

            @Override
            public void onNoAd(int code, String message) {
                if (onNoAd != null) {
                    onNoAd.onLoad(null);
                }
            }
        };
    }

    public interface FeedLoadCallback {
        void onLoad(List list);
    }
}
