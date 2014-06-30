package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
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
    private ClientWrapperImpl localService;
    private String subject;

    private String description;
    private Date dateFrom;
    private Date dateTo;
    private List<String> attenders = new ArrayList<>();

    private Entry oldEntry;

    private static Logger logger = Logger.getLogger(EditEntry.class);
    private boolean showForm = false;


    public EditEntry() {
    }

    @Autowired
    public EditEntry(ClientWrapperImpl localService) {
        this.localService = localService;
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
        logger.info("Create entry....");
        try {
            Entry result = localService.addEntry(localService.newEntry(subject, description, dateFrom, dateTo, attenders, null));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successfully added entry", "Entry was successfully added. New id is " + result.getId());
            FacesContext.getCurrentInstance().addMessage(null, message);

//            reset();
        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

//    public void updateEntry(ActionEvent actionEvent) {
//        //setGrowlInfo("Entry updated", "Update entry");
//        logger.info("Save entry wrapper....");
//        if (oldEntry == null) {
//
//        } else {
//
//        }
//    }
//
    public String updateEntry() {
        logger.info("Save entry wrapper....");
        if (oldEntry == null) {
            logger.info("New entry");
            try {
                localService.addEntry(localService.newEntry(subject, description, dateFrom, dateTo, attenders, null));
                reset();
                setGrowlInfo("Entry added", "Entry successfully created");
            } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
                logger.error(e);
                setGrowlError("Entry update error", e.getMessage());
            }
        } else {
            logger.info("Update entry");
            try {
                localService.updateEntry(localService.newEntry(subject, description, dateFrom, dateTo, attenders, null), oldEntry);
                reset();
                setGrowlInfo("Entry updated", "Entry successfully updated");
            } catch (CalendarEntryBadAttribute e) {
                logger.error(e);
                setGrowlError("Entry update error", e.getMessage());
            }

        }
        return "index";
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

    private void reset() {
        subject = null;
        description = null;
        dateFrom = null;
        dateTo = null;
        attenders.clear();
        oldEntry = null;
    }

    public void resetListener(ActionEvent actionEvent) {
        logger.info("reset");
        if (oldEntry == null) {
            reset();
        }
        else {
            subject = oldEntry.getSubject();
            description = oldEntry.getDescription();
            dateFrom = oldEntry.getStartDate();
            dateTo = oldEntry.getEndDate();
            attenders = oldEntry.getAttenders();
        }
    }

    public String newEntry() {
        logger.info("newEntry");
        reset();
        return "add";
    }


    public String editEntry(Entry entry) {
        logger.info("editEntry " + entry);
        oldEntry = entry;
        if (oldEntry != null) {
            subject = oldEntry.getSubject();
            description = oldEntry.getDescription();
            dateFrom = oldEntry.getStartDate();
            dateTo = oldEntry.getEndDate();
            attenders = oldEntry.getAttenders();
            //showForm = true;
            return "edit";
        } else {
            setGrowlError("Entry not found", "Cancelling edit");
            return "index";
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

    public void validateDateFrom(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        dateFrom = (Date) value;
        logger.info("DateFrom validation: " + dateFrom);
        if (dateFrom == null && dateTo != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "DateFrom value required", "DateFrom value required when DateTo value is not null"));
        }
        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date period is invalid", "DateFrom value should be lesser or equal then DateTo value"));
        }
    }

    public void validateDateTo(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        dateTo = (Date) value;
        logger.info("DateTo validation: " + dateTo);
        if (dateFrom == null && dateTo != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "DateFrom value required", "DateFrom value required when DateTo value is not null"));
        }
        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date period is invalid", "DateFrom value should be lesser or equal then DateTo value"));
        }
    }

    public void validateEntry(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent components = event.getComponent();
        //setGrowlError("Validation error", "Test error");
        FacesMessage msg = new FacesMessage("Test error");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage("test", msg);
        fc.renderResponse();
//        logger.info("Subject is " + subject);
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
