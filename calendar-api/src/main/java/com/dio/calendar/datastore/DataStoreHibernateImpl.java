package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import com.dio.calendar.database.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 03.07.2014.
 */
public class DataStoreHibernateImpl implements DataStore {
    private final SessionFactory sessionFactory;

    private static Logger logger = Logger.getLogger(LoadEntryHibernateImpl.class);

    public DataStoreHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

//        EntryWrapper entry = new EntryWrapper();
//        entry.setId(UUID.randomUUID());
//        entry.setSubject("subject test");
//        entry.setDescription("description test");
//        entry.setStartDate(new Date());
//
//        try {
//            this.write(entry.createEntry());
//        } catch (DataStoreFSException e) {
//            e.printStackTrace();
//        }

//        System.out.println("done!");
//        try {
//            System.out.println(this.getEntries());
//            System.out.println("done!!");
//        } catch (DataStoreFSException e) {
//            e.printStackTrace();
//        }
//        System.out.println("read ids");
//        List<UUID> ids = this.getListEntries();
//        System.out.println(ids);
//        System.out.println("read entries");
//        for (UUID id : ids) {
//            try {
//                System.out.println(this.read(id));
//            } catch (DataStoreFSException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("done!!");
//        try {
//            this.clear();
//        } catch (DataStoreFSException e) {
//            e.printStackTrace();
//        }
//        System.out.println("clear!!");
//        System.out.println("end");
    }

    @Override
    public Boolean write(Entry entry) throws DataStoreFSException {
        logger.info("Write entry " + entry);
        Boolean result = false;
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(new EntryWrapper(entry));
            tx.commit();
            result = true;
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public Boolean delete(UUID id) throws DataStoreFSException {
        Boolean result = false;
        Session session = sessionFactory.openSession();
        try {
            Criteria cr = session.createCriteria(EntryWrapper.class);
            cr.add(Restrictions.eq("id", id));
            EntryWrapper wrapper = (EntryWrapper) cr.uniqueResult();
            if (wrapper != null) {
                Transaction tx = session.beginTransaction();
                session.delete(wrapper);
                tx.commit();
                result = true;
            }
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public Entry read(UUID id) throws DataStoreFSException {
        Entry result = null;
        Session session = sessionFactory.openSession();
        try {
            Criteria cr = session.createCriteria(EntryWrapper.class);
            cr.add(Restrictions.eq("id", id));
            EntryWrapper wrapper = (EntryWrapper) cr.uniqueResult();
            if (wrapper != null) {
                result = ((EntryWrapper) wrapper).createEntry();
            }
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public void clear() throws DataStoreFSException {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("delete from EntryWrapper");
            Transaction tx = session.beginTransaction();
            query.executeUpdate();
            tx.commit();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public List<Entry> getEntries() throws DataStoreFSException {
        List entries = new ArrayList<Entry>();
        Session session = sessionFactory.openSession();
        try {
            List<EntryWrapper> wrapper = session.createCriteria(EntryWrapper.class).list();
            for (Object entryWrapper : wrapper) {
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
    public List<UUID> getListEntries() {
        List ids = new ArrayList<UUID>();
        Session session = sessionFactory.openSession();
        try {
            List<EntryWrapper> wrapper = session.createCriteria(EntryWrapper.class).list();
            for (Object entryWrapper : wrapper) {
                ids.add(((EntryWrapper) entryWrapper).getId());
            }
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        return ids;
    }

    @Override
    public LoadEntry createLoader(CalendarDataStore calendarDataStore, UUID id) {
        return new LoadEntryHibernateImpl(this, calendarDataStore, id);
    }
}
