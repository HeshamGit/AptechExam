package com.mountaineers.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.utilities.ActivityUtils;
import com.mountaineers.myapplication.utilities.SemesterConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // to open the interactive exam & practice portal
    public void goToInteractivePortal(View view) {
        Intent intent = new Intent(this, InteractivePortalActivity.class);
        startActivity(intent);
    }

    // to open sem1 activity
    public void goToSem1(View view) {
        ActivityUtils.startListActivity(this, SemSubjectActivity.class, SemesterConstants.SEMESTER, SemesterConstants.SEMESTER_ONE);
    }

    // to open sem2 activity
    public void goToSem2(View view) {
        ActivityUtils.startListActivity(this, SemSubjectActivity.class, SemesterConstants.SEMESTER, SemesterConstants.SEMESTER_TWO);
    }

    // to open sem3 activity
    public void goToSem3(View view) {
        ActivityUtils.startListActivity(this, SemSubjectActivity.class, SemesterConstants.SEMESTER, SemesterConstants.SEMESTER_THREE);
    }

    // to open sem4 activity
    public void goToSem4(View view) {
        ActivityUtils.startListActivity(this, SemSubjectActivity.class, SemesterConstants.SEMESTER, SemesterConstants.SEMESTER_FOUR);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Made By Hesham Noaman & Abdu Al-Jalil Al-Sharpi", Toast.LENGTH_LONG).show();
    }
}
