package com.example.iurymiguel.daggerapp.bluetooth;

import android.bluetooth.BluetoothAdapter;

public class MyBluetoothManager {

    private BluetoothAdapter mBluetoothAdapter;

    public BluetoothAdapter getBluetoothAdapter() {
        if(mBluetoothAdapter == null) {
            // If it is null, Device does not support Bluetooth.
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return mBluetoothAdapter;
    }

}
