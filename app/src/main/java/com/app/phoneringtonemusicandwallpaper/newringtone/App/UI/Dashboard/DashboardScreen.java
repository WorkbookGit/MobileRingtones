//package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard;
//
//import static android.content.ContentValues.TAG;
//
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.MyApplication.chek_Openads;
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.MyApplication.spl_call;
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localRingtoneList;
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localSmsRingtoneList;
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.editor;
//import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.ratingShow;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RatingBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomBannerAds;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.InterNet.InternetStateChecker;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.Inter_Callback;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategorySoundModel;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.SoundModel;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.SoundsAdapter;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryFragment;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Ringtone.RingtonesFragment;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.SMS.SmsFragment;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.MusicPlayerView.PlayMusicActivity;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.RawFileDurationHelper;
//import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnSoundClickListener;
//import com.app.phoneringtonemusicandwallpaper.newringtone.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.android.material.navigation.NavigationView;
//import com.google.android.play.core.appupdate.AppUpdateInfo;
//import com.google.android.play.core.appupdate.AppUpdateManager;
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.core.install.model.AppUpdateType;
//import com.google.android.play.core.install.model.UpdateAvailability;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.messaging.FirebaseMessaging;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//public class DashboardScreen extends AppCompatActivity implements OnSoundClickListener {
//
//
//    public static List<CategorySoundModel.Datum> list = new ArrayList<>();
//    public static List<CategorySoundModel.Datum> filteredList = new ArrayList<>();
//
//    public static List<File> cachedAudioFiles = new ArrayList<>();
//
//
//    public static boolean checkRingtoneSMS = true;
//
//
//    SoundsAdapter adapter;
//    RecyclerView recyclerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_dashboard_screen);
//
//        try {
//
//            recyclerView = (RecyclerView) findViewById(R.id.recycler_sounds);
//
//            localRingtoneList.add(new SoundModel("Ringtone 1", R.raw.r0));
//            localRingtoneList.add(new SoundModel("Ringtone 2", R.raw.r1));
//            localRingtoneList.add(new SoundModel("Ringtone 3", R.raw.r2));
//            localRingtoneList.add(new SoundModel("Ringtone 4", R.raw.r3));
//            localRingtoneList.add(new SoundModel("Ringtone 5", R.raw.r4));
//            localRingtoneList.add(new SoundModel("Ringtone 6", R.raw.r5));
//            localRingtoneList.add(new SoundModel("Ringtone 7", R.raw.r6));
//            localRingtoneList.add(new SoundModel("Ringtone 8", R.raw.r7));
//            localRingtoneList.add(new SoundModel("Ringtone 9", R.raw.r8));
//            localRingtoneList.add(new SoundModel("Ringtone 10", R.raw.r9));
//            localRingtoneList.add(new SoundModel("Ringtone 11", R.raw.r10));
//            localRingtoneList.add(new SoundModel("Ringtone 12", R.raw.r11));
//            localRingtoneList.add(new SoundModel("Ringtone 13", R.raw.r12));
//            localRingtoneList.add(new SoundModel("Ringtone 13", R.raw.r12));
//            localRingtoneList.add(new SoundModel("Ringtone 14", R.raw.r13));
//            localRingtoneList.add(new SoundModel("Ringtone 15", R.raw.r14));
//            localRingtoneList.add(new SoundModel("Ringtone 16", R.raw.r15));
//            localRingtoneList.add(new SoundModel("Ringtone 17", R.raw.r16));
//            localRingtoneList.add(new SoundModel("Ringtone 18", R.raw.r17));
//            localRingtoneList.add(new SoundModel("Ringtone 19", R.raw.r18));
//            localRingtoneList.add(new SoundModel("Ringtone 20", R.raw.r19));
//            localRingtoneList.add(new SoundModel("Ringtone 21", R.raw.r20));
//            localRingtoneList.add(new SoundModel("Ringtone 22", R.raw.r21));
//            localRingtoneList.add(new SoundModel("Ringtone 23", R.raw.r22));
//            localRingtoneList.add(new SoundModel("Ringtone 24", R.raw.r23));
//            localRingtoneList.add(new SoundModel("Ringtone 25", R.raw.r24));
//            localRingtoneList.add(new SoundModel("Ringtone 26", R.raw.r25));
//            localRingtoneList.add(new SoundModel("Ringtone 27", R.raw.r26));
//            localRingtoneList.add(new SoundModel("Ringtone 28", R.raw.r27));
//            localRingtoneList.add(new SoundModel("Ringtone 29", R.raw.r28));
//            localRingtoneList.add(new SoundModel("Ringtone 30", R.raw.r29));
//            localRingtoneList.add(new SoundModel("Ringtone 31", R.raw.r30));
//            localRingtoneList.add(new SoundModel("Ringtone 32", R.raw.r31));
//            localRingtoneList.add(new SoundModel("Ringtone 33", R.raw.r32));
//            localRingtoneList.add(new SoundModel("Ringtone 34", R.raw.r33));
//            localRingtoneList.add(new SoundModel("Ringtone 35", R.raw.r34));
//            localRingtoneList.add(new SoundModel("Ringtone 36", R.raw.r35));
//
//
//
//            recyclerView.setLayoutManager(new LinearLayoutManager(DashboardScreen.this));
//            adapter = new SoundsAdapter( localRingtoneList);
//            recyclerView.setAdapter(adapter);
//            this.adapter.setOnSoundClickListener(this);
//
//
//        } catch (Exception e) {
//            Log.e("Something want wrong with: ", String.valueOf(e.getMessage()));
//        }
//    }
//
//
//    public static List<File> getCachedAudioFiles(Context context) {
//        File cacheDir = context.getCacheDir();
//        File[] files = cacheDir.listFiles((dir, name) -> name.endsWith(".mp3"));
//        if (files != null && files.length > 0) {
//            cachedAudioFiles.clear();
//            cachedAudioFiles.addAll(Arrays.asList(files));
//        }
//        return files != null ? Arrays.asList(files) : new ArrayList<>();
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.navigation_menu, menu);
//        return true;
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }
//
//    @Override
//    public void onSoundClick(SoundModel soundModel, int i) {
//
//    }
//}


package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard;

import static android.content.ContentValues.TAG;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.MyApplication.chek_Openads;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.MyApplication.spl_call;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.RINGTONE_SOUNDS;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.SMS_SOUNDS;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localRingtoneList;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localSmsRingtoneList;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.editor;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.ratingShow;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomBannerAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.InterNet.InternetStateChecker;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategorySoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.SoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryFragment;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Ringtone.RingtonesFragment;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.SMS.SmsFragment;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.RawFileDurationHelper;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DashboardScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    BottomNavigationView bottom_navigation;
    NavigationView navigationView;
    CustomInterstitialAds customInterstitialAds;
    AlertDialog dialog;
    boolean isShowDialog = false;

    boolean doubleBackToExitPressedOnce = false;


    public static List<CategorySoundModel.Datum> list = new ArrayList<>();
    public static List<CategorySoundModel.Datum> filteredList = new ArrayList<>();

    public static List<File> cachedAudioFiles = new ArrayList<>();

    private int PLAY_STORE_REQUEST_CODE = 100;
    private int EMAIL_REQUEST_CODE = 101;
    boolean ratingBool = false;

    Toolbar toolbar;
    public static boolean checkRingtoneSMS = false;

    SharedPreferences sharedPref;


    public static DashboardScreen dashboardScreen = new DashboardScreen();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_screen);

        try {

            checkAppVersion();
            notification_data();

            dashboardScreen = DashboardScreen.this;
            Bundle bundle1 = new Bundle();
            bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
            bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Main Screen");
            FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1);

            customInterstitialAds = new CustomInterstitialAds(DashboardScreen.this);
            new InternetStateChecker.Builder(DashboardScreen.this).build();


            if (adConfig.getBannerad_showing() != null) {
                if (adConfig.getBannerad_showing()) {
                    if (adConfig.getBanner_id() != null) {
                        CustomBannerAds bannerAds = new CustomBannerAds(this);
                        bannerAds.loadBanner_ads();
                    }
                }
            } else {
                // Handle else case (optional)
            }

            findViewByID();
            setDrawer();
            bottmnav();
            checkAppVersion();
            // checkSystemWritePermission();
            // Permission
            isStoragePermissionGranted();


            sharedPref = DashboardScreen.this.getPreferences(Context.MODE_PRIVATE);


            loadRingtones();
            loadSMSRingtones();

            bottom_navigation.setSelectedItemId(R.id.ic_music);
            loadFragment(new RingtonesFragment());

            getCachedAudioFiles(DashboardScreen.this);

            Log.e("onCreate: ", "Load data..........");
        } catch (Exception e) {
            Log.e("Something want wrong with: ", String.valueOf(e.getMessage()));
        }

    }


    private void loadRingtones() {
        for (int i = 0; i < RINGTONE_SOUNDS.length; i++) {
            int duration = RawFileDurationHelper.getRawFileDuration(DashboardScreen.this, RINGTONE_SOUNDS[i]);
            int minutes = (duration / 1000) / 60;
            int seconds = (duration / 1000) % 60;
            String durationFormatted = String.format("%02d:%02d", minutes, seconds);
            int ringtonePos = i + 1;
            localRingtoneList.add(new SoundModel("Ringtone " + ringtonePos, RINGTONE_SOUNDS[i], durationFormatted));
        }
    }

    private void loadSMSRingtones() {
        for (int i = 0; i < SMS_SOUNDS.length; i++) {
            int duration = RawFileDurationHelper.getRawFileDuration(DashboardScreen.this, SMS_SOUNDS[i]);
            int minutes = (duration / 1000) / 60;
            int seconds = (duration / 1000) % 60;
            String durationFormatted = String.format("%02d:%02d", minutes, seconds);
            int ringtonePos = i + 1;
            localSmsRingtoneList.add(new SoundModel("SMS Ringtone" + ringtonePos, SMS_SOUNDS[i], durationFormatted));
        }
    }


    public static List<File> getCachedAudioFiles(Context context) {
        File cacheDir = context.getCacheDir();
        File[] files = cacheDir.listFiles((dir, name) -> name.endsWith(".mp3"));
        if (files != null && files.length > 0) {
            cachedAudioFiles.clear();
            cachedAudioFiles.addAll(Arrays.asList(files));
        }
        return files != null ? Arrays.asList(files) : new ArrayList<>();
    }

    private void setDrawer() {

        toolbar.setTitle(getResources().getString(R.string.app_name));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    private void bottmnav() {
        bottom_navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment selectedFragment = null;
                String fragmentTag = "";


                if (item.getItemId() == R.id.ic_music) {
                    fragmentTag = "RingtonesFragment";
                    selectedFragment = fragmentManager.findFragmentByTag(fragmentTag);
                    if (selectedFragment == null) {
                        selectedFragment = new RingtonesFragment();
                        transaction.add(R.id.fragment_container, selectedFragment, fragmentTag);
                    }
                    checkRingtoneSMS = false;
                }
                if (item.getItemId() == R.id.ic_SMS) {
                    fragmentTag = "SmsFragment";
                    selectedFragment = fragmentManager.findFragmentByTag(fragmentTag);
                    if (selectedFragment == null) {
                        selectedFragment = new SmsFragment();
                        transaction.add(R.id.fragment_container, selectedFragment, fragmentTag);
                    }
                    checkRingtoneSMS = true;
                }
                if (item.getItemId() == R.id.ic_category) {
                    fragmentTag = "CategoryFragment";
                    selectedFragment = fragmentManager.findFragmentByTag(fragmentTag);
                    if (selectedFragment == null) {
                        selectedFragment = new CategoryFragment();
                        transaction.add(R.id.fragment_container, selectedFragment, fragmentTag);
                    }
                }

                for (Fragment fragment : fragmentManager.getFragments()) {
                    transaction.hide(fragment);
                }
                // Show the selected fragment
                if (selectedFragment != null) {
                    transaction.show(selectedFragment);
                }
                transaction.commit();
                return true;
            }
        });
    }

    public void notification_data() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("MobileRingtone").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "Subscribed";
                if (!task.isSuccessful()) {
                    msg = "Subscribe failed";
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

    private void findViewByID() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nevMenuBar);
        toolbar = findViewById(R.id.toolbar_layout);
    }

    private void checkAppVersion() {

        AppUpdateManager appUpdateManagerMobile = AppUpdateManagerFactory.create(DashboardScreen.this);
        Task<AppUpdateInfo> appUpdateInfoTaskMobile = appUpdateManagerMobile.getAppUpdateInfo();

        appUpdateInfoTaskMobile.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                updatePopup();
                Log.e("Abc >>>>", "update is available");
            } else {
                Log.e("Abc >>>>", "Already latest version");
            }
        });
    }

    private void updatePopup() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        final Dialog dialog = new Dialog(this, R.style.ThemeRateDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView btn_update = dialog.findViewById(R.id.btn_update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectPlay();
            }
        });

        dialog.show();
    }

    private void redirectPlay() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public boolean isStoragePermissionGranted() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted");
            return true;
        } else {

            Log.v(TAG, "Permission is revoked");
            if (Build.VERSION.SDK_INT >= 33) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_AUDIO}, 1);
//                checkSystemWritePermission();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    private boolean checkSystemWritePermission() {
        boolean retVal = false;
        try {
            retVal = Settings.System.canWrite(this);
            Log.d("TAG", "Can Write Settings: " + retVal);
            if (!retVal) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("Permission error", Objects.requireNonNull(e.getMessage()));
        }

        return retVal;
    }

    public void onBackPressed() {
//        MediaPlayerUtils.stopSound();
//        if (Globals.mPlayerCategory != null) {
//            Globals.mPlayerCategory.release();
//        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "BACK again to exit", Toast.LENGTH_SHORT).show();
        chek_Openads = 2;
        spl_call = 0;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean highScore = sharedPref.getBoolean(getString(R.string.ratingPrefKey), false);
        int ratingCount = sharedPref.getInt("rateCount", 0);
        if (/*ratingCount != 3 &&*/ ratingShow == 4 && !highScore) {
//            ratingShow = 0;

            if (!isShowDialog) {
                existPopup();
            }
        }
    }

    private void existPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.exit_dialog, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!dialog.isShowing()) {
            dialog.show();
            isShowDialog = true;
        }

        ImageView close_dialog = dialog.findViewById(R.id.close_dialog);
        RelativeLayout rl_rate = dialog.findViewById(R.id.rl_rate);
        TextView tv_feedback = dialog.findViewById(R.id.tv_feedback);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        ImageView ic_img = dialog.findViewById(R.id.ic_img);

        ic_img.setVisibility(View.VISIBLE);
        ic_img.setImageResource(R.drawable.emj_5);

        rl_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBool) {
                    try {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(getString(R.string.ratingPrefKey), true);
                        editor.apply();
                        startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())), PLAY_STORE_REQUEST_CODE);
                        editor.putInt("rateCount", 3);
                        editor.apply();
                    } catch (Exception e) {
                        Log.e(":~~>", e.getMessage());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                        startActivity(intent);
                        editor.putInt("rateCount", 3);
                        editor.apply();
                    }
                    isShowDialog = false;
                    dialog.cancel();
                } else {
                    try {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(getString(R.string.ratingPrefKey), true);
                        editor.apply();
                        Intent sendIntentGmail = new Intent(Intent.ACTION_SENDTO);
                        sendIntentGmail.setData(Uri.parse("mailto:" + getString(R.string.contact_email)));
                        sendIntentGmail.putExtra(Intent.EXTRA_SUBJECT, "Issue in parsing");

                        try {
                            startActivityForResult(sendIntentGmail, EMAIL_REQUEST_CODE);
                            editor.putInt("rateCount", 3);
                            editor.apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                            // Handle the exception
                        }
                    } catch (Exception e) {
                        Log.e(":~~>", e.getMessage());
                        Intent sendIntentIfGmailFail = new Intent(Intent.ACTION_SEND);
                        sendIntentIfGmailFail.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                        sendIntentIfGmailFail.setType("message/rfc822");
                        sendIntentIfGmailFail.putExtra(Intent.EXTRA_EMAIL, getString(R.string.contact_email));
                        sendIntentIfGmailFail.putExtra(Intent.EXTRA_SUBJECT, "Issue in parsing");
                        if (sendIntentIfGmailFail.resolveActivity(getPackageManager()) != null) {
                            startActivity(Intent.createChooser(sendIntentIfGmailFail, "Pick an Email provider"));
                        }
                        editor.putInt("rateCount", 3);
                        editor.apply();
                    }
                    isShowDialog = false;
                    dialog.cancel();
                }
            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating > 3) {
                    tv_feedback.setText("Rate");
                    ratingBool = true;
                } else if (rating < 4) {
                    tv_feedback.setText("Send Us a Feedback");
                    ratingBool = false;
                }

                if (rating == 1) {
                    ic_img.setVisibility(View.VISIBLE);
                    ic_img.setImageResource(R.drawable.emj_1);
                } else if (rating == 2) {
                    ic_img.setVisibility(View.VISIBLE);
                    ic_img.setImageResource(R.drawable.emj_2);
                } else if (rating == 3) {
                    ic_img.setVisibility(View.VISIBLE);
                    ic_img.setImageResource(R.drawable.emj_3);
                } else if (rating == 4) {
                    ic_img.setVisibility(View.VISIBLE);
                    ic_img.setImageResource(R.drawable.emj_4);
                } else if (rating == 5) {
                    ic_img.setVisibility(View.VISIBLE);
                    ic_img.setImageResource(R.drawable.emj_5);
                } else {
                    ic_img.setVisibility(View.INVISIBLE);
                }
            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ic_privacy_policy) {
            try {
                String url = "https://pentone2.wordpress.com/pentone-privacy-policy/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (Exception e) {
                Log.e("ERROR NEV", e.getMessage());
            }
        } else if (id == R.id.ic_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String shareMessage;
                shareMessage = "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
                Log.e("ERROR NEV", e.getMessage());
            }
        } else if (id == R.id.ic_Contact_us) {
            try {

                Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                selectorIntent.setData(Uri.parse("mailto:"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.contact_email)});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobile Ringtones");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, Mobile Ringtones Team.....");
                emailIntent.setSelector(selectorIntent);
                startActivity(Intent.createChooser(emailIntent, "Send email"));

//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.contact_email)});
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Mobile Ringtones");
//                intent.putExtra(Intent.EXTRA_TEXT,"Hello, Mobile Ringtones Team.....");
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }

            } catch (Exception e) {
                e.printStackTrace();
                Intent sendIntentIfGmailFail = new Intent(Intent.ACTION_SEND);
                sendIntentIfGmailFail.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                sendIntentIfGmailFail.setType("message/rfc822");
                sendIntentIfGmailFail.putExtra(Intent.EXTRA_EMAIL, getString(R.string.contact_email));
                sendIntentIfGmailFail.putExtra(Intent.EXTRA_SUBJECT, "Issue in parsing");

                if (sendIntentIfGmailFail.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sendIntentIfGmailFail, "Pick an Email provider"));
                }
            }
        } else if (id == R.id.ic_rate_us) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(intent);
        }
        drawer.close();

        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}