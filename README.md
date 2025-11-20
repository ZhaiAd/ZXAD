## **宅心科技广告引擎Android接入文档**

> 修改时间：2025.10.13

**一、导入SDK**

将SDK包中的aar放入工程中的libs下并填写如下依赖

```groovy
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    implementation(name: 'open_ad_sdk_7.2.0.9', ext: "aar")//穿山甲
    implementation(name: 'GDTSDK.unionNormal.4.660.1530', ext: "aar")//优量汇
    implementation(name: 'Baidu_MobAds_SDK-release_v9.42', ext: "aar")//百度
    implementation(name: 'kssdk-ad-4.9.20.1-publishRelease-a97b69133a', ext: "aar")//快手
    implementation(name: 'jad_yun_sdk_jingdong_2.6.32_20250523', ext: 'aar')//京东
    implementation(name: 'beizi_fusion_sdk_5.2.1.18', ext: "aar")//倍孜
    implementation(name: 'octopus_ad_sdk_1.6.4.7', ext: "aar")//章鱼
    implementation(name: 'ms-sdk_2.5.8.11_release', ext: 'aar')//美数
    implementation(name: 'domob-ad-sdk-3.6.1', ext: 'aar')//多盟
    implementation(name: 'mm_ad_sdk_7.4.10.0_android', ext: 'aar')//脉盟
    implementation(name: 'wind-common-1.8.6', ext: 'aar')//sigmob
    implementation(name: 'wind-sdk-4.24.7', ext: 'aar')//sigmob
    implementation(name: 'anythink_china_core', ext: 'aar')//塔酷
    implementation(name: 'anythink_core', ext: 'aar')//塔酷
    implementation(name: 'anythink_banner', ext: 'aar')//塔酷
    implementation(name: 'anythink_interstitial', ext: 'aar')//塔酷
    implementation(name: 'anythink_native', ext: 'aar')//塔酷
    implementation(name: 'anythink_rewardvideo', ext: 'aar')//塔酷
    implementation(name: 'anythink_splash', ext: 'aar')//塔酷


    //宅心SDK必须引入以下内容
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

    //美数SDK必须引入以下内容
    implementation 'com.squareup.okhttp3:okhttp:3.12.1'
    implementation 'com.google.code.gson:gson:2.8.6'

    //多盟SDK必须引入以下内容
    implementation 'com.google.protobuf:protobuf-java:4.27.2'
    implementation 'com.domob.sdk:oaid:1.0.251'
    // 可选 如果需要微信小程序广告再添加，SDK-3.2.0 增加
    implementation 'com.tencent.mm.opensdk:wechat-sdk-android:6.8.0'

    //脉盟SDK必须引入以下内容
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    implementation 'com.squareup.okio:okio:3.6.0'
    implementation "com.android.support.constraint:constraint-layout:2.0.4"
    implementation 'com.google.code.gson:gson:2.8.6'

}

AndroidX依赖
如果您的工程使用的是AndroidX的环境，请参考官网升级AndroidX，在`gradle.properties`文件中新增如下配置。
## Android 插件会使用对应的 AndroidX 库而非支持库。
android.useAndroidX=true
## Android 插件会通过重写现有第三方库的二进制文件，自动将这些库迁移为使用 AndroidX。
android.enableJetifier=true
```


**二、初始化**

请在Application的onCreate中调用以下方法进行初始化，初始化需要填写AppID，请联系商务申请。

```java
ZXAD.init(this, APP_ID);
```


**三、开屏广告**

```java
//请联系商务获取POSID
//content为开屏广告容器
SplashAd ad = new SplashAd(POSID);
//打开调试模式，输出所有sdk日志
ad.enableDebug();
ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(content);
    }

    @Override
    public void onNoAd(int code, String message) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
});
ad.setAdViewListener(new AdViewListener() {
    @Override
    public void onShow() {
    }

    @Override
    public void onClose() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
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
});
ad.load(this);
```


**四、激励视频**

```java
//请联系商务获取POSID
RewardVideoAd ad = new RewardVideoAd(POSID);
ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(MainActivity.this);
    }

    @Override
    public void onNoAd(int code, String message) {
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
```


**五、全屏视频**

```java
//请联系商务获取POSID
FullScreenAd ad = new FullScreenAd(POSID);
if (debug) ad.enableDebug(getDebugPlatforms());
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


**六、插屏**

```java
//请联系商务获取POSID
InterstitialAd ad = new InterstitialAd(POSID);
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


**七、Banner**

```java
//请联系商务获取POSID
BannerAd ad = new BannerAd(POSID);
ad.setAdLoadListener(new AdLoadListener() {
    @Override
    public void onLoad() {
        ad.show(mContent);
    }

    @Override
    public void onNoAd(int code, String message) {
    }
});
//mContent.getWidth()为banner广告容器宽度，单位px
ad.load(MainActivity.this, mContent.getWidth(),mContent.getHeight());
```


**八、信息流**

```java
//请联系商务获取POSID
FeedAd advert = new FeedAd(POSID);
advert.setAdLoadListener(new FeedLoadListener() {
    @Override
    public void onLoad(List<FeedAdData> list) {
        mPage = page;
        int count = (page - 1) * 20;
        List<String> fakeData = new ArrayList<>();
        for (int i = count + 1; i <= count + 20; i++) {
            fakeData.add("Item " + i);
        }
        if (page <= 1) adapter.clearData();
        adapter.addData(fakeData, list);

        mIsLoading = false;
        swipeRefresh.setEnabled(true);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onNoAd(int code, String message) {
        mPage = page;
        int count = (page - 1) * 20;
        List<String> fakeData = new ArrayList<>();
        for (int i = count + 1; i <= count + 20; i++) {
            fakeData.add("Item " + i);
        }
        if (page <= 1) adapter.clearData();
        adapter.addData(fakeData, Collections.emptyList());

        mIsLoading = false;
        swipeRefresh.setEnabled(true);
        swipeRefresh.setRefreshing(false);
    }
});
advert.load(this);
```

**九、获取SDK版本**

```java
ZXAD.getVersion();
```

**十、错误回调**
> 在所有广告的onNoAd回调中，message会列出所有三方平台的错误代码和错误信息，请根据错误代码和错误信息进行调试。

**十一、常见问题**

1. 宅心科技SDK仅支持minSdkVersion为24，即兼容的最小手机系统版本为7.0
