package com.nongratis.timetracker.utils;

import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.viewmodel.TaskViewModel;

import java.util.Calendar;

public class DateTimePickerUtil {

    public static void showDateTimePicker(FragmentManager fragmentManager, TaskViewModel taskViewModel, String[] taskDetails) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            // When date is selected, show time picker for start time
            MaterialTimePicker startTimePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .build();
            startTimePicker.addOnPositiveButtonClickListener(dialog -> {
                // When start time is selected, show time picker for end time
                MaterialTimePicker endTimePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .build();
                endTimePicker.addOnPositiveButtonClickListener(dialogEnd -> {
                    // When end time is selected, create a new task and insert it into the database
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(datePicker.getSelection());
                    calendar.set(Calendar.HOUR_OF_DAY, startTimePicker.getHour());
                    calendar.set(Calendar.MINUTE, startTimePicker.getMinute());

                    Task task = new Task();
                    task.setStartTime(calendar.getTimeInMillis());

                    // Set end time
                    calendar.set(Calendar.HOUR_OF_DAY, endTimePicker.getHour());
                    calendar.set(Calendar.MINUTE, endTimePicker.getMinute());
                    task.setEndTime(calendar.getTimeInMillis());

                    task.setWorkflowName(taskDetails[0]);
                    task.setProjectName(taskDetails[1]);
                    task.setDescription(taskDetails[2]);

                    taskViewModel.insert(task);
                });
                endTimePicker.show(fragmentManager, "EndTimePicker");
            });
            startTimePicker.show(fragmentManager, "StartTimePicker");
        });
        datePicker.show(fragmentManager, "DatePicker");
    }
}