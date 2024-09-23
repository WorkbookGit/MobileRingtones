package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.Inter_Callback;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.SoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.MusicPlayerView.PlayMusicActivity;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnSoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.util.List;

public class RingtoneAdapter extends RecyclerView.Adapter<RingtoneAdapter.ViewHolder> {

    private List<SoundModel> list;
    public OnSoundClickListener onSoundClickListener;
    private int prev = -1;

    CustomInterstitialAds customInterstitialAds;

    public RingtoneAdapter(List<SoundModel> arrayList) {
        list = arrayList;

    }

    public void setOnSoundClickListener(OnSoundClickListener onSoundClickListener2) {
        onSoundClickListener = onSoundClickListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        customInterstitialAds = new CustomInterstitialAds(viewGroup.getContext());
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sound_item, viewGroup, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        if (!list.isEmpty()) {

            viewHolder.tv_ringtoneTitle.setText(list.get(position).getName());
            viewHolder.tv_ringtoneDuration.setText(list.get(position).getDuration());

            viewHolder.ringtone_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Show Interstitial Ads
                    customInterstitialAds.loadInterstitialAds(new Inter_Callback() {
                        @Override
                        public boolean Intersitial_adscallback(boolean ads) {
                            Intent intent = new Intent(view.getContext(), PlayMusicActivity.class);
                            intent.putExtra("RingtonePos", position);
                            view.getContext().startActivity(intent);
                            return false;
                        }
                    }, true);
                }
            });
        }

    }


    public int getItemCount() {
        return list.size();
    }

    public void reset() {
        notifyItemChanged(prev);
        prev = -1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ringtone_item_layout;
        ImageView play_btn;
        TextView tv_ringtoneTitle;

        TextView tv_ringtoneDuration;

        ViewHolder(View view) {
            super(view);
            play_btn = (ImageView) view.findViewById(R.id.play_btn);
            ringtone_item_layout = (LinearLayout) view.findViewById(R.id.ringtone_item_layout);
            tv_ringtoneTitle = (TextView) view.findViewById(R.id.tv_ringtoneTitle);
            tv_ringtoneDuration = (TextView) view.findViewById(R.id.tv_ringtoneDuration);
        }
    }
}
