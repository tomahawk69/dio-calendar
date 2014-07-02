package com.dio.calendar.database;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/2/2014.
 */
public class DatabaseTest {
    public static void main(String[] args) {
        try {
            System.out.println("ready");
            EntryDAO dao = DAOUtils.getInstance().getEntryDAO();
            Collection entries = dao.getEntries();
            for (Object entry : entries) {
                Entry i = (Entry)entry;
                System.out.println(i);
            }
            System.out.println("entriesss");
            EntryWrapper entry = new EntryWrapper();
            entry.setId(UUID.randomUUID());
            entry.setSubject("subject test");
            entry.setDescription("description test");

            dao.addEntry(entry.createEntry());

            System.out.println("add");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
