package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.MyApplication.chek_Openads;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.MyApplication.spl_call;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.Adconfig;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.SmsAdapter;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SplashScreen extends AppCompatActivity {

    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;
    public static String prevStarted = "prevStarted";
    public static int OpenAdsWait = 0;
    public static boolean openAds_bool = false;
    public static boolean resAds = true;
    public static int counter_native = 0;
    public static int ratingShow = 1;
    public static SmsAdapter soundsAdapter;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public static Adconfig adConfig = new Adconfig();
    public static SharedPreferences datePref = null;
    AppOpenAd.AppOpenAdLoadCallback loadCallback;

    public static int increment = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        androidx.core.splashscreen.SplashScreen splashScreen = androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);


        // Optionally: add logic to control when to remove the splash screen
        splashScreen.setKeepOnScreenCondition(() -> {
            // Return true if you want to keep the splash screen longer (e.g., during loading)
            return false; // Splash screen will be dismissed immediately
        });

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        fetchRemoteConfig();
    }

    private void fetchRemoteConfig() {
        try {
            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            applyRemoteConfig();
                        } else {
                            // Handle task failure
                            Log.e("RemoteConfig", "Fetch failed", task.getException());
                        }
                    });
        } catch (Exception e) {
//            Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show();
        }
    }

    private void applyRemoteConfig() {
        String jsonArrayString = mFirebaseRemoteConfig.getString("ads_test_id");
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayString);

            // Iterate through the array and process each JSON object
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.has("bannerad_showing")) {
                    adConfig.setBannerad_showing(jsonObject.getBoolean("bannerad_showing"));
                    adConfig.setBanner_id(jsonObject.optString("banner_id", null));
                }
                if (jsonObject.has("nativead_showing")) {
                    adConfig.setNativead_showing(jsonObject.getBoolean("nativead_showing"));
                    adConfig.setNative_id(jsonObject.optString("native_id", null));
                    adConfig.setSamll_native(jsonObject.optBoolean("samll_native", false));
                }
                if (jsonObject.has("interad_showing")) {
                    adConfig.setInterad_showing(jsonObject.getBoolean("interad_showing"));
                    adConfig.setInter_id(jsonObject.optString("inter_id", null));
                    adConfig.setCounter(jsonObject.optInt("counter", 0));
                }
                if (jsonObject.has("openad_showing")) {
                    adConfig.setOpenad_showing(jsonObject.getBoolean("openad_showing"));
                    adConfig.setOpen_id(jsonObject.optString("open_id", null));
                    adConfig.setSplash_showing(jsonObject.optBoolean("splash_showing", false));
                    adConfig.setOnstart_showing(jsonObject.optBoolean("onstart_showing", false));
                }
                if (jsonObject.has("rewardad_showing")) {
                    adConfig.setRewardad_showing(jsonObject.getBoolean("rewardad_showing"));
                    adConfig.setReward_id(jsonObject.optString("reward_id", null));
                }
            }

            if (spl_call == 0) {
                spl_call = 1;
                if (adConfig.getOpenad_showing() != null && adConfig.getOpenad_showing()) {
                    if (adConfig.getSplash_showing() != null && adConfig.getSplash_showing()) {
                        AppOpenAd_SplashScreen(adConfig.getOpen_id());
                    } else {
                        skipData();
                        finish();
                    }
                } else {
                    skipData();
                    finish();
                }
            }
        } catch (JSONException e) {
            skipData();
        }
    }

    private void AppOpenAd_SplashScreen(String openad_id) {

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                appOpenAd.show(SplashScreen.this);
                appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        skipData();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        skipData();
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                skipData();
            }
        };
        AppOpenAd.load(SplashScreen.this, openad_id, new AdRequest.Builder().build(), 1, loadCallback);
    }

    private void skipData() {
        chek_Openads = 1;
        Intent intent = new Intent(SplashScreen.this, DashboardScreen.class);
        intent.putExtra("Slider", false);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}