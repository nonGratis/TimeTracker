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
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.ButtonManager;
import com.nongratis.timetracker.managers.PieChartManager;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeAnalysisFragment extends Fragment {
    private TaskViewModel taskViewModel;
    private PieChartManager pieChartWorkflowManager;
    private PieChartManager pieChartProjectManager;
    private ButtonManager buttonManager;

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
        taskViewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        pieChartWorkflowManager = new PieChartManager(pieChartWorkflow, getContext());
        pieChartProjectManager = new PieChartManager(pieChartProject, getContext());

        btnDay.setOnClickListener(v -> loadData("day"));
        btnWeek.setOnClickListener(v -> loadData("week"));
        btnMonth.setOnClickListener(v -> loadData("month"));

        buttonManager = new ButtonManager(btnDay, btnWeek, btnMonth, R.color.defaultStrokeColor, R.color.selectedStrokeColor);

        loadData("day"); // Default load for the day

        return view;
    }

    private void loadData(String period) {
        taskViewModel.getTasksByPeriod(period).observe(getViewLifecycleOwner(), tasks -> {
            buttonManager.setButtonStyle(period);
            Map<String, Float> workflowEntriesMap = new HashMap<>();
            Map<String, Float> projectEntriesMap = new HashMap<>();
            long totalTime = 0;
            for (Task task : tasks) {
                String workflowLabel = task.getWorkflowName();
                String projectLabel = task.getProjectName();
                if (workflowLabel == null || workflowLabel.isEmpty()) {
                    workflowLabel = "Other";
                }
                if (projectLabel == null || projectLabel.isEmpty()) {
                    projectLabel = "Other";
                }
                float workflowAccumulatedDuration = workflowEntriesMap.getOrDefault(workflowLabel, 0f);
                float projectAccumulatedDuration = projectEntriesMap.getOrDefault(projectLabel, 0f);
                workflowEntriesMap.put(workflowLabel, workflowAccumulatedDuration + task.getDuration());
                projectEntriesMap.put(projectLabel, projectAccumulatedDuration + task.getDuration());
                totalTime += task.getDuration();
            }
            pieChartWorkflowManager.updatePieChart(workflowEntriesMap);
            pieChartProjectManager.updatePieChart(projectEntriesMap);

            // Update total time TextView
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