package com.example.iurymiguel.daggerapp.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    private static Toast sToast;

    public static void showToast(Context context, String message, int duration) {
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(context, message, duration);
        sToast.show();
    }


}
