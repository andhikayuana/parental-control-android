package com.kodemetro.yuana.parentalcontrol.model;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by yuana on 5/2/16.
 */
public class AppInfo implements Serializable {

    public String appName;
    public String packageName;
    public String versionName;
    public int versionCode = 0;
    public Drawable appIcon = null;
    public boolean isSelected;

    public AppInfo(){

    }

    public void print(){
        Log.v("app", "Name: " + appName + " Package: " + packageName);
        Log.v("app", "Name: " + appName + " verionName: " + versionName);
        Log.v("app", "Name: " + appName + " versionCode: " + versionCode);
        Log.v("app", "Name: " + appName + " isSelected: " + isSelected);
    }

    public String getAppName(){
        return appName;
    }

    public void setAppName(String appName){
        this.appName = appName;
    }

    public String getPackageName(){
        return packageName;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public String getVersionName(){
        return versionName;
    }

    public void setVersionName(String versionName){
        this.versionName = versionName;
    }

    public int getVersionCode(){
        return versionCode;
    }

    public void setVersionCode(int versionCode){
        this.versionCode = versionCode;
    }

    public Drawable getAppIcon(){
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon){
        this.appIcon = appIcon;
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean isSelected(){
        return isSelected;
    }

    @Override
    public String toString() {
        return this.packageName + " - " + this.isSelected;
    }
}
