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
        ClientWrapper calendarServiceWrapper = context.getBean("calendarServiceRestClient", ClientWrapperRestImpl.class);
        Entry entry = null;
//        entry = calendarServiceWrapper.getEntry(UUID.fromString("9ff1b57f-3efb-46e0-8813-a30d4277db20"));
//        entry = calendarServiceWrapper.getEntry(UUID.fromString("425fc3d4-242e-40ef-b74c-a8661225d655"));
        System.out.println("Read all entries");
        List<Entry> entries = calendarServiceWrapper.getEntries();
        System.out.println("Returned " + entries.size());
        if (entries.size() > 0) {
            System.out.println(entries.get(0).getClass());
//            System.out.println("Get entry with id " + entry.getId());
//            entry = calendarServiceWrapper.getEntry(entry.getId());
        }

        System.out.println("Create entry");
        EntryWrapper wrapper = new EntryWrapper();
        wrapper.setSubject("test subject");
        Entry newEntry = wrapper.createEntry();

        wrapper.setDescription("test add and delete");
        try {
            calendarServiceWrapper.addEntry(newEntry);
            //calendarServiceWrapper.removeEntry(entry);
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
