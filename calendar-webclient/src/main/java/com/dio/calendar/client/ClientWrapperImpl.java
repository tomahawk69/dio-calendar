package com.dio.calendar.client;

import com.dio.calendar.*;
import com.dio.calendar.datastore.DataStoreFSException;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created by yur on 17.06.2014.
 */
public class ClientWrapperImpl implements ClientWrapper {
    private final CalendarService remoteService;
    private final Logger logger = Logger.getLogger(ClientWrapperImpl.class);

    private List<Entry> searchResults;

    private String searchSubject;

    public void setSearchSubject(String searchTerms) {
        this.searchSubject = searchTerms;
    }

    public String getSearchSubject() {
        return searchSubject;
    }

    public ClientWrapperImpl(CalendarService remoteService) {
        this.remoteService = remoteService;
    }

    @Override
    public Entry newEntry(String subject, String description, Date startDate, Date endDate, List<String> attenders, List<Notification> notifications) {
        Entry result;
        try {
            result = remoteService.newEntry(subject, description, startDate, endDate, attenders, notifications);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
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
        Entry result;
        try {
            result = remoteService.addEntry(entry);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (DataStoreFSException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Entry removeEntry(UUID id) {
        logger.info("remove entry UUID " + id);
        Entry result;
        try {
            result = remoteService.removeEntry(id);
        } catch (DataStoreFSException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        Entry result;
        try {
            result = remoteService.updateEntry(newEntry, oldEntry);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (DataStoreFSException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute {
        Entry result;
        try {
            result = remoteService.updateEntry(newEntry);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (DataStoreFSException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Entry removeEntry(Entry entry) {
        Entry result;
        try {
            result = remoteService.removeEntry(entry);
        } catch (DataStoreFSException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
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
        return null;
    }

    @Override
    public List<Entry> getEntriesBySubject(String subject) {
        logger.info("getEntriesBySubject");
        List<Entry> result = null;
        try {
            result = remoteService.getEntriesBySubject(subject);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Entry> getEntriesBySubject() {
        logger.info("getEntriesBySubject wrapper");
        return getEntriesBySubject(searchSubject);
    }

    @Override
    public List<Entry> getEntriesByAttender(String attender) {
        return null;
    }

    @Override
    public ArrayList<Entry> getEntries() {
        ArrayList<Entry> result = null;
        try {
            result = remoteService.getEntries();
        } catch (RemoteException e) {
            logger.error(e);
        }
        return result;
    }

    @Override
    public void clearData() {
        try {
            remoteService.clearData();
        } catch (DataStoreFSException | IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }

    }

}
