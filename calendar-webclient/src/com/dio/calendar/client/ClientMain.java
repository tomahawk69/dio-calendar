package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.CalendarService;
import com.dio.calendar.Entry;
import com.dio.calendar.datastore.DataStoreFSException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

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
        CalendarService service = context.getBean("calendarService", CalendarService.class);

        Entry testInput = new Entry.Builder().subject("test").build();

        try {
            System.out.println(service);
            Entry entry = service.getEntry(testInput.getId());
            if (entry == null) {
                entry = service.addEntry(testInput);
            }
            service.removeEntry(entry);
//            Collection<Entry> entries = service.getEntries();
        } catch (DataStoreFSException | RemoteException calendarEntryBadAttribute) {
            calendarEntryBadAttribute.printStackTrace();
        } catch (CalendarKeyViolation calendarKeyViolation) {
            calendarKeyViolation.printStackTrace();
        } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
            calendarEntryBadAttribute.printStackTrace();
        }


    }
}
