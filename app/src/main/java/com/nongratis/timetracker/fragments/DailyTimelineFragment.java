package com.nongratis.timetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nongratis.timetracker.AppDatabaseInitializer;
import com.nongratis.timetracker.R;
import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.repository.ITaskRepository;
import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.viewmodel.DailyTimelineViewModel;

public class DailyTimelineFragment extends Fragment {
    private DailyTimelineViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_timeline, container, false);

        TaskDao taskDao = AppDatabaseInitializer.getDatabase().taskDao();
        TaskRepository taskRepository = new TaskRepository(taskDao);
        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new DailyTimelineViewModel(taskRepository);
            }
        };
        viewModel = new ViewModelProvider(this, factory).get(DailyTimelineViewModel.class);

        // TODO: Observe the tasks from the ViewModel and update the UI
        return view;
    }
}