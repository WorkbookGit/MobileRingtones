package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.MusicPlayer;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.CategorySoundsAdapter.check_lastplay;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.cachedAudioFiles;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.filteredList;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.getCachedAudioFiles;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.list;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.adConfig;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Splash.SplashScreen.ratingShow;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.NetworkAudioPlayer.simpleExoPlayer1;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomBannerAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.InterNet.InternetStateChecker;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.Inter_Callback;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategorySoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.NetworkAudioPlayer;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.MediaPlayerUtils;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.SaveAudioTask;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.marcinmoskala.arcseekbar.ArcSeekBar;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NetworkMusicPlayerScreen extends AppCompatActivity {

    TextView tv_ringtoneTitle;
    ImageView backword, forword, set_rintone, download_ringtone, iv_back;
    public static ImageView iv_play_btn;
    private long downloadId;
    int isBack = 0;
    int position;
    ProgressBar progressbar;
    String localFilePath;
    int prefsno;
    ArcSeekBar seekBar;
    SharedPreferences pref;
    CustomInterstitialAds customInterstitialAds;
    CustomBannerAds customBannerAds;
    int currentProgress = 0;
    int maxProgress = 0;
    boolean isRestart = false;
    boolean isFromSearch = false;
    boolean showBadConnectionMessage = false;
    CategorySoundModel.Datum modelItem;
    Handler progressHandler = new Handler();
    Handler loadingTimerHandler = new Handler();
    public static File currentAudioFile;
    boolean isNewLocalRingtone = false;

    ProgressBar ringtoneProgress;
    List<CategorySoundModel.Datum> ringtoneList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_network_music_player_screen);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        new InternetStateChecker.Builder(NetworkMusicPlayerScreen.this).build();

        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "NetworkMusicPlayerScreen");
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "NetworkMusicPlayerScreen");
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1);

//        checkSystemWritePermission();

        findViewByID();
        getIntentData();

        if (isFromSearch) {
            ringtoneList.addAll(filteredList);
        } else {
            ringtoneList.addAll(list);
        }


        currentAudioFile = new File(NetworkMusicPlayerScreen.this.getCacheDir(), ringtoneList.get(position).getFileName());
        tv_ringtoneTitle.setText(ringtoneList.get(position).getFileName());
        maxProgress = (int) Math.round(ringtoneList.get(position).getDuration());
        maxProgress = maxProgress * 10;

        // Play audio after get data from previous screen
        first_time_play();


        iv_play_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                play_audio();
                if (!ringtoneList.isEmpty()) {
                    if (NetworkAudioPlayer.isPlaying || MediaPlayerUtils.isPlaying) {
                        NetworkAudioPlayer.pauseSound();
                        MediaPlayerUtils.pauseSound();
                    } else {
                        try {
                            if (isRestart) {
                                currentProgress = 0;
                                isRestart = false;
                                if (!isNewLocalRingtone && cachedAudioFiles.contains(currentAudioFile)) {
                                    MediaPlayerUtils.startSound(NetworkMusicPlayerScreen.this, 0, currentAudioFile.getPath());
                                    iv_play_btn.setVisibility(View.VISIBLE);
                                    progressbar.setVisibility(View.GONE);
                                    startProgress();
                                } else {
                                    NetworkAudioPlayer.startAudio(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), progressbar, iv_play_btn, false);
//                                    initBufferingPlayer();
//                                    saveAudioFromNetwork(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName());
                                }
                            } else {
                                if (!isNewLocalRingtone && cachedAudioFiles.contains(currentAudioFile)) {
                                    MediaPlayerUtils.resumeSound();
                                    startProgress();
                                } else {
                                    NetworkAudioPlayer.resumeSound();
                                    startProgress();
                                }
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        backword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    progressHandler.removeCallbacksAndMessages(null);
                    maxProgress = (int) Math.round(ringtoneList.get(position).getDuration());
                    maxProgress = maxProgress * 10;

                    if (0 < position) {
                        position = position - 1;
                    } else {
                        Toast.makeText(NetworkMusicPlayerScreen.this, "No More Ringtones", Toast.LENGTH_SHORT).show();
                    }


                    NetworkAudioPlayer.stopAudio();
                    MediaPlayerUtils.stopSound();

                    seekBar.setProgress(0);
                    currentProgress = 0;
                    tv_ringtoneTitle.setText(ringtoneList.get(position).getFileName());
                    currentAudioFile = new File(NetworkMusicPlayerScreen.this.getCacheDir(), ringtoneList.get(position).getFileName());
                    if (cachedAudioFiles.contains(currentAudioFile)) {
                        MediaPlayerUtils.startSound(NetworkMusicPlayerScreen.this, 0, currentAudioFile.getPath());
                        iv_play_btn.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        isNewLocalRingtone = false;
                        startProgress();
                    } else {
                        NetworkAudioPlayer.startAudio(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), progressbar, iv_play_btn, false);
                        initBufferingPlayer();
                        isNewLocalRingtone = true;
                        saveAudioFromNetwork(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName(), false);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        forword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    int lastpos = ringtoneList.size() - 1;
                    progressHandler.removeCallbacksAndMessages(null);
                    maxProgress = (int) Math.round(ringtoneList.get(position).getDuration());
                    maxProgress = maxProgress * 10;

                    if (lastpos != position && position < (ringtoneList.size() - 1)) {
                        position = position + 1;
                    } else {
                        Toast.makeText(NetworkMusicPlayerScreen.this, "No More Ringtones", Toast.LENGTH_SHORT).show();
                    }

                    NetworkAudioPlayer.stopAudio();
                    MediaPlayerUtils.stopSound();

                    seekBar.setProgress(0);
                    currentProgress = 0;
                    tv_ringtoneTitle.setText(ringtoneList.get(position).getFileName());
                    currentAudioFile = new File(NetworkMusicPlayerScreen.this.getCacheDir(), ringtoneList.get(position).getFileName());
                    if (cachedAudioFiles.contains(currentAudioFile)) {
                        MediaPlayerUtils.startSound(NetworkMusicPlayerScreen.this, 0, currentAudioFile.getPath());
                        iv_play_btn.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        isNewLocalRingtone = false;
                        startProgress();
                    } else {
                        NetworkAudioPlayer.startAudio(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), progressbar, iv_play_btn, false);
                        initBufferingPlayer();
                        isNewLocalRingtone = true;
                        saveAudioFromNetwork(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName(), false);
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        set_rintone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                downloadRingtone(2, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName());
                if (cachedAudioFiles.contains(currentAudioFile)) {
                    setRingtone(currentAudioFile);
                } else {
                    ringtoneProgress.setVisibility(View.VISIBLE);
                    set_rintone.setVisibility(View.GONE);

                    saveAudioFromNetwork(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName(), true);
                }
            }
        });

        download_ringtone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                customInterstitialAds.loadInterstitialAds(new Inter_Callback() {
                    @Override
                    public boolean Intersitial_adscallback(boolean ads) {
                        downloadRingtone(1, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName());
                        return false;
                    }
                }, true);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingShow++;
                onBackPressed();
            }
        });

    }


    public void saveAudioFromNetwork(Context context, String audioUrl, String ringtoneName, boolean setRingtone) {

        if (!cachedAudioFiles.contains(currentAudioFile)) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    URL url = new URL(audioUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();

                        // Call the saveAudio method
//                    saveAudio(context, inputStream, fileName);

                        new SaveAudioTask(context, inputStream, ringtoneName, new SaveAudioTask.OnAudioSavedListener() {
                            @Override
                            public void onAudioSaved(File audioFile) {
                                try {
                                    // Close the InputStream after saving the file
                                    if (audioFile != null) {
                                        // The file was saved successfully
                                        // You can update the UI, add it to a list, etc.

                                        getCachedAudioFiles(NetworkMusicPlayerScreen.this);
                                        if (setRingtone) {
                                            setRingtone(audioFile);
                                        }
                                    }  // Handle the error
                                    inputStream.close();
                                    connection.disconnect();
                                } catch (IOException e) {
                                    ringtoneProgress.setVisibility(View.GONE);
                                    set_rintone.setVisibility(View.VISIBLE);
                                    throw new RuntimeException(e);
                                }
                            }
                        }).execute();
                    }
                } catch (Exception e) {
                    ringtoneProgress.setVisibility(View.GONE);
                    set_rintone.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            });
        }
    }


    public void initBufferingPlayer() {
        seekBar.setProgress(0);
        seekBar.setMaxProgress(maxProgress);

        loadingTimerHandler = null;
        loadingTimerHandler = new Handler();

        iv_play_btn.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        showBadConnectionMessage = true;
        loadingTimerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (showBadConnectionMessage && (simpleExoPlayer1 != null && !simpleExoPlayer1.isLoading())) {
                    Toast.makeText(NetworkMusicPlayerScreen.this, "Bad Internet Connection \uD83D\uDE14", Toast.LENGTH_SHORT).show();
                }
            }
        }, 7500);

        simpleExoPlayer1.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);

                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        // The player is buffering (loading the media).
                        break;
                    case Player.STATE_READY:
                        // The player is ready to play.
                        // Here you can start playing the media.
                        // Hide loading dialog
                        Log.e("Progress :: ", "Hide");
                        showBadConnectionMessage = false;
                        iv_play_btn.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                        startProgress();
//                        Toast.makeText(NetworkMusicPlayerScreen.this, "ready to play....", Toast.LENGTH_SHORT).show();
//                        simpleExoPlayer1.play();
                        break;
                    case Player.STATE_ENDED:
                        // The player has finished playing the media.
                        break;
                    case Player.STATE_IDLE:
                        // The player is idle (nothing to play).

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                // Handle player error

                Toast.makeText(NetworkMusicPlayerScreen.this, "Something went wrong please try again later!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public void startProgress() {
        try {
            boolean aa = NetworkAudioPlayer.isPlaying || MediaPlayerUtils.isPlaying && (currentProgress < maxProgress);
            Log.e("asdasdasdasdasd ??>> ", String.valueOf(aa) + "-" + String.valueOf(currentProgress) + "-" + String.valueOf(maxProgress));
            if ((NetworkAudioPlayer.isPlaying || MediaPlayerUtils.isPlaying) && (currentProgress < maxProgress)) {
                progressHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentProgress++;
                        seekBar.setMaxProgress(maxProgress);
                        seekBar.setProgress(currentProgress);
                        startProgress();
                    }
                }, 100);
            } else {
                if (currentProgress == maxProgress) {
                    isRestart = true;
                    seekBar.setProgress(0);
                    currentProgress = 0;
                    NetworkAudioPlayer.stopAudio();
                    MediaPlayerUtils.stopSound();
                    isNewLocalRingtone = false;
                }
            }
        } catch (Exception ignored) {

        }
    }

    private void findViewByID() {
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        iv_play_btn = (ImageView) findViewById(R.id.iv_play_btn);
        forword = (ImageView) findViewById(R.id.iv_next_btn);
        backword = (ImageView) findViewById(R.id.iv_previous_btn);
        set_rintone = (ImageView) findViewById(R.id.iv_set_ringtone);
        download_ringtone = (ImageView) findViewById(R.id.iv_save_ringtone);
        tv_ringtoneTitle = (TextView) findViewById(R.id.tv_ringtoneTitle);
        seekBar = findViewById(R.id.seekBar);
        iv_back = findViewById(R.id.iv_back);
        ringtoneProgress = findViewById(R.id.ringtoneProgress);

        customInterstitialAds = new CustomInterstitialAds(NetworkMusicPlayerScreen.this);

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

    private void getIntentData() {
        position = getIntent().getIntExtra("position", 0);
        modelItem = (CategorySoundModel.Datum) getIntent().getSerializableExtra("model_key");
        localFilePath = getIntent().getStringExtra("localFilePath");
        isFromSearch = getIntent().getBooleanExtra("isFromSearch", false);

    }

    private void first_time_play() {
        try {
            for (int i = 0; i < ringtoneList.size(); i++) {

                if (modelItem.getFileName() != null && modelItem.getFileName().equals(ringtoneList.get(i).getFileName())) {
                    position = i;
                    break;
                }
            }

            MediaPlayerUtils.mediaPlayer = null;
            NetworkAudioPlayer.simpleExoPlayer1 = null;

            if (MediaPlayerUtils.isPlaying) {
                MediaPlayerUtils.stopSound();
            }
            if (NetworkAudioPlayer.isPlaying) {
                NetworkAudioPlayer.stopAudio();
            }

            currentAudioFile = new File(NetworkMusicPlayerScreen.this.getCacheDir(), ringtoneList.get(position).getFileName());
            if (iv_play_btn != null) iv_play_btn.setImageResource(R.drawable.pause_btn);

            if (cachedAudioFiles.contains(currentAudioFile)) {
                MediaPlayerUtils.startSound(NetworkMusicPlayerScreen.this, 0, currentAudioFile.getPath());
                tv_ringtoneTitle.setText(ringtoneList.get(position).getFileName());
                isNewLocalRingtone = false;
                startProgress();
            } else {
                NetworkAudioPlayer.startAudio(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), progressbar, iv_play_btn, false);
                initBufferingPlayer();
                tv_ringtoneTitle.setText(ringtoneList.get(position).getFileName());
                isNewLocalRingtone = true;
                saveAudioFromNetwork(NetworkMusicPlayerScreen.this, ringtoneList.get(position).getFilePath(), ringtoneList.get(position).getFileName(), false);
            }
        } catch (IOException e) {
            Log.e("first_time_play >> ", Objects.requireNonNull(e.getMessage()));
        }
    }


    void downloadRingtone(int pos, String url, String name) {

        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(3);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setTitle(getResources().getString(R.string.app_name));
            request.setDescription("Data download using.2131886108");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            String str2 = Environment.DIRECTORY_DOWNLOADS;
            request.setDestinationInExternalPublicDir(str2, "/Ringtones/" + ".mp3");
            request.setMimeType("audio/mp3");
            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            downloadId = downloadManager.enqueue(request);

            if (pos == 2) {
                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            } else {
                Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("downloadRingtone >> ", String.valueOf(e.getMessage()));

        }
    }


    public void setRingtone(File file) {
        try {
            if (!Settings.System.canWrite(this)) {
                // If the app doesn't have permission, open the system settings screen
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(this, "Please grant permission in settings", Toast.LENGTH_SHORT).show();
            } else {
                // Add the file to MediaStore
                ContentValues values = getContentValues();
                Uri uri = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    uri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                }

                // Insert new record into MediaStore
                Uri newUri = this.getContentResolver().insert(uri, values);

                // Write the file into the newUri using ContentResolver
                try (OutputStream os = this.getContentResolver().openOutputStream(newUri)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Files.copy(file.toPath(), os);
                    }
                }

                // Set the newUri as the default ringtone
                RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, newUri);

                ringtoneProgress.setVisibility(View.GONE);
                set_rintone.setVisibility(View.VISIBLE);
                Toast.makeText(this, "succeed! Enjoy\uD83D\uDE0E", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            ringtoneProgress.setVisibility(View.GONE);
            set_rintone.setVisibility(View.VISIBLE);
            Log.e("Catch Log setRingtone >> ", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(this, "Something want wrong, Try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private static ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.TITLE, "My Ringtone");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mpeg");
        values.put(MediaStore.Audio.Media.ARTIST, "");
        values.put(MediaStore.Audio.Media.DURATION, 230);  // Example duration
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);
        return values;
    }

    private BroadcastReceiver onComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            long receivedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (downloadId == receivedDownloadId) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);

                DownloadManager downloadManager = (DownloadManager) ctxt.getSystemService(Context.DOWNLOAD_SERVICE);
                Cursor cursor = downloadManager.query(query);

                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);

                    // Check if the download is successful
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                        int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                        String downloadedUriString = cursor.getString(uriIndex);

                        // Log the downloaded file's URI
                        Log.d("DownloadPath", "Downloaded URI: " + downloadedUriString);

                        // Now you can get the path using getPath()
                        Uri downloadedUri = Uri.parse(downloadedUriString);
                        String path = downloadedUri.getPath();
                        Log.d("DownloadPath", "Path: " + path);

                        checkAndSetRingtone(path);
                        // Do whatever you need with the path
                    } else {
                        // Log if the download was not successful
                        Log.d("DownloadPath", "Download failed");
                    }
                }
                cursor.close();
            }
        }
    };


    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        retVal = Settings.System.canWrite(this);
        Log.d("TAG", "Can Write Settings: " + retVal);
        if (!retVal) {
            //permission not granted navigate to permission screen
            openAndroidPermissionsMenu();
        }
        return retVal;
    }

    private void checkAndSetRingtone(String path) {

        if (!Settings.System.canWrite(this)) {
            // If the app doesn't have permission, open the system settings screen
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            Toast.makeText(this, "Please grant permission in settings", Toast.LENGTH_SHORT).show();
        } else {
            // The app has permission, set the ringtone
            File file = new File(path);
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);

            try {
                copyToExternalStorage(file.getAbsolutePath());
            } catch (Exception e) {
                Log.e("~ ERRRO ~", e.getMessage());
            }
        }
    }

    private File copyToExternalStorage(String filePath) {
        try {
            File newFile;
            File externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (!externalDir.exists()) {
                externalDir.mkdirs();
            }

            newFile = new File(externalDir, "Ringtone_" + new Date().getTime() + ".ogg");
            copyFile(new File(filePath), newFile);

            Uri ringtoneUri = Uri.fromFile(newFile);
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, ringtoneUri);
            Toast.makeText(this, "Ringtone set successfully", Toast.LENGTH_SHORT).show();
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void copyFile(File source, File dest) throws IOException {
        try (InputStream is = new FileInputStream(source); OutputStream os = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        MediaPlayerUtils.pauseSound();
        NetworkAudioPlayer.pauseSound();
        super.onPause();
    }

    public void onBackPressed() {
        ratingShow++;
        stopRingtone();
        super.onBackPressed();
    }


    @Override
    protected void onStop() {
        super.onStop();
        MediaPlayerUtils.pauseSound();
        NetworkAudioPlayer.pauseSound();
    }


    void stopRingtone() {
        try {
            isBack = 1;
            loadingTimerHandler.removeCallbacksAndMessages(null);
            iv_play_btn.setImageResource(R.drawable.play_btn);
            if (check_lastplay != null) {
                check_lastplay.setImageResource(R.drawable.play_btn);
            }
            MediaPlayerUtils.stopSound();
            NetworkAudioPlayer.stopAudio();
        } catch (Exception ignored) {

        }
    }

}