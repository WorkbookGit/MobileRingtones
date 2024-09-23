package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class CustomBannerAds {

    Context context;

    public CustomBannerAds(Context context) {
        this.context = context;
    }

    public void loadBanner_ads() {

        if (context != null) {
            try {
                MobileAds.initialize(context, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                    }
                });

                final com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(context);
                adView.setAdSize(AdSize.BANNER);
                adView.setAdUnitId(adConfig.getBanner_id());
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);

                adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                        RelativeLayout layout = ((Activity) context).findViewById(R.id.banner_container);
                        layout.addView(adView);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {

                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }

                    @Override
                    public void onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when the user is about to return
                        // to the app after tapping on an ad.
                    }
                });
            } catch (Exception e) {
                Log.e("error", "Something went wrong");
            }
        }
    }
}
