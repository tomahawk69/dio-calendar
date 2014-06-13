package com.dio.calendar;

import java.util.Date;

/**
 * Static validation class for Entry class
 * Created by yur on 05.05.2014.
 */

public class ValidateEntry {
    public static void validateEntry(Entry entry) {


    }

    public static void validateDateRange(Date date1, Date date2) throws CalendarEntryBadAttribute {
        if (date1 != null && date1.compareTo(date2) > 0)
            throw new CalendarEntryBadAttribute("startDate should be lesser then endDate");
    }

}
