package com.example.iurymiguel.daggerapp.dagger.modules;

import android.arch.lifecycle.ViewModel;

import com.example.iurymiguel.daggerapp.bluetooth.MyBluetoothManager;
import com.example.iurymiguel.daggerapp.factories.ViewModelFactory;
import com.example.iurymiguel.daggerapp.viewModels.MainViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;


import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }


    @Provides
    ViewModelFactory providesViewModelFactory(Map<Class<? extends ViewModel>,
            Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    ViewModel providesMainViewModel(MyBluetoothManager bluetoothManager) {
        return new MainViewModel(bluetoothManager);
    }
}
