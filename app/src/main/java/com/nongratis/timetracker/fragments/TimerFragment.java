package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.nongratis.timetracker.R;

import java.util.ArrayList;
import java.util.List;

public class TimerFragment extends Fragment {

    private TextView timerDisplay;
    private ShapeableImageView startStopButton;
    private boolean isRunning = false;
    private long startTime = 0L;
    private Handler handler = new Handler();
    private MaterialAutoCompleteTextView workflowName;


    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            timerDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            handler.postDelayed(this, 1000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        timerDisplay = view.findViewById(R.id.timer_display);
        startStopButton = view.findViewById(R.id.start_stop_button);
        workflowName = view.findViewById(R.id.workflowName);
        setupDropdown(workflowName);

        startStopButton.setOnClickListener(v -> {
            if (isRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        return view;
    }

    private void startTimer() {
        isRunning = true;
        startTime = System.currentTimeMillis();
        handler.post(updateTimer);
        startStopButton.setImageResource(R.drawable.ic_stop);
    }

    private void stopTimer() {
        isRunning = false;
        handler.removeCallbacks(updateTimer);
        startStopButton.setImageResource(R.drawable.ic_start);
    }

    private void setupDropdown(MaterialAutoCompleteTextView dropdown) {
        List<String> items = new ArrayList<>();
        items.add("Add new...");
        // Add existing items here

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                // Handle "Add new" option
            }
        });
    }
}
