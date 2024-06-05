package com.nongratis.timetracker.managers;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.nongratis.timetracker.R;

public class UIManager {
    private TextView timerDisplay;
    private ShapeableImageView startStopButton;
    private ShapeableImageView pauseButton;
    private MaterialAutoCompleteTextView workflowName;
    private MaterialAutoCompleteTextView projectName;
    private MaterialAutoCompleteTextView description;
    private ButtonClickListener buttonClickListener;

    public interface ButtonClickListener {
        void onStartStopButtonClick();
        void onPauseButtonClick();    }

    public UIManager(View view, ButtonClickListener listener) {
        this.buttonClickListener = listener;
        setupUI(view);
    }

    private void setupUI(View view) {
        timerDisplay = view.findViewById(R.id.timer_display);
        workflowName = view.findViewById(R.id.workflowName);
        projectName = view.findViewById(R.id.projectName);
        description = view.findViewById(R.id.description);

        startStopButton = view.findViewById(R.id.start_stop_button);
        pauseButton = view.findViewById(R.id.pause_button);

        startStopButton.setOnClickListener(v -> buttonClickListener.onStartStopButtonClick());
        pauseButton.setOnClickListener(v -> buttonClickListener.onPauseButtonClick());
    }

    public void updateTimerDisplay(boolean isRunning, String currentTime) {
        if (isRunning) {
            startStopButton.setImageResource(R.drawable.ic_stop);
            pauseButton.setVisibility(View.VISIBLE);
        } else {
            startStopButton.setImageResource(R.drawable.ic_start);
            pauseButton.setVisibility(View.GONE);
        }
        timerDisplay.setText(currentTime);
    }

    public String getProjectName() {
        return projectName.getText().toString();
    }

    public String getWorkflowName() {
        return workflowName.getText().toString();
    }

    public String getDescription() {
        return description.getText().toString();
    }
}
