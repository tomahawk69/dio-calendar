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
import javax.xml.ws.Response;
import java.util.*;

/**
 * Created by iovchynnikov on 7/8/2014.
 */
public class ClientWrapperRestImpl {

    private final WebResource restService;
    private final Logger logger = Logger.getLogger(ClientWrapperRestImpl.class);
    private final String servicePath;

    public ClientWrapperRestImpl(String serviceUri, String servicePath) {
        ClientConfig config = new DefaultClientConfig();
//        config.getClasses().add(EntryMessageBodyReader.class);
//        config.getClasses().add(EntryMessageBodyWriter.class);
//        config.getClasses().add(EntriesMessageBodyReader.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(config);
        this.restService = client.resource(UriBuilder.fromUri(serviceUri).build());
        this.servicePath = servicePath;
    }

    public Entry editSubject(Entry oldEntry, String subject) {
        logger.debug(String.format("Update subject to %s of entry %s", subject, oldEntry));
        List<String> requestList = Arrays.asList(oldEntry.getId().toString(), subject);
        EntryRestWrapper entryWrapper = restService.path(servicePath).path("updateSubject").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(requestList).
                post(EntryRestWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    public Entry editDescription(Entry oldEntry, String description) {
        return null;
    }

    
    public Entry editStartDate(Entry oldEntry, Date startDate) {
        return null;
    }

    
    public Entry editEndDate(Entry oldEntry, Date endDate) {
        return null;
    }

    
    public Entry editAttenders(Entry oldEntry, List<String> attenders) {
        return null;
    }

    
    public Entry editNotifications(Entry oldEntry, List<Notification> notifications) {
        return null;
    }

    
    public Entry addEntry(Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation {
        logger.debug("Add entry " + entry);
        EntryRestWrapper entryWrapper = restService.path(servicePath).path("add").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                post(EntryRestWrapper.class, new EntryRestWrapper(entry));
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    public void removeEntryById(UUID id) {
        logger.debug("Remove entry by id " + id);
        restService.
                path(servicePath).path("delete").path(id.toString()).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                delete();
    }

    
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        logger.info(String.format("Smart update entry to %s from %s", newEntry, oldEntry));
        List<EntryRestWrapper> token = new ArrayList<>();
        token.add(new EntryRestWrapper(newEntry));
        token.add(new EntryRestWrapper(oldEntry));
        EntryRestWrapper entryWrapper = restService.
                path(servicePath).path("updateSmart").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(token).
                post(EntryRestWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute {
        logger.debug(String.format("Update entry %s", newEntry));
        EntryRestWrapper entryWrapper = restService.
                path(servicePath).path("update").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(new EntryRestWrapper(newEntry)).
                post(EntryRestWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    public void removeEntry(Entry entry) {
        logger.debug("Remove entry " + entry);
        restService.
                path(servicePath).path("delete").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(new EntryRestWrapper(entry)).
                delete(EntryRestWrapper.class);
    }

    
    public Entry copyEntry(Entry entry) {
        return null;
    }

    
    public TimeInterval[] getFreeTimeLine(String attender, Date date) {
        return new TimeInterval[0];
    }

    
    public Entry getEntry(UUID id) {
        logger.debug("Get entry " + id);
        EntryRestWrapper entryWrapper =
                restService.path(servicePath).path("entry").path(id.toString()).
                        accept(MediaType.APPLICATION_JSON).get(EntryRestWrapper.class);
        if (entryWrapper != null) {
            return entryWrapper.createEntry();
        } else {
            return null;
        }
    }

    
    public List<Entry> getEntriesBySubject(String subject) {
        return null;
    }

    
    public List<Entry> getEntriesByAttender(String attender) {
        return null;
    }

    
    public List<Entry> getEntries() {
        logger.debug("Get entries");
        List<EntryRestWrapper> entriesWrapper =
                restService.path(servicePath).path("entries").
                        accept(MediaType.APPLICATION_JSON).
                        get(new  GenericType<List<EntryRestWrapper>>(){});
        List<Entry> entries = new LinkedList<>();
        if (entriesWrapper != null) {
            for (EntryRestWrapper entryWrapper : entriesWrapper) {
                entries.add(entryWrapper.createEntry());
            }
        }
        return entries;
    }

    
    public void clearData() {
        restService.path(servicePath).path("clear").accept(MediaType.APPLICATION_JSON).post();
    }

}
