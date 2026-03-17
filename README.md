# 宅心科技广告引擎 Android 接入文档

> 版本：2.7.5 | 更新时间：2026.03.17

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
    implementation(name: 'ZhaiXin_v2.7.5_release', ext: "aar")           // 宅心
    implementation(name: 'Beizi_v5.3.0.3', ext: "aar")                   // 倍孜
    implementation(name: 'CSJ_v7.4.2.0', ext: "aar")                     // 穿山甲
    implementation(name: 'GDT_v4.671.1541', ext: "aar")                  // 优量汇
    implementation(name: 'Baidu_v9.440', ext: "aar")                     // 百度
    implementation(name: 'Kuaishou_v5.1.20.1', ext: "aar")               // 快手
    implementation(name: 'JD_v2.6.32', ext: 'aar')                       // 京东
    implementation(name: 'Octopus_v2.6.3.16', ext: "aar")                // 章鱼
    implementation(name: 'Meishu_v2.5.11.0', ext: 'aar')                 // 美数
    implementation(name: 'Domob_v3.7.3', ext: 'aar')                     // 多盟
    implementation(name: 'Maimeng_v7.4.10.0', ext: 'aar')                // 脉盟
    implementation(name: 'Sigmob_Common_v1.9.6', ext: 'aar')             // sigmob
    implementation(name: 'Sigmob_SDK_v4.25.11', ext: 'aar')              // sigmob
    implementation(name: 'AnyThink_ChinaCore', ext: 'aar')               // 塔酷
    implementation(name: 'AnyThink_Core', ext: 'aar')                    // 塔酷
    implementation(name: 'AnyThink_Banner', ext: 'aar')                  // 塔酷
    implementation(name: 'AnyThink_Interstitial', ext: 'aar')            // 塔酷
    implementation(name: 'AnyThink_Native', ext: 'aar')                  // 塔酷
    implementation(name: 'AnyThink_RewardVideo', ext: 'aar')             // 塔酷
    implementation(name: 'AnyThink_Splash', ext: 'aar')                  // 塔酷
    implementation(name: 'AdGain_v4.2.6.6', ext: 'aar')                  // 数字悦动
    implementation(name: 'Fission_v1.0.95.01', ext: 'aar')               // 飞梭
    implementation(name: 'Leyou_v3.1.33', ext: 'aar')                    // 掌上乐游
    implementation(name: 'AdView_v5.0.3', ext: 'aar')                    // 快友
    implementation(name: 'OAID_v1.0.25', ext: 'aar')                     // OAID

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
| 2.7.5 | 2026.03.17 | 更新宅心 SDK 至 2.7.5，倍孜至 5.3.0.3，章鱼至 2.6.3.16，美数至 2.5.11.0，多盟至 3.7.3，飞梭至 1.0.95.01，百度至 9.440，数字悦动至 4.2.6.6，Sigmob 至 4.25.11 |
| 2.7.1 | 2026.03.14 | 更新宅心 SDK 至 2.7.1，倍孜 SDK 至 5.2.3.2 |
| 2.7.0 | 2026.03.13 | 更新 SDK 版本，优化代码结构 |
| 2.6.1 | 2025.11.25 | 初始版本 |
