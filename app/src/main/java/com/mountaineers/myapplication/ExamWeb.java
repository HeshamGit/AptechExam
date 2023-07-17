package com.mountaineers.myapplication;


import android.annotation.SuppressLint;
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
    private WebView exam;
    private String caller;
    private String sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_web);

        //casting the web view
        exam = findViewById(R.id.ExamView);

        //getting the caller activity name
        caller = getIntent().getStringExtra("caller");

        // getting the sample name
        sample = getIntent().getStringExtra(caller);

        setupActionBar();
        setupDarkModeToggle();
        loadWebViewContent(savedInstanceState);
    }

    // Set up the action bar and title
    private void setupActionBar() {
        //to get the actionBar
        ActionBar actionBar = getSupportActionBar();

        //check if the actionBar have a value
        if (actionBar != null) {
            //to make the title of the web view
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(caller + " " + sample);
        }
    }

    // Set up the dark mode toggle button and handle its events
    private void setupDarkModeToggle() {
        //to get the mode of the device
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Check if the device supports force dark mode feature
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            ActionBar actionBar = getSupportActionBar();

            //add the toggle layout to the actionBar
            if (actionBar != null) {
                actionBar.setCustomView(R.layout.toggle_layout);
                //add the title to the actionBar
                actionBar.setDisplayShowCustomEnabled(true);
            }

            //casting the toggleButton
            ToggleButton toggleButton = findViewById(R.id.toggle_btn);

            // Toggle the web view between dark and light mode based on the toggle state
            toggleButton.setOnCheckedChangeListener((buttonView, isChecked) ->
                    setWebViewForceDarkMode(isChecked));

            // Check if the system is in dark mode and set the toggle and web view accordingly
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                toggleButton.setChecked(true);
                setWebViewForceDarkMode(true);
            }
        }
    }

    // Set the force dark mode for the web view
    @SuppressLint("RequiresFeature")
    private void setWebViewForceDarkMode(boolean enabled) {
        int forceDarkMode = enabled ? WebSettingsCompat.FORCE_DARK_ON : WebSettingsCompat.FORCE_DARK_OFF;
        WebSettingsCompat.setForceDark(exam.getSettings(), forceDarkMode);
    }

    // Load the content into the web view
    private void loadWebViewContent(Bundle savedInstanceState) {
        //check if there is a value in save instance
        if (savedInstanceState != null) {
            // Restore the web view state from saved instance state
            exam.restoreState(savedInstanceState);
        } else {
            String url = "file:///android_asset/" + caller + "/" + sample + ".htm";
            // Load the web page into the web view
            exam.loadUrl(url);
        }
    }


    //save the state of web view
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        exam.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exam.destroy();  // Properly release WebView resources
    }
}
