package com.example.iurymiguel.daggerapp.viewModels;

import android.arch.lifecycle.ViewModel;

import com.example.iurymiguel.daggerapp.bluetooth.MyBluetoothManager;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private MyBluetoothManager mMyBluetoothManager;

    @Inject
    public MainViewModel(MyBluetoothManager myBluetoothManager) {
        mMyBluetoothManager = myBluetoothManager;
    }

    public MyBluetoothManager getMyBluetoothManager() {
        return mMyBluetoothManager;
    }

}
