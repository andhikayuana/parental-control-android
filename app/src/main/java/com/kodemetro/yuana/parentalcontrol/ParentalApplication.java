package com.kodemetro.yuana.parentalcontrol;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by yuana on 5/3/16.
 */
public class ParentalApplication extends Application {

    public static ParentalApplication singleton;
    public static String packageName;

    public static ParentalApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        packageName = getPackageName();
    }

    public SharedPreferences getSharedPreferences() {
        return this.getSharedPreferences(this.getPackageName(), MODE_PRIVATE);
    }

    public void logout(){
        SharedPreferences sPref = getSharedPreferences();
        sPref.edit().clear().commit();
    }
}
