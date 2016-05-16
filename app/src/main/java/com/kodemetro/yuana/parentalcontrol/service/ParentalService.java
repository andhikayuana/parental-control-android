package com.kodemetro.yuana.parentalcontrol.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class ParentalService extends Service {

    private final String TAG = "ParentalService";
    private ActivityManager activityManager = null;
    private ExecutorService executorService;

    public ParentalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        LockerThread thread = new LockerThread();
//        executorService.submit(thread);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Started");

        Timer timer  =  new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int phonelaunched = 0,phoneclosed =0;
            int phonelaunches = 1;
            @Override
            public void run() {
                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

                for ( ActivityManager.RunningAppProcessInfo appProcess: runningAppProcessInfo ) {
                    Log.d(appProcess.processName.toString(),"is running");
                    if (appProcess.processName.equals("com.android.browser")) {
                        if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                            if (phonelaunched == 0 ){
                                phonelaunched = 1;
                                Log.d(TAG,"dude phone has been launched");
                                Toast.makeText(getBaseContext(), "browser opened", Toast.LENGTH_SHORT).show();
                            }
                            else if (phoneclosed == 1){
                                phonelaunches++;
                                phoneclosed = 0;
                                Log.d(String.valueOf(phonelaunches),"dude that was counter");
                                Toast.makeText(getBaseContext(), "browser opened ++", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            phoneclosed = 1;
                            Log.d(TAG,"dude phone has been closed");
                            Toast.makeText(getBaseContext(), "browser closed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        },2000,3000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroyed");
    }

}
