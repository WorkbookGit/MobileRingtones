package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.CategoryModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.SoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.CategoryAdapter;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.Utility.Player.MediaPlayerUtils;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnSoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment implements OnSoundClickListener {


    CategoryAdapter adapter;
    ArrayList<CategoryModel> categoryList = new ArrayList<>();
    RecyclerView recyclerView;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_category, container, false);
        setupRecycler(inflate);
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(preference.activityNo, 0);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putInt(preference.activityNo, 1);
//        edit.apply();
        return inflate;
//        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    private void setupRecycler(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getRingtoneData();
        this.adapter.setOnSoundClickListener(this);
    }

    public void getRingtoneData() {
        this.categoryList = new ArrayList<>();
        for (int i = 0; i < Globals.Categories_image.length; i++) {
            this.categoryList.add(new CategoryModel(Globals.Categories_name[i], Globals.Categories_image[i]));
        }
        adapter = new CategoryAdapter(getContext(), this.categoryList);
        this.recyclerView.setAdapter(adapter);
    }

    public void onSoundClick(SoundModel soundModel, int i) {

        MediaPlayerUtils.startSound(getContext(), soundModel.getSound(), "");
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (AudioPlayer.isPlaying) {
//            AudioPlayer.stopAudio();
//            if (Globals.mPlayerCategory != null) {
//                Globals.mPlayerCategory.release();
//            }
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        if (MediaPlayerUtils.isPlaying) {
            MediaPlayerUtils.stopSound();
            if (Globals.mPlayerCategory != null) {
                Globals.mPlayerCategory.release();
            }
        }
    }
}