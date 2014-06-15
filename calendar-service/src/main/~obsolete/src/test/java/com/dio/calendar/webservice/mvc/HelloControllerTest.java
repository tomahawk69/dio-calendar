package com.dio.calendar.webservice.mvc;

import com.dio.calendar.CalendarServiceImpl;
import com.dio.calendar.Entry;
import org.junit.Test;
import org.springframework.ui.ModelMap;

import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class HelloControllerTest {

    @Test
    public void testGetEntries() throws Exception {
        // initialize variable inputs

        // initialize mocks
        CalendarServiceImpl calendarService = mock(CalendarServiceImpl.class);
        ModelMap model = mock(ModelMap.class);
        Collection<Entry> mockEntries = mock(Collection.class);
        when(calendarService.getEntries()).thenReturn(mockEntries);

        // initialize class to test
        HelloController controller = new HelloController(calendarService);

        // invoke method on class to test
        controller.getEntries(model);
        // assert return value

        // verify mock expectations
        verify(calendarService).getEntries();
        verify(model).addAttribute("entries", mockEntries);
        verify(model).addAttribute("message", "This is index page of entries");
    }

    @Test
    public void testGetEntry() throws Exception {
        // initialize variable inputs
        UUID id = UUID.randomUUID();
        // initialize mocks
        CalendarServiceImpl calendarService = mock(CalendarServiceImpl.class);
        ModelMap model = mock(ModelMap.class);
        Entry mockEntry = mock(Entry.class);
        when(calendarService.getEntry(id)).thenReturn(mockEntry);

        // initialize class to test
        HelloController controller = new HelloController(calendarService);

        // invoke method on class to test
        controller.getEntry(id.toString(), model);
        // assert return value

        // verify mock expectations
        verify(calendarService).getEntry(id);
        verify(model).addAttribute("entry", mockEntry);
    }
}