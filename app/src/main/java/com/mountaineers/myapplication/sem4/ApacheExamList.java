package com.mountaineers.myapplication.sem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mountaineers.myapplication.ActivityUtils;
import com.mountaineers.myapplication.ExamWeb;
import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.ViewPagerActivity;

public class ApacheExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apache_exam_list);
    }

    public void OnclickSample(View view) {
        // get the sample name
        String sample = ((Button) view).getText().toString().replaceAll("\\s", "").toLowerCase();

        // initialize var
        String callerVal;
        Class<?> activityClass;

        // check if the sample is img or not
        if (sample.equals("sample13")){
            callerVal = "apacheImg";
            activityClass = ViewPagerActivity.class;
        }
        else {
            callerVal = "apache";
            activityClass = ExamWeb.class;
        }

        //call the method to open the exam sampled
        ActivityUtils.startExamActivity(this, activityClass, callerVal, sample);
    }
}