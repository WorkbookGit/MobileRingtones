package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Ringtone;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localRingtoneList;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.SoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.RingtoneAdapter;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnSoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;


public class RingtonesFragment extends Fragment implements OnSoundClickListener {


    RingtoneAdapter ringtoneAdapter;
    RecyclerView recyclerView;

//    ArrayList<SoundModel> localRingtoneList = new ArrayList();


    public RingtonesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "Ringtone Fragment");
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Ringtone Fragment");
        FirebaseAnalytics.getInstance(requireActivity()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1);

        View inflate = inflater.inflate(R.layout.fragment_ringtones, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_sounds);
        setupRecycler();
//        setupRecycler(inflate);
        // Inflate the layout for this fragment
        return inflate;
    }

    private void setupRecycler() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        getRingtoneData();

//        List<SoundModel> audioList = loadAudioFiles();
        ringtoneAdapter = new RingtoneAdapter(localRingtoneList);
        recyclerView.setAdapter(ringtoneAdapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemViewCacheSize(35);
        this.ringtoneAdapter.setOnSoundClickListener(this);
//        new LoadRingtonesTask().execute();
    }


//    private List<SoundModel> loadAudioFiles() {
//        List<SoundModel> list = new ArrayList<>();
//        list.add(new SoundModel("Ringtone 1", R.raw.r0));
//        list.add(new SoundModel("Ringtone 2", R.raw.r1));
//        list.add(new SoundModel("Ringtone 3", R.raw.r2));
//        list.add(new SoundModel("Ringtone 4", R.raw.r3));
//        list.add(new SoundModel("Ringtone 5", R.raw.r4));
//        list.add(new SoundModel("Ringtone 6", R.raw.r5));
//        list.add(new SoundModel("Ringtone 7", R.raw.r6));
//        list.add(new SoundModel("Ringtone 8", R.raw.r7));
//        list.add(new SoundModel("Ringtone 9", R.raw.r8));
//        list.add(new SoundModel("Ringtone 10", R.raw.r9));
//        list.add(new SoundModel("Ringtone 11", R.raw.r10));
//        list.add(new SoundModel("Ringtone 12", R.raw.r11));
//        list.add(new SoundModel("Ringtone 13", R.raw.r12));
//        list.add(new SoundModel("Ringtone 13", R.raw.r12));
//        list.add(new SoundModel("Ringtone 14", R.raw.r13));
//        list.add(new SoundModel("Ringtone 15", R.raw.r14));
//        list.add(new SoundModel("Ringtone 16", R.raw.r15));
//        list.add(new SoundModel("Ringtone 17", R.raw.r16));
//        list.add(new SoundModel("Ringtone 18", R.raw.r17));
//        list.add(new SoundModel("Ringtone 19", R.raw.r18));
//        list.add(new SoundModel("Ringtone 20", R.raw.r19));
//        list.add(new SoundModel("Ringtone 21", R.raw.r20));
//        list.add(new SoundModel("Ringtone 22", R.raw.r21));
//        list.add(new SoundModel("Ringtone 23", R.raw.r22));
//        list.add(new SoundModel("Ringtone 24", R.raw.r23));
//        list.add(new SoundModel("Ringtone 25", R.raw.r24));
//        list.add(new SoundModel("Ringtone 26", R.raw.r25));
//        list.add(new SoundModel("Ringtone 27", R.raw.r26));
//        list.add(new SoundModel("Ringtone 28", R.raw.r27));
//        list.add(new SoundModel("Ringtone 29", R.raw.r28));
//        list.add(new SoundModel("Ringtone 30", R.raw.r29));
//        list.add(new SoundModel("Ringtone 31", R.raw.r30));
//        list.add(new SoundModel("Ringtone 32", R.raw.r31));
//        list.add(new SoundModel("Ringtone 33", R.raw.r32));
//        list.add(new SoundModel("Ringtone 34", R.raw.r33));
//        list.add(new SoundModel("Ringtone 35", R.raw.r34));
//        list.add(new SoundModel("Ringtone 36", R.raw.r35));
//
//        return list;
//    }


//    private class LoadRingtonesTask extends AsyncTask<Void, Void, List<SoundModel>> {
//
//        @Override
//        protected List<SoundModel> doInBackground(Void... voids) {
//            List<SoundModel> ringtoneList = new ArrayList<>();
//            localRingtoneList.add(new SoundModel("Ringtone 1", R.raw.r0));
//            localRingtoneList.add(new SoundModel("Ringtone 2", R.raw.r1));
//            localRingtoneList.add(new SoundModel("Ringtone 3", R.raw.r2));
//            localRingtoneList.add(new SoundModel("Ringtone 4", R.raw.r3));
//            localRingtoneList.add(new SoundModel("Ringtone 5", R.raw.r4));
//            localRingtoneList.add(new SoundModel("Ringtone 6", R.raw.r5));
//            localRingtoneList.add(new SoundModel("Ringtone 7", R.raw.r6));
//            localRingtoneList.add(new SoundModel("Ringtone 8", R.raw.r7));
//            localRingtoneList.add(new SoundModel("Ringtone 9", R.raw.r8));
//            localRingtoneList.add(new SoundModel("Ringtone 10", R.raw.r9));
//            localRingtoneList.add(new SoundModel("Ringtone 11", R.raw.r10));
//            localRingtoneList.add(new SoundModel("Ringtone 12", R.raw.r11));
//            localRingtoneList.add(new SoundModel("Ringtone 13", R.raw.r12));
//            localRingtoneList.add(new SoundModel("Ringtone 13", R.raw.r12));
//            localRingtoneList.add(new SoundModel("Ringtone 14", R.raw.r13));
//            localRingtoneList.add(new SoundModel("Ringtone 15", R.raw.r14));
//            localRingtoneList.add(new SoundModel("Ringtone 16", R.raw.r15));
//            localRingtoneList.add(new SoundModel("Ringtone 17", R.raw.r16));
//            localRingtoneList.add(new SoundModel("Ringtone 18", R.raw.r17));
//            localRingtoneList.add(new SoundModel("Ringtone 19", R.raw.r18));
//            localRingtoneList.add(new SoundModel("Ringtone 20", R.raw.r19));
//            localRingtoneList.add(new SoundModel("Ringtone 21", R.raw.r20));
//            localRingtoneList.add(new SoundModel("Ringtone 22", R.raw.r21));
//            localRingtoneList.add(new SoundModel("Ringtone 23", R.raw.r22));
//            localRingtoneList.add(new SoundModel("Ringtone 24", R.raw.r23));
//            localRingtoneList.add(new SoundModel("Ringtone 25", R.raw.r24));
//            localRingtoneList.add(new SoundModel("Ringtone 26", R.raw.r25));
//            localRingtoneList.add(new SoundModel("Ringtone 27", R.raw.r26));
//            localRingtoneList.add(new SoundModel("Ringtone 28", R.raw.r27));
//            localRingtoneList.add(new SoundModel("Ringtone 29", R.raw.r28));
//            localRingtoneList.add(new SoundModel("Ringtone 30", R.raw.r29));
//            localRingtoneList.add(new SoundModel("Ringtone 31", R.raw.r30));
//            localRingtoneList.add(new SoundModel("Ringtone 32", R.raw.r31));
//            localRingtoneList.add(new SoundModel("Ringtone 33", R.raw.r32));
//            localRingtoneList.add(new SoundModel("Ringtone 34", R.raw.r33));
//            localRingtoneList.add(new SoundModel("Ringtone 35", R.raw.r34));
//            localRingtoneList.add(new SoundModel("Ringtone 36", R.raw.r35));
//            return ringtoneList;
//        }
//
//        @Override
//        protected void onPostExecute(List<SoundModel> soundModels) {
//            // Update the list and notify adapter after loading is done
//            localRingtoneList.clear();
//            localRingtoneList.addAll(soundModels);
//            ringtoneAdapter.notifyDataSetChanged();
//        }
//    }


    public void getRingtoneData() {
        requireActivity().runOnUiThread(new Runnable() {
            public void run() {
                ringtoneAdapter = new RingtoneAdapter(localRingtoneList);
                recyclerView.setAdapter(ringtoneAdapter);
            }
        });

//        new Thread(() -> {
//            requireActivity().runOnUiThread(() -> {
//
//            });
//        }).start();

    }


    public void onSoundClick(SoundModel soundModel, int i) {
//        MediaPlayerUtils.startSound(getContext(), soundModel.getSound());
    }


}