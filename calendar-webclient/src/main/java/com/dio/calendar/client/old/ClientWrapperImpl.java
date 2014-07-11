package com.dio.calendar.client.old;

import com.dio.calendar.*;
import com.dio.calendar.datastore.DataStoreFSException;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yur on 17.06.2014.
 */
public class ClientWrapperImpl implements ClientWrapper, Serializable {
    private final CalendarService remoteService;
    private final Logger logger = Logger.getLogger(ClientWrapperImpl.class);

    private List<Entry> searchResults;

    private String searchSubject;
    private Boolean isSearch = false;

    public Boolean getIsSearch() {
        return isSearch;
    }
    public void setIsSearch(Boolean isSearch) {
        this.isSearch = isSearch;
    }

    public void showSearch() {
        isSearch = true;
    }

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

    public String addEntry() {
        Entry result;
        logger.info("add entry");
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        logger.info(params);
        String subject = params.get("addForm:subject");
        String description = params.get("addForm:description");

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date dateFrom = null;
        String date = params.get("addForm:dateFrom_input");
        if (StringUtils.isNotBlank(date)) {
            try {
                dateFrom = dateFormat.parse(date);
            } catch (ParseException e) {
                logger.error(e);
            }
        }

        Date dateTo = null;
        date = params.get("addForm:dateTo_input");
        if (StringUtils.isNotBlank(date)) {
            try {
                dateTo = dateFormat.parse(date);
            } catch (ParseException e) {
                logger.error(e);
            }
        }

//        logger.info("subject:" + subject);
//        logger.info("description:" + description);
//        logger.info("dateFrom:" + dateFrom);
//        logger.info("dateTo:" + dateTo);

        try {
            result = addEntry(newEntry(subject, description, dateFrom, dateTo, null, null));
            setGrowlInfo("Successfully added entry", "Entry was successfully added. New id is " + result.getId());
        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            logger.error(e);
            setGrowlError("Error", "Entry was not added: " + e.getMessage());
        }


//        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        String subject = request.getParameter("addForm:subject");
//        System.out.println(subject);

        return "add";
    }

    @Override
    public void removeEntryById(UUID id) {
        logger.info("remove entry by UUID " + id);
        try {
            remoteService.removeEntryById(id);
            // FIXME: setGrowlInfo in tests
            //setGrowlInfo("Entry removed", new StringBuffer("Entry with id ").append(id).append(" was successfully removed").toString());
        } catch (DataStoreFSException | RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void setGrowlInfo(String subject, String body) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, subject, body);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void setGrowlError(String subject, String body) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, subject, body);
        FacesContext.getCurrentInstance().addMessage(null, message);
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
    public void removeEntry(Entry entry) {
        try {
            remoteService.removeEntry(entry);
        } catch (DataStoreFSException e) {
            logger.error(e);
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
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
        logger.info("Get entry by id: " + id);
        Entry result = null;
        try {
            result = remoteService.getEntry(id);
        } catch (RemoteException e) {
            logger.error(e);
        }
        return result;
    }

    public Entry getEntry(String id) {
        logger.info("Get entry: " + id);
        return getEntry(UUID.fromString(id));
    }

    public Entry getEntry() {
        logger.info("Get entry");
        return null;
    }

    @Override
    public List<Entry> getEntriesBySubject(String subject) {
        List<Entry> result = null;
        try {
            result = remoteService.getEntriesBySubject(subject);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Entry> getEntriesBySubject() {
        return getEntriesBySubject(searchSubject);
    }

    @Override
    public List<Entry> getEntriesByAttender(String attender) {
        return null;
    }

    @Override
    public List<Entry> getEntries() {
        List<Entry> result = null;
        try {
            result = remoteService.getEntries();
        } catch (RemoteException e) {
            logger.error(e);
        }
        return result;
    }
    public List<Entry> getEntriesWrapper() {
        if (isSearch) {
            return getEntriesBySubject();
        } else {
            return getEntries();
        }
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

    public void showEditForm() {
        logger.info("showEditForm");
    }

}
