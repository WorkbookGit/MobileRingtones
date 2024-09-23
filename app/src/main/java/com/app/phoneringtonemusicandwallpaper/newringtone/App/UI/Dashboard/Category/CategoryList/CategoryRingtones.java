package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryList;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.filteredList;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.list;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.CategorySoundsAdapter.check_lastplay;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.editor;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.ratingShow;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomNativeAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.WallApiClient;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategorySoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.CategorySoundsAdapter;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.NetworkAudioPlayer;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnCategorySoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CategoryRingtones extends AppCompatActivity implements OnCategorySoundClickListener {

    CategorySoundsAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar pageProgress;
    RelativeLayout noDataView, noSearchData;
    EditText etSearch;
    CategorySoundModel categorySoundModel;

    public static int selectedPosition = -1;
    public static int lastSelectedPosition = -1;
    CustomInterstitialAds customInterstitialAds;
    CustomNativeAds customNativeAds;
    public static String searchText = "";


    ImageView ic_back;
    TextView title;
    String categoryTitle;
    int check_position;
    AlertDialog dialog;
    SharedPreferences sharedPref;

    boolean ratingBool = false;
    private int PLAY_STORE_REQUEST_CODE = 100;
    private int EMAIL_REQUEST_CODE = 101;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_ringtones);


        check_position = getIntent().getIntExtra("position", 0);
        categoryTitle = getIntent().getStringExtra("name");

        sharedPref = CategoryRingtones.this.getPreferences(Context.MODE_PRIVATE);

        list.clear();
        recyclerView = findViewById(R.id.recycler_sounds);
        pageProgress = findViewById(R.id.pageProgress);
        noDataView = findViewById(R.id.rl_noDataFound);
        noSearchData = findViewById(R.id.no_search_data);
        ic_back = findViewById(R.id.ic_back);
        title = findViewById(R.id.app_name);
        etSearch = findViewById(R.id.et_search);


        ic_back.setVisibility(View.VISIBLE);
        title.setText(categoryTitle + "  Ringtones");
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryRingtones.this));

        customInterstitialAds = new CustomInterstitialAds(CategoryRingtones.this);
        customNativeAds = new CustomNativeAds(CategoryRingtones.this);

        // Native Ad
        customNativeAds.loadNativeAds(true, false);

        pageProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noDataView.setVisibility(View.GONE);
        noSearchData.setVisibility(View.GONE);


        if (check_position == 0) {
            LoadRingtoneData(1);
        } else if (check_position == 1) {
            LoadRingtoneData(2);
        } else if (check_position == 2) {
            LoadRingtoneData(3);
        } else if (check_position == 3) {
            LoadRingtoneData(4);
        } else if (check_position == 4) {
            LoadRingtoneData(5);
//            LoadRingtoneData(6);
        } else if (check_position == 5) {
            LoadRingtoneData(6);
//            LoadRingtoneData(7);
        } else if (check_position == 6) {
            LoadRingtoneData(7);
        }


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        boolean highScore = sharedPref.getBoolean(getString(R.string.ratingPrefKey), true);
        int ratingCount = sharedPref.getInt("rateCount", 0);
        if (/*ratingCount != 3 &&*/ ratingShow == 4 && !highScore) {
//            ratingShow = 0;
            existPopup();
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
        }

        ImageView close_dialog = dialog.findViewById(R.id.close_dialog);
        RelativeLayout rl_rate = dialog.findViewById(R.id.rl_rate);
        TextView tv_feedback = dialog.findViewById(R.id.tv_feedback);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);

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
            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void LoadRingtoneData(int categorytype) {

        try {
            String apiUrl = "https://ringtone.deznyx.com/ringtone/" + "fetch_audio_by_category.php?category_id=" + categorytype;
            if (list.isEmpty()) {

                WallApiClient.makeWallpaperStringRequest(this, apiUrl, new WallApiClient.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {

                        try {
                            categorySoundModel = new Gson().fromJson(response, CategorySoundModel.class);

                            list.addAll(categorySoundModel.getData());
//                        ringtoneList.addAll(categorySoundModel.getData());

//                            Collections.shuffle(list);
                            Collections.reverse(list);
                            adapter = new CategorySoundsAdapter(CategoryRingtones.this, list);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnCategorySoundClickListener(CategoryRingtones.this);

                            if (list.isEmpty()) {
                                pageProgress.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                            } else {
                                pageProgress.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                noDataView.setVisibility(View.GONE);
                            }


                            etSearch.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                    // TODO Auto-generated method stub
//                                adapter.getFilter().filter(arg0);

                                    searchText = charSequence.toString().toLowerCase();

                                    filteredList.clear();
                                    for (CategorySoundModel.Datum item : list) {
                                        if (item.getFileName().toLowerCase().contains(searchText)) {
                                            filteredList.add(item);
                                        }
                                    }

                                    if (searchText.isEmpty()) {
                                        // Update the adapter with the filtered data
                                        adapter.updateData(list);
                                    } else {
                                        // Update the adapter with the filtered data
                                        if (filteredList.isEmpty()) {
                                            noSearchData.setVisibility(View.VISIBLE);
                                            adapter.updateData(filteredList);
                                        } else {
                                            noSearchData.setVisibility(View.GONE);
                                            adapter.updateData(filteredList);
                                        }
                                    }
                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                              int arg3) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void afterTextChanged(Editable arg0) {
                                    // TODO Auto-generated method stub

                                }
                            });

                        } catch (Exception e) {
                            if (list.isEmpty()) {
                                pageProgress.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);
                            } else {
                                pageProgress.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                noDataView.setVisibility(View.GONE);
                            }
                            Log.e("Error :: ", String.valueOf(e.getMessage()));
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("Error", "API call failed: " + error);

                        pageProgress.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("catch log>> LoadRingtoneData >>", String.valueOf(e.getMessage()));
        }

    }

    @Override
    public void onCategorySoundClick(String url, ProgressBar progressbar, int i, ImageView play_btn) {
        try {
            NetworkAudioPlayer.startAudio(this, url, progressbar, play_btn, false);
            check_lastplay = play_btn;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            searchText = "";
            if (NetworkAudioPlayer.isPlaying) {
                NetworkAudioPlayer.stopAudio();
            }
        } catch (Exception ignored) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (NetworkAudioPlayer.isPlaying) {
                NetworkAudioPlayer.stopAudio();
            }
        } catch (Exception ignored) {
        }
    }

    void downloadRingtone(String url) {
        try {
//            Uri qureka_url = Uri.parse("https://1112.go.qureka.com/intro/question");
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(3);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setTitle(getResources().getString(R.string.app_name));
//            request.setDescription("Data download using.2131886108");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            String str2 = Environment.DIRECTORY_DOWNLOADS + "/Ringtones";

            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Ringtones");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Ringtones/" + ".mp3"));
            request.setMimeType("*/*");
            ((DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);

            Toast.makeText(this, "Downloading Start", Toast.LENGTH_SHORT).show();
//        alertDialog.dismiss();
//            if (!qureka_url.equals("")) {
//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                CustomTabsIntent customTabsIntent = builder.build();
//                builder.addDefaultShareMenuItem();
//                customTabsIntent.intent.setPackage("com.android.chrome");
//                builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//                customTabsIntent.launchUrl(this, qureka_url);
//            }
        } catch (Exception e) {
            Log.e("error", "Download error");
        }

    }

}