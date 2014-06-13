package com.dio.calendar.webservice.jsf;

import com.dio.calendar.CalendarServiceImpl;
import com.dio.calendar.Entry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by yur on 10.06.2014.
 */
public class JSFWelcomePage2 {
    CalendarServiceImpl calendarService;
    Logger logger = Logger.getLogger(JSFWelcomePage2.class);

    public JSFWelcomePage2() {
    }

    public void setCalendarService(CalendarServiceImpl calendarService) {
        this.calendarService = calendarService;
    }

    public String getMessage() {
        logger.info("getMessage");
        logger.info(calendarService);
        return "Message from welcome 2";
    }

    public List<Entry> getEntries() {
        return new ArrayList<>(calendarService.getEntries());
    }

    public int getEntriesCount() {
        return calendarService.getEntries().size();
    }

    public boolean isShowEntry() {
        return false;
    }

    public Entry getEntry() {
        return null;
    }
}
