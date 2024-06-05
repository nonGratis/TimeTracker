package com.nongratis.timetracker.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;
import com.nongratis.timetracker.data.repository.TimeRepository;
import com.nongratis.timetracker.data.repository.Period;
import java.util.List;

public class TimeAnalysisViewModel extends AndroidViewModel {
    private final TimeRepository repository;
    private final MutableLiveData<Period> selectedPeriod = new MutableLiveData<>(Period.DAY);
    private LiveData<List<TimeAnalysisEntity>> timeAnalysisData;

    public TimeAnalysisViewModel(@NonNull Application application) {
        super(application);
        repository = TimeRepository.getInstance(application);

        timeAnalysisData = Transformations.switchMap(selectedPeriod, period -> {
            long now = System.currentTimeMillis();
            long from = now - period.getDuration();
            return repository.getTimeAnalysis(from, now);
        });
    }

    public LiveData<List<TimeAnalysisEntity>> getTimeAnalysisData() {
        return timeAnalysisData;
    }

    public void setTimeAnalysisData(long from, long to) {
        this.timeAnalysisData = repository.getTimeAnalysis(from, to);
        this.timeAnalysisData.observeForever(timeAnalysisEntities -> {
            Log.d("TimeAnalysisViewModel", "Fetched " + timeAnalysisEntities.size() + " entities.");
            for (TimeAnalysisEntity entity : timeAnalysisEntities) {
                Log.d("TimeAnalysisViewModel", "Entity: " + entity.toString());
            }
        });
    }
    public void setPeriod(Period period) {
        selectedPeriod.setValue(period);
    }
}