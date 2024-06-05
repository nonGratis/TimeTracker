package com.nongratis.timetracker.viewmodel.ViewModelProvider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public class TimeAnalysisViewModel extends ViewModel {
    private final MutableLiveData<String> totalTime = new MutableLiveData<>();
    private final MutableLiveData<List<PieEntry>> workflowData = new MutableLiveData<>();
    private final MutableLiveData<List<PieEntry>> projectData = new MutableLiveData<>();
    private final MutableLiveData<List<PieEntry>> projectASpecificData = new MutableLiveData<>();
    private final MutableLiveData<List<PieEntry>> projectBSpecificData = new MutableLiveData<>();

    // Getters
    public LiveData<String> getTotalTime() {
        return totalTime;
    }

    public LiveData<List<PieEntry>> getWorkflowData() {
        return workflowData;
    }

    public LiveData<List<PieEntry>> getProjectData() {
        return projectData;
    }

    public LiveData<List<PieEntry>> getProjectASpecificData() {
        return projectASpecificData;
    }

    public LiveData<List<PieEntry>> getProjectBSpecificData() {
        return projectBSpecificData;
    }

    // Function to update data
    public void updateData(String period) {
        // Fetch data from repository and update LiveData
        TimeRepository repository = TimeRepository.getInstance();

        totalTime.setValue(repository.getTotalTime(period));
        workflowData.setValue(repository.getWorkflowData(period));
        projectData.setValue(repository.getProjectData(period));
        projectASpecificData.setValue(repository.getProjectSpecificData(period, "ProjectA"));
        projectBSpecificData.setValue(repository.getProjectSpecificData(period, "ProjectB"));
    }
}