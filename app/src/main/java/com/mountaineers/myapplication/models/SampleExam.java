package com.mountaineers.myapplication.models;

public class SampleExam {
    private final Class<?> activityClass;
    private final String subject;
    private final String sampleName;

    public SampleExam(Class<?> activityClass, String caller, String buttonText) {
        this.activityClass = activityClass;
        this.subject = caller;
        this.sampleName = buttonText;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }

    public String getSubject() {
        return subject;
    }

    public String getSampleName() {
        return sampleName;
    }
}