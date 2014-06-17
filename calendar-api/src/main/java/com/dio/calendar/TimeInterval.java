package com.dio.calendar;

/**
 * Created by yur on 03.05.2014.
 */
public class TimeInterval {
    private final int startTime;
    private final int endTime;

    public TimeInterval(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}
