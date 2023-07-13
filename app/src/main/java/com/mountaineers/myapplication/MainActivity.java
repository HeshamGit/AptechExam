package com.mountaineers.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.sem2.Sem2ExamList;
import com.mountaineers.myapplication.sem4.Sem4ExamList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //to open sem1 activity
    public void goToSem1(View view) {
        Toast.makeText(this, "We don't add this part yet ):", Toast.LENGTH_SHORT).show();
    }

    //to open sem1 activity
    public void goToSem2(View view) {
        Intent intent = new Intent(this, Sem2ExamList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Made By Hesham Noaman & Abdu Al-Jalil Al-Sharpi", Toast.LENGTH_LONG).show();
    }

    //to open sem3 activity
    public void goToSem3(View view) {
        Toast.makeText(this, "We don't add this part yet ):", Toast.LENGTH_SHORT).show();
    }

    //to open sem4 activity
    public void goToSem4(View view) {
        Intent intent = new Intent(this, Sem4ExamList.class);
        startActivity(intent);
    }
}