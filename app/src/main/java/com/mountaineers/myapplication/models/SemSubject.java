package com.mountaineers.myapplication.models;

public class SemSubject {
    private final String displayText;
    private final String value;

    public SemSubject(String displayText, String value) {
        this.displayText = displayText;
        this.value = value;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getValue() {
        return value;
    }
}

