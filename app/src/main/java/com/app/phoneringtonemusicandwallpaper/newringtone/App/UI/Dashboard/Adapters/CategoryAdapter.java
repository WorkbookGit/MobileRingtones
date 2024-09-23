package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

//import com.topbestringtonesiosringtones.iphoneringtone.Activities.CategoryRingtones;
//import com.topbestringtonesiosringtones.iphoneringtone.Ads_utils.CustomInterstitialAds;
//import com.topbestringtonesiosringtones.iphoneringtone.Ads_utils.Inter_Callback;
//import com.topbestringtonesiosringtones.iphoneringtone.R;
//import com.topbestringtonesiosringtones.iphoneringtone.interfaces.OnSoundClickListener;
//import com.topbestringtonesiosringtones.iphoneringtone.model.CategoryModel;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.CustomInterstitialAds;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.Inter_Callback;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategoryModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category.CategoryList.CategoryRingtones;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnSoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private int COUNT;
    public Context context;
    private ArrayList<CategoryModel> list;
    public OnSoundClickListener onSoundClickListener;
    private int prev = -1;
    CustomInterstitialAds customInterstitialAds;


    public CategoryAdapter(Context context2, ArrayList<CategoryModel> arrayList) {
        context = context2;
        list = arrayList;
        COUNT = arrayList.size();

    }

    public void setOnSoundClickListener(OnSoundClickListener onSoundClickListener2) {
        onSoundClickListener = onSoundClickListener2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        customInterstitialAds = new CustomInterstitialAds(context);
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item, viewGroup, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        viewHolder.categoryBgImg.setImageResource(list.get(i).getIcon());

        viewHolder.ringtoneItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show Interstitial Ads
                customInterstitialAds.loadInterstitialAds(new Inter_Callback() {
                    @Override
                    public boolean Intersitial_adscallback(boolean ads) {
                        customInterstitialAds = new CustomInterstitialAds(context);
                        Intent intent = new Intent(view.getContext(), CategoryRingtones.class);
                        intent.putExtra("position", i);
                        intent.putExtra("name", list.get(i).getName());
                        view.getContext().startActivity(intent);
                        return false;
                    }
                }, true);
            }
        });
    }


    public int getItemCount() {
        return COUNT;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout ringtoneItem;
        ImageView categoryBgImg;

        ViewHolder(View view) {
            super(view);
            ringtoneItem = (RelativeLayout) view.findViewById(R.id.ringtone_item);
            categoryBgImg = (ImageView) view.findViewById(R.id.category_bg_image);
        }
    }
}
