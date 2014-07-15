package com.dio.calendar.client;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryRestWrapper;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;

import javax.jws.WebResult;
import javax.jws.WebService;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        when(mockService.path("updateSubject")).thenReturn(mockService);
        when(mockService.accept(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockService.type(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
//        when(mockService.entity(equals(requestList))).thenReturn(mockBuilder);
        argument.capture()
        when(mockService.entity(argThat(equals(requestList)))).thenReturn(mockBuilder);
        when(mockService.post(EntryRestWrapper.class)).thenReturn(entryWrapper);
        when(entryWrapper.createEntry()).thenReturn(expectedEntry);

        Entry resultEntry = wrapper.editSubject(oldEntry, testSubject);

        assertEquals(expectedEntry, resultEntry);

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
    public void testNewEntry() throws Exception {

    }

    @Test
    public void testAddEntry() throws Exception {

    }

    @Test
    public void testRemoveEntryById() throws Exception {

    }

    @Test
    public void testUpdateEntry() throws Exception {

    }

    @Test
    public void testUpdateEntry1() throws Exception {

    }

    @Test
    public void testRemoveEntry() throws Exception {

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