package com.mountaineers.myapplication.sem2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.ActivityUtils;
import com.mountaineers.myapplication.ExamWeb;
import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.ViewPagerActivity;


public class RedHatExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_hat_exam_list);
    }

    public void OnclickSample(View view) {
        // get the sample name
        String sample = ((Button) view).getText().toString().replaceAll("\\s", "").toLowerCase();

        // initialize var
        String callerVal;
        Class<?> activityClass;

        //check from sample type image or html
        switch (sample) {
            case "sample12":
                callerVal = "linuxImg";
                activityClass = ViewPagerActivity.class;
                break;
            case "sample13":
                callerVal = "linuxPdf";
                activityClass = ViewPagerActivity.class;
                break;
            default:
                callerVal = "red_hat";
                activityClass = ExamWeb.class;
                break;
        }

        //call the method to open the exam sampled
        ActivityUtils.startExamActivity(this, activityClass, callerVal, sample);
    }

}