package com.nongratis.timetracker.data.repository;

public enum Period {
    DAY(86400000L), WEEK(604800000L), MONTH(2592000000L);

    private final long duration;

    Period(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }
}