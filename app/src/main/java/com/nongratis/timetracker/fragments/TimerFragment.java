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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.nongratis.timetracker.AppDatabaseInitializer;
import com.nongratis.timetracker.Constants;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.TaskManager;
import com.nongratis.timetracker.managers.TimerManager;
import com.nongratis.timetracker.utils.NotificationHelper;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.ViewModelProvider.TaskViewModelFactory;

public class TimerFragment extends Fragment {

    private final Handler handler = new Handler();
    private TextView timerDisplay;
    private ShapeableImageView startStopButton;
    private ShapeableImageView pauseButton;
    private MaterialAutoCompleteTextView workflowName;
    private MaterialAutoCompleteTextView projectName;
    private MaterialAutoCompleteTextView description;
    private NotificationHelper notificationHelper;
    private TimerManager timerManager;
    private final Runnable updateTimer = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            String elapsedTime = timerManager.getElapsedTime();
            timerDisplay.setText(elapsedTime);
            notificationHelper.updateNotification(elapsedTime, false);
            handler.postDelayed(this, Constants.NOTIFICATION_DELAY);
        }
    };
    private TaskManager taskManager;
    private TaskViewModel taskViewModel;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ACTION_PAUSE_TIMER.equals(intent.getAction())) {
                pauseTimer();
            } else if (Constants.ACTION_STOP_TIMER.equals(intent.getAction())) {
                stopTimer();
            } else if (Constants.ACTION_RESUME_TIMER.equals(intent.getAction())) {
                resumeTimer();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationHelper = new NotificationHelper(requireContext());

        TaskDao taskDao = AppDatabaseInitializer.getDatabase().taskDao();
        TaskRepository taskRepository = new TaskRepository(taskDao);
        TaskViewModelFactory factory = new TaskViewModelFactory(taskRepository);
        taskViewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_PAUSE_TIMER);
        filter.addAction(Constants.ACTION_STOP_TIMER);
        filter.addAction(Constants.ACTION_RESUME_TIMER);
        requireActivity().registerReceiver(receiver, filter);

        timerManager = new TimerManager();
        taskManager = new TaskManager(taskViewModel);
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
        projectName = view.findViewById(R.id.projectName);
        description = view.findViewById(R.id.description);

        startStopButton = view.findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(v -> {
            if (timerManager.isRunning()) {
                stopTimer();
            } else {
                startTimer();
            }
        });

        pauseButton = view.findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(v -> {
            if (timerManager.isRunning()) {
                pauseTimer();
            } else {
                startTimer();
            }
        });

        return view;
    }

    private void startTimer() {
        timerManager.startTimer();
        notificationHelper.startNotification(timerManager.getElapsedTime(), false);
        updateUI();
    }

    private void stopTimer() {
        taskManager.saveTask(workflowName.getText().toString(), projectName.getText().toString(), description.getText().toString(), timerManager.getStartTime(), System.currentTimeMillis());
        // Stop timer
        timerManager.stopTimer();
        updateUI();
        notificationHelper.updateNotification(timerManager.getElapsedTime(), false);
        timerDisplay.setText(R.string.start_time);
    }

    private void pauseTimer() {
        taskManager.saveTask(workflowName.getText().toString(), projectName.getText().toString(), description.getText().toString(), timerManager.getStartTime(), System.currentTimeMillis());        timerManager.pauseTimer();
        notificationHelper.updateNotification(timerManager.getElapsedTime(), true);
        updateUI();
    }

    private void resumeTimer() {
        startNewTask();
        timerManager.startTimer();
        notificationHelper.updateNotification(timerManager.getElapsedTime(), false);
        updateUI();
    }

    private void startNewTask() {
        // Reset the start time of the timer
        timerManager.startTimer();
    }

    private void updateUI() {
        if (timerManager.isRunning()) {
            handler.post(updateTimer);
            startStopButton.setImageResource(R.drawable.ic_stop);
            pauseButton.setVisibility(View.VISIBLE);
        } else {
            handler.removeCallbacks(updateTimer);
            startStopButton.setImageResource(R.drawable.ic_start);
            pauseButton.setVisibility(View.GONE);
        }
    }
}
