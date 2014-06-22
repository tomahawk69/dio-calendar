package com.dio.calendar.client;

import com.dio.calendar.CalendarService;
import com.dio.calendar.Entry;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 17.06.2014.
 */
public interface ClientWrapper extends CalendarService {


    @Override
    com.dio.calendar.Entry newEntry(String subject, String description, Date startDate, Date endDate, List<String> attenders, List<com.dio.calendar.Notification> notifications);

    @Override
    com.dio.calendar.Entry editSubject(com.dio.calendar.Entry oldEntry, String subject) ;

    @Override
    com.dio.calendar.Entry editDescription(com.dio.calendar.Entry oldEntry, String description);

    @Override
    com.dio.calendar.Entry editStartDate(com.dio.calendar.Entry oldEntry, Date startDate);

    @Override
    com.dio.calendar.Entry editEndDate(com.dio.calendar.Entry oldEntry, Date endDate);

    @Override
    com.dio.calendar.Entry editAttenders(com.dio.calendar.Entry oldEntry, List<String> attenders);

    @Override
    com.dio.calendar.Entry editNotifications(com.dio.calendar.Entry oldEntry, List<com.dio.calendar.Notification> notifications);

    @Override
    com.dio.calendar.Entry addEntry(com.dio.calendar.Entry entry) throws com.dio.calendar.CalendarEntryBadAttribute, com.dio.calendar.CalendarKeyViolation;

    @Override
    com.dio.calendar.Entry removeEntry(UUID id);

    @Override
    com.dio.calendar.Entry updateEntry(com.dio.calendar.Entry newEntry, com.dio.calendar.Entry oldEntry) throws com.dio.calendar.CalendarEntryBadAttribute;

    @Override
    com.dio.calendar.Entry updateEntry(com.dio.calendar.Entry newEntry) throws com.dio.calendar.CalendarEntryBadAttribute;

    @Override
    com.dio.calendar.Entry removeEntry(com.dio.calendar.Entry entry);

    @Override
    com.dio.calendar.Entry copyEntry(com.dio.calendar.Entry entry);

    @Override
    com.dio.calendar.TimeInterval[] getFreeTimeLine(String attender, Date date);

    @Override
    com.dio.calendar.Entry getEntry(UUID id);

    @Override
    List<com.dio.calendar.Entry> getEntriesBySubject(String subject);

    @Override
    List<com.dio.calendar.Entry> getEntriesByAttender(String attender);

    @Override
    Collection<Entry> getEntries();

    @Override
    void clearData();
}
