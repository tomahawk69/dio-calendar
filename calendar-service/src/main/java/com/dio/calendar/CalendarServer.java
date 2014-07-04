package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStore;
import com.dio.calendar.datastore.DataStoreFSException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.*;

import static com.dio.calendar.Utils.constructDate;


/**
 *
 * 1) Add search by email
 * 2) Add interface for DataStore
 * 3) Tests for DataStore (real)
 * 4) Fix tests for CalendarService
 *
 * Created by yur on 04.05.2014.
 */
public class CalendarServer {

    private static void printEntries(Iterable<Entry> entries) {
        for (Entry item : entries) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        context.getEnvironment().acceptsProfiles("work");
        CalendarDataStore data = context.getBean("dataStore", CalendarDataStore.class);
        CalendarService service = context.getBean("calendarService", CalendarService.class);

//        try {
//            data.init();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            while (data.getIsLoad()) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            List<String> att1 = new LinkedList<>();
//            att1.add("First");
//            att1.add("Second");
//
//            List<String> att2 = new LinkedList<>();
//            att2.add("First");
//            att2.add("Third");
//
//
//            Entry e1 = service.newEntry("Subject1", "Description1", constructDate(2014, 5, 1, 10, 0, 0), constructDate(2014, 5, 1, 11, 0, 0), att1, new ArrayList<Notification>());
//            Entry e2 = service.newEntry("Subject2", "Description2", constructDate(2014, 5, 2, 4, 0, 0), constructDate(2014, 5, 2, 5, 0, 0), att2, new ArrayList<Notification>());
//
//            System.out.println("Zero iteration");
//            printEntries(service.getEntries());
//
//            service.addEntry(e1);
//            service.addEntry(e2);
//
//            System.out.println("--indexes for First:");
//            printEntries(service.getEntriesByAttender("First"));
//            System.out.println("--indexes for Second:");
//            printEntries(service.getEntriesByAttender("Second"));
//
//            System.out.println("1st iteration");
//            printEntries(service.getEntries());
//
//            e1 = service.editSubject(e1, "Subject3");
//            service.updateEntry(e1);
//            Entry e2New = service.editSubject(e2, e2.getSubject() + "_new");
//            service.updateEntry(e2New, e2);
//
//            System.out.println("2st iteration");
//            printEntries(service.getEntries());
//            System.out.println("--indexes for First:");
//            printEntries(service.getEntriesByAttender("First"));
//            System.out.println("--indexes for Second:");
//            printEntries(service.getEntriesByAttender("Second"));
//
//            service.removeEntry(e2);
//
//            System.out.println("3st iteration");
//            printEntries(service.getEntries());
//            System.out.println("--indexes for First:");
//            printEntries(service.getEntriesByAttender("First"));
//            System.out.println("--indexes for Second:");
//            printEntries(service.getEntriesByAttender("Second"));
//            System.out.println("--clearing second:");
//            for (Entry entry : service.getEntriesByAttender("Second")) {
//                service.removeEntry(entry);
//            }
//            data.loadData();
//            System.out.println("--indexes for Second:");
//            printEntries(service.getEntriesByAttender("Second"));
//
//            Entry e3 = service.newEntry("Subject tesT", "Description1", constructDate(2014, 5, 1, 10, 0, 0), constructDate(2014, 5, 1, 11, 0, 0), att1, new ArrayList<Notification>());
//            Entry e4 = service.newEntry("Test", "Description1", constructDate(2014, 5, 1, 10, 0, 0), constructDate(2014, 5, 1, 11, 0, 0), att1, new ArrayList<Notification>());
//            service.clearData();
//            service.addEntry(e3);
//            service.addEntry(e4);
//            System.out.println("--indexes for subject test:");
//            printEntries(service.getEntriesBySubject("test"));
//            System.out.println("--indexes for subject subject test:");
//            printEntries(service.getEntriesBySubject("subject test"));
//            System.out.println("--indexes for subject test1:");
//            printEntries(service.getEntriesBySubject("test1"));
//
//            service.clearData();
//            for (int i = 1; i <= 200; i++) {
//                service.addEntry(service.newEntry("Mass test " + i, null, null, null, att1, new ArrayList<Notification>()));
//            }
//            data.init();
//            System.out.println(service.getEntriesBySubject("test").size());
//            while (data.getIsLoad()) {
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println(service.getEntriesBySubject("test").size());
//
//        } catch (CalendarEntryBadAttribute calendarEntryBadAttribute) {
//            calendarEntryBadAttribute.printStackTrace();
//            System.exit(1);
//        } catch (CalendarKeyViolation calendarKeyViolation) {
//            calendarKeyViolation.printStackTrace();
//            System.exit(1);
//        } catch (DataStoreFSException e) {
//            e.printStackTrace();
//            System.exit(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ;

    }
}
