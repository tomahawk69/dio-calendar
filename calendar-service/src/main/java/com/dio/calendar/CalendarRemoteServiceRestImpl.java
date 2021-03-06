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
import java.text.ParseException;
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
    public EntryRemoteWrapper getEntry( @PathParam("id") String id)  {
        logger.debug("get uuid " + id);
        Entry entry = data.getEntry(UUID.fromString(id));
        if (entry == null) {
            throw new NotFoundException(String.format("Entry %s not found", id));
        }
        return new EntryRemoteWrapper(entry);
    }

    public Entry getEntry(UUID id)  {
        logger.debug("get entry by id " + id);
        return data.getEntry(id);
    }


    public List<Entry> getEntriesBySubject(String subject) {
        logger.debug("getEntriesBySubject " + subject);
        return data.getEntryBySubject(subject);
    }


    @Path("entries")
    @GET
    @Produces("application/json")
    public List<EntryRemoteWrapper> getEntries() {
        logger.debug("Get entries execute");
        List<Entry> entries = data.getEntries();

        List<EntryRemoteWrapper> entriesWrapper = new LinkedList<>();
        if (entries != null) {
            for (Entry entry : entries) {
                entriesWrapper.add(new EntryRemoteWrapper(entry));
            }
        }
        return entriesWrapper;
    }

    @Path("clear")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void clearData() throws DataStoreFSException, IOException {
        logger.warn("Clear data temporarily switched off");
        //data.clearData();
    }


    @Path("updateSubject")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editSubjectRest(@RequestParam List<String> token) {
        logger.debug(String.format("Update subject to %s for entry entry with id %s", token.get(1), token.get(0)));
        Entry entry = getEntry(UUID.fromString(token.get(0)));
        if (entry == null) {
            throw new NotFoundException(String.format("Entry with id %s is not found", token.get(0)));
        }
        entry = editSubject(entry, token.get(1));
        if (entry == null) {
            throw new NotFoundException(String.format("Entry with id %s is not found (2)", token.get(0)));
        }
        return Response.ok().entity(new EntryRemoteWrapper(entry)).type(MediaType.APPLICATION_JSON).build();
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
    public void removeEntry(EntryRemoteWrapper entryWrapper) throws DataStoreFSException {
        logger.debug("Removing entry: " + entryWrapper);
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
        logger.debug("Updated entry. Old value: " + oldEntry + "; new value: " + newEntry);
        data.updateEntry(newEntry, oldEntry);
        return data.getEntry(newEntry.getId());
    }

    @Path("updateSmart")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRemoteWrapper updateEntry(@RequestParam List<EntryRemoteWrapper> token) throws CalendarEntryBadAttribute, DataStoreFSException {
        logger.debug("Smart entry update: " + token);
        Entry newEntry = token.get(0).createEntry();
        Entry oldEntry = token.get(1).createEntry();
        Entry resultEntry = data.updateEntry(newEntry, oldEntry);
        if (resultEntry == null) {
            throw new NotFoundException("Entry not found");
        }
        return new EntryRemoteWrapper(resultEntry);
    }


    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRemoteWrapper updateEntry(@RequestParam EntryRemoteWrapper entryWrapper) throws CalendarEntryBadAttribute, DataStoreFSException {
        logger.debug("Dire update entry: " + entryWrapper);
        Entry entry = entryWrapper.createEntry();
        validateDateRange(entry.getStartDate(), entry.getEndDate());
        Entry resultEntry = data.updateEntry(entry, null);
        if (resultEntry == null) {
            throw new NotFoundException("Entry not found");
        }
        return new EntryRemoteWrapper(resultEntry);
    }


    // FIXME: send parameters as entry

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRemoteWrapper newEntry(List<Object> token) {
        try {
            Entry entry = newEntry((String)token.get(0), (String)token.get(1),
                    EntryApi.stringToDate((String)token.get(2)),
                    EntryApi.stringToDate((String)token.get(3)),
                    (List<String>)token.get(4),
                    null);
            return new EntryRemoteWrapper(entry);
        } catch (ParseException e) {
            logger.error(e);
            throw new WebApplicationException(e);
        }
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

    // TODO: Response
    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntryRemoteWrapper addEntry(@RequestParam EntryRemoteWrapper entryWrapper) throws CalendarEntryBadAttribute, CalendarKeyViolation, DataStoreFSException {
        logger.debug("Adding entry: " + entryWrapper);
        Entry entry = entryWrapper.createEntry();
        validateDateRange(entry.getStartDate(), entry.getEndDate());
        Entry resultEntry = data.addEntry(entry);
        if (resultEntry == null) {
            throw new NotFoundException("Entry not found");
        }
        return new EntryRemoteWrapper(resultEntry);
    }

    public Entry copyEntry(Entry entry) {
        return new Entry.Builder(entry).build();
    }
}
