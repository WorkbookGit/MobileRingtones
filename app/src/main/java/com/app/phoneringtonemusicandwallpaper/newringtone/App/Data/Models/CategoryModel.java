package com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models;

public class CategoryModel {
    int icon;
    String name;

    public CategoryModel(String name, int icons) {
        this.icon = icons;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getIcon() {
        return this.icon;
    }

}
