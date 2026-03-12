# 宅心科技广告引擎 Android 接入文档

> 版本：2.7.0 | 更新时间：2026.03.13

---

## 目录

1. [导入 SDK](#一导入-sdk)
2. [AndroidX 配置](#二-androidx-配置)
3. [初始化](#三初始化)
4. [广告类型](#四广告类型)
   - [开屏广告](#41-开屏广告)
   - [激励视频](#42-激励视频)
   - [全屏视频](#43-全屏视频)
   - [插屏广告](#44-插屏广告)
   - [横幅广告](#45-横幅广告)
   - [信息流广告](#46-信息流广告)
5. [其他 API](#五其他-api)
6. [常见问题](#六常见问题)

---

## 一、导入 SDK

将 SDK 包中的 aar 放入工程的 `libs` 目录下，并添加以下依赖：

```groovy
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    // 广告平台 SDK
    implementation(name: 'ZhaiXin_2.7.0_release', ext: "aar")           // 宅心
    implementation(name: 'open_ad_sdk_7.2.0.9', ext: "aar")              // 穿山甲
    implementation(name: 'GDTSDK.unionNormal.4.662.1532', ext: "aar")    // 优量汇
    implementation(name: 'Baidu_MobAds_SDK-release_v9.422', ext: "aar")  // 百度
    implementation(name: 'kssdk-ad-4.10.30.1-publishRelease-22c31096b4', ext: "aar")  // 快手
    implementation(name: 'jad_yun_sdk_jingdong_2.6.32_20250523', ext: 'aar')  // 京东
    implementation(name: 'beizi_fusion_sdk_5.2.1.18', ext: "aar")        // 倍孜
    implementation(name: 'octopus_ad_sdk_1.6.4.7', ext: "aar")           // 章鱼
    implementation(name: 'ms-sdk_2.5.8.11_release', ext: 'aar')          // 美数
    implementation(name: 'domob-ad-sdk-3.6.1', ext: 'aar')               // 多盟
    implementation(name: 'mm_ad_sdk_7.4.10.0_android', ext: 'aar')       // 脉盟
    implementation(name: 'wind-common-1.8.6', ext: 'aar')                // sigmob
    implementation(name: 'wind-sdk-4.24.7', ext: 'aar')                  // sigmob
    implementation(name: 'anythink_china_core', ext: 'aar')              // 塔酷
    implementation(name: 'anythink_core', ext: 'aar')                    // 塔酷
    implementation(name: 'anythink_banner', ext: 'aar')                  // 塔酷
    implementation(name: 'anythink_interstitial', ext: 'aar')            // 塔酷
    implementation(name: 'anythink_native', ext: 'aar')                  // 塔酷
    implementation(name: 'anythink_rewardvideo', ext: 'aar')             // 塔酷
    implementation(name: 'anythink_splash', ext: 'aar')                  // 塔酷
    implementation(name: 'adgain-sdk-4.2.3.2', ext: 'aar')               // 数字悦动
    implementation(name: 'fissionSdk-release-1.0.87.81-amp', ext: 'aar') // 飞梭
    implementation(name: 'LYAdSDK-android-v3.1.33', ext: 'aar')          // 掌上乐游
    implementation(name: 'adview-android-5.0.3', ext: 'aar')             // 快友
    implementation(name: 'oaid_sdk_1.0.25', ext: 'aar')                  // OAID

    // 基础依赖
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
    implementation 'com.github.gzu-liyujiang:Android_CN_OAID:4.2.4'

    // 第三方库
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.okio:okio:3.6.0'
    implementation "com.android.support.constraint:constraint-layout:2.0.4"
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    implementation 'com.google.protobuf:protobuf-java:4.27.5'
    implementation 'com.tencent.mm.opensdk:wechat-sdk-android:6.8.0'
    implementation "com.huawei.hms:ads-lite:13.4.78.301"
    implementation "com.google.guava:guava:31.0.1-android"
}
```

---

## 二、AndroidX 配置

如工程使用 AndroidX，在 `gradle.properties` 中添加：

```groovy
android.useAndroidX=true
android.enableJetifier=true
```

---

## 三、初始化

在 Application 的 `onCreate` 中初始化：

```java
// AppID 请联系商务申请
ZXAD.init(this, APP_ID);
```

---

## 四、广告类型

### 4.1 开屏广告

```java
// POSID 请联系商务获取
SplashAd ad = new SplashAd(POSID);
ad.enableDebug();  // 开启调试日志

ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(content);  // content 为广告容器
    }

    @Override
    public void onNoAd(int code, String message) {
        // 无广告时的处理，建议延迟跳转
        handler.postDelayed(() -> gotoMain(), 3000);
    }
});

ad.setAdViewListener(new AdViewListener() {
    @Override
    public void onClose() {
        gotoMain();
    }

    @Override
    public void onResourceError() {
        gotoMain();
    }
});

ad.load(this);
```

### 4.2 激励视频

```java
RewardVideoAd ad = new RewardVideoAd(POSID);
ad.enableDebug();

ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(MainActivity.this);
    }
});

ad.setAdViewListener(new AdViewListener() {
    @Override
    public void onReward() {
        Toast.makeText(MainActivity.this, "奖励已获取", Toast.LENGTH_SHORT).show();
    }
});

ad.load(MainActivity.this);
```

### 4.3 全屏视频

```java
FullScreenAd ad = new FullScreenAd(POSID);
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
```

### 4.4 插屏广告

```java
InterstitialAd ad = new InterstitialAd(POSID);
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
```

### 4.5 横幅广告

```java
BannerAd ad = new BannerAd(POSID);
ad.enableDebug();

ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(mContent);
    }
});

// 设置广告尺寸（单位：px）
ad.load(MainActivity.this, mContent.getWidth(), mContent.getHeight());
```

### 4.6 信息流广告

```java
FeedAd advert = new FeedAd(POSID);
advert.enableDebug();

advert.setAdLoadListener(new FeedLoadListener() {
    @Override
    public void onLoad(List<FeedAdData> list) {
        // 将广告数据插入到列表数据中
        adapter.addData(fakeData, list);
    }

    @Override
    public void onNoAd(int code, String message) {
        // 无广告时展示普通数据
        adapter.addData(fakeData, Collections.emptyList());
    }
});

advert.load(this);
```

---

## 五、其他 API

### 获取 SDK 版本

```java
String version = ZXAD.getVersion();
```

### 错误回调

所有广告的 `onNoAd` 回调中，`message` 参数会列出所有三方平台的错误代码和信息，请据此调试。

---

## 六、常见问题

### 1. 最低系统版本

宅心科技 SDK 仅支持 **minSdkVersion ≥ 24**（Android 7.0）

### 2. POSID 获取

请联系商务申请各广告类型的 POSID

### 3. 调试模式

调用 `ad.enableDebug()` 可开启 SDK 调试日志，发布前请关闭

---

## 更新日志

| 版本 | 日期 | 说明 |
|------|------|------|
| 2.7.0 | 2026.03.13 | 更新 SDK 版本，优化代码结构 |
| 2.6.1 | 2025.11.25 | 初始版本 |
