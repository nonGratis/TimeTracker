package com.nongratis.timetracker.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_analysis")
public class TimeAnalysisEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public long timestamp;
    public String workflowCategory;
    public String projectName;
    public long duration; // Ensure this is correctly mapped to your database column
}