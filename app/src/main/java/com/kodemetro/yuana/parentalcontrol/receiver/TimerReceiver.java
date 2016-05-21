package com.kodemetro.yuana.parentalcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.kodemetro.yuana.parentalcontrol.ParentalApplication;
import com.kodemetro.yuana.parentalcontrol.service.ParentalService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerReceiver extends BroadcastReceiver {

    private final String TAG = "TimerReceiver";

    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

    private SharedPreferences sPref = ParentalApplication
            .getInstance()
            .getSharedPreferences();

    public TimerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "MASUK di TimerReceiver", Toast.LENGTH_SHORT).show();

        int timer = sPref.getInt("timer",1);

        unLockTimer(context, timer);
    }

    private void unLockTimer(final Context context, int timer) {

        Log.d(TAG, "Mulai hitung timer");

        Runnable task = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.setAction(ParentalApplication.STOP_TIMER);
                context.sendBroadcast(i);
            }
        };

        worker.schedule(task, timer, TimeUnit.MINUTES);

        Log.d(TAG, "Selesai hitung timer");
    }
}