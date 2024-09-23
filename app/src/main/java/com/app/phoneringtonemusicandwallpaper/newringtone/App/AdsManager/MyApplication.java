package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;
import androidx.work.Configuration;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks, Configuration.Provider {

    public static int spl_call = 0;
    public static int chek_Openads = 0;

    private AppOpenManager appOpenManager;
    public static String openAdsId;

    @Override
    public Configuration getWorkManagerConfiguration() {

        Configuration myConfig = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.INFO)
                .build();
        return myConfig;
    }

    public static Activity activity;
    private static Context context;

    public static Context getAppContext() {
        return context;
    }


    public static Context getApplication() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        context = getApplicationContext();


//        if (resAds) {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        appOpenManager = new AppOpenManager(this);
//        }

        registerActivityLifecycleCallbacks(this);
        sharedPreferences = getSharedPreferences(getPackageName(), 0);
    }

    public void attachBaseContext(Context context2) {
        super.attachBaseContext(context2);
    }

    public static SharedPreferences sharedPreferences;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

        MyApplication.activity = activity;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        MyApplication.activity = activity;

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        MyApplication.activity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        MyApplication.activity = null;
    }

}
