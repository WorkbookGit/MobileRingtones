package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.MusicPlayerView;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localRingtoneList;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localSmsRingtoneList;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.checkRingtoneSMS;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.ratingShow;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomBannerAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.Inter_Callback;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.MediaPlayerUtils;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.NetworkAudioPlayer;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.RawFileDurationHelper;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.marcinmoskala.arcseekbar.ArcSeekBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlayMusicActivity extends AppCompatActivity {


    TextView tv_ringtoneTitle;
    ImageView iv_save_ringtone, iv_set_ringtone, iv_previous_btn, iv_play_btn, iv_next_btn, iv_back;
    ArcSeekBar seekBar;
    int ringtonePos;
    boolean isFromLocal = false;
    String localFilePath = "";
    int RingtoneSound;
    int currentProgress = 0;
    int maxProgress = 0;
    boolean isRestart = false;
    Handler progressHandler = new Handler();
    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/Ringtones/";
    CustomInterstitialAds customInterstitialAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_music);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewByID();
        getIntentData();
        firstTimePlay();
        onClick();

        int duration = 0;
        if (isFromLocal) {
            try {
                String mediaPath = Uri.parse(localFilePath).getPath();
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(mediaPath);
                String localDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                mmr.release();
                if (localDuration != null && !localDuration.isEmpty()) {
                    duration = Integer.parseInt(localDuration);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            if (checkRingtoneSMS) {
                duration = RawFileDurationHelper.getRawFileDuration(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound());
            } else {
                duration = RawFileDurationHelper.getRawFileDuration(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound());
            }
        }

        int minutes = (duration / 1000) / 60;
        int seconds = (duration / 1000) % 60;

        maxProgress = seconds + (minutes * 60);
        maxProgress = maxProgress * 10;
        seekBar.setMaxProgress(maxProgress);
        seekBar.setProgress(0);


        startProgress();
    }

    private void startProgress() {
        try {
            if (MediaPlayerUtils.isPlaying && (currentProgress < maxProgress)) {
                progressHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentProgress++;
                        seekBar.setProgress(currentProgress);
                        startProgress();
                    }
                }, 100);
            } else {
                if (currentProgress == maxProgress) {
                    isRestart = true;
                    seekBar.setProgress(0);
                    currentProgress = 0;
                    MediaPlayerUtils.stopSound();
                    iv_play_btn.setImageResource(R.drawable.play_btn);
                }
            }
        } catch (Exception ignored) {

        }
    }


    private void firstTimePlay() {
        iv_play_btn.setImageResource(R.drawable.pause_btn);

        if (isFromLocal) {
            MediaPlayerUtils.startSound(PlayMusicActivity.this, 0, localFilePath);
        } else {
            if (checkRingtoneSMS) {
                MediaPlayerUtils.startSound(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound(), "");
            } else {
                MediaPlayerUtils.startSound(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound(), "");
            }
            /*MediaPlayerUtils.startSound(PlayMusicActivity.this, RingtoneSound);*/
        }
    }

    private void onClick() {
        iv_play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Static Ringtone*/
                if (MediaPlayerUtils.isPlaying) {
                    MediaPlayerUtils.pauseSound();
                    iv_play_btn.setImageResource(R.drawable.play_btn);
                } else {
                    if (isRestart) {
                        currentProgress = 0;
                        isRestart = false;
                        if (isFromLocal) {
                            MediaPlayerUtils.startSound(PlayMusicActivity.this, 0, localFilePath);
                        } else {
                            if (checkRingtoneSMS) {
                                MediaPlayerUtils.startSound(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound(), "");
                            } else {
                                MediaPlayerUtils.startSound(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound(), "");
                            }
                        }

                    } else {
                        MediaPlayerUtils.resumeSound();
                    }
                    iv_play_btn.setImageResource(R.drawable.pause_btn);
                    startProgress();
                }

            }
        });

        iv_save_ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customInterstitialAds.loadInterstitialAds(new Inter_Callback() {
                    @Override
                    public boolean Intersitial_adscallback(boolean ads) {

                        if (checkRingtoneSMS) {
                            downloadRingtone(String.valueOf(localSmsRingtoneList.get(ringtonePos).getSound()), 0);
                        } else {
                            downloadRingtone(String.valueOf(localRingtoneList.get(ringtonePos).getSound()), 0);
                        }

                        return false;
                    }
                }, true);


            }
        });

        iv_set_ringtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MediaPlayerUtils.isPlaying) {
                    MediaPlayerUtils.pauseSound();
                    iv_play_btn.setImageResource(R.drawable.play_btn);
                }

                if (!Settings.System.canWrite(PlayMusicActivity.this)) {
                    // If the app doesn't have permission, open the system settings screen
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    Toast.makeText(PlayMusicActivity.this, "Please grant permission in settings", Toast.LENGTH_SHORT).show();
                } else {

                    customInterstitialAds.loadInterstitialAds(new Inter_Callback() {
                        @Override
                        public boolean Intersitial_adscallback(boolean ads) {

                            if (checkRingtoneSMS) {
                                downloadRingtone(String.valueOf(localSmsRingtoneList.get(ringtonePos).getSound()), 1);
                            } else {
                                downloadRingtone(String.valueOf(localRingtoneList.get(ringtonePos).getSound()), 1);
                            }
                            return false;
                        }
                    }, true);
                }


            }
        });
        iv_previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressHandler.removeCallbacksAndMessages(null);
                    if (MediaPlayerUtils.isPlaying) {
                        MediaPlayerUtils.stopSound();
                        iv_play_btn.setImageResource(R.drawable.play_btn);
                    }
                    seekBar.setProgress(0);
                    currentProgress = 0;
                    if (isFromLocal) {
                        MediaPlayerUtils.startSound(PlayMusicActivity.this, 0, localFilePath);
                    } else {
                        if (checkRingtoneSMS) {
                            ringtonePos = (ringtonePos - 1 + localSmsRingtoneList.size()) % localSmsRingtoneList.size();
                            tv_ringtoneTitle.setText(localSmsRingtoneList.get(ringtonePos).getName());
                            MediaPlayerUtils.startSound(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound(), "");
                        } else {
                            ringtonePos = (ringtonePos - 1 + localRingtoneList.size()) % localRingtoneList.size();
                            tv_ringtoneTitle.setText(localRingtoneList.get(ringtonePos).getName());
                            MediaPlayerUtils.startSound(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound(), "");
                        }
                    }


                    int duration;
                    if (checkRingtoneSMS) {
                        duration = RawFileDurationHelper.getRawFileDuration(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound());
                    } else {
                        duration = RawFileDurationHelper.getRawFileDuration(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound());
                    }
                    int minutes = (duration / 1000) / 60;
                    int seconds = (duration / 1000) % 60;

                    maxProgress = seconds + (minutes * 60);
                    maxProgress = maxProgress * 10;
                    seekBar.setMaxProgress(maxProgress);
                    iv_play_btn.setImageResource(R.drawable.pause_btn);
                    startProgress();
                } catch (Exception ignored) {
                }
            }
        });

        iv_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressHandler.removeCallbacksAndMessages(null);
                    if (MediaPlayerUtils.isPlaying) {
                        MediaPlayerUtils.stopSound();
                        iv_play_btn.setImageResource(R.drawable.play_btn);

                    }
                    seekBar.setProgress(0);
                    currentProgress = 0;

                    if (isFromLocal) {
                        MediaPlayerUtils.startSound(PlayMusicActivity.this, 0, localFilePath);
                    } else {
                        if (checkRingtoneSMS) {
                            ringtonePos = (ringtonePos + 1) % localSmsRingtoneList.size();
                            tv_ringtoneTitle.setText(localSmsRingtoneList.get(ringtonePos).getName());
                            MediaPlayerUtils.startSound(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound() /*RingtoneSound*/, "");
                        } else {
                            ringtonePos = (ringtonePos + 1) % localRingtoneList.size();
                            tv_ringtoneTitle.setText(localRingtoneList.get(ringtonePos).getName());
                            MediaPlayerUtils.startSound(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound() /*RingtoneSound*/, "");
                        }
                    }
                    int duration;
                    if (checkRingtoneSMS) {
                        duration = RawFileDurationHelper.getRawFileDuration(PlayMusicActivity.this, localSmsRingtoneList.get(ringtonePos).getSound());
                    } else {
                        duration = RawFileDurationHelper.getRawFileDuration(PlayMusicActivity.this, localRingtoneList.get(ringtonePos).getSound());
                    }

                    int minutes = (duration / 1000) / 60;
                    int seconds = (duration / 1000) % 60;
                    maxProgress = seconds + (minutes * 60);
                    maxProgress = maxProgress * 10;

                    seekBar.setMaxProgress(maxProgress);
                    iv_play_btn.setImageResource(R.drawable.pause_btn);
                    startProgress();
                } catch (Exception ignored) {
                }

            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingShow++;

                onBackPressed();
            }
        });
    }


    private void downloadRingtone(String ringtoneUrl, int type) {
        try {
            InputStream inputStream = null;
            Log.e("ringtone--->", "download_ringtone: " + ringtoneUrl);
            inputStream = getResources().openRawResource(Integer.parseInt(ringtoneUrl));
            byte[] bArr = new byte[0];
            try {
                bArr = new byte[inputStream.available()];
                inputStream.read(bArr);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String str = "MobileRingtone_" + System.currentTimeMillis() + ".ogg";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(path + str);
                fileOutputStream.write(bArr);
                fileOutputStream.flush();
                fileOutputStream.close();
                sendBroadcast(new Intent(
                        "android.intent.action.MEDIA_SCANNER_SCAN_FILE",
                        Uri.parse("file://" + path + str)
                ));
                File file = new File(path, str);
                if (type == 0) {
                    Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    /*Set Ringtone */
                    if (checkRingtoneSMS) {
                        setRingtone(file.getAbsolutePath(), 1);
                    } else {
                        setRingtone(file.getAbsolutePath(), 2);
                    }

                }
                Log.d("Save :~>", "saveCallRingtone: " + file.getAbsolutePath());
            } catch (IOException ignored) {
                Log.e("~~~>", ignored.toString());
            }

        } catch (Exception e) {
            Log.e("error", "Download error");
        }
    }


    private boolean setRingtone(String absolutePath, int type) {
        MediaScannerConnection.scanFile(this, new String[]{absolutePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                System.out.println("Ringtone file " + path + " was scanned successfully: " + uri);
                if (path == null || path.isEmpty()) {
                    RingtoneManager.setActualDefaultRingtoneUri(
                            PlayMusicActivity.this,
                            RingtoneManager.TYPE_RINGTONE,
                            MediaStore.Audio.Media.getContentUriForPath(
                                    new File(Settings.System.DEFAULT_RINGTONE_URI.getPath()).getAbsolutePath()
                            )
                    );
                    return;
                }
                try {
                    if (type == 1) {
                        RingtoneManager.setActualDefaultRingtoneUri(PlayMusicActivity.this, RingtoneManager.TYPE_NOTIFICATION, uri);
                    } else {
                        RingtoneManager.setActualDefaultRingtoneUri(PlayMusicActivity.this, RingtoneManager.TYPE_RINGTONE, uri);

                    }
                } catch (Throwable ignored) {
                }
            }
        });
        if (type == 1) {
            Toast.makeText(this, "Ringtone has been set successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SMS has been set successfully", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void getIntentData() {
        ringtonePos = getIntent().getIntExtra("RingtonePos", 0);
        isFromLocal = getIntent().getBooleanExtra("fromLocal", false);
        localFilePath = getIntent().getStringExtra("localFilePath");
        if (checkRingtoneSMS) {
            tv_ringtoneTitle.setText(localSmsRingtoneList.get(ringtonePos).getName());
        } else {
            tv_ringtoneTitle.setText(localRingtoneList.get(ringtonePos).getName());
        }
    }

    private void findViewByID() {
        tv_ringtoneTitle = findViewById(R.id.tv_ringtoneTitle);
        iv_save_ringtone = findViewById(R.id.iv_save_ringtone);
        iv_set_ringtone = findViewById(R.id.iv_set_ringtone);
        seekBar = findViewById(R.id.seekBar);
        iv_previous_btn = findViewById(R.id.iv_previous_btn);
        iv_play_btn = findViewById(R.id.iv_play_btn);
        iv_next_btn = findViewById(R.id.iv_next_btn);
        iv_back = findViewById(R.id.iv_back);

        customInterstitialAds = new CustomInterstitialAds(PlayMusicActivity.this);

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ratingShow++;

        MediaPlayerUtils.stopSound();
        iv_play_btn.setImageResource(R.drawable.play_btn);
    }


    @Override
    protected void onStop() {
        super.onStop();
        iv_play_btn.setImageResource(R.drawable.play_btn);
        NetworkAudioPlayer.pauseSound();
        MediaPlayerUtils.pauseSound();
    }

    void stopRingtone() {
        try {
            MediaPlayerUtils.stopSound();
            iv_play_btn.setImageResource(R.drawable.play_btn);
        } catch (Exception ignored) {

        }

    }
}