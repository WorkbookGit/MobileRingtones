package com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class RawFileDurationHelper {

    private static final String TAG = "RawFileDurationHelper";

    public static int getRawFileDuration(Context resources, int rawResourceId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(resources, rawResourceId);
        if (mediaPlayer != null) {
            int duration = mediaPlayer.getDuration(); // in milliseconds
            mediaPlayer.release();
            return duration;
        } else {
            Log.e(TAG, "Failed to create MediaPlayer for raw resource " + rawResourceId);
            return -1; // Return -1 to indicate failure
        }
    }
}