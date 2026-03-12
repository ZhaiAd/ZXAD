package com.zhaixin.advert.demo;

import com.zhaixin.listener.AdLoadListener;

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
}
