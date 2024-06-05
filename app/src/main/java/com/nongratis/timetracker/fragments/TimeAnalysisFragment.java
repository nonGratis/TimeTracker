package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;
import com.nongratis.timetracker.data.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TimeAnalysisFragment extends Fragment {
    private PieChart pieChartWorkflow;
    private PieChart pieChartProject;
    private TaskViewModel taskViewModel;
    private Button btnDat, btnWeek, btnMonth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_analysis, container, false);

        pieChartWorkflow = view.findViewById(R.id.pieChartWorkflow);
        pieChartProject = view.findViewById(R.id.pieChartProject);
        btnDat = view.findViewById(R.id.btnDay);
        btnWeek = view.findViewById(R.id.btnWeek);
        btnMonth = view.findViewById(R.id.btnMonth);

        TaskRepository taskRepository = new TaskRepository(requireActivity().getApplication());
        TaskViewModelFactory factory = new TaskViewModelFactory(taskRepository);
        taskViewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        btnDat.setOnClickListener(v -> loadData("day"));
        btnWeek.setOnClickListener(v -> loadData("week"));
        btnMonth.setOnClickListener(v -> loadData("month"));

        loadData("day"); // Default load for the day

        return view;
    }

    private void loadData(String period) {
        taskViewModel.getTasksByPeriod(period).observe(getViewLifecycleOwner(), tasks -> {
            updatePieChart(pieChartWorkflow, tasks, "workflow");
            updatePieChart(pieChartProject, tasks, "project");
        });
    }

    private void updatePieChart(PieChart pieChart, List<Task> tasks, String type) {
        List<PieEntry> entries = new ArrayList<>();
        // Process tasks to create PieEntries based on type (workflow/project)
        for (Task task : tasks) {
            if (type.equals("workflow")) {
                entries.add(new PieEntry(task.getDuration(), task.getWorkflowName()));
            } else {
                entries.add(new PieEntry(task.getDuration(), task.getProjectName()));
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "Time Analysis");
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart
    }
}