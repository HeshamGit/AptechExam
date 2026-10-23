package com.mountaineers.myapplication.utilities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AndroidBridge {

    private final Activity activity;

    public AndroidBridge(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String loadAsset(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return "";
        }
        try {
            // Clean path
            String cleanPath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
            InputStream is = activity.getAssets().open(cleanPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @JavascriptInterface
    public void showToast(final String message) {
        if (activity == null || message == null) return;
        activity.runOnUiThread(() -> Toast.makeText(activity, message, Toast.LENGTH_SHORT).show());
    }

    @JavascriptInterface
    public void vibrate(int milliseconds) {
        if (activity == null) return;
        try {
            Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(Math.max(10, milliseconds), VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(Math.max(10, milliseconds));
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    @JavascriptInterface
    public void exitPortal() {
        if (activity != null) {
            activity.runOnUiThread(activity::finish);
        }
    }
}
