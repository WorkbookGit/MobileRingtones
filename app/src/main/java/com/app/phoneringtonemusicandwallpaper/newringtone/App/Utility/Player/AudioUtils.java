package com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioUtils {

//    public static File saveAudioFileToCache(Context context, InputStream inputStream, String fileName) {
//        File cacheDir = context.getCacheDir();
//        File audioFile = new File(cacheDir, fileName);
//
//        try (FileOutputStream fos = new FileOutputStream(audioFile)) {
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) > 0) {
//                fos.write(buffer, 0, length);
//            }
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return audioFile;
//    }
//
//
//    public static List<File> getCachedAudioFiles(Context context) {
//        File cacheDir = context.getCacheDir();
//        File[] files = cacheDir.listFiles((dir, name) -> name.endsWith(".mp3")); // Adjust extension as needed
//        return files != null ? Arrays.asList(files) : new ArrayList<>();
//    }

}
