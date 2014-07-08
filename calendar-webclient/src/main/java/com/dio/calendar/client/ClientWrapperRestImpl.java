package com.dio.calendar.client;

import com.dio.calendar.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/8/2014.
 */
public class ClientWrapperRestImpl implements ClientWrapper {

    private final WebResource restService;
    private final Logger logger = Logger.getLogger(ClientWrapperRestImpl.class);
    private final String servicePath;

    public ClientWrapperRestImpl(String serviceUri, String servicePath) {
        ClientConfig config = new DefaultClientConfig();
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
        return null;
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
        System.out.println("add entry " + entry);

        return restService.path(servicePath).path("add").
                accept(MediaType.APPLICATION_JSON).
                type(MediaType.APPLICATION_JSON).
                post(Entry.class, entry);
    }

    @Override
    public Entry removeEntryById(UUID id) {
        return null;
    }

    @Override
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        return null;
    }

    @Override
    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute {
        return null;
    }

    @Override
    public Entry removeEntry(Entry entry) {
        return null;
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
        System.out.println("get entry " + id);
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
        List<Entry> entries = restService.path(servicePath).path("entries").accept(MediaType.APPLICATION_JSON).get(new  GenericType<List<Entry>>(){});
//        System.out.println(entries);
//        EntryWrapper[] entries = restService.path(servicePath).path("entries").accept(MediaType.APPLICATION_JSON).get(EntryWrapper[].class);
//        System.out.println(entries);
//        EntriesWrapper wrapper = restService.path(servicePath).path("entries").accept(MediaType.APPLICATION_JSON).get(EntriesWrapper.class);
//        EntryWrapper wrapper = restService.path(servicePath).path("entries").accept(MediaType.APPLICATION_JSON).get(EntryWrapper.class);
//        return wrapper.getEntries();
        return entries;
    }

    @Override
    public void clearData() {

    }



//    public <T> getResourceObject(String path, T object) {
//        return restService.path(servicePath).path(path).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//    }
}
