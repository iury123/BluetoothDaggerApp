package com.example.iurymiguel.daggerapp.dagger.components;

import android.app.Application;

import com.example.iurymiguel.daggerapp.application.MyApplication;
import com.example.iurymiguel.daggerapp.dagger.modules.ActivityModule;
import com.example.iurymiguel.daggerapp.dagger.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules =
        {
                ViewModelModule.class,
                ActivityModule.class,
                AndroidSupportInjectionModule.class
        })
@Singleton
public interface AppComponent extends AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
