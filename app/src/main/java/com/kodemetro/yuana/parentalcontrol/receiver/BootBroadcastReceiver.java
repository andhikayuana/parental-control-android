package com.kodemetro.yuana.parentalcontrol.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kodemetro.yuana.parentalcontrol.service.ParentalService;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public BootBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_BOOT_COMPLETED)){
            context.startService(new Intent(context, ParentalService.class));
        }
        else if(action.equals(Intent.ACTION_SHUTDOWN)) {
            context.startService(new Intent(context, ParentalService.class));
        }
    }
}
