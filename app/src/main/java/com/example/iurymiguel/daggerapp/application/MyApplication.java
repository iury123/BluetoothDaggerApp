package com.example.iurymiguel.daggerapp.application;

import com.example.iurymiguel.daggerapp.dagger.components.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class MyApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent
                .builder()
                .application(this)
                .build();
    }
}
