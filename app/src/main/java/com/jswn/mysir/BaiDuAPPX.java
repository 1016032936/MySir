package com.jswn.mysir;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.appx.BDAppWallAd;
import com.baidu.appx.BDBannerAd;
import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDSplashAd;

/**
 * Created by 极速蜗牛 on 2016/11/12 0012.
 */

public class BaiDuAPPX {
    public String SDK_APP_KEY = "2o3hKaU5L4cQCoV2mHPCiahcm0ykz0mG";
    public String SDK_BANNER_AD_ID = "10SicWnY7kNBxGmkdnuhjKCI";
    public String SDK_SPLASH_AD_ID = "SUD6qstZcUdyanPwKAGe0QmD";
    public String SDK_INTERSTITIAL_AD_ID = "Xu8qlmGgl1M6LVtxzzzmsoxF";
    public String SDK_AppWallAd_AD_ID = "y9fe0GGtBYzjwnjSrSqg68aw";

    public BDInterstitialAd inter;
    public BDSplashAd splash;
    public BDAppWallAd bdAppWallAd;
    public BDBannerAd binnar;

    public Context mContext;

    public BaiDuAPPX(Context context) {
        mContext = context;
        inter = new BDInterstitialAd((Activity) mContext, SDK_APP_KEY,
                SDK_INTERSTITIAL_AD_ID);
        splash = new BDSplashAd((Activity) mContext, SDK_APP_KEY,
                SDK_SPLASH_AD_ID);
        bdAppWallAd = new BDAppWallAd((Activity) mContext, SDK_APP_KEY,
                SDK_AppWallAd_AD_ID);
        binnar = new BDBannerAd((Activity) mContext, SDK_APP_KEY,
                SDK_BANNER_AD_ID);
    }

    /**
     * 应用墙广告
     */
    public void show_bdAppWallAd(){
        if (!bdAppWallAd.isLoaded()){
            bdAppWallAd.loadAd();
        }if (bdAppWallAd.isLoaded()){
            bdAppWallAd.doShowAppWall();
        }
    }
    /**
     * 横屏广告
     */
    public void show_binnar() {
        binnar.setAdSize(BDBannerAd.SIZE_FLEXIBLE);
        binnar.setAdListener(new AdListener());
//        RelativeLayout contair = new RelativeLayout(this);
//        contair.addView(binnar);
//        ll.addView(contair);
    }

    /**
     * 插屏广告
     */
    public void show_inter() {
        inter.setAdListener(new AdListener());
        if (!inter.isLoaded()) {
            inter.loadAd();
        }
        if (inter.isLoaded()) {
            inter.showAd();
        }
    }

    /**
     * 开屏广告
     */
    public void show_splash() {

        splash.setAdListener(new AdListener());
        if (!splash.isLoaded()) {
            splash.loadAd();
        }
        if (splash.isLoaded()) {
            splash.showAd();
        }
    }


    public void destory() {
        if (splash != null) {
            splash.destroy();
            splash = null;
        }
        if (inter != null) {
            inter.destroy();
            inter = null;
        }
    }

    private class AdListener implements BDBannerAd.BannerAdListener, BDInterstitialAd.InterstitialAdListener, BDSplashAd.SplashAdListener {

        @Override
        public void onAdvertisementDataDidLoadFailure() {
//            Toast.makeText(mContext, "onAdvertisementDataDidLoadFailure", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdvertisementDataDidLoadSuccess() {
//            Toast.makeText(mContext, "onAdvertisementDataDidLoadSuccess", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdvertisementViewDidClick() {
//            Toast.makeText(mContext, "onAdvertisementViewDidClick", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdvertisementViewDidShow() {
//            Toast.makeText(mContext, "onAdvertisementViewDidShow", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdvertisementViewWillStartNewIntent() {
//            Toast.makeText(mContext, "onAdvertisementViewWillStartNewIntent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdvertisementViewDidHide() {
//            Toast.makeText(mContext, "onAdvertisementViewDidHide", Toast.LENGTH_SHORT).show();
        }

    }
}
