package com.nongratis.timetracker.managers;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nongratis.timetracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieChartManager {
    private final PieChart pieChart;
    private final Context context;

    public PieChartManager(PieChart pieChart, Context context) {
        this.pieChart = pieChart;
        this.context = context;
    }

    public void updatePieChart(Map<String, Float> entriesMap) {
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : entriesMap.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        // Assign different saturation for different categories
        List<Integer> colors = new ArrayList<>();
        int baseColor = context.getResources().getColor(R.color.purple_100, null);
        for (int i = entries.size(); i > 0 ; i--) {
            colors.add(adjustSaturation(baseColor, i / (float) entries.size()));
        }
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();

        // Disable labels and numerical data
        pieChart.setDrawEntryLabels(false); // Disable labels
        data.setValueTextSize(0f); // Disable numerical data

        // Setup legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setHighlightPerTapEnabled(true);
    }

    private int adjustSaturation(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        float minSaturation = 0.01f; // Minimum saturation limit
        float maxSaturation = 0.6f; // Maximum saturation limit
        hsv[1] = minSaturation + ((maxSaturation - minSaturation) * factor);
        return Color.HSVToColor(hsv);
    }

}