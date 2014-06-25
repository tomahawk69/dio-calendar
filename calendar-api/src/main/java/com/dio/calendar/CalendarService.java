package com.dio.calendar;

import com.dio.calendar.datastore.DataStoreFSException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

// TODO: interface without remote
// TODO: remove dataFS exceptons from remote (convert to runtime exceptions


/**
 * RMI:
 * - serializable objects
 * - Remote interface
 * - RemoteException on all methods
 * - RMI registrator
 * - RmiServiceExporter
 * GetFreeTimeline(date, attender)
 * Created by yur on 03.05.2014.
 */
public interface CalendarService extends Remote {

    // Entry manipulations
    Entry newEntry(String subject, String description, Date startDate, Date endDate,
              List<String> attenders, List<Notification> notifications) throws RemoteException;
    Entry editSubject(Entry oldEntry, String subject) throws RemoteException;
    Entry editDescription(Entry oldEntry, String description) throws RemoteException;
    Entry editStartDate(Entry oldEntry, Date startDate) throws RemoteException;
    Entry editEndDate(Entry oldEntry, Date endDate) throws RemoteException;
    Entry editAttenders(Entry oldEntry, List<String> attenders) throws RemoteException;
    Entry editNotifications(Entry oldEntry, List<Notification> notifications) throws RemoteException;

    // data manipulations

    Entry addEntry(Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation, DataStoreFSException, RemoteException;

    Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute, DataStoreFSException, RemoteException;
    Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute, DataStoreFSException, RemoteException;
    Entry removeEntry(Entry entry) throws DataStoreFSException, RemoteException;
    Entry removeEntryById(UUID id) throws DataStoreFSException, RemoteException;

    Entry copyEntry(Entry entry) throws RemoteException;
    TimeInterval[] getFreeTimeLine(String attender, Date date) throws RemoteException;

    Entry getEntry(UUID id) throws RemoteException;

    List<Entry> getEntriesBySubject(String subject) throws RemoteException;
    List<Entry> getEntriesByAttender(String attender) throws RemoteException;

    ArrayList<Entry> getEntries() throws RemoteException;

    void clearData() throws DataStoreFSException, IOException;

    // search for date returns list of entries
    // Time API
    // HashMap<DateFrom, HashMap<DateTo, UUID>>
    // DateFrom <= param + DateTo >= param

    // Add runnable class
    // for loading entry

}
