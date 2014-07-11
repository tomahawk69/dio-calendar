package com.dio.calendar;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yur on 11.07.2014.
 */
public class EntryApi {

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static Logger logger = Logger.getLogger(EntryApi.class);

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        } else {
            return df.format(date);
        }
    }

    public static Date stringToDate(String string) throws ParseException {
        Date result = null;
        if (string != null) {
            try {
                result = df.parse(string);
            }
            catch (ParseException e) {
                logger.error(String.format("Wrong String '%s' provided to encode into the Date", string));
                throw e;
            }
        }
        return result;
    }

}