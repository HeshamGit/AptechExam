package com.mountaineers.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemExamList extends AppCompatActivity {

    private Map<String, Integer> semesterTitles;
    private Map<String, List<SubjectModel>> SemesterSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_exam_list);

        ListView listViewSemester = findViewById(R.id.listViewSemester);

        // Retrieve the semester name from the intent
        String semester = getIntent().getStringExtra("semester");

        // Initialize semester titles
        initializeSemesterTitles();

        // Set the title based on the semester
        TextView semesterTitle = findViewById(R.id.semesterTitle);
        semesterTitle.setText(getSemesterTitle(semester));

        // Initialize subjects of semester
        initializeSemesterSubjects();

        // Set the appropriate subject list of semester based on the semester name
        List<SubjectModel> SubjectsForSemester = getSubjectsForSemester(semester);

        // Create the adapter and set it to the ListView
        SemExamAdapter adapter = new SemExamAdapter(this, SubjectsForSemester);
        listViewSemester.setAdapter(adapter);

    }

    // Get the title of the semester based on the given semester name
    private String getSemesterTitle(String semester) {
        Integer titleResId = semesterTitles.get(semester);
        return (titleResId != null) ? getString(titleResId) : "";
    }

    // Get the list of subjects for the given semester
    private List<SubjectModel> getSubjectsForSemester(String semester) {
        List<SubjectModel> subjects = SemesterSubjects.get(semester);
        return (subjects != null) ? subjects : new ArrayList<>();
    }

    // Initialize the semester titles map
    private void initializeSemesterTitles() {
        // Initialize the semester titles
        semesterTitles = new HashMap<>();
        semesterTitles.put("sem1", R.string.semester_one_exams);
        semesterTitles.put("sem2", R.string.semester_two_exams);
        semesterTitles.put("sem3", R.string.semester_three_exams);
        semesterTitles.put("sem4", R.string.semester_four_exams);
    }

    private void initializeSemesterSubjects() {
        // Initialize the semesters subjects
        SemesterSubjects = new HashMap<>();
        SemesterSubjects.put("sem1", getSemOneSubjects());
        SemesterSubjects.put("sem2", getSemTwoSubjects());
        SemesterSubjects.put("sem3", getSemThreeSubjects());
        SemesterSubjects.put("sem4", getSemFourSubjects());
    }


    // Get the list of subjects for semester 1
    private List<SubjectModel> getSemOneSubjects() {
        List<SubjectModel> semOneSubjects = new ArrayList<>();
        semOneSubjects.add(new SubjectModel(getString(R.string.html5), "html5"));
        semOneSubjects.add(new SubjectModel(getString(R.string.elementary_c), "elementary_c"));
        semOneSubjects.add(new SubjectModel(getString(R.string.programming_principles), "programming_principles"));
        semOneSubjects.add(new SubjectModel(getString(R.string.xml), "xml"));
        semOneSubjects.add(new SubjectModel(getString(R.string.oop_concept), "oop_concept"));
        semOneSubjects.add(new SubjectModel(getString(R.string.sql_server), "sql_server"));
        semOneSubjects.add(new SubjectModel(getString(R.string.ui_ux), "ui_ux"));
        semOneSubjects.add(new SubjectModel(getString(R.string.google_apps), "google_apps"));

        return semOneSubjects;
    }

    // Get the list of subjects for semester 2
    private List<SubjectModel> getSemTwoSubjects() {
        List<SubjectModel> semTwoSubjects = new ArrayList<>();
        semTwoSubjects.add(new SubjectModel(getString(R.string.java), "java"));
        semTwoSubjects.add(new SubjectModel(getString(R.string.c_sharp), "cSharp"));
        semTwoSubjects.add(new SubjectModel(getString(R.string.linux_read_hat), "red_hat"));
        semTwoSubjects.add(new SubjectModel(getString(R.string.iot), "iot"));
        semTwoSubjects.add(new SubjectModel(getString(R.string.seo), "seo"));
        semTwoSubjects.add(new SubjectModel(getString(R.string.oop_in_java), "oop_in_java"));
        semTwoSubjects.add(new SubjectModel(getString(R.string.online_ad), "online_ad"));

        return semTwoSubjects;
    }

    // Get the list of subjects for semester 3
    private List<SubjectModel> getSemThreeSubjects() {
        List<SubjectModel> semThreeSubjects = new ArrayList<>();
        semThreeSubjects.add(new SubjectModel(getString(R.string.agile), "agile"));
        semThreeSubjects.add(new SubjectModel(getString(R.string.dev_app_java), "dev_app_java"));
        semThreeSubjects.add(new SubjectModel(getString(R.string.fund_java_enterprise), "fund_java_enterprise"));
        semThreeSubjects.add(new SubjectModel(getString(R.string.web_component), "web_component"));

        return semThreeSubjects;
    }

    // Get the list of subjects for semester 4
    private List<SubjectModel> getSemFourSubjects() {
        List<SubjectModel> semFourSubjects = new ArrayList<>();
        semFourSubjects.add(new SubjectModel(getString(R.string.php), "php"));
        semFourSubjects.add(new SubjectModel(getString(R.string.apache), "apache"));
        semFourSubjects.add(new SubjectModel(getString(R.string.mysql), "mysql"));
        semFourSubjects.add(new SubjectModel(getString(R.string.ajax_java), "ajax_Java"));
        semFourSubjects.add(new SubjectModel(getString(R.string.android_app), "android_app"));
        semFourSubjects.add(new SubjectModel(getString(R.string.test_android_app), "testing_android"));
        semFourSubjects.add(new SubjectModel(getString(R.string.java_web_services), "java_web_services"));
        semFourSubjects.add(new SubjectModel(getString(R.string.cloud_platform_service), "cloud_platform"));
        semFourSubjects.add(new SubjectModel(getString(R.string.EJB), "ejb"));
        semFourSubjects.add(new SubjectModel(getString(R.string.software_engineering), "soft_eng"));
        semFourSubjects.add(new SubjectModel(getString(R.string.software_project), "ms_project"));
        return semFourSubjects;
    }

}