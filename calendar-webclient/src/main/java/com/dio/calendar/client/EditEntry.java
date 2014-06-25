package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 6/25/2014.
 */

@ManagedBean
@RequestScoped
public class EditEntry{
    private final ClientWrapperImpl localService;
    private String subject;

    private String description;
    private Date dateFrom;
    private Date dateTo;
    private List<String> attenders = new ArrayList<>();

    private Entry oldEntry;

    private static Logger logger = Logger.getLogger(EditEntry.class);

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

    public String createEntry() {
        try {
            Entry result = localService.addEntry(localService.newEntry(subject, description, dateFrom, dateTo, attenders, null));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successfully added entry", "Entry was successfully added. New id is " + result.getId());
            FacesContext.getCurrentInstance().addMessage(null, message);
            reset();
        } catch (CalendarEntryBadAttribute | CalendarKeyViolation e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return "add2";

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

    public void init() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();

        String reqId = req.getParameter("id");

        if (reqId != null) {
            oldEntry = localService.getEntry(UUID.fromString(reqId));
            if (oldEntry != null) {
                subject = oldEntry.getSubject();
                description = oldEntry.getDescription();
                dateFrom = oldEntry.getStartDate();
                dateTo = oldEntry.getEndDate();
                attenders = oldEntry.getAttenders();
            }
        }

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

    public String showEditForm() {
        logger.warn("showEditForm");
        reset();

        return "index";
    }

}
