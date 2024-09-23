package com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SaveAudioTask extends AsyncTask<Void, Void, File> {
    private Context context;
    private InputStream inputStream;
    private String fileName;
    private OnAudioSavedListener listener;

    public interface OnAudioSavedListener {
        void onAudioSaved(File audioFile);
    }

    public SaveAudioTask(Context context, InputStream inputStream, String fileName, OnAudioSavedListener listener) {
        this.context = context;
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.listener = listener;
    }

    @Override
    protected File doInBackground(Void... voids) {
        File cacheDir = context.getCacheDir();
        File audioFile = new File(cacheDir, fileName);

        try (FileOutputStream fos = new FileOutputStream(audioFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.flush();
            return audioFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(File audioFile) {
        if (listener != null) {
            listener.onAudioSaved(audioFile);
        }
    }
}

