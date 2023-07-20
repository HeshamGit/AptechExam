package com.mountaineers.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.adapters.SemSubjectAdapter;
import com.mountaineers.myapplication.models.SemSubject;
import com.mountaineers.myapplication.utilities.ActivityUtils;
import com.mountaineers.myapplication.utilities.SemesterConstants;
import com.mountaineers.myapplication.utilities.SubjectConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemSubjectActivity extends AppCompatActivity {
    private Map<String, List<SemSubject>> SemesterSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_subject);

        ListView listViewSemester = findViewById(R.id.listViewSemester);

        // Retrieve the semester name from the intent
        String semester = getIntent().getStringExtra(SemesterConstants.SEMESTER);

        // Set the title based on the semester
        TextView semesterTitle = findViewById(R.id.semesterTitle);
        semesterTitle.setText(ActivityUtils.getTitle(this, semester));

        // Initialize subjects of semester
        initializeSemesterSubjects();

        // Set the appropriate subject list of semester based on the semester name
        List<SemSubject> SubjectsForSemester = getSubjectsForSemester(semester);

        // Create the adapter and set it to the ListView
        SemSubjectAdapter adapter = new SemSubjectAdapter(this, SubjectsForSemester);
        listViewSemester.setAdapter(adapter);

    }

    // Get the list of subjects for the given semester
    private List<SemSubject> getSubjectsForSemester(String semester) {
        List<SemSubject> subjects = SemesterSubjects.get(semester);
        return (subjects != null) ? subjects : new ArrayList<>();
    }


    private void initializeSemesterSubjects() {
        // Initialize the semesters subjects
        SemesterSubjects = new HashMap<>();
        SemesterSubjects.put(SemesterConstants.SEMESTER_ONE, getSemOneSubjects());
        SemesterSubjects.put(SemesterConstants.SEMESTER_TWO, getSemTwoSubjects());
        SemesterSubjects.put(SemesterConstants.SEMESTER_THREE, getSemThreeSubjects());
        SemesterSubjects.put(SemesterConstants.SEMESTER_FOUR, getSemFourSubjects());
    }


    // Get the list of subjects for semester 1
    private List<SemSubject> getSemOneSubjects() {
        List<SemSubject> semOneSubjects = new ArrayList<>();
        semOneSubjects.add(new SemSubject(getString(R.string.html5), SubjectConstants.HTML5));
        semOneSubjects.add(new SemSubject(getString(R.string.elementary_c), SubjectConstants.ELEMENTARY_C));
        semOneSubjects.add(new SemSubject(getString(R.string.programming_principles), SubjectConstants.PROGRAMMING_PRINCIPLES));
        semOneSubjects.add(new SemSubject(getString(R.string.xml), SubjectConstants.XML));
        semOneSubjects.add(new SemSubject(getString(R.string.oop_concept), SubjectConstants.OOP_CONCEPT));
        semOneSubjects.add(new SemSubject(getString(R.string.sql_server), SubjectConstants.SQL_SERVER));
        semOneSubjects.add(new SemSubject(getString(R.string.ui_ux), SubjectConstants.UI_UX));
        semOneSubjects.add(new SemSubject(getString(R.string.google_apps), SubjectConstants.GOOGLE_APPS));

        return semOneSubjects;
    }

    // Get the list of subjects for semester 2
    private List<SemSubject> getSemTwoSubjects() {
        List<SemSubject> semTwoSubjects = new ArrayList<>();
        semTwoSubjects.add(new SemSubject(getString(R.string.java), SubjectConstants.JAVA));
        semTwoSubjects.add(new SemSubject(getString(R.string.c_sharp), SubjectConstants.CSHARP));
        semTwoSubjects.add(new SemSubject(getString(R.string.linux_read_hat), SubjectConstants.RED_HAT));
        semTwoSubjects.add(new SemSubject(getString(R.string.iot), SubjectConstants.IOT));
        semTwoSubjects.add(new SemSubject(getString(R.string.seo), SubjectConstants.SEO));
        semTwoSubjects.add(new SemSubject(getString(R.string.oop_in_java), SubjectConstants.OOP_IN_JAVA));
        semTwoSubjects.add(new SemSubject(getString(R.string.online_ad), SubjectConstants.ONLINE_AD));

        return semTwoSubjects;
    }

    // Get the list of subjects for semester 3
    private List<SemSubject> getSemThreeSubjects() {
        List<SemSubject> semThreeSubjects = new ArrayList<>();
        semThreeSubjects.add(new SemSubject(getString(R.string.agile), SubjectConstants.AGILE));
        semThreeSubjects.add(new SemSubject(getString(R.string.dev_app_java), SubjectConstants.DEV_APP_JAVA));
        semThreeSubjects.add(new SemSubject(getString(R.string.fund_java_enterprise), SubjectConstants.FUND_JAVA_ENTERPRISE));
        semThreeSubjects.add(new SemSubject(getString(R.string.web_component), SubjectConstants.WEB_COMPONENT));

        return semThreeSubjects;
    }

    // Get the list of subjects for semester 4
    private List<SemSubject> getSemFourSubjects() {
        List<SemSubject> semFourSubjects = new ArrayList<>();
        semFourSubjects.add(new SemSubject(getString(R.string.php), SubjectConstants.PHP));
        semFourSubjects.add(new SemSubject(getString(R.string.apache), SubjectConstants.APACHE));
        semFourSubjects.add(new SemSubject(getString(R.string.mysql), SubjectConstants.MYSQL));
        semFourSubjects.add(new SemSubject(getString(R.string.ajax_java), SubjectConstants.AJAX_JAVA));
        semFourSubjects.add(new SemSubject(getString(R.string.android_app), SubjectConstants.ANDROID_APP));
        semFourSubjects.add(new SemSubject(getString(R.string.test_android_app), SubjectConstants.TESTING_ANDROID));
        semFourSubjects.add(new SemSubject(getString(R.string.java_web_services), SubjectConstants.JAVA_WEB_SERVICES));
        semFourSubjects.add(new SemSubject(getString(R.string.cloud_platform_service), SubjectConstants.CLOUD_PLATFORM));
        semFourSubjects.add(new SemSubject(getString(R.string.EJB), SubjectConstants.EJB));
        semFourSubjects.add(new SemSubject(getString(R.string.software_engineering), SubjectConstants.SOFT_ENG));
        semFourSubjects.add(new SemSubject(getString(R.string.software_project), SubjectConstants.MS_PROJECT));
        return semFourSubjects;
    }

}