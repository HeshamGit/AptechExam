package com.mountaineers.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;


public class SemExamAdapter extends BaseAdapter {

    private final Context context;
    private final List<SubjectModel> subjectModels;

    public SemExamAdapter(Context context, List<SubjectModel> subjectModels) {
        this.context = context;
        this.subjectModels = subjectModels;
    }

    @Override
    public int getCount() {
        return subjectModels.size();
    }

    @Override
    public Object getItem(int position) {
        return subjectModels.get(position);
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
        }else {
            // If convertView is not null, reuse the existing ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the subject at the current position
        SubjectModel subject = subjectModels.get(position);

        // Set the subject name on the button
        viewHolder.btnSubject.setText(subject.getDisplayText());

        // Set click listener for the button
        viewHolder.btnSubject.setOnClickListener(v -> {

            // Start the ExamListActivity for the selected subject
            startExamListActivity(subject.getValue());
        });

        return convertView;
    }

    // ViewHolder pattern to improve ListView performance
    private static class ViewHolder {
        Button btnSubject;
    }

    // Start the ExamListActivity for the selected subject
    private void startExamListActivity(String subjectValue) {
        Intent intent = new Intent(context, ExamListActivity.class);
        intent.putExtra("subject", subjectValue);
        context.startActivity(intent);
    }
}
