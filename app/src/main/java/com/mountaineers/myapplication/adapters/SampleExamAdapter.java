package com.mountaineers.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.mountaineers.myapplication.R;
import com.mountaineers.myapplication.activities.InteractivePortalActivity;
import com.mountaineers.myapplication.activities.WebActivity;
import com.mountaineers.myapplication.models.SampleExam;

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

        // For HTML exams, provide the choice between Interactive Portal, Practice Quiz, or Classic Mode
        if (activityClass == WebActivity.class) {
            CharSequence[] options = new CharSequence[]{
                    "🎓 Official Aptech Exam Portal (Timed)",
                    "⚡ Instant Practice (1-Question-at-a-Time)",
                    "📖 Classic Study Mode (Answers Shown)"
            };

            new AlertDialog.Builder(context)
                    .setTitle(sampleExam.getSampleName() + " - Select Mode")
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            Intent intent = new Intent(context, InteractivePortalActivity.class);
                            intent.putExtra(InteractivePortalActivity.EXTRA_SUBJECT, subject);
                            intent.putExtra(InteractivePortalActivity.EXTRA_SAMPLE, sample);
                            intent.putExtra(InteractivePortalActivity.EXTRA_MODE, "portal");
                            context.startActivity(intent);
                        } else if (which == 1) {
                            Intent intent = new Intent(context, InteractivePortalActivity.class);
                            intent.putExtra(InteractivePortalActivity.EXTRA_SUBJECT, subject);
                            intent.putExtra(InteractivePortalActivity.EXTRA_SAMPLE, sample);
                            intent.putExtra(InteractivePortalActivity.EXTRA_MODE, "practice");
                            context.startActivity(intent);
                        } else {
                            startExamActivity(context, activityClass, subject, sample);
                        }
                    })
                    .show();
        } else {
            // Launch image gallery or direct activity
            startExamActivity(context, activityClass, subject, sample);
        }
    }

    private void startExamActivity(Context context, Class<?> activityClass, String caller, String sample) {
        // to go activity
        Intent intent = new Intent(context, activityClass);
        //to show the caller activity and sample on the activity
        intent.putExtra("caller", caller);
        intent.putExtra(caller, sample);
        context.startActivity(intent);
    }

    static class ViewHolder {
        final Button btnSample;

        ViewHolder(View view) {
            btnSample = view.findViewById(R.id.btnSample);
        }
    }
}
