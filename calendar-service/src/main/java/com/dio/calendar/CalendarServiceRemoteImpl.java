package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStore;
import com.dio.calendar.datastore.DataStoreFSException;
import org.apache.log4j.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import static com.dio.calendar.ValidateEntry.validateDateRange;

/**
 *
 *
 * Created by iovchynnikov on 4/30/14.
 */
public class CalendarServiceRemoteImpl implements CalendarService {
    private final CalendarDataStore data;
    private static Logger logger = Logger.getLogger(CalendarServiceRemoteImpl.class);

    public CalendarServiceRemoteImpl(CalendarDataStore data) {
        this.data = data;
    }

    @Override
    public TimeInterval[] getFreeTimeLine(String attender, Date date) {
        List<TimeInterval> result = new ArrayList<>();
        // stuff to get results
        return result.toArray(new TimeInterval[result.size()]);
    }

    @Override
    public Entry getEntry(UUID id) throws RemoteException {
        return data.getEntry(id);
    }

    @Override
    public List<Entry> getEntriesBySubject(String subject) {
        logger.debug("getEntriesBySubject " + subject);
        return data.getEntryBySubject(subject);
    }

    @Override
    public List<Entry> getEntriesByAttender(String attender) {
        return data.getEntryByAttender(attender);
    }

    @Override
    public List<Entry> getEntries() {
        return data.getEntries();
    }

    @Override
    public void clearData() throws DataStoreFSException, IOException {
        data.clearData();
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
    public void removeEntry(Entry entry) throws DataStoreFSException {
        logger.debug("Removing entry: " + entry);
        data.removeEntry(entry.getId());
    }

    @Override
    public void removeEntryById(UUID id) throws DataStoreFSException {
        logger.debug("Removing entry with id: " + id);
        data.removeEntry(id);
    }

    @Override
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute, DataStoreFSException {
        validateDateRange(newEntry.getStartDate(), newEntry.getEndDate());
        logger.debug("Updated entry. Old value: " + oldEntry + "; new value: " + newEntry);
        data.updateEntry(newEntry, oldEntry);
        return data.getEntry(newEntry.getId());
    }

    @Override
    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute, DataStoreFSException {
        validateDateRange(newEntry.getStartDate(), newEntry.getEndDate());
        logger.debug("Full update entry: " + newEntry);
        return data.updateEntry(newEntry, null);
    }

    @Override
    public Entry addEntry(Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation, DataStoreFSException {
        logger.debug("Adding entry: " + entry);
        validateDateRange(entry.getStartDate(), entry.getEndDate());
        return data.addEntry(entry);
    }

    @Override
    public Entry copyEntry(Entry entry) {
        return new Entry.Builder(entry).build();
    }
}
