package com.mountaineers.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {
    private ActivityWebBinding binding;
    private String caller;
    private String sample;
    private boolean isDarkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebBinding.inflate(LayoutInflater.from(this)); //binding activity_exam_web layout
        setContentView(binding.getRoot());

        // disable access the internet since we do not need it here
        binding.ExamView.getSettings().setBlockNetworkLoads(true);

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

            TextView toggleBtn = findViewById(R.id.btn_theme_toggle);
            if (toggleBtn == null) return;

            // Initialize state based on system mode
            isDarkMode = (nightModeFlags == Configuration.UI_MODE_NIGHT_YES);
            updateThemeButtonState(toggleBtn, isDarkMode);
            setWebViewForceDarkMode(isDarkMode);

            // Toggle the web view between dark and light mode based on the toggle state
            toggleBtn.setOnClickListener(v -> {
                isDarkMode = !isDarkMode;
                updateThemeButtonState(toggleBtn, isDarkMode);
                setWebViewForceDarkMode(isDarkMode);
            });
        }
    }

    private void updateThemeButtonState(TextView btn, boolean darkMode) {
        if (darkMode) {
            btn.setText(R.string.light_mode);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mode_light, 0, 0, 0);
            btn.setContentDescription("Switch to Light Mode");
        } else {
            btn.setText(R.string.dark_mode);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mode_night, 0, 0, 0);
            btn.setContentDescription("Switch to Dark Mode");
        }
    }

    // Set the force dark mode for the web view
    @SuppressLint("RequiresFeature")
    private void setWebViewForceDarkMode(boolean enabled) {
        int forceDarkMode = enabled ? WebSettingsCompat.FORCE_DARK_ON : WebSettingsCompat.FORCE_DARK_OFF;
        WebSettingsCompat.setForceDark(binding.ExamView.getSettings(), forceDarkMode);
    }

    // Load the content into the web view
    private void loadWebViewContent(Bundle savedInstanceState) {
        //check if there is a value in save instance
        if (savedInstanceState != null) {
            // Restore the web view state from saved instance state
            binding.ExamView.restoreState(savedInstanceState);
        } else {
            String url = "file:///android_asset/" + caller + "/" + sample + ".htm";
            // Load the web page into the web view
            binding.ExamView.loadUrl(url);
        }
    }

    //save the state of web view
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.ExamView.saveState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Properly release WebView resources
        binding.ExamView.destroy();
    }
}
