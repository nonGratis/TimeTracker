package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.repository.TimePeriod;
import com.nongratis.timetracker.viewmodel.TimeAnalysisViewModel;

import java.util.List;

public class TimeAnalysisFragment extends Fragment {
    private TimeAnalysisViewModel viewModel;
    private TextView totalTime;
    private PieChart pieChartWorkflow, pieChartProject;
    private Button btnDay, btnWeek, btnMonth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_analysis, container, false);

        totalTime = view.findViewById(R.id.totalTime);
        pieChartWorkflow = view.findViewById(R.id.pieChartWorkflow);
        pieChartProject = view.findViewById(R.id.pieChartProject);
        btnDay = view.findViewById(R.id.btnDay);
        btnWeek = view.findViewById(R.id.btnWeek);
        btnMonth = view.findViewById(R.id.btnMonth);

        viewModel = new ViewModelProvider(this).get(TimeAnalysisViewModel.class);

        btnDay.setOnClickListener(v -> updateDisplay("day"));
        btnWeek.setOnClickListener(v -> updateDisplay("week"));
        btnMonth.setOnClickListener(v -> updateDisplay("month"));

        // Observe LiveData
        viewModel.getTotalTime().observe(getViewLifecycleOwner(), time -> totalTime.setText("Total Time: " + time));
        viewModel.getWorkflowData().observe(getViewLifecycleOwner(), data -> setPieChartData(pieChartWorkflow, data));
        viewModel.getProjectData().observe(getViewLifecycleOwner(), data -> setPieChartData(pieChartProject, data));

        // Initialize with default period (e.g., day)
        updateDisplay("day");

        return view;
    }

    private void updateDisplay(String period) {
        TimePeriod timePeriod;
        switch (period) {
            case "day":
                timePeriod = TimePeriod.DAY;
                break;
            case "week":
                timePeriod = TimePeriod.WEEK;
                break;
            case "month":
                timePeriod = TimePeriod.MONTH;
                break;
            default:
                throw new IllegalArgumentException("Invalid time period: " + period);
        }
        viewModel.updateData(timePeriod);
    }

    private void setPieChartData(PieChart pieChart, List<PieEntry> data) {
        PieDataSet dataSet = new PieDataSet(data, "Label"); // Add appropriate label
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // Refresh chart
    }
}