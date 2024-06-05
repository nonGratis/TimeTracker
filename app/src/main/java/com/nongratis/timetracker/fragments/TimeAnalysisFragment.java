package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;
import com.nongratis.timetracker.data.repository.Period;
import com.nongratis.timetracker.viewmodel.TimeAnalysisViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeAnalysisFragment extends Fragment {
    private static final String TAG = "TimeAnalysisFragment";
    private TimeAnalysisViewModel viewModel;
    private PieChart pieChartWorkflow;
    private PieChart pieChartProject;
    private TextView totalTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_analysis, container, false);

        pieChartWorkflow = view.findViewById(R.id.pieChartWorkflow);
        pieChartProject = view.findViewById(R.id.pieChartProject);
        totalTime = view.findViewById(R.id.totalTime);

        viewModel = new ViewModelProvider(this).get(TimeAnalysisViewModel.class);
        viewModel.getTimeAnalysisData().observe(getViewLifecycleOwner(), this::updateCharts);
        viewModel.setPeriod(Period.DAY);

        view.findViewById(R.id.btnDay).setOnClickListener(v -> viewModel.setPeriod(Period.DAY));
        view.findViewById(R.id.btnWeek).setOnClickListener(v -> viewModel.setPeriod(Period.WEEK));
        view.findViewById(R.id.btnMonth).setOnClickListener(v -> viewModel.setPeriod(Period.MONTH));

        return view;
    }

    private void updateCharts(List<TimeAnalysisEntity> data) {
        if (data == null || data.isEmpty()) {
            Log.w(TAG, "No data available");
            totalTime.setText("Total Time: 0");
            return;
        }

        Map<String, Long> workflowCategoryData = new HashMap<>();
        Map<String, Long> projectData = new HashMap<>();
        long totalDuration = 0;

        for (TimeAnalysisEntity entry : data) {
            totalDuration += entry.duration;
            workflowCategoryData.merge(entry.workflowCategory, entry.duration, Long::sum);
            projectData.merge(entry.projectName, entry.duration, Long::sum);
        }

        Log.d(TAG, "Total Duration: " + totalDuration);
        totalTime.setText("Total Time: " + totalDuration);

        // Update PieChart for Workflow Categories
        List<PieEntry> workflowEntries = new ArrayList<>();
        for (Map.Entry<String, Long> entry : workflowCategoryData.entrySet()) {
            workflowEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet workflowDataSet = new PieDataSet(workflowEntries, "Workflow Categories");
        PieData workflowData = new PieData(workflowDataSet);
        pieChartWorkflow.setData(workflowData);
        pieChartWorkflow.invalidate();

        // Update PieChart for Projects
        List<PieEntry> projectEntries = new ArrayList<>();
        for (Map.Entry<String, Long> entry : projectData.entrySet()) {
            projectEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
        PieDataSet projectDataSet = new PieDataSet(projectEntries, "Projects");
        PieData projectPieData = new PieData(projectDataSet);
        pieChartProject.setData(projectPieData);
        pieChartProject.invalidate();
    }
}