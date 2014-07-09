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
public class ClientWrapperRestImpl implements ClientWrapper {

    private final WebResource restService;
    private final Logger logger = Logger.getLogger(ClientWrapperRestImpl.class);
    private final String servicePath;

    public ClientWrapperRestImpl(String serviceUri, String servicePath) {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(EntryMessageBodyReader.class);
        config.getClasses().add(EntryMessageBodyWriter.class);
        config.getClasses().add(EntriesMessageBodyReader.class);
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(config);
        this.restService = client.resource(UriBuilder.fromUri(serviceUri).build());
        this.servicePath = servicePath;
    }

    @Override
    public Entry newEntry(String subject, String description, Date startDate, Date endDate, List<String> attenders, List<Notification> notifications) {
        return null;
    }

    @Override
    public Entry editSubject(Entry oldEntry, String subject) {
        logger.debug(String.format("Update subject to %s of entry %s", subject, oldEntry));
        List<String> requestList = Arrays.asList(oldEntry.getId().toString(), subject);
        Entry entry = restService.path(servicePath).path("updateSubject").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(requestList).
                post(Entry.class);
        return entry;
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
    public Entry addEntry(Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation {
        logger.debug("Add entry " + entry);
        return restService.path(servicePath).path("add").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                post(Entry.class, entry);
    }

    @Override
    public void removeEntryById(UUID id) {
        logger.debug("Remove entry by id " + id);
        restService.
                path(servicePath).path("delete").path(id.toString()).
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                delete();
    }

    @Override
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        return null;
    }

    @Override
    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute {
        logger.debug(String.format("Update entry %s", newEntry));
        return restService.
                path(servicePath).path("update").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(newEntry).
                post(Entry.class);
    }

    @Override
    public void removeEntry(Entry entry) {
        logger.debug("Remove entry " + entry);
        restService.
                path(servicePath).path("delete").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                entity(entry).
                delete(Entry.class);
    }

    @Override
    public Entry copyEntry(Entry entry) {
        return null;
    }

    @Override
    public TimeInterval[] getFreeTimeLine(String attender, Date date) {
        return new TimeInterval[0];
    }

    @Override
    public Entry getEntry(UUID id) {
        logger.debug("Get entry " + id);
        return restService.path(servicePath).path("entry").path(id.toString()).accept(MediaType.APPLICATION_JSON).get(Entry.class);
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
        List<Entry> entries = restService.path(servicePath).path("entries").accept(MediaType.APPLICATION_JSON).get(new  GenericType<List<Entry>>(){});
        return entries;
    }

    @Override
    public void clearData() {
        restService.path(servicePath).path("clear").accept(MediaType.APPLICATION_JSON).post();
    }



//    public <T> getResourceObject(String path, T object) {
//        return restService.path(servicePath).path(path).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//    }
}
