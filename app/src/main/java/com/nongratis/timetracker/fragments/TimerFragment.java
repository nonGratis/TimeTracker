package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.NotificationManager;
import com.nongratis.timetracker.managers.UIManager;
import com.nongratis.timetracker.utils.DateTimePickerUtil;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;
import com.nongratis.timetracker.viewmodel.TimerViewModel;

public class TimerFragment extends Fragment implements UIManager.ButtonClickListener {

    private TimerViewModel timerViewModel;
    private TaskViewModel taskViewModel;
    private UIManager uiManager;
    private NotificationManager notificationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModels();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        uiManager = new UIManager(view, this);
        notificationManager = new NotificationManager(requireActivity());

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
    }

    private void observeViewModels() {
        timerViewModel.getElapsedTime().observe(getViewLifecycleOwner(), elapsedTime -> {
            uiManager.updateTimerDisplay(timerViewModel.isRunning(), elapsedTime);
            notificationManager.updateNotification(elapsedTime, timerViewModel.isPaused());
        });
    }

    @Override
    public void onStartStopButtonClick() {
        if (timerViewModel.isRunning()) {
            String[] taskDetails = getTaskDetails();
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