package com.mountaineers.myapplication.sem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        //casting and get the name of the btn
        Button btn = findViewById(view.getId());
        String btnName = btn.getText().toString().trim().toLowerCase();
        //get the name of the sample
        String sample = btnName.substring(0, 6) + btnName.substring(7);

        //declare the var
        Intent intent;
        String callerVal;
        //check from sample type image or html
        if (sample.equals("sample13") ) {
            intent = new Intent(this, ViewPagerActivity.class);
            callerVal = "apacheImg";
        } else {
            intent = new Intent(this, ExamWeb.class);
            callerVal = "apache";
        }
        //to show the caller activity and sample NO
        intent.putExtra("caller", callerVal);
        intent.putExtra(callerVal, sample);
        //start the activity
        startActivity(intent);
    }
}