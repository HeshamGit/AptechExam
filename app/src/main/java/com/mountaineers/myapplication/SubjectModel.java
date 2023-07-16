package com.mountaineers.myapplication;

public class SubjectModel {
    private final String displayText;
    private final String value;

    public SubjectModel(String displayText, String value) {
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

