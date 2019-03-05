package com.example.iurymiguel.daggerapp.dagger.modules;

import com.example.iurymiguel.daggerapp.views.fragments.DeviceDetailsFragment;
import com.example.iurymiguel.daggerapp.views.fragments.DevicesListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityFragmentsModule {

    @ContributesAndroidInjector
    abstract DevicesListFragment devicesListFragment();

    @ContributesAndroidInjector
    abstract DeviceDetailsFragment deviceDetailsFragment();
}
