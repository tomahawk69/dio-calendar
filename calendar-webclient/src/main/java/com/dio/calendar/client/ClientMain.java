package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

        Entry entry = calendarServiceWrapper.getEntry(UUID.fromString("9ff1b57f-3efb-46e0-8813-a30d4277db20"));

        System.out.println("Parsed entry " + entry);
        EntryWrapper wrapper = new EntryWrapper();
        wrapper.setDescription("test update");
        Entry newEntry = wrapper.createEntry();
        try {
            calendarServiceWrapper.addEntry(newEntry);
        } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
            calendarEntryBadAttribute.printStackTrace();
        } catch (CalendarKeyViolation calendarKeyViolation) {
            calendarKeyViolation.printStackTrace();
        }

//        EntryWrapper wrapper = new EntryWrapper(entry);
//        wrapper.setSubject("test update");
//        Entry newEntry = wrapper


//        System.out.println(calendarServiceWrapper.getEntries());

//        CalendarService service = context.getBean("calendarService", CalendarService.class);

//        Entry testInput = calendarServiceWrapper.newEntry("test", null, null, null, null, null);
//        Entry entry = calendarServiceWrapper.getEntry(testInput.getId());
//        if (entry == null) {
//            try {
//                entry = calendarServiceWrapper.addEntry(testInput);
//            } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
//                calendarEntryBadAttribute.printStackTrace();
//            } catch (CalendarKeyViolation calendarKeyViolation) {
//                calendarKeyViolation.printStackTrace();
//            }
//        } else {
//            try {
//                entry = calendarServiceWrapper.updateEntry(testInput);
//            } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
//                calendarEntryBadAttribute.printStackTrace();
//            }
//        }
//         calendarServiceWrapper.removeEntry(entry);

    }
}
