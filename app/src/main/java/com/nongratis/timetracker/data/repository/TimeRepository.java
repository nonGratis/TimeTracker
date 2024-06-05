package com.nongratis.timetracker.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.nongratis.timetracker.data.dao.TimeAnalysisDao;
import com.nongratis.timetracker.data.database.TimeTrackerDatabase;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;
import java.util.List;

public class TimeRepository {
    private static TimeRepository instance;
    private final TimeAnalysisDao timeAnalysisDao;

    private TimeRepository(Context context) {
        TimeTrackerDatabase database = TimeTrackerDatabase.getInstance(context);
        timeAnalysisDao = database.timeAnalysisDao();
    }

    public static synchronized TimeRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TimeRepository(context.getApplicationContext());
        }
        return instance;
    }

    public LiveData<List<TimeAnalysisEntity>> getTimeAnalysis(long from, long to) {
        return timeAnalysisDao.getTimeAnalysis(from, to);
    }
}