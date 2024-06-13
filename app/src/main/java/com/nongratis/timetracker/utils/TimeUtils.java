package com.nongratis.timetracker.utils;

import java.util.Calendar;

public class TimeUtils {
    public static long[] getTimeRangeForPeriod(String period) {
        long startTime = 0, endTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();

        switch (period) {
            case "day":
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTime = calendar.getTimeInMillis();
                break;
            case "week":
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTime = calendar.getTimeInMillis();
                break;
            case "month":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTime = calendar.getTimeInMillis();
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        return new long[]{startTime, endTime};
    }
}
