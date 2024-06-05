package com.nongratis.timetracker.data.repository;

public enum Period {
    DAY(86400000L),  // 24 * 60 * 60 * 1000 milliseconds
    WEEK(604800000L), // 7 * 24 * 60 * 60 * 1000 milliseconds
    MONTH(2592000000L); // 30 * 24 * 60 * 60 * 1000 milliseconds

    private final long duration;

    Period(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
}