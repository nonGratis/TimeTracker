package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;

import com.nongratis.timetracker.R;

public class TimeAnalysisFragment extends Fragment {
    private PieChart pieChartWorkflow;
    private PieChart pieChartProject;
    private TextView totalTime;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_analysis, container, false);

        pieChartWorkflow = view.findViewById(R.id.pieChartWorkflow);
        pieChartProject = view.findViewById(R.id.pieChartProject);
        totalTime = view.findViewById(R.id.totalTime);
        return view;
    }
}