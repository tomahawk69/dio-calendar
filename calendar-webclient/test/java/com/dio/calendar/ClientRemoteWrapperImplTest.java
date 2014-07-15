package com.dio.calendar;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryRestWrapper;
import com.dio.calendar.client.ClientRemoteWrapperImpl;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;

import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class ClientRemoteWrapperImplTest {
    private final String servicePath = "rest";

    @Test
    public void testEditSubject() throws Exception {
        String testSubject = "Test subject";
        List<String> requestList;

        Entry oldEntry = new Entry.Builder().id(UUID.randomUUID()).build();
        Entry expectedEntry = new Entry.Builder().subject(testSubject).build();

        requestList = Arrays.asList(oldEntry.getId().toString(), testSubject);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);
        EntryRestWrapper entryWrapper = mock(EntryRestWrapper.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.UPDATE_SUBJECT)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(requestList)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(entryWrapper);
        when(entryWrapper.createEntry()).thenReturn(expectedEntry);

        Entry resultEntry = wrapper.editSubject(oldEntry, testSubject);

        assertEquals(expectedEntry, resultEntry);
    }

    @Test
    public void testEditSubjectNull() throws Exception {
        String testSubject = "Test subject";
        List<String> requestList;

        Entry oldEntry = new Entry.Builder().id(UUID.randomUUID()).build();
        Entry expectedEntry = new Entry.Builder().subject(testSubject).build();

        requestList = Arrays.asList(oldEntry.getId().toString(), testSubject);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);
        EntryRestWrapper entryWrapper = mock(EntryRestWrapper.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.UPDATE_SUBJECT)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(requestList)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(null);

        Entry resultEntry = wrapper.editSubject(oldEntry, testSubject);

        assertNull(resultEntry);

        verify(entryWrapper, times(0)).createEntry();
    }

    // same as previous
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
    public void testNewEntry() throws Exception {

        String subject = "subject";
        String description = "description";
        Date startDate = new Date();
        Date endDate = new Date();
        List<String> attenders = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        List<Object> token = Arrays.asList(subject, description, startDate, endDate, attenders, notifications);

        Entry expectedEntry = new Entry.Builder().
                subject(subject).
                description(description).
                startDate(startDate).
                endDate(endDate).
                attenders(attenders).
                notifications(notifications).
                build();

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);
        EntryRestWrapper entryWrapper = mock(EntryRestWrapper.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.NEW_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(token)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(entryWrapper);
        when(entryWrapper.createEntry()).thenReturn(expectedEntry);

        Entry resultEntry = wrapper.newEntry(subject, description,
                startDate, endDate, attenders, notifications);

        assertEquals(expectedEntry, resultEntry);

    }

    @Test
    public void testNewEntryNull() throws Exception {

        List<Object> token = Arrays.asList(null, null, null, null, null, null);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);
        EntryRestWrapper entryWrapper = mock(EntryRestWrapper.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.NEW_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(token)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(null);

        Entry resultEntry = wrapper.newEntry(null, null,null, null, null, null);

        assertNull(resultEntry);

        verify(entryWrapper, times(0)).createEntry();

    }

    @Test
    public void testAddEntry() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        EntryRestWrapper entryWrapper = new EntryRestWrapper(entry);
        Entry expectedEntry = new Entry.Builder().id(entry.getId()).build();
        EntryRestWrapper expectedEntryWrapper = mock(EntryRestWrapper.class);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.ADD_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class, entryWrapper)).thenReturn(expectedEntryWrapper);
        when(expectedEntryWrapper.createEntry()).thenReturn(expectedEntry);

        Entry resultEntry = wrapper.addEntry(entry);

        assertEquals(expectedEntry, resultEntry);
    }

    @Test
    public void testAddEntryNull() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        EntryRestWrapper entryWrapper = new EntryRestWrapper(entry);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.ADD_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class, entryWrapper)).thenReturn(null);

        Entry resultEntry = wrapper.addEntry(entry);

        assertNull(resultEntry);
    }

    @Test
    public void testRemoveEntryById() throws Exception {
        UUID id = UUID.randomUUID();

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.DELETE_ENTRY)).thenReturn(mockService);
        when(mockService.path(id.toString())).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);

        wrapper.removeEntryById(id);

        verify(mockBuilder).delete();

    }

    @Test
    public void testUpdateEntrySmart() throws Exception {
        Entry newEntry = new Entry.Builder().id(UUID.randomUUID()).build();
        Entry oldEntry = new Entry.Builder().id(newEntry.getId()).build();
        Entry expectedEntry = new Entry.Builder().id(newEntry.getId()).build();
        EntryRestWrapper mockEntryWrapper = mock(EntryRestWrapper.class);

        List<EntryRestWrapper> token =
                Arrays.asList(new EntryRestWrapper(newEntry),
                        new EntryRestWrapper(oldEntry));

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.UPDATE_SMART)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(token)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(mockEntryWrapper);
        when(mockEntryWrapper.createEntry()).thenReturn(expectedEntry);

        Entry resultEntry = wrapper.updateEntry(newEntry, oldEntry);

        assertEquals(expectedEntry, resultEntry);

    }

    @Test
    public void testUpdateEntrySmartNull() throws Exception {
        Entry newEntry = new Entry.Builder().id(UUID.randomUUID()).build();
        Entry oldEntry = new Entry.Builder().id(newEntry.getId()).build();

        List<EntryRestWrapper> token =
                Arrays.asList(new EntryRestWrapper(newEntry),
                        new EntryRestWrapper(oldEntry));

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.UPDATE_SMART)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(token)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(null);

        Entry resultEntry = wrapper.updateEntry(newEntry, oldEntry);

        assertNull(resultEntry);

    }

    @Test
    public void testUpdateEntry() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        Entry expectedEntry = new Entry.Builder().id(entry.getId()).build();
        EntryRestWrapper entryWrapper = new EntryRestWrapper(entry);
        EntryRestWrapper mockEntryWrapper = mock(EntryRestWrapper.class);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.UPDATE_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(entryWrapper)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(mockEntryWrapper);
        when(mockEntryWrapper.createEntry()).thenReturn(expectedEntry);

        Entry resultEntry = wrapper.updateEntry(entry);

        assertEquals(expectedEntry, resultEntry);
    }

    @Test
    public void testUpdateEntryNull() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        EntryRestWrapper entryWrapper = new EntryRestWrapper(entry);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.UPDATE_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(entryWrapper)).thenReturn(mockBuilder);
        when(mockBuilder.post(EntryRestWrapper.class)).thenReturn(null);

        Entry resultEntry = wrapper.updateEntry(entry);

        assertNull(resultEntry);
    }

    @Test
    public void testRemoveEntry() throws Exception {
        Entry entry = new Entry.Builder().id(UUID.randomUUID()).build();
        EntryRestWrapper entryWrapper = new EntryRestWrapper(entry);

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.DELETE_ENTRY)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.entity(entryWrapper)).thenReturn(mockBuilder);

        wrapper.removeEntry(entry);

        verify(mockBuilder).delete(EntryRestWrapper.class);
    }

    @Test
    public void testCopyEntry() throws Exception {

    }

    @Test
    public void testGetFreeTimeLine() throws Exception {

    }

    @Test
    public void testGetEntry() throws Exception {
        UUID id = UUID.randomUUID();
        Entry expectedEntry = new Entry.Builder().id(id).build();

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        EntryRestWrapper resultEntryWrapper = new EntryRestWrapper(expectedEntry);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.GET_ENTRY)).thenReturn(mockService);
        when(mockService.path(id.toString())).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.get(EntryRestWrapper.class)).thenReturn(resultEntryWrapper);

        Entry resultEntry = wrapper.getEntry(id);

        assertEquals(expectedEntry, resultEntry);

    }

    @Test
    public void testGetEntryNull() throws Exception {
        UUID id = UUID.randomUUID();

        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.GET_ENTRY)).thenReturn(mockService);
        when(mockService.path(id.toString())).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.get(EntryRestWrapper.class)).thenReturn(null);

        Entry resultEntry = wrapper.getEntry(id);

        assertNull(resultEntry);

    }

    @Test
    public void testGetEntriesBySubject() throws Exception {

    }

    @Test
    public void testGetEntriesByAttender() throws Exception {

    }

    @Test
    public void testGetEntries() throws Exception {
        WebResource mockService = mock(WebResource.class);
        WebResource.Builder mockBuilder = mock(WebResource.Builder.class);

        Entry entry1 = new Entry.Builder().id(UUID.randomUUID()).build();
        Entry entry2 = new Entry.Builder().id(UUID.randomUUID()).build();
        EntryRestWrapper wrapperItem1 = new EntryRestWrapper(entry1);
        EntryRestWrapper wrapperItem2 = new EntryRestWrapper(entry2);

        List<EntryRestWrapper> resultEntriesWrapper = Arrays.asList(wrapperItem1, wrapperItem2);
        List<Entry> expectedEntries = Arrays.asList(entry1, entry2);

        ClientRemoteWrapperImpl wrapper = new ClientRemoteWrapperImpl(mockService, servicePath);

        when(mockService.path(servicePath)).thenReturn(mockService);
        when(mockService.path(ClientRemoteWrapperImpl.GET_ENTRIES)).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
//        when(mockBuilder.get(resultEntriesWrapper.getClass())).thenReturn(resultEntriesWrapper);
        when(mockBuilder.get(new GenericType<List<EntryRestWrapper>>(){})).thenReturn(resultEntriesWrapper);

        List<Entry> resultEntries = wrapper.getEntries();

        GenericType type = new GenericType<List<EntryRestWrapper>>(){};
        System.out.println(type.getClass());
//        type = (GenericType) type.getType();
//        System.out.println(type.getType());


        verify(mockService).accept(MediaType.APPLICATION_JSON);
//        verify(mockService).get(type);

//        assertEquals(expectedEntries, resultEntries);
    }

    @Test
    public void testClearData() throws Exception {

    }
}