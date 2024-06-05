package com.nongratis.timetracker.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;
import com.nongratis.timetracker.data.repository.TimeAnalysisRepository;
import com.nongratis.timetracker.data.repository.Period;
import java.util.List;

public class TimeAnalysisViewModel extends AndroidViewModel {
    private final TimeAnalysisRepository repository;
    private final MutableLiveData<Period> selectedPeriod = new MutableLiveData<>(Period.DAY);
    private final LiveData<List<TimeAnalysisEntity>> timeAnalysisData;

    public TimeAnalysisViewModel(@NonNull Application application) {
        super(application);
        repository = new TimeAnalysisRepository(application);

        timeAnalysisData = Transformations.switchMap(selectedPeriod, period -> {
            long now = System.currentTimeMillis();
            long from = now - period.getDuration();
            return repository.getTimeAnalysis(from, now);
        });
    }

    public LiveData<List<TimeAnalysisEntity>> getTimeAnalysisData() {
        return timeAnalysisData;
    }

    public void setPeriod(Period period) {
        selectedPeriod.setValue(period);
    }
}