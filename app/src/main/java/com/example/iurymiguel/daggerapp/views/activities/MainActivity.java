package com.example.iurymiguel.daggerapp.views.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.iurymiguel.daggerapp.R;
import com.example.iurymiguel.daggerapp.databinding.ActivityMainBinding;
import com.example.iurymiguel.daggerapp.factories.ViewModelFactory;
import com.example.iurymiguel.daggerapp.utils.Utils;
import com.example.iurymiguel.daggerapp.viewModels.MainViewModel;
import com.example.iurymiguel.daggerapp.views.fragments.DeviceDetailsFragment;
import com.example.iurymiguel.daggerapp.views.fragments.DevicesListFragment;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelFactory mViewModelFactory;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ActivityMainBinding mBinding;
    private MainViewModel mMainViewModel;
    private BluetoothAdapter mBluetoothAdapter;
    private final int REQUEST_ENABLE_BT = 1;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
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

        registerReceiver(mBroadcastReceiver, new IntentFilter("onBluetoothStateChanged"));
        initializeBluetooth();
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
        unregisterReceiver(mBroadcastReceiver);
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
