package com.dio.calendar;

import java.util.Calendar;
import java.util.Date;

/**
 * Helper functions for Calendar project
 * Created by iovchynnikov on 5/5/14.
 */
public class Utils {

    /**
     * Function for constructing the date
     * Worked with all types of parameters
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param secs
     * @return date
     */
    public static Date constructDate(int year, int month, int day, int hour, int min, int secs) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, min, secs);
        Date result = cal.getTime();
        return result;
    }


}
