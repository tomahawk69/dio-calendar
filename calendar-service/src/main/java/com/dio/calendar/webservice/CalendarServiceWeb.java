package com.dio.calendar.webservice;

import com.dio.calendar.CalendarServiceRemoteImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Created by yur on 15.06.2014.
 */
@Controller
@RequestMapping("/calendar-service-status")
public class CalendarServiceWeb {
    private final CalendarServiceRemoteImpl calendarService;

    @Inject
    public CalendarServiceWeb(CalendarServiceRemoteImpl calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printHello(ModelMap model) {
        return "calendar-service-status";
    }
}
