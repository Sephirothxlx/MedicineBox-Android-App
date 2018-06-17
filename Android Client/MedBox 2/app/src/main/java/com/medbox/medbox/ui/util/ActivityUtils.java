package com.medbox.medbox.ui.util;

import android.app.Activity;
import android.os.Build;

/**
 * Created by luzhoutao on 2017/2/25.
 */

public class ActivityUtils {

    private ActivityUtils(){}

    public static boolean isAlive(Activity activity) {
        return activity != null && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) && !activity.isFinishing();
    }
}
