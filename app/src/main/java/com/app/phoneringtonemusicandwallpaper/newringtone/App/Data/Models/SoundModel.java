package com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models;

import javax.inject.Inject;

public class SoundModel {
//    int icon;

    String name;
    int sound;
    String duration;

    @Inject
    public SoundModel(String str, int i3, String duration) {
//        this.icon = i;
        this.name = str;
        this.sound = i3;
        this.duration = duration;
    }

//    public int getIcon() {
//        return this.icon;
//    }

    public String getName() {
        return this.name;
    }


    public int getSound() {
        return this.sound;
    }

    public String getDuration() {
        return this.duration;
    }

}
