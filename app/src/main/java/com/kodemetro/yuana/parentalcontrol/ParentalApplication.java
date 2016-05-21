package com.kodemetro.yuana.parentalcontrol;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.JsonToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by yuana on 5/3/16.
 */
public class ParentalApplication extends Application {

    public static ParentalApplication singleton;
    public static String packageName;

    public static final String TAG = "com.kodemetro.yuana.parentalcontrol.";
    public static final String STOP_TIMER = TAG + "STOP_TIMER";
    public static final String LOCK = TAG + "LOCK";

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
        sPref.edit().putBoolean("login", false).commit();
    }

    public JSONObject getQA(){

        boolean matematika = getSharedPreferences().getBoolean("materi_matematika", true);
        boolean b_inggris  = getSharedPreferences().getBoolean("materi_inggris", true);

        String[] QA1 = getResources().getStringArray(R.array.matematika);
        String[] QA2  = getResources().getStringArray(R.array.bahasa_inggris);

        ArrayList<String> temp = new ArrayList<String>();

        String[] QA = null;

        if (matematika == true && b_inggris == true) {
            temp.addAll(Arrays.asList(QA1));
            temp.addAll(Arrays.asList(QA2));

            QA = temp.toArray(new String[QA1.length + QA2.length]);
        }
        else if(matematika == true && b_inggris == false) {

            QA = QA1;

        }
        else if(matematika == false && b_inggris == true) {

            QA = QA2;
        }
        else{
            //
        }

        String randomQA = QA[new Random().nextInt(QA.length)];

        JSONObject json = null;

        try {
            json = new JSONObject(randomQA);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return json;

    }
}
