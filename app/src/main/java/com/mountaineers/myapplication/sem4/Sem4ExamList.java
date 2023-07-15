package com.mountaineers.myapplication.sem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mountaineers.myapplication.ExamListActivity;
import com.mountaineers.myapplication.R;

public class Sem4ExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem4_exam_list);
    }

    public void phpExam(View view) {
        startExamListActivity("php");
    }

    public void apacheExam(View view) {
        startExamListActivity("apache");
    }

    public void mysqlExam(View view) {
        startExamListActivity("mysql");
    }

    public void ajaxJavaExam(View view) {
        startExamListActivity("ajax_Java");
    }

    public void androidAppExam(View view) {
        startExamListActivity("android_app");
    }

    public void testAndroidExam(View view) {
        startExamListActivity("testing_android");
    }

    public void javaWebServicesExam(View view) {
        startExamListActivity("java_web_services");
    }

    public void cloudPlatformServiceExam(View view) {
        startExamListActivity("cloud_platform");
    }

    public void EJBExam(View view) {
        startExamListActivity("ejb");
    }

    public void softwareEngineeringExam(View view) {
        startExamListActivity("soft_eng");
    }

    public void softwareProjectExam(View view) {
        startExamListActivity("ms_project");
    }

    private void startExamListActivity(String subject) {
        Intent intent = new Intent(this, ExamListActivity.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}