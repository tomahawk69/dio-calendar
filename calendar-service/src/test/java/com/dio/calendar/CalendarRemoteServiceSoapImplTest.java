package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStoreImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalendarRemoteServiceSoapImplTest {

    @Test
    public void testGetEntry() throws Exception {
        Entry entry = new Entry.Builder().build();
        EntryRemoteWrapper entryWrapper = new EntryRemoteWrapper(entry);
        List<Entry> entries = Arrays.asList(entry);
        List<EntryRemoteWrapper> resultExpected = Arrays.asList(entryWrapper);

        CalendarDataStoreImpl mockDataStore = mock(CalendarDataStoreImpl.class);
        CalendarRemoteServiceSoapImpl service = new CalendarRemoteServiceSoapImpl(mockDataStore);

        when(mockDataStore.getEntries()).thenReturn(entries);

        List<EntryRemoteWrapper> resultReceived = service.getEntries();

        assertEquals(resultExpected, resultReceived);

    }

    @Test
    public void testGetEntryNull() throws Exception {
        CalendarDataStoreImpl mockDataStore = mock(CalendarDataStoreImpl.class);
        CalendarRemoteServiceSoapImpl service = new CalendarRemoteServiceSoapImpl(mockDataStore);

        when(mockDataStore.getEntries()).thenReturn(null);

        List<EntryRemoteWrapper> resultReceived = service.getEntries();

        assertEquals(0, resultReceived.size());

    }

    @Test
    public void testGetEntries() throws Exception {
        Entry entry = new Entry.Builder().build();
        EntryRemoteWrapper resultExpected = new EntryRemoteWrapper(entry);

        CalendarDataStoreImpl mockDataStore = mock(CalendarDataStoreImpl.class);
        CalendarRemoteServiceSoapImpl service = new CalendarRemoteServiceSoapImpl(mockDataStore);

        when(mockDataStore.getEntry(entry.getId())).thenReturn(entry);

        EntryRemoteWrapper resultReceived = service.getEntry(entry.getId().toString());

        assertEquals(resultExpected, resultReceived);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetEntriesNull() throws Exception {
        Entry entry = new Entry.Builder().build();
        EntryRemoteWrapper resultExpected = new EntryRemoteWrapper(entry);

        CalendarDataStoreImpl mockDataStore = mock(CalendarDataStoreImpl.class);
        CalendarRemoteServiceSoapImpl service = new CalendarRemoteServiceSoapImpl(mockDataStore);

        thrown.expect(NullPointerException.class);

        when(mockDataStore.getEntry(entry.getId())).thenReturn(null);

        service.getEntry(entry.getId().toString());
    }
}