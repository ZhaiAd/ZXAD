# 宅心科技广告引擎 Android 接入文档 (ADX 版)

> 版本：3.3.1 | 更新时间：2026.03.14

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
    // 宅心广告 SDK
    implementation(name: 'ZhaiXin_3.3.1_release', ext: "aar")
    implementation(name: 'ag', ext: 'aar')
    implementation(name: 'china_core', ext: 'aar')
    implementation(name: 'core', ext: 'aar')
    implementation(name: 'banner', ext: 'aar')
    implementation(name: 'interstitial', ext: 'aar')
    implementation(name: 'native', ext: 'aar')
    implementation(name: 'rewardvideo', ext: 'aar')
    implementation(name: 'splash', ext: 'aar')
    implementation(name: 'ly', ext: 'aar')
    
    // 基础依赖
    implementation 'com.github.gzu-liyujiang:Android_CN_OAID:4.2.4'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
    
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
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
        ad.biddingFailed("CHUANSHANJIA",3000); // 竞价失败回传
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

// 获取 eCPM
ad.getEcpm();
```

### 4.2 激励视频

```java
RewardVideoAd ad = new RewardVideoAd(POSID);
ad.enableDebug();

ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(MainActivity.this);
        ad.biddingFailed("CHUANSHANJIA",3000); // 竞价失败回传
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
        ad.biddingFailed("CHUANSHANJIA",3000); // 竞价失败回传
    }

    @Override
    public void onNoAd(int code, String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
        ad.biddingFailed("CHUANSHANJIA",3000); // 竞价失败回传
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
        ad.biddingFailed("CHUANSHANJIA",3000); // 竞价失败回传
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

### 获取 eCPM

```java
double ecpm = ad.getEcpm();
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

### 4. ADX 版本特性

ADX 版本使用 AnyThink (塔酷) 聚合 SDK，支持更多广告源和更高的填充率

---

## 更新日志

| 版本 | 日期 | 说明 |
|------|------|------|
| 3.3.1 | 2026.03.14 | 更新宅心 SDK 至 3.3.1 |
| 3.3.0 | 2026.03.13 | 更新ADX 版本 |
| 2.7.0 | 2026.03.13 | 标准版，更新 SDK 版本 |
| 2.6.1 | 2025.11.25 | 初始版本 |
