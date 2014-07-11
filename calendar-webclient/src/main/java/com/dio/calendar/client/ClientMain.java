package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/27/14.
 */
public class ClientMain {

    /*
    * add getEvents, deleteEvents etc
    * remember all
    *
    * */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:clientApplicationContext.xml");
        ClientWrapperRestImpl calendarServiceWrapper = context.getBean("calendarServiceRestClient", ClientWrapperRestImpl.class);
        Entry entry = null;

        System.out.println("Read all entries");
        List<Entry> entries = calendarServiceWrapper.getEntries();
        System.out.println("Returned " + entries.size());

        if (entries.size() > 0) {
            entry = entries.get(0);
//            System.out.println(entries.get(0).getClass());
            System.out.println("Get entry with id " + entry.getId());
            entry = calendarServiceWrapper.getEntry(entry.getId());
            System.out.println("Read entry " + entry);
        }

        System.out.println("Create entry");
        EntryWrapper wrapper = new EntryWrapper();
        wrapper.setSubject("test subject");
        Entry newEntry = wrapper.createEntry();

        wrapper.setDescription("test add, update and delete");
        try {
            System.out.println("Adding entry " + newEntry);
            entry = calendarServiceWrapper.addEntry(newEntry);
            System.out.println("Added entry: " + entry);

            System.out.println("Updating entry...");
            entry = calendarServiceWrapper.updateEntry(newEntry);
            System.out.println("Updated entry: " + entry);

            System.out.println("Smart updating entry...");
            entry = calendarServiceWrapper.updateEntry(newEntry, entry);
            System.out.println("Updated entry: " + entry);

            System.out.println("Updating entry " + newEntry);
            entry = calendarServiceWrapper.editSubject(entry, "test subject update 2");
            System.out.println("Updated entry " + entry);

            System.out.println("Removing. enrty..");
            calendarServiceWrapper.removeEntryById(entry.getId());
            System.out.println("Done");

        } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
            calendarEntryBadAttribute.printStackTrace();
        } catch (CalendarKeyViolation calendarKeyViolation) {
            calendarKeyViolation.printStackTrace();
        }



//        EntryWrapper wrapper = new EntryWrapper(entry);
//        wrapper.setSubject("test update");
//        Entry newEntry = wrapper


//        System.out.println("Clear all entries");
//        calendarServiceWrapper.clearData();


    }
}
