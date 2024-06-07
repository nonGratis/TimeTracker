package com.nongratis.timetracker.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nongratis.timetracker.Constants;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.UIManager;
import com.nongratis.timetracker.utils.DateTimePickerUtil;
import com.nongratis.timetracker.utils.NotificationHelper;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;
import com.nongratis.timetracker.viewmodel.TimerViewModel;

public class TimerFragment extends Fragment implements UIManager.ButtonClickListener {

    private TimerViewModel timerViewModel;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constants.ACTION_PAUSE_TIMER.equals(intent.getAction())) {
                timerViewModel.pauseTimer();
            } else if (Constants.ACTION_STOP_TIMER.equals(intent.getAction())) {
                timerViewModel.stopTimer();
            } else if (Constants.ACTION_RESUME_TIMER.equals(intent.getAction())) {
                timerViewModel.resumeTimer();
            }
        }
    };
    private TaskViewModel taskViewModel;
    private UIManager uiManager;
    private NotificationHelper notificationHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModels();
        setupBroadcastReceiver();
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
        uiManager = new UIManager(view, this);

        view.findViewById(R.id.add_button).setOnClickListener(v -> turnDateTimePicker());

        observeViewModels();
        return view;
    }

    private void turnDateTimePicker() {
        DateTimePickerUtil.showDateTimePicker(getChildFragmentManager(), taskViewModel, getTaskDetails());
    }

    private void initViewModels() {
        TaskRepository taskRepository = new TaskRepository(requireActivity().getApplication());
        TaskViewModelFactory factory = new TaskViewModelFactory(requireActivity().getApplication(), taskRepository);
        taskViewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);
        timerViewModel = new ViewModelProvider(this).get(TimerViewModel.class);
        notificationHelper = new NotificationHelper(requireContext());
    }

    private void setupBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_PAUSE_TIMER);
        filter.addAction(Constants.ACTION_STOP_TIMER);
        filter.addAction(Constants.ACTION_RESUME_TIMER);
        requireActivity().registerReceiver(receiver, filter);
    }

    private void observeViewModels() {
        timerViewModel.getElapsedTime().observe(getViewLifecycleOwner(), elapsedTime -> {
            uiManager.updateTimerDisplay(timerViewModel.isRunning(), elapsedTime);
            notificationHelper.updateNotification(elapsedTime, timerViewModel.isPaused());
        });
    }

    @Override
    public void onStartStopButtonClick() {
        String[] taskDetails = getTaskDetails();

        if (timerViewModel.isRunning()) {
            timerViewModel.saveTimer(taskDetails[0], taskDetails[1], taskDetails[2]);
            timerViewModel.stopTimer();
        } else {
            timerViewModel.startTimer();
        }
    }

    @Override
    public void onDeleteButtonClick() {
        if (timerViewModel.isRunning()) {
            timerViewModel.stopTimer();
        }
    }

    @Override
    public void onPauseButtonClick() {
        String[] taskDetails = getTaskDetails();

        if (timerViewModel.isRunning()) {
            timerViewModel.saveTimer(taskDetails[0], taskDetails[1], taskDetails[2]);
            timerViewModel.stopTimer();
        } else {
            timerViewModel.resumeTimer();
        }
    }

    private String[] getTaskDetails() {
        String workflowName = ((TextView) requireView().findViewById(R.id.workflowName)).getText().toString();
        String projectName = ((TextView) requireView().findViewById(R.id.projectName)).getText().toString();
        String description = ((TextView) requireView().findViewById(R.id.description)).getText().toString();

        return new String[]{workflowName, projectName, description};
    }
}