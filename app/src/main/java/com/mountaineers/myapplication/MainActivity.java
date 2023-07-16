package com.mountaineers.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //to open sem1 activity
    public void goToSem1(View view) {
        ActivityUtils.startListActivity(this, SemExamList.class, "semester", "sem1");
    }

    //to open sem1 activity
    public void goToSem2(View view) {
        ActivityUtils.startListActivity(this, SemExamList.class, "semester", "sem2");
    }

    //to open sem3 activity
    public void goToSem3(View view) {
        ActivityUtils.startListActivity(this, SemExamList.class, "semester", "sem3");
    }

    //to open sem4 activity
    public void goToSem4(View view) {
        ActivityUtils.startListActivity(this, SemExamList.class, "semester", "sem4");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Made By Hesham Noaman & Abdu Al-Jalil Al-Sharpi", Toast.LENGTH_LONG).show();
    }
}