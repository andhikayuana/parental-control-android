package com.kodemetro.yuana.parentalcontrol.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.widget.Toast;

import com.kodemetro.yuana.parentalcontrol.LockScreenActivity;
import com.kodemetro.yuana.parentalcontrol.MainActivity;
import com.kodemetro.yuana.parentalcontrol.ParentalApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuana on 5/3/16.
 */
public class ParentalService extends Service {

    private final String TAG = "ParentalService";
    private ActivityManager activityManager = null;
    private LockerThread lockerThread;
//    private SetTimerLock setTimerLock;
    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
    private boolean isInterrupted;
    private String excludeApp = null;
    private SharedPreferences sPref = ParentalApplication.getInstance().getSharedPreferences();
    private String[] apps_to_lock;

    public ParentalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        apps_to_lock = sPref.getString("apps_to_lock", "").split(";");

//        executorService.submit(thread);

        isInterrupted = false;

//        lockerThread = new LockerThread();
//        lockerThread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Started");

//        setTimerLock = new SetTimerLock();

        Timer timer  =  new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

                @SuppressWarnings("deprecation")
                String runApp = am.getRunningTasks(1).get(0).topActivity.getPackageName().toString();

                Log.d(TAG, "BEGIN ------------------------");

                Log.d(TAG, "NOW app => " + runApp + " - " +excludeApp);

                for (String lockApp : apps_to_lock) {

                    if (runApp.equals(lockApp) && excludeApp == null) {

                        Log.d(TAG, runApp + " - LOCKED");

                        excludeApp = lockApp;

                        unLockTimer();

//                        setTimerLock.start();
/*
                        Intent homeIntent = new Intent();
                        homeIntent.setAction(Intent.ACTION_MAIN);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(homeIntent);*/

                        //screenlock
                        Intent intent = new Intent(ParentalService.this, LockScreenActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                );
                        startActivity(intent);

                        //todo simpen package di excludeApp : dengan waktu sekian menit
                        //setelah waktu sekian menit habis, maka masukkan kembali ke apps_lock pref
                        //cek jika belum jawab, excludeApp diisi null
                        //bikin handler atau runnable

                    }
                    else {

                        Log.d(TAG, "NOT LOCKED");

                    }

                }

                Log.d(TAG, "END   ------------------------" + " - " + excludeApp);

            }
        }, 1000, 1000);

        return START_STICKY;
    }

    private void unLockTimer(){

        Log.d(TAG, "mulai hitung timer");

        Runnable task = new Runnable() {
            @Override
            public void run() {

                if (excludeApp != null) {

                    excludeApp = null;
                }

            }
        };

        worker.schedule(task, 20, TimeUnit.SECONDS);

        Log.d(TAG, "selesai timer dan hapus");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
        isInterrupted = true;
    }
/*
    class SetTimerLock extends Thread {
        @Override
        public void run() {
            super.run();

            Log.d(TAG, "mulai hitung timer");

            if (excludeApp != null) {
                try {
                    Thread.sleep(10000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                excludeApp = null;
            }

            Log.d(TAG, "selesai timer dan hapus");
        }
    }*/

    class LockerThread extends Thread{

        @SuppressWarnings("deprecation")
        private String getTopActivityPkgName() {
            String mPackageName;

            activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            if (Build.VERSION.SDK_INT > 20){
                mPackageName = activityManager.getRunningAppProcesses().get(0).processName;
            }
            else{
                mPackageName = activityManager.getRunningTasks(1).get(0).topActivity
                        .getPackageName();
            }
            return mPackageName;
        }

        @Override
        public void run() {
            super.run();
            String runPkgName = getTopActivityPkgName();

            Log.d(TAG, "BEGIN ------------------------");

            Log.i(TAG, "TopActivity: " + runPkgName);

//            if (excludeProcNames.contains(runPkgName))
//                Log.i("APP LOCKER", runPkgName + " process is exclude!");
//            else
//            {
                Log.i(TAG, runPkgName + " process locked!");
                Intent homeIntent = new Intent();
                homeIntent.setAction(Intent.ACTION_MAIN);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                startActivity(homeIntent);

                //screenlock
                Intent intent = new Intent(ParentalService.this, LockScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
//            }

            Log.d(TAG, "END ------------------------");
        }
    }
}
