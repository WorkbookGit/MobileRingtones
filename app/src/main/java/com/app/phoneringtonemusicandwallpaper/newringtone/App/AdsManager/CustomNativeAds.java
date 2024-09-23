package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager;


import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.resAds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

public class CustomNativeAds {

    AlertDialog dialog;

    Context context;

    AdLoader.Builder builder;


    public CustomNativeAds(Context context) {
        this.context = context;
    }

    public void loadNativeAds(boolean ads_layout, boolean show_dialog) {
        if (resAds) {
            if (show_dialog) {
                show();
            }
            loadGoogleNativeAds(ads_layout);
        }
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder.setView(inflater.inflate(R.layout.please_wait_dialog, null));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    private void loadGoogleNativeAds(boolean ads_layout) {

        if (adConfig.getNativead_showing() != null) {
            if (adConfig.getNativead_showing()) {
                if (adConfig.getNative_id() != null) {
                    builder = new AdLoader.Builder(context, adConfig.getNative_id());
                }
            } else {
                builder = new AdLoader.Builder(context, adConfig.getNative_id());
            }
        } else {
            builder = new AdLoader.Builder(context, adConfig.getNative_id());
        }


        builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                if (((Activity) context).isDestroyed()) {
                    nativeAd.destroy();
                    return;
                }
                if (ads_layout) {
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template = ((Activity) context).findViewById(R.id.gnt_small_template_view);
                    template.setVisibility(View.VISIBLE);
                    template.setStyles(styles);
                    template.setNativeAd(nativeAd);
                } else {
                    NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                    TemplateView template1 = ((Activity) context).findViewById(R.id.gnt_medium_template_view);
                    template1.setVisibility(View.VISIBLE);
                    template1.setStyles(styles);
                    template1.setNativeAd(nativeAd);
                }
                dismiss();
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                dismiss();
//                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                    loadGoogleNativeAds(ads_layout);
            }
        });

        builder.withNativeAdOptions(new NativeAdOptions.Builder().build());
        builder.build().loadAd(new AdRequest.Builder().build());
    }

}
