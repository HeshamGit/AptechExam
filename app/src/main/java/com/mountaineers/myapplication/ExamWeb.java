package com.mountaineers.myapplication;


import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

public class ExamWeb extends AppCompatActivity {
    WebView exam;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_web);

        //casting the web view and toggleButton
        exam = findViewById(R.id.ExamView);

        //getting the caller activity name
        String caller = getIntent().getExtras().getString("caller");

        //to make the title of the web view
        String title = caller + " " + getIntent().getExtras().getString(caller);

        //to get the actionBar
        ActionBar actionBar = getSupportActionBar();
        //check if the actionBar have a value
        assert actionBar != null;
        //to make the title of the web view
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);

        //to get the mode of the device
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        //check if the device support the feature
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            //add the toggle layout to the actionBar
            actionBar.setCustomView(R.layout.toggle_layout);
            //add the title to the actionBar
            actionBar.setDisplayShowCustomEnabled(true);

            //casting the toggleButton
            toggleButton = findViewById(R.id.toggle_btn);
            //make on click listener to toggle the web view between the dark and light Mode
            toggleButton.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    WebSettingsCompat.setForceDark(exam.getSettings(),
                            WebSettingsCompat.FORCE_DARK_ON);
                } else {
                    WebSettingsCompat.setForceDark(exam.getSettings(),
                            WebSettingsCompat.FORCE_DARK_OFF);
                }
            });

            //to check if the dark mode is enabled
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                //to make the web view dark
                WebSettingsCompat.setForceDark(exam.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                toggleButton.setChecked(true);
            }

        }

        //check if there is a value in save instance
        if (savedInstanceState != null) {
            //to restore the value that was saved  in the save instance
            exam.restoreState(savedInstanceState);
        } else {
            //open the web view depend on the chosen activity and sample
            String sample = getIntent().getExtras().getString(caller);
            exam.loadUrl("file:///android_asset/" + caller + "/" + sample + ".htm");
        }
    }

    //save the state of web view
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        exam.saveState(outState);
    }
}