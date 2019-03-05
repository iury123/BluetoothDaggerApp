package com.example.iurymiguel.daggerapp.utils;

import android.os.Handler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TimeoutHandler {

    @Inject
    public TimeoutHandler(){}


    public void startTimer(final TimeoutListener listener, int duration) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                listener.onTimeout();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, duration);
    }
}

