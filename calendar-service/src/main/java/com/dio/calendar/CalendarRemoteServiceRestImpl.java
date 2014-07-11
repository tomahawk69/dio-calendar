package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStoreImpl;
import com.dio.calendar.datastore.DataStoreFSException;
import com.sun.jersey.api.NotFoundException;
import org.apache.log4j.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

import static com.dio.calendar.ValidateEntry.validateDateRange;

/**
 *
 *
 * Created by iovchynnikov on 4/30/14.
 */
@Path("/")
public class CalendarRemoteServiceRestImpl implements CalendarRemoteService {

    private final CalendarDataStoreImpl data;
    private static Logger logger = Logger.getLogger(CalendarRemoteServiceRestImpl.class);

    public CalendarRemoteServiceRestImpl(CalendarDataStoreImpl data) {
        this.data = data;
    }

    @Path("entry/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public EntryRestWrapper getEntry( @PathParam("id") String id)  {
        System.out.println("get uuid " + id);
        Entry entry = data.getEntry(UUID.fromString(id));
        if (entry == null) {
            throw new NotFoundException(String.format("Entry %s not found", id));
        }
        return new EntryRestWrapper(entry);
    }

    public Entry getEntry(UUID id)  {
        System.out.println("get entry by id " + id);
        return data.getEntry(id);
    }


    public List<Entry> getEntriesBySubject(String subject) {
        logger.info("getEntriesBySubject " + subject);
        return data.getEntryBySubject(subject);
    }


    @Path("entries")
    @GET
    @Produces("application/json")
    public List<EntryRestWrapper> getEntries() {
        logger.info("Get entries execute");
        List<Entry> entries = data.getEntries();

        List<EntryRestWrapper> entriesWrapper = new LinkedList<>();
        if (entries != null) {
            for (Entry entry : entries) {
                entriesWrapper.add(new EntryRestWrapper(entry));
            }
        }
        return entriesWrapper;
    }

    @Path("clear")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void clearData() throws DataStoreFSException, IOException {
        logger.info("Clear data executed");
        //data.clearData();
    }


    public Entry newEntry(String subject, String description, Date startDate, Date endDate,
                          List<String> attenders, List<Notification> notifications) {
        return new Entry.Builder().
                subject(subject).
                description(description).
                startDate(startDate).
                endDate(endDate).
                attenders(attenders).
                notifications(notifications).
                build();
    }

    @Path("updateSubject")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editSubjectRest(@RequestParam List<String> token) {
        logger.info(String.format("Update subject to %s for entry entry with id %s", token.get(1), token.get(0)));
        Entry entry = getEntry(UUID.fromString(token.get(0)));
        if (entry == null) {
            throw new NotFoundException(String.format("Entry with id %s is not found", token.get(0)));
        }
        entry = editSubject(entry, token.get(1));
        if (entry == null) {
            throw new NotFoundException(String.format("Entry with id %s is not found (2)", token.get(0)));
        }
        return Response.ok().entity(new EntryRestWrapper(entry)).type(MediaType.APPLICATION_JSON).build();
    }

    public Entry editSubject(Entry oldEntry, String subject) {
        return new Entry.Builder(oldEntry).
                subject(subject).
                build();
    }

    // TODO
    public Entry editDescription(Entry oldEntry, String description) {
        return new Entry.Builder(oldEntry).
                description(description).
                build();
    }

    // TODO
    public Entry editStartDate(Entry oldEntry, Date startDate) {
        return new Entry.Builder(oldEntry).
                startDate(startDate).
                build();
    }

    // TODO
    public Entry editEndDate(Entry oldEntry, Date endDate) {
        return new Entry.Builder(oldEntry).
                endDate(endDate).
                build();
    }

    // TODO
    public Entry editAttenders(Entry oldEntry, List<String> attenders) {
        return new Entry.Builder(oldEntry).
                attenders(attenders).
                build();
    }

    // TODO
    public Entry editNotifications(Entry oldEntry, List<Notification> notifications) {
        return new Entry.Builder(oldEntry).
                notifications(notifications).
                build();
    }

    // data routines

//    @Path("delete")
//    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
    public void removeEntry(EntryRestWrapper entryWrapper) throws DataStoreFSException {
        logger.info("Removing entry: " + entryWrapper);
        Entry entry = entryWrapper.createEntry();
        if (data.removeEntry(entry.getId()) == null)
            throw new NotFoundException("Entry not found");
    }

    @Path("delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeEntryById(@PathParam("id") String id) throws DataStoreFSException {
        logger.debug("Removing entry with id: " + id);
        data.removeEntry(UUID.fromString(id));
    }

    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute, DataStoreFSException {
        validateDateRange(newEntry.getStartDate(), newEntry.getEndDate());
        logger.info("Updated entry. Old value: " + oldEntry + "; new value: " + newEntry);
        data.updateEntry(newEntry, oldEntry);
        return data.getEntry(newEntry.getId());
    }

    @Path("updateSmart")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRestWrapper updateEntry(@RequestParam List<EntryRestWrapper> token) throws CalendarEntryBadAttribute, DataStoreFSException {
        logger.info("Smart entry update: " + token);
        Entry newEntry = token.get(0).createEntry();
        Entry oldEntry = token.get(1).createEntry();
        Entry resultEntry = data.updateEntry(newEntry, oldEntry);
        if (resultEntry == null) {
            throw new NotFoundException("Entry not found");
        }
        return new EntryRestWrapper(resultEntry);
    }


    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRestWrapper updateEntry(@RequestParam EntryRestWrapper entryWrapper) throws CalendarEntryBadAttribute, DataStoreFSException {
        logger.info("Dire update entry: " + entryWrapper);
        Entry entry = entryWrapper.createEntry();
        validateDateRange(entry.getStartDate(), entry.getEndDate());
        Entry resultEntry = data.updateEntry(entry, null);
        if (resultEntry == null) {
            throw new NotFoundException("Entry not found");
        }
        return new EntryRestWrapper(resultEntry);
    }

    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRestWrapper addEntry(@RequestParam EntryRestWrapper entryWrapper) throws CalendarEntryBadAttribute, CalendarKeyViolation, DataStoreFSException {
        logger.info("Adding entry: " + entryWrapper);
        Entry entry = entryWrapper.createEntry();
        validateDateRange(entry.getStartDate(), entry.getEndDate());
        Entry resultEntry = data.addEntry(entry);
        if (resultEntry == null) {
            throw new NotFoundException("Entry not found");
        }
        return new EntryRestWrapper(resultEntry);
    }

    public Entry copyEntry(Entry entry) {
        return new Entry.Builder(entry).build();
    }
}
