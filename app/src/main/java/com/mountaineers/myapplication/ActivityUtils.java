package com.mountaineers.myapplication;

import android.content.Context;
import android.content.Intent;

public class ActivityUtils {

    public static void startExamActivity(Context context, Class<?> activityClass, String caller, String sample) {
        // to go activity
        Intent intent = new Intent(context, activityClass);
        //to show the caller activity and sample on the activity
        intent.putExtra("caller", caller);
        intent.putExtra(caller, sample);
        context.startActivity(intent);
    }

}
