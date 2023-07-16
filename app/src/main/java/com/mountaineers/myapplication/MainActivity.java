package com.mountaineers.myapplication;

import android.content.Intent;
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
    public void goToSem1(View view) {startExamListActivity("sem1");}

    //to open sem1 activity
    public void goToSem2(View view) {
        startExamListActivity("sem2");
    }

    //to open sem3 activity
    public void goToSem3(View view) {
        startExamListActivity("sem3");
    }

    //to open sem4 activity
    public void goToSem4(View view) {
        startExamListActivity("sem4");
    }

    private void startExamListActivity(String semester) {
        Intent intent = new Intent(this, SemExamList.class);
        intent.putExtra("semester", semester);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Made By Hesham Noaman & Abdu Al-Jalil Al-Sharpi", Toast.LENGTH_LONG).show();
    }
}