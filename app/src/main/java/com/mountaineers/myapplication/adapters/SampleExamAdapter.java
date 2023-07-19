package com.mountaineers.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.models.SampleExam;
import com.mountaineers.myapplication.utilities.ActivityUtils;

import java.util.List;

public class SampleExamAdapter extends BaseAdapter {

    private final Context context;
    private final List<SampleExam> sampleExams;
    private final LayoutInflater inflater;

    public SampleExamAdapter(Context context, List<SampleExam> sampleExams) {
        this.context = context;
        this.sampleExams = sampleExams;
        this.inflater = LayoutInflater.from(context);
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
        ViewHolder viewHolder;

        if (convertView == null) {
            // If convertView is null, inflate the layout and create a new ViewHolder
            convertView = inflater.inflate(R.layout.list_item_exam, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, reuse the existing ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the sample exam at the current position
        SampleExam sampleExam = sampleExams.get(position);

        // Set the sample name on the button
        viewHolder.btnSample.setText(sampleExam.getSampleName());

        // Set click listener for the button
        viewHolder.btnSample.setOnClickListener(v -> handleSampleExamClick(sampleExam));

        return convertView;
    }

    private void handleSampleExamClick(SampleExam sampleExam) {

        // get subject name
        String subject = sampleExam.getSubject();
        // get the sample name and Convert it to lowercase without spaces
        String sample = sampleExam.getSampleName().replaceAll("\\s", "").toLowerCase();
        // get the activity class
        Class<?> activityClass = sampleExam.getActivityClass();

        // Launch the appropriate activity based on subject and sample
        ActivityUtils.startExamActivity(context, activityClass, subject, sample);
    }

    static class ViewHolder {
        final Button btnSample;

        ViewHolder(View view) {
            btnSample = view.findViewById(R.id.btnSample);
        }
    }
}

