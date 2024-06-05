package com.nongratis.timetracker.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_analysis")
public class TimeAnalysisEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String projectName;
    public String workflowCategory;
    public long duration;
    public long timestamp;
}