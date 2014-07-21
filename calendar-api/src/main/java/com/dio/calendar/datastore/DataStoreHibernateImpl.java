package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryEntity;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 03.07.2014.
 */
public class DataStoreHibernateImpl implements DataStore {

    private final SessionFactory sessionFactory;

    private static Logger logger = Logger.getLogger(LoadEntryImpl.class);

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
        logger.debug("Write entry " + entry);
        Session session = sessionFactory.openSession();
        try {
            Transaction tx = session.beginTransaction();
            session.save(new EntryEntity(entry));
            tx.commit();
        }
        finally {
            session.close();
        }
        return true;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Boolean delete(UUID id) throws DataStoreFSException {
        Boolean result = false;
        Session session = sessionFactory.openSession();
        try {
//            Criteria cr = session.createCriteria(EntryEntity.class);
//            cr.add(Restrictions.eq("id", id.toString()));
//            EntryEntity wrapper = (EntryEntity) cr.uniqueResult();
//            if (wrapper != null) {
//                Transaction tx = session.beginTransaction();
//                System.out.println(wrapper.getId());
//                session.delete(wrapper);
//                tx.commit();
//                result = true;
//            }
            Query query = session.createQuery("delete EntryEntity where id = :id");
            query.setParameter("id", id.toString());
            result = query.executeUpdate() > 0;
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
        logger.debug("Read entry: " + id);

        Entry result = null;
        Session session = sessionFactory.openSession();
        try {
            Criteria cr = session.createCriteria(EntryEntity.class);
            cr.add(Restrictions.eq("id", id.toString()));
            EntryEntity wrapper = (EntryEntity) cr.uniqueResult();
            if (wrapper != null) {
                result = wrapper.createEntry();
            }
        }
        catch (Exception e) {
            logger.error(e);
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public void clear() throws DataStoreFSException {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("delete from EntryEntity");
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
            List<EntryEntity> wrapper = session.createCriteria(EntryEntity.class).list();
            for (Object entryEntity : wrapper) {
                entries.add(((EntryEntity) entryEntity).createEntry());
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
            List<EntryEntity> wrapper = session.createCriteria(EntryEntity.class).list();
            for (Object entryEntity : wrapper) {
//                System.out.println(((EntryEntity) entryEntity).getId());
//                System.out.println(session.createCriteria(EntryEntity.class).
//                        add(Restrictions.eq("id", ((EntryEntity) entryEntity).getId())).list());
//                ids.add(UUID.fromString(((EntryEntity) entryEntity).getId()));
                ids.add(UUID.fromString(((EntryEntity) entryEntity).getId()));
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
        return new LoadEntryImpl(this, calendarDataStore, id);
    }
}
