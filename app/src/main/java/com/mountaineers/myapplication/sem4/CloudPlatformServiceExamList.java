package com.mountaineers.myapplication.sem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mountaineers.myapplication.ExamWeb;
import com.mountaineers.myapplication.R;

public class CloudPlatformServiceExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_platform_service_exam_list);
    }

    public void OnclickSample(View view) {
        //casting and get the name of the btn
        Button btn = findViewById(view.getId());
        String btnName = btn.getText().toString().trim().toLowerCase();
        //get the name of the sample
        String sample = btnName.substring(0, 6) + btnName.substring(7);

        Intent intent = new Intent(this, ExamWeb.class);

        //to show the caller activity and sample NO
        intent.putExtra("caller", "cloud_platform");
        intent.putExtra("cloud_platform", sample);
        //start the activity
        startActivity(intent);
    }
}