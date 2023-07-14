package com.mountaineers.myapplication.sem2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.ActivityUtils;
import com.mountaineers.myapplication.ExamWeb;
import com.mountaineers.myapplication.R;

public class IOTExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_o_t_exam_list);
    }

    public void OnclickSample(View view) {
        // get the sample name
        String sample = ((Button) view).getText().toString().replaceAll("\\s", "").toLowerCase();

        //call the method to open the exam sampled
        ActivityUtils.startExamActivity(this, ExamWeb.class, "iot", sample);
    }
}