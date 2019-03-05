package com.example.iurymiguel.daggerapp.views.activities;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.iurymiguel.daggerapp.R;
import com.example.iurymiguel.daggerapp.databinding.ActivityMainBinding;
import com.example.iurymiguel.daggerapp.factories.ViewModelFactory;
import com.example.iurymiguel.daggerapp.utils.TimeoutHandler;
import com.example.iurymiguel.daggerapp.utils.Utils;
import com.example.iurymiguel.daggerapp.viewModels.MainViewModel;
import com.example.iurymiguel.daggerapp.views.fragments.DeviceDetailsFragment;
import com.example.iurymiguel.daggerapp.views.fragments.DevicesListFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelFactory mViewModelFactory;
    @Inject
    TimeoutHandler mTimeoutHandler;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ActivityMainBinding mBinding;
    private MainViewModel mMainViewModel;
    private BluetoothAdapter mBluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 1;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private final BroadcastReceiver mOnBluetoothStateChangeListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra("state", -1);

            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    Utils.showToast(context, "Bluetooth ligando.",
                            Toast.LENGTH_SHORT);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Utils.showToast(context, "Bluetooth desligando.",
                            Toast.LENGTH_SHORT);
                    break;
                case BluetoothAdapter.STATE_ON:
                    Utils.showToast(context, "Bluetooth ligado.",
                            Toast.LENGTH_SHORT);
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Utils.showToast(context, "Bluetooth desligado.",
                            Toast.LENGTH_SHORT);
                    break;
                default:
            }
        }
    };

    private final BroadcastReceiver mOnDeviceFoundListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i("DDD", device.toString());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MainViewModel.class);

        setSupportActionBar(mBinding.toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mBinding.viewPager.setAdapter(mSectionsPagerAdapter);

        mBinding.viewPager.addOnPageChangeListener(new TabLayout
                .TabLayoutOnPageChangeListener(mBinding.tabs));

        mBinding.tabs.addOnTabSelectedListener(new TabLayout
                .ViewPagerOnTabSelectedListener(mBinding.viewPager));

        registerBroadcasts();
        checkIfHasLocationPermission();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Utils.showToast(this, "Bluetooth ativado.",
                    Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcasts();
    }

    private void initializeBluetooth() {
        if (!isThereBluetooth()) {
            Utils.showToast(this, "O dispositivo não possui Bluetooth.",
                    Toast.LENGTH_SHORT);
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private boolean isThereBluetooth() {
        mBluetoothAdapter = mMainViewModel.getMyBluetoothManager()
                .getBluetoothAdapter();
        return (mBluetoothAdapter != null);
    }


    private void registerBroadcasts() {
        registerReceiver(mOnBluetoothStateChangeListener,
                new IntentFilter("onBluetoothStateChanged"));
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mOnDeviceFoundListener, filter);
    }


    private void unregisterBroadcasts() {
        unregisterReceiver(mOnBluetoothStateChangeListener);
        unregisterReceiver(mOnDeviceFoundListener);
    }

    private void checkIfHasLocationPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Utils.showToast(this, "É necessário habilitar a permissão" +
                        " de localização para o correto funcionamento do app", Toast.LENGTH_LONG);
                mTimeoutHandler.startTimer(this::requestPermissions, 4000);
            } else {
                requestPermissions();
            }
        } else {
            initializeBluetooth();
        }
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeBluetooth();
                }
                break;
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final int NUM_OF_FRAGMENTS = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DevicesListFragment.newInstance();
                default:
                    return DeviceDetailsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_FRAGMENTS;
        }
    }
}
