package com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.SMS;

import static com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Globals.localSmsRingtoneList;

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
import com.app.phoneringtonemusicandwallpaper.newringtone.App.UI.Dashboard.Adapters.SmsAdapter;
import com.app.phoneringtonemusicandwallpaper.newringtone.App.interfaces.OnSoundClickListener;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;


public class SmsFragment extends Fragment implements OnSoundClickListener {


    SmsAdapter adapter;
    RecyclerView recyclerView;


    public SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "SMS Fragment");
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "SMS Fragment");
        FirebaseAnalytics.getInstance(requireActivity()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_ringtones, container, false);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_sounds);
        setupRecycler();
        return inflate;
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        getRingtoneData();

        adapter = new SmsAdapter(localSmsRingtoneList);
        recyclerView.setAdapter(adapter);
        this.adapter.setOnSoundClickListener(this);
//        new LoadRingtonesTask().execute();
    }


//    private class LoadRingtonesTask extends AsyncTask<Void, Void, List<SoundModel>> {
//
//        @Override
//        protected List<SoundModel> doInBackground(Void... voids) {
//            List<SoundModel> ringtoneList = new ArrayList<>();
//
//
//            localSmsRingtoneList.add(new SoundModel("SMStone 1", R.raw.r0));
//            localSmsRingtoneList.add(new SoundModel("SMStone 2", R.raw.r1));
//            localSmsRingtoneList.add(new SoundModel("SMStone 3", R.raw.r2));
//            localSmsRingtoneList.add(new SoundModel("SMStone 4", R.raw.r3));
//            localSmsRingtoneList.add(new SoundModel("SMStone 5", R.raw.r4));
//            localSmsRingtoneList.add(new SoundModel("SMStone 6", R.raw.r5));
//            localSmsRingtoneList.add(new SoundModel("SMStone 7", R.raw.r6));
//            localSmsRingtoneList.add(new SoundModel("SMStone 8", R.raw.r7));
//            localSmsRingtoneList.add(new SoundModel("SMStone 9", R.raw.r8));
//            localSmsRingtoneList.add(new SoundModel("SMStone 10", R.raw.r9));
//            localSmsRingtoneList.add(new SoundModel("SMStone 11", R.raw.r10));
//            localSmsRingtoneList.add(new SoundModel("SMStone 12", R.raw.r11));
//            localSmsRingtoneList.add(new SoundModel("SMStone 13", R.raw.r12));
//            localSmsRingtoneList.add(new SoundModel("SMStone 14", R.raw.r13));
//            localSmsRingtoneList.add(new SoundModel("SMStone 15", R.raw.r14));
//            localSmsRingtoneList.add(new SoundModel("SMStone 16", R.raw.r15));
//            localSmsRingtoneList.add(new SoundModel("SMStone 17", R.raw.r16));
//            localSmsRingtoneList.add(new SoundModel("SMStone 18", R.raw.r17));
//            localSmsRingtoneList.add(new SoundModel("SMStone 19", R.raw.r18));
//            localSmsRingtoneList.add(new SoundModel("SMStone 20", R.raw.r19));
//            localSmsRingtoneList.add(new SoundModel("SMStone 21", R.raw.r20));
//            localSmsRingtoneList.add(new SoundModel("SMStone 22", R.raw.r21));
//            localSmsRingtoneList.add(new SoundModel("SMStone 23", R.raw.r22));
//            return ringtoneList;
//        }
//
//        @Override
//        protected void onPostExecute(List<SoundModel> soundModels) {
//            // Update the list and notify adapter after loading is done
//            localSmsRingtoneList.clear();
//            localSmsRingtoneList.addAll(soundModels);
//            adapter.notifyDataSetChanged();
//        }
//    }


    @Override
    public void onSoundClick(SoundModel soundModel, int i) {

    }
}