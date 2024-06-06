package com.nongratis.timetracker.managers;

import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.nongratis.timetracker.R;

public class ButtonManager {
    private final MaterialButton btnDay, btnWeek, btnMonth;
    private final int defaultStrokeColor;
    private final int selectedStrokeColor;

    public ButtonManager(MaterialButton btnDay, MaterialButton btnWeek, MaterialButton btnMonth, int defaultStrokeColor, int selectedStrokeColor) {
        this.btnDay = btnDay;
        this.btnWeek = btnWeek;
        this.btnMonth = btnMonth;
        this.defaultStrokeColor = defaultStrokeColor;
        this.selectedStrokeColor = selectedStrokeColor;
    }

    public void resetButtonStyles() {
        btnDay.setStrokeColorResource(defaultStrokeColor);
        btnWeek.setStrokeColorResource(defaultStrokeColor);
        btnMonth.setStrokeColorResource(defaultStrokeColor);
    }

    public void setButtonStyle(String period) {
        resetButtonStyles();
        switch (period) {
            case "day":
                btnDay.setStrokeColorResource(selectedStrokeColor);
                break;
            case "week":
                btnWeek.setStrokeColorResource(selectedStrokeColor);
                break;
            case "month":
                btnMonth.setStrokeColorResource(selectedStrokeColor);
                break;
        }
    }
}