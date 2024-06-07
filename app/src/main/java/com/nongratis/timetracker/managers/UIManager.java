package com.nongratis.timetracker.managers;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.nongratis.timetracker.R;

public class UIManager {

    private final TextView timerTextView;
    private final ShapeableImageView startStopButton;
    private final ShapeableImageView pauseButton;
    private final ShapeableImageView deleteButton;
    private final ButtonClickListener buttonClickListener;

    public UIManager(View view, ButtonClickListener buttonClickListener) {
        this.timerTextView = view.findViewById(R.id.timer_text_view);
        this.startStopButton = view.findViewById(R.id.start_stop_button);
        this.pauseButton = view.findViewById(R.id.pause_button);
        this.deleteButton = view.findViewById(R.id.delete_button);
        this.buttonClickListener = buttonClickListener;

        setupListeners();
    }

    private void setupListeners() {
        startStopButton.setOnClickListener(v -> buttonClickListener.onStartStopButtonClick());
        pauseButton.setOnClickListener(v -> buttonClickListener.onPauseButtonClick());
        deleteButton.setOnClickListener(v -> buttonClickListener.onDeleteButtonClick());
    }

    public void updateTimerDisplay(boolean isRunning, String elapsedTime) {
        timerTextView.setText(elapsedTime);
        if (isRunning) {
            startStopButton.setImageResource(R.drawable.ic_stop);
            pauseButton.setVisibility(View.VISIBLE);
        } else {
            startStopButton.setImageResource(R.drawable.ic_start);
            pauseButton.setVisibility(View.GONE);
        }
    }

    public interface ButtonClickListener {
        void onStartStopButtonClick();

        void onPauseButtonClick();

        void onDeleteButtonClick();
    }
}