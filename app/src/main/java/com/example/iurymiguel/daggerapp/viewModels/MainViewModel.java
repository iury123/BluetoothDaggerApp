package com.example.iurymiguel.daggerapp.viewModels;

import android.arch.lifecycle.ViewModel;

import com.example.iurymiguel.daggerapp.bluetooth.MyBluetoothManager;

public class MainViewModel extends ViewModel {

    private MyBluetoothManager mMyBluetoothManager;

    public MyBluetoothManager getMyBluetoothManager() {
        return mMyBluetoothManager;
    }


}
