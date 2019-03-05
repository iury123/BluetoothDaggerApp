package com.example.iurymiguel.daggerapp.views.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iurymiguel.daggerapp.R;
import com.example.iurymiguel.daggerapp.bluetooth.MyBluetoothManager;
import com.example.iurymiguel.daggerapp.factories.ViewModelFactory;
import com.example.iurymiguel.daggerapp.viewModels.MainViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class DevicesListFragment extends DaggerFragment {


    @Inject ViewModelFactory mViewModelFactory;
    private MainViewModel mMainViewModel;


    public DevicesListFragment() {
        // Required empty public constructor
    }

    public static DevicesListFragment newInstance() {
        DevicesListFragment fragment = new DevicesListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(MainViewModel.class);
        Log.i("AAA0", mMainViewModel.toString());
        Log.i("AAA1", mMainViewModel.getMyBluetoothManager().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_devices_list, container, false);
    }
}
