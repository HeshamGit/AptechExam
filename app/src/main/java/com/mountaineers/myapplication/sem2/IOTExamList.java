package com.mountaineers.myapplication.sem2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.ExamWeb;
import com.mountaineers.myapplication.R;

public class IOTExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_o_t_exam_list);
    }

    public void OnclickSample(View view) {
        //casting and get the name of the btn
        Button btn = findViewById(view.getId());
        String btnName = btn.getText().toString().trim().toLowerCase();
        //get the name of the sample
        String sample = btnName.substring(0, 6) + btnName.substring(7);

        Intent intent = new Intent(this, ExamWeb.class);

        //to show the caller activity and sample NO
        intent.putExtra("caller", "iot");
        intent.putExtra("iot", sample);
        //start the activity
        startActivity(intent);
    }
}