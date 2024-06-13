package com.nongratis.timetracker.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Calendar;

public class TimeUtilsTest {
    @Test
    public void testGetTimeRangeForDay() {
        long currentTime = System.currentTimeMillis();
        long[] timeRange = TimeUtils.getTimeRangeForPeriod("day");
        long startTime = timeRange[0];
        long endTime = timeRange[1];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expectedStartTime = calendar.getTimeInMillis();

        assertEquals("Start time should be at the beginning of the day", startTime, expectedStartTime);
        assertTrue("End time should be current time", endTime >= currentTime && endTime <= currentTime + 1000);
    }

    @Test
    public void testGetTimeRangeForMonth() {
        long[] timeRange = TimeUtils.getTimeRangeForPeriod("month");
        long startTime = timeRange[0];
        long endTime = timeRange[1];

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long expectedStartTime = calendar.getTimeInMillis();
        long expectedEndTime = System.currentTimeMillis();

        assertEquals("Start time should be at the beginning of the month", startTime, expectedStartTime);
        assertEquals("End time should be current time", endTime, expectedEndTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTimeRangeForInvalidPeriod() {
        TimeUtils.getTimeRangeForPeriod("invalid_period");
    }
}
