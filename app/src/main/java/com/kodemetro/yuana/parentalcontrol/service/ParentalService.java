package com.kodemetro.yuana.parentalcontrol.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.widget.Toast;

import com.kodemetro.yuana.parentalcontrol.LockScreenActivity;
import com.kodemetro.yuana.parentalcontrol.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by yuana on 5/3/16.
 */
public class ParentalService extends Service {

    private final String TAG = "ParentalService";
    private ActivityManager activityManager = null;
    private LockerThread lockerThread;
    private boolean isInterrupted;

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

//        executorService.submit(thread);

        isInterrupted = false;

        lockerThread = new LockerThread();
        lockerThread.start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Started");


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
        isInterrupted = true;

    }

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

        }
    }
}
