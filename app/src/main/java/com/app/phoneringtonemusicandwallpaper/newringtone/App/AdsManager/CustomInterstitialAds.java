package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.increment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class CustomInterstitialAds {

    public static InterstitialAd ginterstitialAd;
    Context context;
    AlertDialog loadingDialog;
    public Inter_Callback inter_callback;
    Boolean isVideoInterAds = false;

    public CustomInterstitialAds(Context context) {
        this.context = context;
    }

    public void loadInterstitialAds(Inter_Callback inter_callback, Boolean isVideoAds) {

        this.inter_callback = inter_callback;
        isVideoInterAds = isVideoAds;

        if (adConfig.getInterad_showing() != null) {
            if (adConfig.getInterad_showing()) {
                if (adConfig.getCounter() != null) {
                    int tempIncrement = adConfig.getCounter();
                    if (increment == tempIncrement) {
                        show();
                        if (adConfig.getInter_id() != null) {
                            load_GinterstitialAd();
                        }
                    } else {
                        increment++;
                        inter_callback.Intersitial_adscallback(true);
                    }
                } else {
                    inter_callback.Intersitial_adscallback(true);
                }
            } else {
                inter_callback.Intersitial_adscallback(true);
            }
        } else {
            inter_callback.Intersitial_adscallback(true);
        }
    }

    public void show() {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            builder.setView(inflater.inflate(R.layout.please_wait_dialog, null));
            builder.setCancelable(false);
            loadingDialog = builder.create();
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadingDialog.show();
        }
    }

    public void dismiss() {

        try {
            if (loadingDialog != null) {
                if (loadingDialog.isShowing()) loadingDialog.dismiss();
            }
        } catch (Exception e) {
            Log.e("Error", "Loading dialog not show");
        }
    }

    private void load_GinterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, adConfig.getInter_id(),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        ginterstitialAd = interstitialAd;
                        dismiss();
                        increment = 1;
                        ginterstitialAd.show((Activity) context);
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.

                                       /* if (exit == 1) {
                                            exit = 0;
                                            context.startActivity(new Intent(context, ExitActivity.class));
                                            return;
                                        }*/

//                                        if (splashAds == 1) {
//                                            splashAds = 2;
//                                            context.startActivity(new Intent(context, GetStartActivity_crick.class));
//                                        }

                                        dismiss();
                                        ginterstitialAd = null;
                                        inter_callback.Intersitial_adscallback(true);


//                                        load_GinterstitialAd();     // Reload Interstitial Ads

                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ginterstitialAd = null;
                                        dismiss();

                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                        dismiss();
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        ginterstitialAd = null;
                        dismiss();
                        inter_callback.Intersitial_adscallback(true);
                        // admob interstitial failed
//                        load_interstitialAd();
                    }
                });
    }
}

//package com.topbestringtonesiosringtones.iphoneringtone.Ads_utils;
//
//
//import static com.topbestringtonesiosringtones.iphoneringtone.Activities.SplashScreen.addtype;
//import static com.topbestringtonesiosringtones.iphoneringtone.Activities.SplashScreen.adsModelClass;
//import static com.topbestringtonesiosringtones.iphoneringtone.Activities.SplashScreen.exit;
//import static com.topbestringtonesiosringtones.iphoneringtone.Activities.SplashScreen.increment;
//import static com.topbestringtonesiosringtones.iphoneringtone.Activities.SplashScreen.resAds;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Handler;
//import android.util.Log;
//import android.view.LayoutInflater;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
//import com.topbestringtonesiosringtones.iphoneringtone.Activities.ExitActivity;
//import com.topbestringtonesiosringtones.iphoneringtone.R;
//
//public class CustomInterstitialAds {
//
//    public static InterstitialAd interstitialAd;
//    public static com.google.android.gms.ads.interstitial.InterstitialAd ginterstitialAd;
//    Context context;
//
//    AlertDialog loadingDialog;
//
//    public CustomInterstitialAds(Context context) {
//        this.context = context;
//    }
//
//    public void loadInterstitialAds() {
//        if (resAds) {
//            if (adsModelClass.getMyadtype().equalsIgnoreCase("admob")) {
//                load_GinterstitialAd();
//            }
//        }
//
//    }
//
//
//
//    public void ShowInterstitialAds() {
//        if (adsModelClass != null && resAds) {
//            if (adsModelClass.getCounter() == increment) {
//                show();
//                if (addtype.equalsIgnoreCase("admob")) {
//                    if (ginterstitialAd != null) {
//                        increment = 1;
//                        ginterstitialAd.show((Activity) context);
//                        Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                dismiss();
//                            }
//                        }, 1500);
//
//                    } else {
//                        dismiss();
//                    }
//                }
//                dismiss();
//            } else {
////                Toast.makeText(context, "++"+increment, Toast.LENGTH_SHORT).show();
//                increment++;
//            }
//
//        }
//    }
//
//
//    public void show() {
//        if (context != null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            builder.setView(inflater.inflate(R.layout.please_wait_dialog, null));
//            builder.setCancelable(false);
//            loadingDialog = builder.create();
//            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            loadingDialog.show();
//        }
//    }
//
//    public void dismiss() {
//
//        try {
//            if (loadingDialog != null) {
//                if (loadingDialog.isShowing()) loadingDialog.dismiss();
//            }
//        } catch (Exception e) {
//            Log.e("Error", "Loading dialog not show");
//        }
//    }
//
//    private void load_GinterstitialAd() {
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        com.google.android.gms.ads.interstitial.InterstitialAd.load(context, adsModelClass.getGinter(),
//                adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//
//                        // an ad is loaded.
//                        ginterstitialAd = interstitialAd;
//
//                        addtype = "admob";
//                        /*if (splashAds == 0) {
//                            splashAds = 1;
//                            ginterstitialAd.show((Activity) context);
//                        }*/
//
//                        interstitialAd.setFullScreenContentCallback(
//                                new FullScreenContentCallback() {
//                                    @Override
//                                    public void onAdDismissedFullScreenContent() {
//                                        // Called when fullscreen content is dismissed.
//                                        // Make sure to set your reference to null so you don't
//                                        // show it a second time.
//
//                                        if (exit == 1) {
//                                            exit = 0;
//                                            context.startActivity(new Intent(context, ExitActivity.class));
//                                            return;
//                                        }
//
////                                        if (splashAds == 1) {
////                                            splashAds = 2;
////                                            context.startActivity(new Intent(context, GetStartActivity_crick.class));
////                                        }
//                                        ginterstitialAd = null;
//                                        dismiss();
//                                        load_GinterstitialAd();     // Reload Interstitial Ads
//
//                                        Log.d("TAG", "The ad was dismissed.");
//                                    }
//
//                                    @Override
//                                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
//                                        // Called when fullscreen content failed to show.
//                                        // Make sure to set your reference to null so you don't
//                                        // show it a second time.
//                                        ginterstitialAd = null;
//                                        dismiss();
//                                        Log.d("TAG", "The ad failed to show.");
//                                    }
//
//                                    @Override
//                                    public void onAdShowedFullScreenContent() {
//                                        // Called when fullscreen content is shown.
//                                        Log.d("TAG", "The ad was shown.");
//                                        dismiss();
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        ginterstitialAd = null;
//                        dismiss();
//                        // admob interstitial failed
////                        load_interstitialAd();
//                    }
//                });
//    }
//
//}
