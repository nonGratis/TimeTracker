package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nongratis.timetracker.AppDatabaseInitializer;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.adapter.TimelineAdapter;
import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.viewmodel.DailyTimelineViewModel;
import com.nongratis.timetracker.data.repository.ITaskRepository;
import com.nongratis.timetracker.viewmodel.DailyTimelineViewModelFactory;

public class DailyTimelineFragment extends Fragment {

    private DailyTimelineViewModel viewModel;
    private RecyclerView timelineRecyclerView;
    private TimelineAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_timeline, container, false);
        timelineRecyclerView = view.findViewById(R.id.timelineRecyclerView);
        timelineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TaskDao taskDao = AppDatabaseInitializer.getDatabase().taskDao();
        ITaskRepository taskRepository = new TaskRepository(taskDao);
        DailyTimelineViewModelFactory factory = new DailyTimelineViewModelFactory(taskRepository);
        viewModel = new ViewModelProvider(this, factory).get(DailyTimelineViewModel.class);

        // Assuming dayStart and dayEnd are calculated for the current day
        long dayStart = calculateDayStart();
        long dayEnd = calculateDayEnd();

        viewModel.getTimeSegmentsForDay(dayStart, dayEnd).observe(getViewLifecycleOwner(), segments -> {
            if (adapter == null) {
                adapter = new TimelineAdapter(segments);
                timelineRecyclerView.setAdapter(adapter);
            } else {
                adapter.updateTimeSegments(segments);
            }
        });
    }

    private long calculateDayStart() {
        // Placeholder for actual day start calculation logic
        return 0;
    }

    private long calculateDayEnd() {
        // Placeholder for actual day end calculation logic
        return 0;
    }
}
