package com.nongratis.timetracker.fragments;

import android.graphics.Color;
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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;
import com.nongratis.timetracker.data.entities.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

            // Calculate total time
            long totalTime = 0;
            for (Task task : tasks) {
                totalTime += task.getDuration();
            }

            // Update total time TextView
            TextView totalTimeTextView = getView().findViewById(R.id.totalTime);
            totalTimeTextView.setText(String.format("Total Time: %s", formatDuration(totalTime)));
        });
    }

    private void updatePieChart(PieChart pieChart, List<Task> tasks, String type) {
        Map<String, Float> entriesMap = new HashMap<>();
        // Process tasks to create PieEntries based on type (workflow/project)
        for (Task task : tasks) {
            String label;
            if (type.equals("workflow")) {
                label = task.getWorkflowName();
            } else {
                label = task.getProjectName();
            }
            if (label == null || label.isEmpty()) {
                label = "Other";
            }
            float previousDuration = entriesMap.containsKey(label) ? entriesMap.get(label) : 0;
            entriesMap.put(label, previousDuration + task.getDuration());
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : entriesMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Time Analysis");

        // Assign different saturation for different categories
        List<Integer> colors = new ArrayList<>();
        int baseColor = getResources().getColor(R.color.purple_200);
        for (int i = 0; i < entries.size(); i++) {
            colors.add(adjustSaturation(baseColor, i / (float) entries.size()));
        }
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh the chart

        // Setup legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);;
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
    }

    private int adjustSaturation(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        float minSaturation = 0.10f; // Minimum saturation limit
        hsv[1] = minSaturation + ((1 - minSaturation) * factor);
        return Color.HSVToColor(hsv);
    }
    private String formatDuration(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}