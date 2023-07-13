package com.mountaineers.myapplication.sem2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
        //casting and get the name of the btn
        Button btn = findViewById(view.getId());
        String btnName = btn.getText().toString().trim().toLowerCase();
        //get the name of the sample
        String sample = btnName.substring(0,6) + btnName.substring(7);

        //declare the var
        Intent intent;
        String callerVal;
        //check from sample type image or html
        if (sample.equals("sample6") ) {
            intent = new Intent(this, ViewPagerActivity.class);
            callerVal = "javaImg";
        } else {
            intent = new Intent(this, ExamWeb.class);
            callerVal = "java";
        }
        //to show the caller activity and sample NO
        intent.putExtra("caller", callerVal);
        intent.putExtra(callerVal, sample);
        //start the activity
        startActivity(intent);
    }
}