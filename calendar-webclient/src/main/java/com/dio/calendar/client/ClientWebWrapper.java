package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.Notification;
import com.dio.calendar.client.old.ClientWrapperImpl;
import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by yur on 11.07.2014.
 */
public class ClientWebWrapper implements Serializable {
    private final ClientRemoteWrapperRestImpl wrapperService;
    private final Logger logger = Logger.getLogger(ClientWrapperImpl.class);

    private List<Entry> searchResults;

    private List<Entry> filteredEntries;

    private String searchSubject;

    public List<Entry> getFilteredEntries() {
        return filteredEntries;
    }

    public void setFilteredEntries(List<Entry> filteredEntries) {
        this.filteredEntries = filteredEntries;
    }

    public ClientWebWrapper(ClientRemoteWrapperRestImpl wrapperService) {
        this.wrapperService = wrapperService;
    }

    public void setSearchSubject(String searchTerms) {
        this.searchSubject = searchTerms;
    }

    public boolean filterByDescription(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (StringUtils.isBlank(filterText)) {
            return true;
        }
        if (value == null) {
            return false;
        }
        return ((String) value).toLowerCase(locale).contains(filterText.toLowerCase(locale));
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
            Entry resultEntry = wrapperService.addEntry(entry);
            setFilterEntryIndex(-1, resultEntry);
            return resultEntry;
        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public void removeEntryById(UUID id) {
        logger.debug("remove entry by UUID " + id);
        Integer index = GetFilterEntryIndexByUUID(id);
        wrapperService.removeEntryById(id);
        removeFilterEntry(index);
    }

    public Integer GetFilterEntryIndexByUUID(UUID id) {
        for (int i = 0; i < filteredEntries.size(); i++) {
            if (filteredEntries.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public void removeFilterEntry(Integer index) {
        if (index >= 0) {
            filteredEntries.remove(filteredEntries.get(index));
        }
    }

    public void setGrowlInfo(String subject, String body) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, subject, body);
        addGrowlMessage(message);
    }

    public void setGrowlError(String subject, String body) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, subject, body);
        addGrowlMessage(message);
    }

    public void addGrowlMessage(FacesMessage message) {
        FacesContext context = getCurrentFacesInstance();
        if (context != null) {
            context.addMessage(null, message);
        }
    }

    public FacesContext getCurrentFacesInstance() {
        return FacesContext.getCurrentInstance();
    }

    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute {
        Integer index = GetFilterEntryIndex(oldEntry);
        Entry result = wrapperService.updateEntry(newEntry, oldEntry);
        setFilterEntryIndex(index, result);
        return result;
    }

    public void setFilterEntryIndex(Integer index, Entry entry) {
        if (index >= 0) {
            filteredEntries.set(index, entry);
        } else {
            if (filteredEntries != null) {
                filteredEntries.add(0, entry);
            }
        }
    }

    public int GetFilterEntryIndex(Entry entry) {
        if (filteredEntries == null) {
            return -1;
        } else {
            return filteredEntries.indexOf(entry);
        }
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
        logger.debug("Get entry by id: " + id);
        return wrapperService.getEntry(id);
    }

    public Entry getEntry(String id) {
        logger.debug("Get entry: " + id);
        return getEntry(UUID.fromString(id));
    }

    public Entry getEntry() {
        logger.debug("Get entry");
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
        return getEntries();
    }

}
