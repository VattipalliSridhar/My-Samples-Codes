package com.apps.myapplication;

import android.graphics.drawable.Drawable;

public class MainData {
    private Drawable logo;
    private String name;
    private String packageName;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName2) {
        this.packageName = packageName2;
    }

    public Drawable getLogo() {
        return this.logo;
    }

    public void setLogo(Drawable logo2) {
        this.logo = logo2;
    }
}
