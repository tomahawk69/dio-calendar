package com.dio.calendar.webservice.mvc;

import com.dio.calendar.CalendarServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by yur on 09.06.2014.
 */
//@Controller
@Scope("request")
public class HelloController {
    private final UUID id = UUID.randomUUID();
    private final CalendarServiceImpl calendarService;

    @Inject
    public HelloController(CalendarServiceImpl calendarService) {
        this.calendarService = calendarService;
    }
    @RequestMapping(value = {"/mvc", "/mvc/index"}, method = RequestMethod.GET)
    public String getIndex(ModelMap model) {
        model.addAttribute("message", "This is index page of MVC sample");
        model.addAttribute("body1", "Om mani padme hum");
        model.addAttribute("body2", "id="+id);
        return "index";
    }
    @RequestMapping(value = {"/mvc/entries"}, method = RequestMethod.GET)
    public ModelAndView getEntries(ModelMap model) {
        model.addAttribute("message", "This is index page of entries");
        model.addAttribute("entries", calendarService.getEntries());
        return createModelView("entries", model);
    }

    public ModelAndView createModelView(String entries, ModelMap model) {
        return new ModelAndView(entries, model);
    }

    @RequestMapping(value = {"/mvc/entry"}, method = RequestMethod.GET)
    public ModelAndView getEntry(@RequestParam("id") String id, ModelMap model) {
        try {
            model.addAttribute("entry", calendarService.getEntry(UUID.fromString(id)));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ModelAndView("entry", model);
    }
}
