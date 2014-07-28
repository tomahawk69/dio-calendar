package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 6/25/2014.
 */

@ManagedBean(name="editEntry")
@SessionScoped
public class EditEntry implements Serializable {
    @Autowired
//    private ClientRemoteWrapperRestImpl localService;
    private ClientWebWrapper serviceWrapper;
    private String subject;

    private String description;
    private Date dateFrom;
    private Date dateTo;
    private List<String> attenders = new ArrayList<>();
    private String attender;

    private Entry oldEntry;
    public boolean showForm = false;

    private static Logger logger = Logger.getLogger(EditEntry.class);

    public EditEntry() {    }

    @Autowired
    public EditEntry(ClientWebWrapper serviceWrapper) {
        this.serviceWrapper = serviceWrapper;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public List<String> getAttenders() {
        return attenders;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setAttenders(List<String> attenders) {
        this.attenders = attenders;
    }

    public void createEntry(ActionEvent actionEvent) {
        logger.debug("Create entry....");
        try {
            Entry result = serviceWrapper.addEntry(serviceWrapper.newEntry(subject, description, dateFrom, dateTo, attenders, null));
            serviceWrapper.setGrowlInfo("Successfully added entry", "Entry was successfully added. New id is " + result.getId());
        } catch (Exception e) {
//        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public void updateEntry() {
        logger.debug("Save entry wrapper....");
        if (oldEntry == null) {
            logger.debug("New entry");
            try {
                serviceWrapper.addEntry(serviceWrapper.newEntry(subject, description, dateFrom, dateTo, attenders, null));
//                localService.addEntry(localService.newEntry(subject, description, dateFrom, dateTo, attenders, null));
                reset();
                showForm = false;
                serviceWrapper.setGrowlInfo("Entry added", "Entry successfully created");
//            } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            } catch (Exception e) {
                logger.error(e);
                serviceWrapper.setGrowlError("Entry update error", e.getMessage());
            }
        } else {
            logger.debug("Update entry");
            try {
                Entry newEntry = new Entry.Builder().id(oldEntry.getId()).subject(subject).description(description).
                        startDate(dateFrom).endDate(dateTo).attenders(attenders).build();
                serviceWrapper.updateEntry(newEntry, oldEntry);
                reset();
                showForm = false;
                serviceWrapper.setGrowlInfo("Entry updated", "Entry successfully updated");
            } catch (CalendarEntryBadAttribute e) {
                logger.error(e);
                serviceWrapper.setGrowlError("Entry update error", e.getMessage());
            }

        }
    }

    public Boolean isEdit() {
        return (oldEntry != null);
    }

    public UUID getOldId() {
        if (isEdit()) {
            return oldEntry.getId();
        } else {
            return null;
        }
    }

    public boolean isShowForm() {
        return showForm;
    }


    private void reset() {
        subject = null;
        description = null;
        dateFrom = null;
        dateTo = null;
        attenders.clear();
        oldEntry = null;
        attender = null;
    }

    public void resetListener(ActionEvent actionEvent) {
        logger.debug("reset");
        Entry tempEntry = oldEntry;
        reset();
        oldEntry = tempEntry;
        if (oldEntry != null) {
            subject = oldEntry.getSubject();
            description = oldEntry.getDescription();
            dateFrom = oldEntry.getStartDate();
            dateTo = oldEntry.getEndDate();
            attenders = oldEntry.getAttenders();
        }
        RequestContext context = getCurrentRequestInstance();
        if (context != null) {
            context.reset("formEdit");
        }
    }

    private RequestContext getCurrentRequestInstance() {
        return RequestContext.getCurrentInstance();
    }

    public void newEntry() {
        logger.debug("newEntry");
        reset();
        showForm = true;
    }


    public void editEntry(Entry entry) {
        logger.debug("editEntry " + entry);
        oldEntry = entry;
        if (oldEntry != null) {
            subject = oldEntry.getSubject();
            description = oldEntry.getDescription();
            dateFrom = oldEntry.getStartDate();
            dateTo = oldEntry.getEndDate();
            attenders = new ArrayList<>(oldEntry.getAttenders());
            showForm = true;
        } else {
            serviceWrapper.setGrowlError("Entry not found", "Cancelling edit");
        }
    }

    public String getAttender() {
        return attender;
    }

    public void setAttender(String attender) {
        this.attender = attender;
    }

    public void validateDateFrom(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        dateFrom = (Date) value;
        logger.debug("DateFrom validation: " + dateFrom);
        if (dateFrom == null && dateTo != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "DateFrom value required", "DateFrom value required when DateTo value is not null"));
        }
        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date period is invalid", "DateFrom value should be lesser or equal then DateTo value"));
        }
    }

    public void validateDateTo(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        dateTo = (Date) value;
        logger.debug("DateTo validation: " + dateTo);
        if (dateFrom == null && dateTo != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "DateFrom value required", "DateFrom value required when DateTo value is not null"));
        }
        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date period is invalid", "DateFrom value should be lesser or equal then DateTo value"));
        }
    }

    public void validateEntry(ComponentSystemEvent event) {
//        UIComponent components = event.getComponent();
        serviceWrapper.setGrowlError("Validation error", "Test error");
    }

    public void removeAttender() {
        attenders.remove(attender);
    }

    public void removeAttender(String attender) {
        this.attender = attender;
        attenders.remove(attender);
    }

    public void addAttender() {
        if (StringUtils.isBlank(attender)) {
            serviceWrapper.setGrowlError("Error adding attender", "Attender is empty");
            return;
        }
        if (attenders.indexOf(attender) < 0) {
            attenders.add(attender);
            attender = null;
        } else {
            serviceWrapper.setGrowlError("Error adding attender", String.format("Attender %s already exists", attender));
        }
    }

    public Boolean getShowForm() {
        return showForm;
    }

    public void hideForm() {
        showForm = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditEntry editEntry = (EditEntry) o;

        if (attenders != null ? !attenders.equals(editEntry.attenders) : editEntry.attenders != null) return false;
        if (dateFrom != null ? !dateFrom.equals(editEntry.dateFrom) : editEntry.dateFrom != null) return false;
        if (dateTo != null ? !dateTo.equals(editEntry.dateTo) : editEntry.dateTo != null) return false;
        if (description != null ? !description.equals(editEntry.description) : editEntry.description != null)
            return false;
        if (subject != null ? !subject.equals(editEntry.subject) : editEntry.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (attenders != null ? attenders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EditEntry{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dateFrom=").append(dateFrom);
        sb.append(", dateTo=").append(dateTo);
        sb.append(", attenders=").append(attenders);
        sb.append('}');
        return sb.toString();
    }
}