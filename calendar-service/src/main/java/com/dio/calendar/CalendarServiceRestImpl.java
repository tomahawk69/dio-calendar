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
public class CalendarServiceRestImpl implements CalendarService {

    private final CalendarDataStoreImpl data;
    private static Logger logger = Logger.getLogger(CalendarServiceRemoteImpl.class);

    public CalendarServiceRestImpl(CalendarDataStoreImpl data) {
        this.data = data;
    }

    @Override
    public TimeInterval[] getFreeTimeLine(String attender, Date date) {
        List<TimeInterval> result = new ArrayList<>();
        // stuff to get results
        return result.toArray(new TimeInterval[result.size()]);
    }

    @Path("entry/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Entry getEntry( @PathParam("id") UUID id)  {
        System.out.println("get uuid " + id);
        return data.getEntry(id);
    }

    @Override
    public List<Entry> getEntriesBySubject(String subject) {
        logger.info("getEntriesBySubject " + subject);
        return data.getEntryBySubject(subject);
    }

    @Override
    public List<Entry> getEntriesByAttender(String attender) {
        return data.getEntryByAttender(attender);
    }

    @Override
    @Path("entries")
    @GET
    @Produces("application/json")
    public List<Entry> getEntries() {
        logger.info("Get entries execute");
        return data.getEntries();
    }

    @Override
    @Path("clear")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public void clearData() throws DataStoreFSException, IOException {
        logger.info("Clear data executed");
        //data.clearData();
    }


    @Override
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
        Entry entry = editSubject(getEntry(UUID.fromString(token.get(0))), token.get(1));
        if(entry == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for UUID: " + token.get(0)).build();
        }
        return Response.ok().entity(entry).type(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Entry editSubject(Entry oldEntry, String subject) {
        return new Entry.Builder(oldEntry).
                subject(subject).
                build();
    }

    @Override
    public Entry editDescription(Entry oldEntry, String description) {
        return new Entry.Builder(oldEntry).
                description(description).
                build();
    }

    @Override
    public Entry editStartDate(Entry oldEntry, Date startDate) {
        return new Entry.Builder(oldEntry).
                startDate(startDate).
                build();
    }

    @Override
    public Entry editEndDate(Entry oldEntry, Date endDate) {
        return new Entry.Builder(oldEntry).
                endDate(endDate).
                build();
    }

    @Override
    public Entry editAttenders(Entry oldEntry, List<String> attenders) {
        return new Entry.Builder(oldEntry).
                attenders(attenders).
                build();
    }

    @Override
    public Entry editNotifications(Entry oldEntry, List<Notification> notifications) {
        return new Entry.Builder(oldEntry).
                notifications(notifications).
                build();
    }

    // data routines

    @Override
//    @Path("delete")
//    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
    public void removeEntry(Entry entry) throws DataStoreFSException {
        logger.info("Removing entry: " + entry);
        if (data.removeEntry(entry.getId()) == null)
            throw new NotFoundException("Entry not found");
    }

    @Override
    @Path("delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeEntryById(@PathParam("id") UUID id) throws DataStoreFSException {
        logger.debug("Removing entry with id: " + id);
        data.removeEntry(id);
    }

    @Override
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
    public Entry updateEntry(@RequestParam List<Entry> token) throws CalendarEntryBadAttribute, DataStoreFSException {
        logger.info("Smart entry update: " + token);
        return data.updateEntry(token.get(0), token.get(1));
    }


    @Override
    @Path("update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Entry updateEntry(@RequestParam Entry newEntry) throws CalendarEntryBadAttribute, DataStoreFSException {
        validateDateRange(newEntry.getStartDate(), newEntry.getEndDate());
        logger.info("Dire update entry: " + newEntry);
        return data.updateEntry(newEntry, null);
    }

    @Override
    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Entry addEntry(@RequestParam Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation, DataStoreFSException {
        logger.info("Adding entry: " + entry);
        validateDateRange(entry.getStartDate(), entry.getEndDate());
        return data.addEntry(entry);
    }

    @Override
    public Entry copyEntry(Entry entry) {
        return new Entry.Builder(entry).build();
    }
}
