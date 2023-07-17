package com.mountaineers.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamListActivity extends AppCompatActivity {

    private Map<String, Integer> subjectTitles;
    private Map<String, List<SampleExam>> subjectSampleExams;

    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        ListView listViewExams = findViewById(R.id.listViewExams);

        // Retrieve the subject name from the intent
        subject = getIntent().getStringExtra("subject");

        // Initialize subject titles and sample exams mappings
        initializeSubjectTitles();
        initializeSubjectSampleExams();

        // Set the title based on the subject
        TextView examTitle = findViewById(R.id.examTitle);
        examTitle.setText(getSubjectTitle(subject));

        // Set the appropriate sample exams list based on the subject
        List<SampleExam> sampleExams = getSampleExams(subject);

        // Create the adapter and set it to the ListView
        ExamListAdapter adapter = new ExamListAdapter(this, sampleExams);

        listViewExams.setAdapter(adapter);
    }

    private void initializeSubjectTitles() {
        // Initialize the subject titles
        subjectTitles = new HashMap<>();
        // semester one
        subjectTitles.put("html5", R.string.html5_exam);
        subjectTitles.put("elementary_c", R.string.elementary_c_exam);
        subjectTitles.put("programming_principles", R.string.programming_principles_exam);
        subjectTitles.put("xml", R.string.xml_exam);
        subjectTitles.put("oop_concept", R.string.oop_concept_exam);
        subjectTitles.put("sql_server", R.string.sql_server_exam);
        subjectTitles.put("ui_ux", R.string.ui_ux_exam);
        subjectTitles.put("google_apps", R.string.google_apps_exam);


        // semester two
        subjectTitles.put("java", R.string.java_exam);
        subjectTitles.put("cSharp", R.string.csharp_exam);
        subjectTitles.put("iot", R.string.iot_exam);
        subjectTitles.put("red_hat", R.string.read_hat_exam);
        subjectTitles.put("seo", R.string.seo_exam);
        subjectTitles.put("oop_in_java", R.string.oop_in_java_exam);
        subjectTitles.put("online_ad", R.string.online_ad_exam);

        // semester three
        subjectTitles.put("agile", R.string.agile_exam);
        subjectTitles.put("dev_app_java", R.string.dev_app_java_exam);
        subjectTitles.put("fund_java_enterprise", R.string.fund_java_enterprise_exam);
        subjectTitles.put("web_component", R.string.web_component_exam);

        // semester four
        subjectTitles.put("ms_project", R.string.software_project_exam);
        subjectTitles.put("soft_eng", R.string.software_engineering_exam);
        subjectTitles.put("ejb", R.string.ejb_exam);
        subjectTitles.put("cloud_platform", R.string.cloud_platform_service_exam);
        subjectTitles.put("java_web_services", R.string.java_web_services_exam);
        subjectTitles.put("testing_android", R.string.test_android_app_exam);
        subjectTitles.put("android_app", R.string.android_app_exam);
        subjectTitles.put("ajax_Java", R.string.ajax_java_exam);
        subjectTitles.put("mysql", R.string.mysql_exam);
        subjectTitles.put("apache", R.string.apache_exam);
        subjectTitles.put("php", R.string.php_exam);
    }

    private void initializeSubjectSampleExams() {
        // Initialize the subject sample exams
        subjectSampleExams = new HashMap<>();
        // semester one
        subjectSampleExams.put("html5", getHTML5SampleExams());
        subjectSampleExams.put("elementary_c", getElementaryCSampleExams());
        subjectSampleExams.put("programming_principles", getProgrammingPrinciplesSampleExams());
        subjectSampleExams.put("xml", getXMLSampleExams());
        subjectSampleExams.put("oop_concept", getOOPConceptSampleExams());
        subjectSampleExams.put("sql_server", getSQLServerSampleExams());
        subjectSampleExams.put("ui_ux", getUIUXSampleExams());
        subjectSampleExams.put("google_apps", getGoogleAppsSampleExams());

        // semester two
        subjectSampleExams.put("java", getJavaSampleExams());
        subjectSampleExams.put("cSharp", getCsharpSampleExams());
        subjectSampleExams.put("iot", getIOTSampleExams());
        subjectSampleExams.put("red_hat", getRedHatSampleExams());
        subjectSampleExams.put("seo", getSEOSampleExams());
        subjectSampleExams.put("oop_in_java", getOOPJavaSampleExams());
        subjectSampleExams.put("online_ad", getOnlineAdSampleExams());

        // semester three
        subjectSampleExams.put("agile", getAgileSampleExams());
        subjectSampleExams.put("dev_app_java", getDevAppJavaSampleExams());
        subjectSampleExams.put("fund_java_enterprise", getFundJavaEnterpriseSampleExams());
        subjectSampleExams.put("web_component", getWebComponentSampleExams());


        // semester four
        subjectSampleExams.put("ms_project", getMSPProjectSampleExams());
        subjectSampleExams.put("soft_eng", getSoftwareEngineeringSampleExams());
        subjectSampleExams.put("ejb", getEJBSampleExams());
        subjectSampleExams.put("cloud_platform", getCloudPlatformSampleExams());
        subjectSampleExams.put("java_web_services", getJavaWebServicesSampleExams());
        subjectSampleExams.put("testing_android", getTestingAndroidSampleExams());
        subjectSampleExams.put("android_app", getAndroidAppSampleExams());
        subjectSampleExams.put("ajax_Java", getAjaxJavaSampleExams());
        subjectSampleExams.put("mysql", getMySQLSampleExams());
        subjectSampleExams.put("apache", getApacheSampleExams());
        subjectSampleExams.put("php", getPHPSampleExams());
    }

    // to get title of the list view
    private String getSubjectTitle(String subject) {
        Integer titleResId = subjectTitles.get(subject);
        return (titleResId != null) ? getString(titleResId) : "";
    }

    // to get sample exams list
    private List<SampleExam> getSampleExams(String subject) {
        List<SampleExam> exams = subjectSampleExams.get(subject);
        return (exams != null) ? exams : new ArrayList<>();
    }

    // Helper method to add sample exams to the list
    private void addSampleExams(List<SampleExam> sampleExams, int count) {
        for (int i = 1; i <= count; i++) {
            sampleExams.add(new SampleExam(ExamWeb.class, subject, "Sample" + " " + i));
        }
    }

    // semester one exam
    private List<SampleExam> getHTML5SampleExams() {
        List<SampleExam> HTML5Exams = new ArrayList<>();
        addSampleExams(HTML5Exams, 14);
        HTML5Exams.add(new SampleExam(ViewPagerActivity.class, "HTML5Img", "Sample 15"));
        return HTML5Exams;
    }

    private List<SampleExam> getElementaryCSampleExams() {
        List<SampleExam> elementaryCExams = new ArrayList<>();
        addSampleExams(elementaryCExams, 8);
        elementaryCExams.add(new SampleExam(ViewPagerActivity.class, "elementaryCImg", "Sample 9"));
        return elementaryCExams;
    }

    private List<SampleExam> getProgrammingPrinciplesSampleExams() {
        List<SampleExam> ProgrammingPrinciplesExams = new ArrayList<>();
        addSampleExams(ProgrammingPrinciplesExams, 5);
        return ProgrammingPrinciplesExams;
    }

    private List<SampleExam> getXMLSampleExams() {
        List<SampleExam> XMLExams = new ArrayList<>();
        addSampleExams(XMLExams, 6);
        XMLExams.add(new SampleExam(ViewPagerActivity.class, "XMLImg", "Sample 7"));
        return XMLExams;
    }

    private List<SampleExam> getOOPConceptSampleExams() {
        List<SampleExam> OOPConceptExams = new ArrayList<>();
        addSampleExams(OOPConceptExams, 3);
        return OOPConceptExams;
    }

    private List<SampleExam> getSQLServerSampleExams() {
        List<SampleExam> SQLServerExams = new ArrayList<>();
        addSampleExams(SQLServerExams, 11);
        SQLServerExams.add(new SampleExam(ViewPagerActivity.class, "SQLServerImg", "Sample 12"));
        return SQLServerExams;
    }

    private List<SampleExam> getUIUXSampleExams() {
        List<SampleExam> UIUXExams = new ArrayList<>();
        addSampleExams(UIUXExams, 9);
        return UIUXExams;
    }

    private List<SampleExam> getGoogleAppsSampleExams() {
        List<SampleExam> googleAppsExams = new ArrayList<>();
        addSampleExams(googleAppsExams, 3);
//        googleAppsExams.add(new SampleExam(ViewPagerActivity.class, "googleAppsImg", "Sample 4"));
        return googleAppsExams;
    }

    // semester two exam
    // Generate sample exams for Java subject
    private List<SampleExam> getJavaSampleExams() {
        List<SampleExam> javaExams = new ArrayList<>();
        addSampleExams(javaExams, 5);
        javaExams.add(new SampleExam(ViewPagerActivity.class, "javaImg", "Sample 6"));
        return javaExams;
    }

    // Generate sample exams for C# subject
    private List<SampleExam> getCsharpSampleExams() {
        List<SampleExam> CsharpExams = new ArrayList<>();
        addSampleExams(CsharpExams, 5);
        CsharpExams.add(new SampleExam(ViewPagerActivity.class, "cSharpImg", "Sample 6"));
        CsharpExams.add(new SampleExam(ViewPagerActivity.class, "cSharpPdf", "Sample 7"));
        return CsharpExams;
    }

    private List<SampleExam> getRedHatSampleExams() {
        List<SampleExam> redHatExams = new ArrayList<>();
        addSampleExams(redHatExams, 11);
        redHatExams.add(new SampleExam(ViewPagerActivity.class, "linuxImg", "Sample 12"));
        redHatExams.add(new SampleExam(ViewPagerActivity.class, "linuxPdf", "Sample 13"));
        return redHatExams;
    }

    private List<SampleExam> getIOTSampleExams() {
        List<SampleExam> iotExams = new ArrayList<>();
        iotExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        return iotExams;
    }

    private List<SampleExam> getSEOSampleExams() {
        List<SampleExam> iotExams = new ArrayList<>();
        iotExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        iotExams.add(new SampleExam(ViewPagerActivity.class, "SeoPdf", "Sample 2"));
        return iotExams;
    }

    private List<SampleExam> getOnlineAdSampleExams() {
        List<SampleExam> onlineAdExams = new ArrayList<>();
        onlineAdExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        onlineAdExams.add(new SampleExam(ExamWeb.class, subject, "Sample 2"));
        return onlineAdExams;
    }

    private List<SampleExam> getOOPJavaSampleExams() {
        List<SampleExam> oopJavaExams = new ArrayList<>();

        addSampleExams(oopJavaExams, 5);
        oopJavaExams.add(new SampleExam(ViewPagerActivity.class, "oopJavaImg", "Sample 6"));
        return oopJavaExams;
    }

    // semester three exam
    private List<SampleExam> getWebComponentSampleExams() {
        List<SampleExam> webComponentExams = new ArrayList<>();
        webComponentExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        webComponentExams.add(new SampleExam(ViewPagerActivity.class, "WebComponentPdf", "Sample 2"));
        return webComponentExams;
    }

    private List<SampleExam> getFundJavaEnterpriseSampleExams() {
        List<SampleExam> fundJavaEnterpriseExams = new ArrayList<>();
        fundJavaEnterpriseExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        fundJavaEnterpriseExams.add(new SampleExam(ExamWeb.class, subject, "Sample 2"));
        fundJavaEnterpriseExams.add(new SampleExam(ViewPagerActivity.class, "FundJavaEnterprisePdf", "Sample 3"));
        return fundJavaEnterpriseExams;
    }

    private List<SampleExam> getDevAppJavaSampleExams() {
        List<SampleExam> devAppJavaExams = new ArrayList<>();
        devAppJavaExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        devAppJavaExams.add(new SampleExam(ExamWeb.class, subject, "Sample 2"));
        devAppJavaExams.add(new SampleExam(ViewPagerActivity.class, "DevAppJavaPdf", "Sample 3"));
        return devAppJavaExams;
    }

    private List<SampleExam> getAgileSampleExams() {
        List<SampleExam> agileExams = new ArrayList<>();
        agileExams.add(new SampleExam(ExamWeb.class, subject, "Sample 1"));
        agileExams.add(new SampleExam(ViewPagerActivity.class, "AgilePdf", "Sample 2"));
        return agileExams;
    }

    // semester four exam
    private List<SampleExam> getMSPProjectSampleExams() {
        List<SampleExam> msProjectExams = new ArrayList<>();
        addSampleExams(msProjectExams, 9);
        return msProjectExams;
    }

    private List<SampleExam> getSoftwareEngineeringSampleExams() {
        List<SampleExam> softwareEngineeringExams = new ArrayList<>();
        addSampleExams(softwareEngineeringExams, 10);
        return softwareEngineeringExams;
    }

    private List<SampleExam> getEJBSampleExams() {
        List<SampleExam> ejbExams = new ArrayList<>();
        ejbExams.add(new SampleExam(ExamWeb.class, "ejb", "Sample 1"));
        return ejbExams;
    }

    private List<SampleExam> getCloudPlatformSampleExams() {
        List<SampleExam> cloudPlatformExams = new ArrayList<>();
        addSampleExams(cloudPlatformExams, 5);
        return cloudPlatformExams;
    }

    private List<SampleExam> getJavaWebServicesSampleExams() {
        List<SampleExam> javaWebServicesExams = new ArrayList<>();
        addSampleExams(javaWebServicesExams, 3);
        return javaWebServicesExams;
    }

    private List<SampleExam> getTestingAndroidSampleExams() {
        List<SampleExam> testingAndroidExams = new ArrayList<>();
        addSampleExams(testingAndroidExams, 2);
        return testingAndroidExams;
    }

    private List<SampleExam> getAndroidAppSampleExams() {
        List<SampleExam> androidAppExams = new ArrayList<>();
        addSampleExams(androidAppExams, 4);
        androidAppExams.add(new SampleExam(ViewPagerActivity.class, "androidAppImg", "Sample 5"));
        return androidAppExams;
    }

    private List<SampleExam> getAjaxJavaSampleExams() {
        List<SampleExam> ajaxJavaExams = new ArrayList<>();
        addSampleExams(ajaxJavaExams, 3);
        return ajaxJavaExams;
    }

    private List<SampleExam> getMySQLSampleExams() {
        List<SampleExam> mySQLExams = new ArrayList<>();
        addSampleExams(mySQLExams, 13);
        return mySQLExams;
    }

    private List<SampleExam> getApacheSampleExams() {
        List<SampleExam> apacheExams = new ArrayList<>();
        addSampleExams(apacheExams, 12);
        apacheExams.add(new SampleExam(ViewPagerActivity.class, "apacheImg", "Sample 13"));
        return apacheExams;
    }

    private List<SampleExam> getPHPSampleExams() {
        List<SampleExam> phpExams = new ArrayList<>();
        addSampleExams(phpExams, 4);
        return phpExams;
    }

}