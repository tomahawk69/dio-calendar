package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.datastore.DataStoreFSException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;

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
        ClientWrapper calendarServiceWrapper = context.getBean("calendarServiceWrapper", ClientWrapper.class);

//        CalendarService service = context.getBean("calendarService", CalendarService.class);

        Entry testInput = calendarServiceWrapper.newEntry("test", null, null, null, null, null);
        Entry entry = calendarServiceWrapper.getEntry(testInput.getId());
        if (entry == null) {
            try {
                entry = calendarServiceWrapper.addEntry(testInput);
            } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
                calendarEntryBadAttribute.printStackTrace();
            } catch (CalendarKeyViolation calendarKeyViolation) {
                calendarKeyViolation.printStackTrace();
            }
        } else {
            try {
                entry = calendarServiceWrapper.updateEntry(testInput);
            } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
                calendarEntryBadAttribute.printStackTrace();
            }
        }
         calendarServiceWrapper.removeEntry(entry);

    }
}
