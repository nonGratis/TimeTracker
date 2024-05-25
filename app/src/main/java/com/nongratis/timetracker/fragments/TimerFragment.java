package com.nongratis.timetracker.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.nongratis.timetracker.utils.NotificationHelper;
import com.nongratis.timetracker.utils.TimerLogic;

import java.util.ArrayList;
import java.util.List;

public class TimerFragment extends Fragment {

    private TextView timerDisplay;
    private ShapeableImageView startStopButton;
    private ShapeableImageView pauseButton;
    private final Handler handler = new Handler();
    private MaterialAutoCompleteTextView workflowName;
    private final TimerLogic timerLogic = new TimerLogic();
    private NotificationHelper notificationHelper;

    private final Runnable updateTimer = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            String elapsedTime = timerLogic.getElapsedTime();
            timerDisplay.setText(elapsedTime);
            notificationHelper.updateNotification(elapsedTime, false);
            handler.postDelayed(this, 1000);
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("PAUSE_TIMER".equals(intent.getAction())) {
                pauseTimer();
            } else if ("STOP_TIMER".equals(intent.getAction())) {
                stopTimer();
            } else if ("RESUME_TIMER".equals(intent.getAction())) {
                resumeTimer();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationHelper = new NotificationHelper(requireContext());

        IntentFilter filter = new IntentFilter();
        filter.addAction("PAUSE_TIMER");
        filter.addAction("STOP_TIMER");
        filter.addAction("RESUME_TIMER");
        requireActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        timerDisplay = view.findViewById(R.id.timer_display);
        workflowName = view.findViewById(R.id.workflowName);
        setupDropdown(workflowName);

        startStopButton = view.findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(v -> {
            if (timerLogic.isRunning()) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        pauseButton = view.findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(v -> {
            if (timerLogic.isRunning()) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        return view;
    }

    private void startTimer() {
        timerLogic.startTimer();
        notificationHelper.startNotification(timerLogic.getElapsedTime(), false);
        updateUI();
    }

    private void stopTimer() {
        timerLogic.stopTimer();
        notificationHelper.updateNotification(timerLogic.getElapsedTime(), false);
        updateUI();
    }

    private void pauseTimer() {
        timerLogic.pauseTimer();
        notificationHelper.updateNotification(timerLogic.getElapsedTime(), true);
        updateUI();
    }

    private void resumeTimer() {
        timerLogic.startTimer();
        notificationHelper.updateNotification(timerLogic.getElapsedTime(), false);
        updateUI();
    }

    private void updateUI() {
        if (timerLogic.isRunning()) {
            handler.post(updateTimer);
            startStopButton.setImageResource(R.drawable.ic_stop);
            pauseButton.setVisibility(View.VISIBLE);
        } else {
            handler.removeCallbacks(updateTimer);
            startStopButton.setImageResource(R.drawable.ic_start);
            pauseButton.setVisibility(View.GONE);
        }
    }

    private void setupDropdown(MaterialAutoCompleteTextView dropdown) {
        List<String> items = new ArrayList<>();
        items.add("Add new...");
        // Add existing items here

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0) {
                // Handle "Add new" option
            }
        });
    }
}
