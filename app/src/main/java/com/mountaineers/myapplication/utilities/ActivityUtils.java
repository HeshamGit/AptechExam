package com.mountaineers.myapplication.utilities;

import android.content.Context;
import android.content.Intent;

import com.mountaineers.myapplication.models.SampleExam;
import com.mountaineers.myapplication.models.SemSubject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityUtils {

    public static void startListActivity(Context context, Class<?> activityClassString, String key, String value) {
        Intent intent = new Intent(context, activityClassString);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    // Get the title of the activity from string resource file based on the given name
    public static String getTitle(Context context, String name) {
        // Construct the resource name by adding "_exam" to the name
        String resourceName = name + "_exam";

        // Get the resource ID for the corresponding string resource
        int resourceId = context.getResources().getIdentifier(resourceName, "string", context.getPackageName());

        // Fetch the string from resources based on the resource ID
        return context.getResources().getString(resourceId);
    }
}
