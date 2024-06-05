package com.nongratis.timetracker.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.dao.TimeAnalysisDao;
import com.nongratis.timetracker.data.database.AppDatabase;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;

import java.util.List;

public class TimeAnalysisRepository {
    private final TimeAnalysisDao timeAnalysisDao;

    public TimeAnalysisRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        timeAnalysisDao = db.timeAnalysisDao();
    }

    public LiveData<List<TimeAnalysisEntity>> getTimeAnalysis(long from, long to) {
        LiveData<List<TimeAnalysisEntity>> data = timeAnalysisDao.getTimeAnalysis(from, to);
        data.observeForever(timeAnalysisEntities -> {
            Log.d("TimeAnalysisRepository", "Fetched " + timeAnalysisEntities.size() + " entities.");
            for (TimeAnalysisEntity entity : timeAnalysisEntities) {
                Log.d("TimeAnalysisRepository", "Entity: " + entity.toString());
            }
        });
        return data;
    }
}
