package com.mountaineers.myapplication.sem4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.sem2.JavaExamList;

public class Sem4ExamList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem4_exam_list);
    }

    public void phpExam(View view) {
        Intent intent = new Intent(this, PHPExamList.class);
        startActivity(intent);
    }

    public void apacheExam(View view) {
        Intent intent = new Intent(this, ApacheExamList.class);
        startActivity(intent);
    }

    public void mysqlExam(View view) {
        Intent intent = new Intent(this, MySQLExamList.class);
        startActivity(intent);
    }

    public void ajaxJavaExam(View view) {
        Intent intent = new Intent(this, AjaxJavaExamList.class);
        startActivity(intent);
    }

    public void androidAppExam(View view) {
        Intent intent = new Intent(this, AndroidAppExamList.class);
        startActivity(intent);
    }

    public void testAndroidExam(View view) {
        Intent intent = new Intent(this, TestAndroidAppExamList.class);
        startActivity(intent);
    }

    public void javaWebServicesExam(View view) {
        Intent intent = new Intent(this, JavaWebServicesExamList.class);
        startActivity(intent);
    }

    public void cloudPlatformServiceExam(View view) {
        Intent intent = new Intent(this, CloudPlatformServiceExamList.class);
        startActivity(intent);
    }

    public void EJBExam(View view) {
        Intent intent = new Intent(this, EJBExamList.class);
        startActivity(intent);
    }

    public void softwareEngineeringExam(View view) {
        Intent intent = new Intent(this, SoftwareEngineeringExamList.class);
        startActivity(intent);
    }

    public void softwareProjectExam(View view) {
        Intent intent = new Intent(this, SoftwareProjectExamList.class);
        startActivity(intent);
    }
}