package com.nongratis.timetracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.entities.TimeSegment;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {
    private List<TimeSegment> timeSegments;

    public TimelineAdapter(List<TimeSegment> timeSegments) {
        this.timeSegments = timeSegments;
    }

    public void updateTimeSegments(List<TimeSegment> newSegments) {
        timeSegments = newSegments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline_hour, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        TimeSegment segment = timeSegments.get(position);
        holder.bind(segment);
    }

    @Override
    public int getItemCount() {
        return timeSegments.size();
    }

    static class TimelineViewHolder extends RecyclerView.ViewHolder {
        private TextView hourTextView;
        private View timelineSegment;

        public TimelineViewHolder(@NonNull View itemView) {
            super(itemView);
            hourTextView = itemView.findViewById(R.id.hourTextView);
            timelineSegment = itemView.findViewById(R.id.timelineSegment);
        }

        public void bind(TimeSegment segment) {
            // Adjust the hourTextView and timelineSegment based on the segment data
            hourTextView.setText(formatTime(segment.getStartTime()));
            // Adjust timelineSegment view properties as needed
        }

        private String formatTime(long timeInMillis) {
            // Placeholder for actual time formatting logic
            return "00:00";
        }
    }
}
