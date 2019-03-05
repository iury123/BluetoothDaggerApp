package com.example.iurymiguel.daggerapp.broadcastReceivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothEventListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            Intent i = new Intent("onBluetoothStateChanged");
            i.putExtra("state", intent
                    .getIntExtra(BluetoothAdapter.EXTRA_STATE, -1));
            context.sendBroadcast(i);
        }
    }
}
