package com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.MusicPlayer.NetworkMusicPlayerScreen.iv_play_btn;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.MusicPlayer.NetworkMusicPlayerScreen;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

public class NetworkAudioPlayer {
    public static boolean isPlaying = false;
    public static SimpleExoPlayer simpleExoPlayer1;


    public static void startAudio(Context context, String url, ProgressBar progressbar, ImageView play_img, boolean isLocal) throws IOException {
        try {

            if (simpleExoPlayer1 == null) {
                simpleExoPlayer1 = new SimpleExoPlayer.Builder(context).build();

                DataSource.Factory datasoecefactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "app"));
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(datasoecefactory).createMediaSource(MediaItem.fromUri(url));
                simpleExoPlayer1.prepare(mediaSource);

                NetworkMusicPlayerScreen networkMusicPlayerScreen = new NetworkMusicPlayerScreen();
                simpleExoPlayer1.play();
//                networkMusicPlayerScreen.initBufferingPlayer();
                iv_play_btn.setImageResource(R.drawable.pause_btn);
            } else if (simpleExoPlayer1.isPlaying()) {
                simpleExoPlayer1.stop();
                simpleExoPlayer1.release();
                simpleExoPlayer1 = null;

                simpleExoPlayer1 = new SimpleExoPlayer.Builder(context).build();

                DataSource.Factory datasoecefactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "app"));
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(datasoecefactory).createMediaSource(MediaItem.fromUri(url));
                simpleExoPlayer1.prepare(mediaSource);
                simpleExoPlayer1.play();
                iv_play_btn.setImageResource(R.drawable.pause_btn);

            }
            isPlaying = true;
            MediaPlayerUtils.isPlaying = false;
        } catch (Exception ignored) {
        }
    }

    public static void stopAudio() {

        if (simpleExoPlayer1 != null) {
            if (simpleExoPlayer1.isPlaying()) {
                simpleExoPlayer1.stop();
            }
            simpleExoPlayer1.release();
            simpleExoPlayer1 = null;
            isPlaying = false;
            MediaPlayerUtils.isPlaying = false;
            iv_play_btn.setImageResource(R.drawable.play_btn);
        }
    }

    public static void pauseSound() {
        if (simpleExoPlayer1 != null) {
            if (simpleExoPlayer1.isPlaying()) {
                simpleExoPlayer1.pause();
            }
            isPlaying = false;
            MediaPlayerUtils.isPlaying = false;
            iv_play_btn.setImageResource(R.drawable.play_btn);
        }
    }

    public static void resumeSound() {
        if (simpleExoPlayer1 != null) {
            if (!simpleExoPlayer1.isPlaying()) {
                simpleExoPlayer1.play();
            }
            isPlaying = true;
            MediaPlayerUtils.isPlaying = false;
            iv_play_btn.setImageResource(R.drawable.pause_btn);
        }
    }


}
