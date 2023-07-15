package com.mountaineers.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class ExamListAdapter extends BaseAdapter {

    private final Context context;
    private final List<SampleExam> sampleExams;

    public ExamListAdapter(Context context, List<SampleExam> sampleExams) {
        this.context = context;
        this.sampleExams = sampleExams;
    }

    @Override
    public int getCount() {
        return sampleExams.size();
    }

    @Override
    public Object getItem(int position) {
        return sampleExams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Inflate the list item layout if convertView is null
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_exam, parent, false);
        }

        // Get the sample exam at the current position
        SampleExam sampleExam = sampleExams.get(position);

        // Set the sample name on the button
        Button btnSample = convertView.findViewById(R.id.btnSample);
        btnSample.setText(sampleExam.getSampleName());

        // Set click listener for the button
        btnSample.setOnClickListener(v -> {

            // Handle the button click event for the sample exam
            String subject = sampleExam.getSubject();
            String sample = sampleExam.getSampleName();
            Class<?> activityClass = sampleExam.getActivityClass();

            // Invoke the method to handle the sample exam click event
            handleSampleExamClick(subject, sample, activityClass);
        });

        return convertView;
    }

    private void handleSampleExamClick(String subject, String sample, Class<?> activityClass) {
        // Implement the logic to handle the sample exam click event

        // Convert the sample name to lowercase without spaces
        String sampleName = sample.replaceAll("\\s", "").toLowerCase();

        // Launch the appropriate activity based on subject and sample
        ActivityUtils.startExamActivity(context, activityClass, subject, sampleName);
    }
}

