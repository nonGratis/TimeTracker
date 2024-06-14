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

import com.google.android.material.imageview.ShapeableImageView;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.NotificationManager;
import com.nongratis.timetracker.utils.DateTimePickerUtil;
import com.nongratis.timetracker.viewmodel.TaskViewModel;
import com.nongratis.timetracker.viewmodel.TaskViewModelFactory;
import com.nongratis.timetracker.viewmodel.TimerViewModel;

public class TimerFragment extends Fragment {

    private TimerViewModel timerViewModel;
    private TaskViewModel taskViewModel;
    private NotificationManager notificationManager;

    private TextView timerTextView;
    private ShapeableImageView startStopButton;
    private ShapeableImageView pauseButton;
    private ShapeableImageView deleteButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModels();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        timerTextView = view.findViewById(R.id.timer_text_view);
        startStopButton = view.findViewById(R.id.start_stop_button);
        pauseButton = view.findViewById(R.id.pause_button);
        deleteButton = view.findViewById(R.id.delete_button);

        startStopButton.setOnClickListener(v -> onStartStopButtonClick());
        pauseButton.setOnClickListener(v -> onPauseButtonClick());
        deleteButton.setOnClickListener(v -> onDeleteButtonClick());
        view.findViewById(R.id.add_button).setOnClickListener(v -> turnDateTimePicker());

        notificationManager = new NotificationManager(requireActivity());

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
            updateTimerDisplay(timerViewModel.isRunning(), elapsedTime);
            if (timerViewModel.isRunning() || timerViewModel.isPaused()) {
                notificationManager.updateNotification(elapsedTime, timerViewModel.isPaused());
            } else {
                notificationManager.cancelNotification();
            }
        });
    }

    private void onStartStopButtonClick() {
        timerViewModel.onStartStopButtonClick(getTaskDetails());
    }

    private void onDeleteButtonClick() {
        timerViewModel.onDeleteButtonClick();
    }

    private void onPauseButtonClick() {
        timerViewModel.onPauseButtonClick(getTaskDetails());
    }

    private String[] getTaskDetails() {
        String workflowName = ((TextView) requireView().findViewById(R.id.workflowName)).getText().toString();
        String projectName = ((TextView) requireView().findViewById(R.id.projectName)).getText().toString();
        String description = ((TextView) requireView().findViewById(R.id.description)).getText().toString();
        return new String[]{workflowName, projectName, description};
    }

    private void updateTimerDisplay(boolean isRunning, String elapsedTime) {
        timerTextView.setText(elapsedTime);
        if (isRunning) {
            startStopButton.setImageResource(R.drawable.ic_stop);
            pauseButton.setVisibility(View.VISIBLE);
        } else {
            startStopButton.setImageResource(R.drawable.ic_start);
            pauseButton.setVisibility(View.GONE);
        }
    }
}
