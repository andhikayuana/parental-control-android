package com.kodemetro.yuana.parentalcontrol.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.AndroidProcess;
import com.kodemetro.yuana.parentalcontrol.LockScreenActivity;
import com.kodemetro.yuana.parentalcontrol.ParentalApplication;

import java.util.List;

/**
 * Created by yuana on 5/3/16.
 */
public class ParentalService extends Service {

    private final String TAG = "ParentalService";

    LockerThread    lockerThread;
    boolean         isInterrupted;
    List<String>    excludeProcNames;

    SharedPreferences sPref = ParentalApplication.getInstance().getSharedPreferences();

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

        isInterrupted = false;

    }

    class LockerThread extends Thread {

        //TODO locker thread here

        @Override
        public void run() {
            super.run();

            String[] apps_to_lock = sPref.getString("apps_to_lock", "").split(";");

            while (!isInterrupted) {

                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

                @SuppressWarnings("deprecation")
                ActivityManager.RunningTaskInfo proc = am.getRunningTasks(1).get(0);

                String pkgName = proc.topActivity.getPackageName();

                Log.d(TAG, "BEGIN ====================================");

                Log.d(TAG, "proc name => "  + pkgName );

                for (String lockApp : apps_to_lock ) {
                    if (lockApp.equals(pkgName)) {

                        Intent homeIntent = new Intent();
                        homeIntent.setAction(Intent.ACTION_MAIN);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(homeIntent);

                        //screen Lock
                        Intent intent = new Intent(ParentalService.this, LockScreenActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);

                    }
                }

                Log.d(TAG, "END ====================================");

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }

        }
    }
}
