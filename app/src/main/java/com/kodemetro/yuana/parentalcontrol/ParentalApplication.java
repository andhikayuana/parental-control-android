package com.kodemetro.yuana.parentalcontrol;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public String getQA(){

        //TODO get QA

        boolean matematika = getSharedPreferences().getBoolean("materi_matematika", true);
        boolean b_inggris  = getSharedPreferences().getBoolean("materi_inggris", true);

        List<String> QA_Matematika = Arrays.asList(getResources()
                .getStringArray(R.array.matematika));

        List<String> QA_B_inggris  = Arrays.asList(getResources()
                .getStringArray(R.array.bahasa_inggris));

        List<String> QA = new ArrayList<String>();

        if (matematika == true && b_inggris == true) {

        }
        else if(matematika == true && b_inggris == false) {

        }
        else if(matematika == false && b_inggris == true) {

        }
        else{

        }


//        String randomQA = QA[new Random().nextInt(QA.size())];

//        return  randomQA;
        return  null;
    }
}
