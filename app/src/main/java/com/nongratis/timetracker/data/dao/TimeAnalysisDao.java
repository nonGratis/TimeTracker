package com.nongratis.timetracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;

import java.util.List;

@Dao
public interface TimeAnalysisDao {
    @Query("SELECT * FROM time_analysis WHERE timestamp >= :from AND timestamp <= :to")
    LiveData<List<TimeAnalysisEntity>> getTimeAnalysis(long from, long to);
    // Additional queries for aggregation
}