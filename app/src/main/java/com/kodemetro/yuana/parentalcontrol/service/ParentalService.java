package com.kodemetro.yuana.parentalcontrol.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

    private static final ScheduledExecutorService worker = Executors
            .newSingleThreadScheduledExecutor();

    private SharedPreferences sPref = ParentalApplication
            .getInstance()
            .getSharedPreferences();

    private String[] apps_to_lock;

    private boolean lockAgain   = true;

    private String excludeApp   = null;

    private final BroadcastReceiver stopTimerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String in = intent.getAction();

            if (in.equals(ParentalApplication.STOP_TIMER)) {

                lockAgain   = true;
                excludeApp  = null;
            }
            else if (in.equals(ParentalApplication.LOCK)) {

                lockAgain   = false;
                excludeApp  = intent.getStringExtra("exclude");

                Log.d(TAG, "dari LOCK => " + excludeApp);
            }
        }
    };

    public ParentalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate");

        super.onCreate();

        IntentFilter inFilter = new IntentFilter();
        inFilter.addAction(ParentalApplication.STOP_TIMER);
        inFilter.addAction(ParentalApplication.LOCK);
        registerReceiver(stopTimerReceiver, inFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "Started");

        Timer timer  =  new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                apps_to_lock = sPref.getString("apps_to_lock", "").split(";");

                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

                @SuppressWarnings("deprecation")
                String runApp = am.getRunningTasks(1).get(0).topActivity.getPackageName().toString();

                Log.d(TAG, "BEGIN ------------------------");

                Log.d(TAG, "NOW app => " + runApp);

                Log.d(TAG, "dari scheduler => "+ runApp + " - "  + excludeApp + " - " + lockAgain);

                if (!runApp.equals(excludeApp)) {
                    lockAgain = true;
                }

                for (String lockApp : apps_to_lock) {

                    if (runApp.equals(lockApp) && lockAgain == true && !runApp.equals(excludeApp)) {

                        Log.d(TAG, runApp + " - LOCKED");

                        sPref.edit().putString("exclude", runApp).commit();

                        //screenlock
                        Intent intent = new Intent(ParentalService.this, LockScreenActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else {

                        Log.d(TAG, "NOT LOCKED");

                    }

                }

                Log.d(TAG, "LOCK AGAIN ? -> " +lockAgain);

                Log.d(TAG, "END   ------------------------");

            }
        }, 1000, 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "Destroyed");

        super.onDestroy();

        unregisterReceiver(stopTimerReceiver);
    }
}
