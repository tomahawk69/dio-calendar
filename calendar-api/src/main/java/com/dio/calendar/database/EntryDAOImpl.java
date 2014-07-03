package com.dio.calendar.database;

import com.dio.calendar.Entry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.dio.calendar.EntryWrapper;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * Created by iovchynnikov on 7/2/2014.
 */
public class EntryDAOImpl implements EntryDAO {

    @Override
    public void addEntry(EntryWrapper entry) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HibernateTransactionManager tm = new HibernateTransactionManager(HibernateUtil.getSessionFactory());
        try {
//            session.beginTransaction();
            Transaction tx = session.beginTransaction();
            session.save(entry);
            tx.commit();
//            session.getTransaction().commit();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void addEntry(Entry entry) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
//            session.beginTransaction();
            Transaction tx = session.beginTransaction();
            session.save(new EntryWrapper(entry));
            tx.commit();
//            session.getTransaction().commit();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateEntry(Entry entry) throws SQLException {

    }

    @Override
    public void updateEntry(Entry entry, Entry oldEntry) throws SQLException {

    }

    @Override
    public Entry getEntryById(UUID id) throws SQLException {
        return null;
    }

    @Override
    public List getEntries() throws SQLException {
        List entries = new ArrayList<Entry>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            for (Object entryWrapper : session.createCriteria(EntryWrapper.class).list()) {
                entries.add(((EntryWrapper) entryWrapper).createEntry());
            }
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return entries;
    }

    @Override
    public void deleteEntry(Entry bus) throws SQLException {

    }

    @Override
    public void deleteEntryById(UUID id) throws SQLException {

    }

    @Override
    public Collection getEntriesBySubject(String subject) throws SQLException {
        return null;
    }
}
