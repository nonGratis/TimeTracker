package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.button.MaterialButton;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.PieChartManager;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;
import com.nongratis.timetracker.viewmodel.TimeAnalysisViewModel;

import java.util.concurrent.TimeUnit;

public class TimeAnalysisFragment extends Fragment {
    private TimeAnalysisViewModel timeAnalysisViewModel;
    private PieChartManager pieChartWorkflowManager;
    private PieChartManager pieChartProjectManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_analysis, container, false);

        PieChart pieChartWorkflow = view.findViewById(R.id.pieChartWorkflow);
        PieChart pieChartProject = view.findViewById(R.id.pieChartProject);
        MaterialButton btnDay = view.findViewById(R.id.btnDay);
        MaterialButton btnWeek = view.findViewById(R.id.btnWeek);
        MaterialButton btnMonth = view.findViewById(R.id.btnMonth);

        TaskRepository taskRepository = new TaskRepository(requireActivity().getApplication());
        TaskViewModelFactory factory = new TaskViewModelFactory(requireActivity().getApplication(), taskRepository);
        TaskViewModel taskViewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);
        timeAnalysisViewModel = new TimeAnalysisViewModel(taskViewModel);

        pieChartWorkflowManager = new PieChartManager(pieChartWorkflow, getContext());
        pieChartProjectManager = new PieChartManager(pieChartProject, getContext());

        btnDay.setOnClickListener(v -> timeAnalysisViewModel.loadData("day"));
        btnWeek.setOnClickListener(v -> timeAnalysisViewModel.loadData("week"));
        btnMonth.setOnClickListener(v -> timeAnalysisViewModel.loadData("month"));

        timeAnalysisViewModel.loadData("day"); // Default load for the day

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeAnalysisViewModel.getWorkflowEntriesLiveData().observe(getViewLifecycleOwner(), workflowEntriesMap -> {
            pieChartWorkflowManager.updatePieChart(workflowEntriesMap);
        });

        timeAnalysisViewModel.getProjectEntriesLiveData().observe(getViewLifecycleOwner(), projectEntriesMap -> {
            pieChartProjectManager.updatePieChart(projectEntriesMap);
        });

        timeAnalysisViewModel.getTotalTimeLiveData().observe(getViewLifecycleOwner(), totalTime -> {
            TextView totalTimeTextView = getView().findViewById(R.id.totalTime);
            totalTimeTextView.setText(String.format("Total Time: %s", formatDuration(totalTime)));
        });
    }

    private String formatDuration(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}