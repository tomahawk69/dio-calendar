package com.dio.calendar;

/**
 * Created by iovchynnikov on 4/30/14.
 */
public enum EnumNotificationPeriod {
    HOUR("hour", "hour(s)"),
    DAY("day", "day(s)");

    private final String id;
    private final String caption;

    private EnumNotificationPeriod(String id, String caption) {
        this.id = id;
        this.caption = caption;
    }

}
