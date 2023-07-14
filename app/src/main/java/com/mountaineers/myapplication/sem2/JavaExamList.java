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

public class JavaExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_exam_list);
    }

    public void OnclickSample(View view) {
        // get the sample name
        String sample = ((Button) view).getText().toString().replaceAll("\\s", "").toLowerCase();

        // initialize var
        String callerVal;
        Class<?> activityClass;

        //check from sample type image or html
        if ("sample6".equals(sample)) {
            callerVal = "javaImg";
            activityClass = ViewPagerActivity.class;
        } else {
            callerVal = "java";
            activityClass = ExamWeb.class;
        }

        //call the method to open the exam sampled
        ActivityUtils.startExamActivity(this, activityClass, callerVal, sample);
    }
}