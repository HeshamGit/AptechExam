package com.mountaineers.myapplication.sem2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.ExamListActivity;
import com.mountaineers.myapplication.R;

public class Sem2ExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem2_exam_list);
    }

    public void javaExam(View view) {
        startExamListActivity("java");
    }

    public void CSharpExam(View view) {
        startExamListActivity("cSharp");
    }

    public void ReadHatExam(View view) {
        startExamListActivity("red_hat");
    }

    public void SEOExam(View view) {
        startExamListActivity("seo");
    }

    public void IOTExam(View view) {
        startExamListActivity("iot");
    }

    private void startExamListActivity(String subject) {
        Intent intent = new Intent(this, ExamListActivity.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}