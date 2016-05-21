package com.kodemetro.yuana.parentalcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

    public TimerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Receive com.kodemetro.yuana.parentalcontrol.LOCK

        Toast.makeText(context, "MASUK di TimerReceiver", Toast.LENGTH_SHORT).show();

        unLockTimer(context);
    }

    private void unLockTimer(final Context context) {

        Log.d(TAG, "Mulai hitung timer");

        Runnable task = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                i.setAction(ParentalApplication.STOP_TIMER);
                context.sendBroadcast(i);
            }
        };

        worker.schedule(task, 8, TimeUnit.SECONDS);

        Log.d(TAG, "Selesai hitung timer");
    }
}
