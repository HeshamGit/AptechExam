package com.mountaineers.myapplication.sem2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mountaineers.myapplication.R;

public class Sem2ExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem2_exam_list);
    }

    public void javaExam(View view) {
        Intent intent = new Intent(this, JavaExamList.class);
        startActivity(intent);
    }

    public void CSharpExam(View view) {
        Intent intent = new Intent(this, CSharpExamList.class);
        startActivity(intent);
    }

    public void ReadHatExam(View view) {
        Intent intent = new Intent(this, RedHatExamList.class);
        startActivity(intent);
    }

    public void SEOExam(View view) {
        Intent intent = new Intent(this, SEOExamList.class);
        startActivity(intent);
    }

    public void IOTExam(View view) {
        Intent intent = new Intent(this, IOTExamList.class);
        startActivity(intent);
    }
}