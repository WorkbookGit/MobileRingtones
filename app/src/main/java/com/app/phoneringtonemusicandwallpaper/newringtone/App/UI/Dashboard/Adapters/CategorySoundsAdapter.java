package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters;


import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryList.CategoryRingtones.lastSelectedPosition;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryList.CategoryRingtones.searchText;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryList.CategoryRingtones.selectedPosition;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.cachedAudioFiles;
import static com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.DashboardScreen.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.Inter_Callback;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategorySoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.MusicPlayer.NetworkMusicPlayerScreen;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnCategorySoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CategorySoundsAdapter extends RecyclerView.Adapter<CategorySoundsAdapter.ViewHolder> {

    public static ImageView check_lastplay;

    public Context context;
    private List<CategorySoundModel.Datum> ringtoneList = new ArrayList<>();
    private List<CategorySoundModel.Datum> filteredData = new ArrayList<>();
    boolean refreshBtnIcon = true;

    public OnCategorySoundClickListener onCategorySoundClickListener;

    CustomInterstitialAds customInterstitialAds;

    public CategorySoundsAdapter(Context context2, List<CategorySoundModel.Datum> arrayList) {
        context = context2;
        ringtoneList = arrayList;
    }

    public void setOnCategorySoundClickListener(OnCategorySoundClickListener onSoundClickListener2) {
        onCategorySoundClickListener = onSoundClickListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.sound_item, viewGroup, false));
    }

    @SuppressLint({"ClickableViewAccessibility", "DefaultLocale"})
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int pos) {
        customInterstitialAds = new CustomInterstitialAds(context);
        if (searchText.equals("")) {
            updateView(ringtoneList, pos, viewHolder);
        } else {
            updateView(filteredData, pos, viewHolder);
        }

    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void updateView(List<CategorySoundModel.Datum> lastList, int pos, ViewHolder viewHolder) {

        viewHolder.tv_ringtoneTitle.setText(lastList.get(pos).getFileName());
        String duration = String.format("%.2f", lastList.get(pos).getDuration());
        int lastSlashIndex = duration.lastIndexOf('.');
        if (lastSlashIndex != -1) {
            String newDuration = duration.substring(0, lastSlashIndex);
            viewHolder.tv_ringtoneDuration.setText("00:" + newDuration);
        }

        viewHolder.ringtone_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Show Interstitial Ads
                customInterstitialAds.loadInterstitialAds(new Inter_Callback() {
                    @Override
                    public boolean Intersitial_adscallback(boolean ads) {
                        refreshBtnIcon = false;
                        lastSelectedPosition = selectedPosition;
                        selectedPosition = pos;
                        if (selectedPosition >= 0) {
                            notifyItemChanged(selectedPosition);
                        }
                        if (lastSelectedPosition >= 0) {
                            notifyItemChanged(lastSelectedPosition);
                        }

//                        if (AudioPlayer.isPlaying) {
//                            AudioPlayer.stopAudio();
//                        }

                        File cacheDir = context.getCacheDir();

                        if (searchText.isEmpty()) {
                            File currentAudioFile = new File(cacheDir, list.get(pos).getFileName());
                            Intent intent;
                            if (cachedAudioFiles.contains(currentAudioFile)) {
                                intent = new Intent(view.getContext(), NetworkMusicPlayerScreen.class);
                                intent.putExtra("fromLocal", true);
                                intent.putExtra("isFromSearch", false);
                                intent.putExtra("localFilePath", currentAudioFile.getPath());
                                intent.putExtra("position", pos);
                                intent.putExtra("model_key", ringtoneList.get(pos));
                            } else {
                                intent = new Intent(view.getContext(), NetworkMusicPlayerScreen.class);
                                intent.putExtra("position", pos);
                                intent.putExtra("isFromSearch", false);
                                intent.putExtra("model_key", ringtoneList.get(pos));
                            }
                            view.getContext().startActivity(intent);
                        } else {
                            File currentAudioFile = new File(cacheDir, filteredData.get(pos).getFileName());
                            if (cachedAudioFiles.contains(currentAudioFile)) {
                                Intent intent = new Intent(view.getContext(), NetworkMusicPlayerScreen.class);
                                intent.putExtra("fromLocal", true);
                                intent.putExtra("isFromSearch", false);
                                intent.putExtra("localFilePath", currentAudioFile.getPath());
                                intent.putExtra("position", pos);
                                intent.putExtra("model_key", filteredData.get(pos));
                                view.getContext().startActivity(intent);
                            } else {
                                Intent intent = new Intent(view.getContext(), NetworkMusicPlayerScreen.class);
                                intent.putExtra("position", pos);
                                intent.putExtra("isFromSearch", false);
                                intent.putExtra("model_key", filteredData.get(pos));
                                view.getContext().startActivity(intent);
                            }
                        }
                        return false;
                    }
                }, true);
            }
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<CategorySoundModel.Datum> newData) {
        try {
            if (filteredData != null) {
                filteredData.clear();
                filteredData.addAll(newData);
                notifyDataSetChanged();
            }
        } catch (Exception ignored) {

        }

    }

    public int getItemCount() {
        if (searchText.equals("")) {
            return ringtoneList.size();
        } else {
            return filteredData.size();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ringtone_item_layout;
        ImageView play_btn;
        TextView tv_ringtoneTitle, tv_ringtoneDuration;
        ProgressBar progressbar;

        ViewHolder(View view) {
            super(view);
            progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
            play_btn = (ImageView) view.findViewById(R.id.play_btn);
            ringtone_item_layout = (LinearLayout) view.findViewById(R.id.ringtone_item_layout);
            tv_ringtoneTitle = (TextView) view.findViewById(R.id.tv_ringtoneTitle);
            tv_ringtoneDuration = (TextView) view.findViewById(R.id.tv_ringtoneDuration);
        }
    }
}
