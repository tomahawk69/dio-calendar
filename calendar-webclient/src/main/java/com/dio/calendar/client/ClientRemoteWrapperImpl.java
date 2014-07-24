package com.dio.calendar.client;

import com.dio.calendar.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.*;

/**
 * Created by iovchynnikov on 7/8/2014.
 */
public class ClientRemoteWrapperImpl implements ClientRemoteWrapper {

    private final WebResource restService;
    private final Logger logger = Logger.getLogger(ClientRemoteWrapperImpl.class);
    private final String servicePath;

    public static final String UPDATE_SUBJECT = "updateSubject";
    public static final String NEW_ENTRY = "new";
    public static final String ADD_ENTRY = "add";
    public static final String DELETE_ENTRY = "delete";
    public static final String UPDATE_ENTRY = "update";
    public static final String UPDATE_SMART = "updateSmart";
    public static final String GET_ENTRY = "entry";
    public static final String GET_ENTRIES = "entries";

    public ClientRemoteWrapperImpl(String serviceUri, String servicePath) {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(config);
        this.restService = client.resource(UriBuilder.fromUri(serviceUri).build());
        this.servicePath = servicePath;
    }

    public ClientRemoteWrapperImpl(WebResource restService, String servicePath) {
        this.restService = restService;
        this.servicePath = servicePath;
    }

    @Override
    public Entry editSubject(Entry oldEntry, String subject) {
        logger.debug(String.format("Update subject to %s of entry %s", subject, oldEntry));
        List<String> requestList = Arrays.asList(oldEntry.getId().toString(), subject);
        EntryRemoteWrapper entryWrapper = restService.path(servicePath).path(UPDATE_SUBJECT).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(requestList).
                post(EntryRemoteWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    @Override
    public Entry editDescription(Entry oldEntry, String description) {
        return null;
    }

    
    @Override
    public Entry editStartDate(Entry oldEntry, Date startDate) {
        return null;
    }

    
    @Override
    public Entry editEndDate(Entry oldEntry, Date endDate) {
        return null;
    }

    
    @Override
    public Entry editAttenders(Entry oldEntry, List<String> attenders) {
        return null;
    }

    
    @Override
    public Entry editNotifications(Entry oldEntry, List<Notification> notifications) {
        return null;
    }

    @Override
    public Entry newEntry(String subject, String description, Date startDate, Date endDate, List<String> attenders, List<Notification> notifications) {
        logger.debug("New entry");
        List<Object> token = Arrays.asList(subject, description, startDate, endDate, attenders, notifications);
//        result = remoteService.newEntry(subject, description, startDate, endDate, attenders, notifications);

        EntryRemoteWrapper entryWrapper = restService.path(servicePath).
                path(NEW_ENTRY).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(token).
                post(EntryRemoteWrapper.class);

        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    @Override
    public Entry addEntry(Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation {
        logger.debug("Add entry " + entry);
        EntryRemoteWrapper entryWrapper = restService.path(servicePath).path(ADD_ENTRY).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                post(EntryRemoteWrapper.class, new EntryRemoteWrapper(entry));
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    @Override
    public void removeEntryById(UUID id) {
        logger.debug("Remove entry by id " + id);
        restService.
                path(servicePath).path(DELETE_ENTRY).path(id.toString()).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                delete();
    }

    
    @Override
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        logger.debug(String.format("Smart update entry to %s from %s", newEntry, oldEntry));
        List<EntryRemoteWrapper> token =
                Arrays.asList(new EntryRemoteWrapper(newEntry),
                    new EntryRemoteWrapper(oldEntry));
        EntryRemoteWrapper entryWrapper = restService.
                path(servicePath).path(UPDATE_SMART).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(token).
                post(EntryRemoteWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    @Override
    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute {
        logger.debug(String.format("Update entry %s", newEntry));
        EntryRemoteWrapper entryWrapper = restService.
                path(servicePath).path(UPDATE_ENTRY).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(new EntryRemoteWrapper(newEntry)).
                post(EntryRemoteWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    @Override
    public void removeEntry(Entry entry) {
        logger.debug("Remove entry " + entry);
        restService.
                path(servicePath).path(DELETE_ENTRY).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(new EntryRemoteWrapper(entry)).
                delete(EntryRemoteWrapper.class);
    }

    
    @Override
    public Entry copyEntry(Entry entry) {
        return null;
    }

    
    public TimeInterval[] getFreeTimeLine(String attender, Date date) {
        return new TimeInterval[0];
    }

    
    @Override
    public Entry getEntry(UUID id) {
        logger.debug("Get entry " + id);
        EntryRemoteWrapper entryWrapper =
                restService.path(servicePath).path(GET_ENTRY).path(id.toString()).
                        accept(MediaType.APPLICATION_JSON).get(EntryRemoteWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    @Override
    public List<Entry> getEntriesBySubject(String subject) {
        return null;
    }

    
    @Override
    public List<Entry> getEntriesByAttender(String attender) {
        return null;
    }

    
    @Override
    public List<Entry> getEntries() {
        logger.debug("Get entries");
        List<EntryRemoteWrapper> entriesWrapper =
                restService.path(servicePath).path(GET_ENTRIES).
                        accept(MediaType.APPLICATION_JSON).
                        get(new GenericType<List<EntryRemoteWrapper>>(){});

//        System.out.println(entriesWrapper);
//        System.out.println(new GenericType<List<EntryRestWrapper>>(){});
        List<Entry> entries = new LinkedList<>();
        if (entriesWrapper != null) {
            for (EntryRemoteWrapper entryWrapper : entriesWrapper) {
                entries.add(entryWrapper.createEntry());
            }
        }
        return entries;
    }

    
    @Override
    public void clearData() {
        restService.path(servicePath).path("clear").accept(MediaType.APPLICATION_JSON).post();
    }

}
