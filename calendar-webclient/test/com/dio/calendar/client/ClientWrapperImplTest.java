package com.dio.calendar.client;

import com.dio.calendar.CalendarService;
import com.dio.calendar.Notification;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClientWrapperImplTest {

    @Test
    public void testNewEntry() throws Exception {
        String subject = "subject";
        String description = "description";
        Date startDate = new Date();
        Date endDate = new Date();
        List<String> attenders = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        CalendarService service = mock(CalendarService.class);

        ClientWrapper wrapper = new ClientWrapperImpl(service);

        wrapper.newEntry(subject, description, startDate, endDate, attenders, notifications);

        verify(service).newEntry(subject, description, startDate, endDate, attenders, notifications);
    }

    @Test
    public void testEditSubject() throws Exception {

    }

    @Test
    public void testEditDescription() throws Exception {

    }

    @Test
    public void testEditStartDate() throws Exception {

    }

    @Test
    public void testEditEndDate() throws Exception {

    }

    @Test
    public void testEditAttenders() throws Exception {

    }

    @Test
    public void testEditNotifications() throws Exception {

    }

    @Test
    public void testAddEntry() throws Exception {

    }

    @Test
    public void testRemoveEntry() throws Exception {

    }

    @Test
    public void testUpdateEntry() throws Exception {

    }

    @Test
    public void testUpdateEntry1() throws Exception {

    }

    @Test
    public void testRemoveEntry1() throws Exception {

    }

    @Test
    public void testCopyEntry() throws Exception {

    }

    @Test
    public void testGetFreeTimeLine() throws Exception {

    }

    @Test
    public void testGetEntry() throws Exception {

    }

    @Test
    public void testGetEntriesBySubject() throws Exception {

    }

    @Test
    public void testGetEntriesByAttender() throws Exception {

    }

    @Test
    public void testGetEntries() throws Exception {

    }

    @Test
    public void testClearData() throws Exception {

    }
}