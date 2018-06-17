package com.medbox.medbox.ui.util;


import android.os.Handler;
import android.os.Looper;
/**
 * Created by luzhoutao on 2017/2/25.
 */

public class HandlerUtils {
    private HandlerUtils(){}

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static boolean post(Runnable r) {
        return handler.post(r);
    }

    public static boolean postDelayed(Runnable r, long delayMillis) {
        return handler.postDelayed(r, delayMillis);
    }
}
