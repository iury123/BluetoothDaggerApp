package com.example.iurymiguel.daggerapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MyBluetoothManager {

    private BluetoothAdapter mBluetoothAdapter;

    @Inject
    public MyBluetoothManager() {

    }

    public BluetoothAdapter getBluetoothAdapter() {
        if(mBluetoothAdapter == null) {
            // If it is null, Device does not support Bluetooth.
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return mBluetoothAdapter;
    }


    public Set<BluetoothDevice> getBondedDevices() {
        return mBluetoothAdapter.getBondedDevices();
    }

}
