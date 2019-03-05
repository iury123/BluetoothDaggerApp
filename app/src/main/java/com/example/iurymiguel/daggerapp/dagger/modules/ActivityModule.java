package com.example.iurymiguel.daggerapp.dagger.modules;

import com.example.iurymiguel.daggerapp.views.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = MainActivityFragmentsModule.class)
    abstract MainActivity mainActivity();
}
