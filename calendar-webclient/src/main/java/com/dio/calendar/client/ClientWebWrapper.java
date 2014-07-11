package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.Notification;
import com.dio.calendar.client.old.ClientWrapperImpl;
import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 11.07.2014.
 */
public class ClientWebWrapper {
    private final ClientRemoteWrapperImpl wrapperService;
    private final Logger logger = Logger.getLogger(ClientWrapperImpl.class);

    private List<Entry> searchResults;

    private String searchSubject;
    private Boolean isSearch = false;

    public ClientWebWrapper(ClientRemoteWrapperImpl wrapperService) {
        this.wrapperService = wrapperService;
    }

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

    public Entry newEntry(String subject, String description, Date startDate, Date endDate, List<String> attenders, List<Notification> notifications) {
        return wrapperService.newEntry(subject, description, startDate, endDate, attenders, notifications);
    }

    public Entry editSubject(Entry oldEntry, String subject) {
        return wrapperService.editSubject(oldEntry, subject);
    }

    public Entry editDescription(Entry oldEntry, String description) {
        return wrapperService.editDescription(oldEntry, description);
    }

    public Entry editStartDate(Entry oldEntry, Date startDate) {
        return wrapperService.editStartDate(oldEntry, startDate);
    }

    public Entry editEndDate(Entry oldEntry, Date endDate) {
        return wrapperService.editEndDate(oldEntry, endDate);
    }

    public Entry editAttenders(Entry oldEntry, List<String> attenders) {
        return wrapperService.editAttenders(oldEntry, attenders);
    }

    public Entry editNotifications(Entry oldEntry, List<Notification> notifications) {
        return wrapperService.editNotifications(oldEntry, notifications);
    }

    public Entry addEntry(Entry entry) {
        try {
            return wrapperService.addEntry(entry);
        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

//    public String addEntry() {
//        Entry result;
//        logger.info("add entry");
//        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        logger.info(params);
//        String subject = params.get("addForm:subject");
//        String description = params.get("addForm:description");
//
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//        Date dateFrom = null;
//        String date = params.get("addForm:dateFrom_input");
//        if (StringUtils.isNotBlank(date)) {
//            try {
//                dateFrom = dateFormat.parse(date);
//            } catch (ParseException e) {
//                logger.error(e);
//            }
//        }
//
//        Date dateTo = null;
//        date = params.get("addForm:dateTo_input");
//        if (StringUtils.isNotBlank(date)) {
//            try {
//                dateTo = dateFormat.parse(date);
//            } catch (ParseException e) {
//                logger.error(e);
//            }
//        }
//
////        logger.info("subject:" + subject);
////        logger.info("description:" + description);
////        logger.info("dateFrom:" + dateFrom);
////        logger.info("dateTo:" + dateTo);
//
//        try {
//            result = addEntry(newEntry(subject, description, dateFrom, dateTo, null, null));
//            setGrowlInfo("Successfully added entry", "Entry was successfully added. New id is " + result.getId());
//        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
//            logger.error(e);
//            setGrowlError("Error", "Entry was not added: " + e.getMessage());
//        }
//
//
////        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
////        String subject = request.getParameter("addForm:subject");
////        System.out.println(subject);
//
//        return "add";
//    }

    public void removeEntryById(UUID id) {
        logger.info("remove entry by UUID " + id);
        wrapperService.removeEntryById(id);
        // FIXME: setGrowlInfo in tests
        //setGrowlInfo("Entry removed", new StringBuffer("Entry with id ").append(id).append(" was successfully removed").toString());
    }

    private void setGrowlInfo(String subject, String body) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, subject, body);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void setGrowlError(String subject, String body) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, subject, body);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        return wrapperService.updateEntry(newEntry, oldEntry);
    }

    public Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute {
        return wrapperService.updateEntry(newEntry);
    }

    public void removeEntry(Entry entry) {
        wrapperService.removeEntry(entry);
    }

    public Entry copyEntry(Entry entry) {
        return wrapperService.copyEntry(entry);
    }

    public Entry getEntry(UUID id) {
        logger.info("Get entry by id: " + id);
        return wrapperService.getEntry(id);
    }

    public Entry getEntry(String id) {
        logger.info("Get entry: " + id);
        return getEntry(UUID.fromString(id));
    }

    public Entry getEntry() {
        logger.info("Get entry");
        return null;
    }

    public List<Entry> getEntriesBySubject(String subject) {
        return wrapperService.getEntriesBySubject(subject);
    }

    public List<Entry> getEntriesBySubject() {
        return getEntriesBySubject(searchSubject);
    }

    public List<Entry> getEntriesByAttender(String attender) {
        return null;
    }

    public List<Entry> getEntries() {
        return wrapperService.getEntries();
    }

    public List<Entry> getEntriesWrapper() {
        if (isSearch) {
            return getEntriesBySubject();
        } else {
            return getEntries();
        }
    }

    public void clearData() {
        wrapperService.clearData();
    }

    public void showEditForm() {
        logger.info("showEditForm");
    }

}
