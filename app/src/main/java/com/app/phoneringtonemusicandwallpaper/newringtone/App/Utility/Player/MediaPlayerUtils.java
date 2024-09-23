package com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.MusicPlayer.NetworkMusicPlayerScreen.iv_play_btn;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.util.Objects;

public class MediaPlayerUtils {
    public static boolean isPlaying = false;
    public static MediaPlayer mediaPlayer;

    public static void startSound(Context context, int i, String filePath) {
        try {
            Log.e("FilePath >>> ", filePath);
            if (mediaPlayer == null) {
                if (!filePath.isEmpty()) {
                    mediaPlayer = MediaPlayer.create(context, Uri.parse(filePath));
                } else {
                    mediaPlayer = MediaPlayer.create(context, i);
                }
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    isPlaying = true;
                    NetworkAudioPlayer.isPlaying = false;
                    if (iv_play_btn != null) {
                        iv_play_btn.setImageResource(R.drawable.pause_btn);
                    }
                }
            } else if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                if (!filePath.isEmpty()) {
                    mediaPlayer = MediaPlayer.create(context, Uri.parse(filePath));
                } else {
                    mediaPlayer = MediaPlayer.create(context, i);
                }
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    isPlaying = true;
                    NetworkAudioPlayer.isPlaying = false;
                    if (iv_play_btn != null) {
                        iv_play_btn.setImageResource(R.drawable.pause_btn);
                    }
                }
            } else if (!mediaPlayer.isPlaying()) {
                mediaPlayer.release();
                mediaPlayer = null;
                if (!filePath.isEmpty()) {
                    mediaPlayer = MediaPlayer.create(context, Uri.parse(filePath));
                } else {
                    mediaPlayer = MediaPlayer.create(context, i);
                }
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    isPlaying = true;
                    NetworkAudioPlayer.isPlaying = false;
                    if (iv_play_btn != null) {
                        iv_play_btn.setImageResource(R.drawable.pause_btn);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("startSound >> ", Objects.requireNonNull(e.getMessage()));
        }

//        mediaPlayer.setLooping(true);
    }

    public static void stopSound() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
                isPlaying = false;
                NetworkAudioPlayer.isPlaying = false;
                if (iv_play_btn != null) {
                    iv_play_btn.setImageResource(R.drawable.play_btn);
                }
            }
        } catch (Exception e) {
            Log.e("stopSound >> ", Objects.requireNonNull(e.getMessage()));
        }

    }

    public static void pauseSound() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                isPlaying = false;
                NetworkAudioPlayer.isPlaying = false;
                if (iv_play_btn != null) {
                    iv_play_btn.setImageResource(R.drawable.play_btn);
                }
            }
        } catch (Exception e) {
            Log.e("pauseSound >> ", Objects.requireNonNull(e.getMessage()));
        }
    }

    public static void resumeSound() {
        try {
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                isPlaying = true;
                NetworkAudioPlayer.isPlaying = false;
                if (iv_play_btn != null) {
                    iv_play_btn.setImageResource(R.drawable.pause_btn);
                }
            }
        } catch (Exception e) {
            Log.e("resumeSound >> ", Objects.requireNonNull(e.getMessage()));
        }
    }
}
