package com.nongratis.timetracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.entities.TimeSegment;
import com.nongratis.timetracker.data.repository.ITaskRepository;

import java.util.ArrayList;
import java.util.List;

public class DailyTimelineViewModel extends ViewModel {
    private final ITaskRepository taskRepository;
    private LiveData<List<TimeSegment>> timeSegments;

    public DailyTimelineViewModel(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        // Initialize timeSegments LiveData
        timeSegments = new MutableLiveData<>();
    }

    public LiveData<List<TimeSegment>> getTimeSegmentsForDay(long dayStart, long dayEnd) {
        // Get tasks for the day and convert to time segments
        LiveData<List<Task>> tasksForDay = taskRepository.getTasksBetween(dayStart, dayEnd);
        if (tasksForDay != null) {
            timeSegments = Transformations.map(tasksForDay, this::convertTasksToTimeSegments);
        } else {
            // Handle the case where tasksForDay is null
            timeSegments = new MutableLiveData<>(new ArrayList<>());
        }
        return timeSegments;
    }

    private List<TimeSegment> convertTasksToTimeSegments(List<Task> tasks) {
        List<TimeSegment> segments = new ArrayList<>();
        for (Task task : tasks) {
            segments.add(new TimeSegment(task.getStartTime(), task.getEndTime()));
        }
        return segments;
    }
}
