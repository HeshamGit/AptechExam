package com.mountaineers.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.mountaineers.myapplication.activities.SampleExamActivity;
import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.models.SemSubject;
import com.mountaineers.myapplication.utilities.ActivityUtils;

import java.util.List;


public class SemSubjectAdapter extends BaseAdapter {

    private final Context context;
    private final List<SemSubject> semSubjects;

    public SemSubjectAdapter(Context context, List<SemSubject> semSubjects) {
        this.context = context;
        this.semSubjects = semSubjects;
    }

    @Override
    public int getCount() {
        return semSubjects.size();
    }

    @Override
    public Object getItem(int position) {
        return semSubjects.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_exam, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.btnSubject = convertView.findViewById(R.id.btnSample);
            convertView.setTag(viewHolder);
        } else {
            // If convertView is not null, reuse the existing ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the subject at the current position
        SemSubject subject = semSubjects.get(position);

        // Set the subject name on the button
        viewHolder.btnSubject.setText(subject.getDisplayText());

        // Set click listener for the button
        viewHolder.btnSubject.setOnClickListener(v -> {

            // Start the SampleExamActivity for the selected subject
            ActivityUtils.startListActivity(context, SampleExamActivity.class, "subject", subject.getValue());
        });

        return convertView;
    }

    // ViewHolder pattern to improve ListView performance
    private static class ViewHolder {
        Button btnSubject;
    }
}
