package com.dio.calendar.client;

import com.dio.calendar.CalendarService;
import com.dio.calendar.Entry;
import com.dio.calendar.Notification;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClientWrapperImplTest {

//    TODO: Entry not mock!

    @Test
    public void testNewEntry() throws Exception {
        String subject = "subject";
        String description = "description";
        Date startDate = new Date();
        Date endDate = new Date();
        List<String> attenders = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        CalendarService service = mock(CalendarService.class);
        Entry mockEntry = mock(Entry.class);
        when(service.newEntry(subject, description, startDate, endDate, attenders, notifications)).thenReturn(mockEntry);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        Entry resultEntry = wrapper.newEntry(subject, description, startDate, endDate, attenders, notifications);
        assertEquals(mockEntry, resultEntry);

        //verify(service).newEntry(subject, description, startDate, endDate, attenders, notifications);
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
        CalendarService service = mock(CalendarService.class);
        Entry mockEntry = mock(Entry.class);
        Entry mockEntryReturn = mock(Entry.class);
        when(service.addEntry(mockEntry)).thenReturn(mockEntryReturn);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        Entry resultEntry = wrapper.addEntry(mockEntry);
        assertEquals(mockEntryReturn, resultEntry);
    }

    @Test
    public void testRemoveEntry() throws Exception {
        CalendarService service = mock(CalendarService.class);
        Entry mockEntry = mock(Entry.class);
        Entry mockEntryReturn = mock(Entry.class);
        when(service.removeEntry(mockEntry)).thenReturn(mockEntryReturn);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        Entry resultEntry = wrapper.removeEntry(mockEntry);
        assertEquals(mockEntryReturn, resultEntry);
    }

    @Test
    public void testRemoveEntryUuid() throws Exception {
        CalendarService service = mock(CalendarService.class);
        UUID uuid = UUID.randomUUID();
        Entry mockEntryReturn = mock(Entry.class);
        when(service.removeEntry(uuid)).thenReturn(mockEntryReturn);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        Entry resultEntry = wrapper.removeEntry(uuid);
        assertEquals(mockEntryReturn, resultEntry);
    }

    @Test
    public void testUpdateEntry() throws Exception {
        CalendarService service = mock(CalendarService.class);
        Entry mockEntry = mock(Entry.class);
        Entry mockEntryReturn = mock(Entry.class);
        when(service.updateEntry(mockEntry)).thenReturn(mockEntryReturn);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        Entry resultEntry = wrapper.updateEntry(mockEntry);
        assertEquals(mockEntryReturn, resultEntry);
    }

    @Test
    public void testUpdateEntryEntry() throws Exception {
        CalendarService service = mock(CalendarService.class);
        Entry mockEntryOld = mock(Entry.class);
        Entry mockEntryNew = mock(Entry.class);
        Entry mockEntryReturn = mock(Entry.class);
        when(service.updateEntry(mockEntryNew, mockEntryOld)).thenReturn(mockEntryReturn);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        Entry resultEntry = wrapper.updateEntry(mockEntryNew, mockEntryOld);
        assertEquals(mockEntryReturn, resultEntry);
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
    public void testGetEntriesBySubjectSubject() throws Exception {
        String searchSubject = "mass test";
        CalendarService service = mock(CalendarService.class);
        List<Entry> mockEntries = new ArrayList<>();
        when(service.getEntriesBySubject(searchSubject)).thenReturn(mockEntries);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        List<Entry> results = wrapper.getEntriesBySubject(searchSubject);
        assertEquals(mockEntries, results);
    }

    @Test
    public void testGetEntriesBySubject() throws Exception {
        String searchSubject = "mass test";
        CalendarService service = mock(CalendarService.class);
        List<Entry> mockEntries = new ArrayList<>();
        ClientWrapperImpl wrapper = spy(new ClientWrapperImpl(service));
        doReturn(mockEntries).when(wrapper).getEntriesBySubject(searchSubject);

        List<Entry> results = wrapper.getEntriesBySubject();
        assertEquals(mockEntries, results);
    }

    @Test
    public void testGetEntriesByAttender() throws Exception {

    }

    @Test
    public void testGetEntries() throws Exception {
        CalendarService service = mock(CalendarService.class);
        ArrayList<Entry> mockEntries = new ArrayList<>();
        when(service.getEntries()).thenReturn(mockEntries);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        List<Entry> results = wrapper.getEntries();
        assertEquals(mockEntries, results);
   }

    @Test
    public void testClearData() throws Exception {
        CalendarService service = mock(CalendarService.class);
        ClientWrapper wrapper = new ClientWrapperImpl(service);

        wrapper.clearData();

        verify(service).clearData();
    }
}