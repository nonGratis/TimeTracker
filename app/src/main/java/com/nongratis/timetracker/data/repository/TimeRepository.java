package com.nongratis.timetracker.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.dao.TimeAnalysisDao;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;

import java.util.List;

import com.nongratis.timetracker.data.database.AppDatabase;

public class TimeRepository {
    private static TimeRepository instance;
    private final TimeAnalysisDao timeAnalysisDao;

    private TimeRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        timeAnalysisDao = db.timeAnalysisDao();
    }

    public static synchronized TimeRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TimeRepository(context);
        }
        return instance;
    }

    public LiveData<List<TimeAnalysisEntity>> getTimeAnalysis(long from, long to) {
        return timeAnalysisDao.getTimeAnalysis(from, to);
    }
}