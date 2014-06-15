package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.CalendarService;
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
        ApplicationContext context = new ClassPathXmlApplicationContext("clientApplicationContext.xml");
        Wrapper calendarServiceInterfaceWrapper = context.getBean("calendarServiceInterfaceWrapper", Wrapper.class);
        calendarServiceInterfaceWrapper.init();

//        CalendarService service = context.getBean("calendarService", CalendarService.class);

        Entry testInput = calendarServiceInterfaceWrapper.newEntry("test", null, null, null, null, null);

        Entry entry = calendarServiceInterfaceWrapper.getEntry(testInput.getId());
        if (entry == null) {
            entry = calendarServiceInterfaceWrapper.addEntry(testInput);
        } else {
            entry = calendarServiceInterfaceWrapper.saveEntry(testInput);
        }
        calendarServiceInterfaceWrapper.removeEntry(entry);

    }
}
