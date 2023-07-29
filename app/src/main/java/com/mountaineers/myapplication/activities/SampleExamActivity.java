package com.mountaineers.myapplication.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mountaineers.myapplication.adapters.SampleExamAdapter;
import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.models.SampleExam;
import com.mountaineers.myapplication.utilities.ActivityUtils;
import com.mountaineers.myapplication.utilities.SubjectConstants;

import java.util.ArrayList;
import java.util.List;

public class SampleExamActivity extends AppCompatActivity {
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_exam);

        ListView listViewExams = findViewById(R.id.listViewExams);

        // Retrieve the subject name from the intent
        subject = getIntent().getStringExtra(SubjectConstants.SUBJECT);

        // Set the title based on the subject
        TextView examTitle = findViewById(R.id.examTitle);
        examTitle.setText(ActivityUtils.getTitle(this, subject));

        // Create the adapter and set it to the ListView and pass to it the sample exams list
        SampleExamAdapter adapter = new SampleExamAdapter(this, getSubjectSampleExams(subject));

        listViewExams.setAdapter(adapter);
    }

    private List<SampleExam> getSubjectSampleExams(String subject) {

        // get the subject sample exams for the specific subject using a switch statement
        switch (subject) {
            case SubjectConstants.HTML5: return getHTML5SampleExams();
            case SubjectConstants.ELEMENTARY_C: return getElementaryCSampleExams();
            case SubjectConstants.PROGRAMMING_PRINCIPLES: return getProgrammingPrinciplesSampleExams();
            case SubjectConstants.XML: return getXMLSampleExams();
            case SubjectConstants.OOP_CONCEPT: return getOOPConceptSampleExams();
            case SubjectConstants.SQL_SERVER: return getSQLServerSampleExams();
            case SubjectConstants.UI_UX: return getUIUXSampleExams();
            case SubjectConstants.GOOGLE_APPS: return getGoogleAppsSampleExams();
            case SubjectConstants.JAVA: return getJavaSampleExams();
            case SubjectConstants.CSHARP: return getCsharpSampleExams();
            case SubjectConstants.IOT: return getIOTSampleExams();
            case SubjectConstants.RED_HAT: return getRedHatSampleExams();
            case SubjectConstants.SEO: return getSEOSampleExams();
            case SubjectConstants.OOP_IN_JAVA: return getOOPJavaSampleExams();
            case SubjectConstants.ONLINE_AD: return getOnlineAdSampleExams();
            case SubjectConstants.AGILE: return getAgileSampleExams();
            case SubjectConstants.DEV_APP_JAVA: return getDevAppJavaSampleExams();
            case SubjectConstants.FUND_JAVA_ENTERPRISE: return getFundJavaEnterpriseSampleExams();
            case SubjectConstants.WEB_COMPONENT: return getWebComponentSampleExams();
            case SubjectConstants.MS_PROJECT: return getMSPProjectSampleExams();
            case SubjectConstants.SOFT_ENG: return getSoftwareEngineeringSampleExams();
            case SubjectConstants.EJB: return getEJBSampleExams();
            case SubjectConstants.CLOUD_PLATFORM: return getCloudPlatformSampleExams();
            case SubjectConstants.JAVA_WEB_SERVICES: return getJavaWebServicesSampleExams();
            case SubjectConstants.TESTING_ANDROID: return getTestingAndroidSampleExams();
            case SubjectConstants.ANDROID_APP: return getAndroidAppSampleExams();
            case SubjectConstants.AJAX_JAVA: return getAjaxJavaSampleExams();
            case SubjectConstants.MYSQL: return getMySQLSampleExams();
            case SubjectConstants.APACHE: return getApacheSampleExams();
            case SubjectConstants.PHP: return getPHPSampleExams();
            default: return new ArrayList<>();
        }
    }

    // Helper method to add sample exams to the list
    private void addSampleExams(List<SampleExam> sampleExams, int count) {
        for (int i = 1; i <= count; i++) {
            sampleExams.add(new SampleExam(WebActivity.class, subject, "Sample" + " " + i));
        }
    }

    // semester one exam
    private List<SampleExam> getHTML5SampleExams() {
        List<SampleExam> HTML5Exams = new ArrayList<>();
        addSampleExams(HTML5Exams, 14);
        HTML5Exams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.html5_images), "Sample 15"));
        return HTML5Exams;
    }

    private List<SampleExam> getElementaryCSampleExams() {
        List<SampleExam> elementaryCExams = new ArrayList<>();
        addSampleExams(elementaryCExams, 8);
        elementaryCExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.elementary_c_images), "Sample 9"));
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
        XMLExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.xml_images), "Sample 7"));
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
        SQLServerExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.sql_server_images), "Sample 12"));
        return SQLServerExams;
    }

    private List<SampleExam> getUIUXSampleExams() {
        List<SampleExam> UIUXExams = new ArrayList<>();
        addSampleExams(UIUXExams, 9);
        return UIUXExams;
    }

    private List<SampleExam> getGoogleAppsSampleExams() {
        List<SampleExam> googleAppsExams = new ArrayList<>();
        addSampleExams(googleAppsExams, 4);
        return googleAppsExams;
    }

    // semester two exam
    // Generate sample exams for Java subject
    private List<SampleExam> getJavaSampleExams() {
        List<SampleExam> javaExams = new ArrayList<>();
        addSampleExams(javaExams, 5);
        javaExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.java_images), "Sample 6"));
        return javaExams;
    }

    // Generate sample exams for C# subject
    private List<SampleExam> getCsharpSampleExams() {
        List<SampleExam> CsharpExams = new ArrayList<>();
        addSampleExams(CsharpExams, 5);
        CsharpExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.csharp_images), "Sample 6"));
        CsharpExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.csharp_pdf), "Sample 7"));
        return CsharpExams;
    }

    private List<SampleExam> getRedHatSampleExams() {
        List<SampleExam> redHatExams = new ArrayList<>();
        addSampleExams(redHatExams, 11);
        redHatExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.linux_images), "Sample 12"));
        redHatExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.linux_pdf), "Sample 13"));
        return redHatExams;
    }

    private List<SampleExam> getIOTSampleExams() {
        List<SampleExam> iotExams = new ArrayList<>();
        iotExams.add(new SampleExam(WebActivity.class, subject, "Sample 1"));
        return iotExams;
    }

    private List<SampleExam> getSEOSampleExams() {
        List<SampleExam> iotExams = new ArrayList<>();
        iotExams.add(new SampleExam(WebActivity.class, subject, "Sample 1"));
        iotExams.add(new SampleExam(WebActivity.class, subject, "Sample 2"));
        iotExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.seo_pdf), "Sample 3"));
        return iotExams;
    }

    private List<SampleExam> getOnlineAdSampleExams() {
        List<SampleExam> onlineAdExams = new ArrayList<>();
        onlineAdExams.add(new SampleExam(WebActivity.class, subject, "Sample 1"));
        onlineAdExams.add(new SampleExam(WebActivity.class, subject, "Sample 2"));
        return onlineAdExams;
    }

    private List<SampleExam> getOOPJavaSampleExams() {
        List<SampleExam> oopJavaExams = new ArrayList<>();
        addSampleExams(oopJavaExams, 6);
        return oopJavaExams;
    }

    // semester three exam
    private List<SampleExam> getWebComponentSampleExams() {
        List<SampleExam> webComponentExams = new ArrayList<>();
        addSampleExams(webComponentExams, 3);
        webComponentExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.web_component_pdf), "Sample 3"));
        return webComponentExams;
    }

    private List<SampleExam> getFundJavaEnterpriseSampleExams() {
        List<SampleExam> fundJavaEnterpriseExams = new ArrayList<>();
        fundJavaEnterpriseExams.add(new SampleExam(WebActivity.class, subject, "Sample 1"));
        fundJavaEnterpriseExams.add(new SampleExam(WebActivity.class, subject, "Sample 2"));
        fundJavaEnterpriseExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.fund_java_enterprise_pdf), "Sample 3"));
        return fundJavaEnterpriseExams;
    }

    private List<SampleExam> getDevAppJavaSampleExams() {
        List<SampleExam> devAppJavaExams = new ArrayList<>();
        addSampleExams(devAppJavaExams, 3);
        devAppJavaExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.dev_app_java_pdf), "Sample 3"));
        return devAppJavaExams;
    }

    private List<SampleExam> getAgileSampleExams() {
        List<SampleExam> agileExams = new ArrayList<>();
        agileExams.add(new SampleExam(WebActivity.class, subject, "Sample 1"));
        agileExams.add(new SampleExam(WebActivity.class, subject, "Sample 2"));
        agileExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.agile_pdf), "Sample 3"));
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
        ejbExams.add(new SampleExam(WebActivity.class, subject, "Sample 1"));
        ejbExams.add(new SampleExam(WebActivity.class, subject, "Sample 2"));
        return ejbExams;
    }

    private List<SampleExam> getCloudPlatformSampleExams() {
        List<SampleExam> cloudPlatformExams = new ArrayList<>();
        addSampleExams(cloudPlatformExams, 5);
        return cloudPlatformExams;
    }

    private List<SampleExam> getJavaWebServicesSampleExams() {
        List<SampleExam> javaWebServicesExams = new ArrayList<>();
        addSampleExams(javaWebServicesExams, 4);
        return javaWebServicesExams;
    }

    private List<SampleExam> getTestingAndroidSampleExams() {
        List<SampleExam> testingAndroidExams = new ArrayList<>();
        addSampleExams(testingAndroidExams, 2);
        return testingAndroidExams;
    }

    private List<SampleExam> getAndroidAppSampleExams() {
        List<SampleExam> androidAppExams = new ArrayList<>();
        addSampleExams(androidAppExams, 5);
        androidAppExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.android_app_images), "Sample 5"));
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
        apacheExams.add(new SampleExam(GalleryActivity.class, String.valueOf(R.array.apache_images), "Sample 13"));
        return apacheExams;
    }

    private List<SampleExam> getPHPSampleExams() {
        List<SampleExam> phpExams = new ArrayList<>();
        addSampleExams(phpExams, 4);
        return phpExams;
    }
}