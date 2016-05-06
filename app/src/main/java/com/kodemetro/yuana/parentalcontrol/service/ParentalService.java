package com.kodemetro.yuana.parentalcontrol.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ParentalService extends Service {
    public ParentalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
