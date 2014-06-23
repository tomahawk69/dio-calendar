package com.dio.calendar.datastore;

import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.Notification;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created by iovchynnikov on 4/30/14.
 */
public class CalendarDataStoreImpl implements CalendarDataStore {
    private final DataStoreFS fileStore;
    private HashMap<UUID, Entry> entries;
    private Map<String, HashSet<UUID>> indexEntryAttenders, indexEntrySubjects;
    private Map<String, Notification> notifications;
    private static Logger logger = Logger.getLogger(CalendarDataStoreImpl.class);

    // locks here
    private ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    private AtomicBoolean isLoadData = new AtomicBoolean(false);
    private final Object lockLocks = new Object();

    private final ReentrantLock lockEntryWord = new ReentrantLock(false);

    public CalendarDataStoreImpl(DataStoreFS fs) {
        fileStore = fs;
        entries = new HashMap<>();
        notifications = new HashMap<>();
        indexEntryAttenders = new HashMap<>();
        indexEntrySubjects = new HashMap<>();

    }

    private void indexEntry(Entry entry) {
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing entry " + entry);
        }
        indexEntrySubject(entry);
        indexEntryAttenders(entry);
    }

    private void indexEntryAttenders(Entry entry) {
        List<String> attenders = entry.getAttenders();
        if (attenders != null) {
            UUID id = entry.getId();
            for (String item : attenders) {
                indexEntryWord(id, item, indexEntryAttenders);
            }
        }
    }

    private void indexEntrySubject(Entry entry) {
        String subject = entry.getSubject();
        if (subject != null) {
            UUID id = entry.getId();
            for (String word : getSplit(subject)) {
                indexEntryWord(id, word, indexEntrySubjects);
            }
        }
    }

    private void indexEntryWord(UUID id, String word, Map<String, HashSet<UUID>> index) {
        createLock(word);
        ReentrantLock lock = (ReentrantLock) locks.get(word);
        if (lock.tryLock()) {
            try {
                HashSet<UUID> values = index.get(word);
                if (values == null)
                    values = new HashSet<>();
                values.add(id);
                index.put(word, values);
            } finally {
                lock.unlock();
            }
        }
        else {
            logger.error("couldn't acquire lock");
        }
    }


    private void indexEntryWordSyn(UUID id, String word, Map<String, HashSet<UUID>> index) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            HashSet<UUID> values = index.get(word);
            if (values == null)
                values = new HashSet<>();
            values.add(id);
            index.put(word, values);
        }
        finally {
            lock.unlock();
        }
    }

    private void createLock(String word) {
        if (!locks.contains(word)) {
            lockEntryWord.lock();
            try {
                if (!locks.contains(word)) {
                    locks.put(word, new ReentrantLock());
                }
            }
            finally {
                lockEntryWord.unlock();
            }
        }
    }

    private void createLockIn(String word) {
        if (!locks.contains(word)) {
            synchronized (lockLocks) {
                if (!locks.contains(word)) {
                    locks.put(word, new Object());
                }
            }
        }
    }



    private void unIndexEntry(Entry entry) {
        if (logger.isDebugEnabled()) {
            logger.debug("Unindexing entry " + entry);
        }
        if (entry != null) {
            unIndexEntryAttenders(entry);
            unIndexEntrySubject(entry);
        } else {
            logger.warn("Entry is null");
        }
    }

    private void unIndexEntryAttenders(Entry entry) {
        List<String> attenders = entry.getAttenders();
        if (attenders != null) {
            UUID id = entry.getId();
            for (String item : attenders) {
                unIndexEntryWord(id, item, indexEntryAttenders);
            }
        }
    }

    private void unIndexEntrySubject(Entry entry) {
        String subject = entry.getSubject();
        if (subject != null) {
            UUID id = entry.getId();
            for (String word : getSplit(subject)) {
                unIndexEntryWord(id, word, indexEntrySubjects);
            }
        }
    }

    private void unIndexEntryWord(UUID id, String word, Map<String, HashSet<UUID>> index) {
        createLock(word);
        ReentrantLock lock = (ReentrantLock) locks.get(word);
        lock.lock();
        try {
            HashSet<UUID> values = indexEntrySubjects.get(word);
            if (values != null) {
                values.remove(id);
                if (values.size() == 0) {
                    index.remove(word);
                } else {
                    index.put(word, values);
                }
            }
        }
        finally {
            lock.unlock();
        }
    }

    private void unIndexEntryWordSyn(UUID id, String word, Map<String, HashSet<UUID>> index) {
        createLockIn(word);
        Object lock = locks.get(word);
        synchronized(lock) {
            HashSet<UUID> values = indexEntrySubjects.get(word);
            if (values != null) {
                values.remove(id);
                if (values.size() == 0) {
                    index.remove(word);
                } else {
                    index.put(word, values);
                }
            }
        }
    }

    private static String[] getSplit(String subject) {
        String[] subjects = subject.trim().toLowerCase().split("\\s");
        Set<String> items = new HashSet(Arrays.asList(subjects));
        Iterator<String> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().length() < 3) {
                it.remove();
            }
        }
        return items.toArray(new String[items.size()]);
    }

    @Override
    public Entry removeEntry(UUID id ) throws DataStoreFSException {
        if (logger.isDebugEnabled()) {
            logger.debug("Removing entry with ID=" + id);
        }
        if (id == null) {
            logger.warn("Passed null ID");
            return null;
        } else {
            Entry result = getEntry(id);
            if (result == null) {
                logger.info("Object with ID=" + id + " not found");
            } else {
                try {
                    fileStore.delete(id);
                    unIndexEntry(result);
                    entries.remove(id);
                } catch (DataStoreFSException e) {
                    throw e;
                }
            }
            return result;
        }
    }

    @Override
    public ArrayList<Entry> getEntries() {
        return new ArrayList(entries.values());
    }

    @Override
    public Collection<Notification> getNotifications() {
        return notifications.values();
    }

    @Override
    public boolean entryExists(UUID id) {
        return entries.containsKey(id);
    }

    @Override
    public Entry getEntry(UUID id) {
        return entries.get(id);
    }

    @Override
    public void init() throws IOException {
        logger.info("Init dataStore");
        loadData();
    }

    @Override
    public void loadData() throws IOException {
        if (! isLoadData.compareAndSet(false, true)) {
            logger.warn("Loading data in progress");
            return;
        }

        logger.info("Load dataStore");
        entries.clear();
        indexEntryAttenders.clear();
        indexEntrySubjects.clear();
        try {
            int threads = Runtime.getRuntime().availableProcessors() * 5;
            logger.info("Used threads count: " + threads);
            ExecutorService exec = Executors.newFixedThreadPool(threads);
            List<Future> futures = new LinkedList<>();
            for (Path file : fileStore.getListFiles()) {
                LoadFileImpl runLoadFile = new LoadFileImpl(fileStore, this, file);
                futures.add(exec.submit(runLoadFile));
            }
            CheckLoadFileImpl checkLoad = new CheckLoadFileImpl(futures, this);
            exec.submit(checkLoad);

            exec.shutdown();

            // TODO: ++ Reentrance: Registry (?)
            // unstructured, lock polling, lock waits, interrupting locks, optional fairness policy
            // Collection hold value
            // Blocking operations


//            exec.shutdown();
//            try {
//                exec.awaitTermination(1, TimeUnit.HOURS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            while (!exec.isTerminated()) {
//            }
        } catch (DataStoreFSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw e;
        }

    }

    @Override
    public void clearData() throws DataStoreFSException {
        logger.info("Clear dataStore");
        try {
            for (Entry entry : entries.values()) {
                fileStore.delete(entry.getId());
            }
            entries.clear();
            indexEntrySubjects.clear();
            indexEntryAttenders.clear();
        } catch (DataStoreFSException e) {
            throw e;
        }
    }

    @Override
    public List<Entry> getEntryByAttender(String attender) {
        HashSet<UUID> items = indexEntryAttenders.get(attender);
        List<Entry> result = new LinkedList();
        if (items != null)
            for (UUID item : items)
                result.add(getEntry(item));
        return result;
    }

    @Override
    public List<Entry> getEntryBySubject(String subject) {
        logger.info("getEntryBySubject " + subject);
        List<Entry> result = new LinkedList();
        if (subject != null) {
            HashSet<UUID> entries = new HashSet<>();
            for (String word : getSplit(subject)) {
                HashSet<UUID> values = indexEntrySubjects.get(word);
                if (values != null)
                    entries.addAll(values);
            }
            for (UUID id : entries)
                result.add(getEntry(id));
        }
        return result;
    }

    @Override
    public Entry addEntry(Entry entry) throws CalendarKeyViolation, DataStoreFSException {
        if (logger.isDebugEnabled()) {
            logger.debug("Saving entry " + entry);
        }
        if (entry != null) {
            if (entryExists(entry.getId())) {
                throw new CalendarKeyViolation("Entry with ID=" + entry.getId() + " already entryExists in the database");
            }
            try {
                fileStore.write(entry);
                addEntryToEntries(entry);
            } catch (DataStoreFSException e) {
                throw e;
            }
        } else {
            logger.warn("Empty entry passed");
        }
        return entry;
    }

    @Override
    public void addEntryToEntries(Entry entry) {
        entries.put(entry.getId(), entry);
        indexEntry(entry);
    }

    @Override
    public Entry updateEntry(Entry newEntry, Entry oldEntry) throws DataStoreFSException {
        if (logger.isDebugEnabled()) {
            logger.debug("Saving entry " + newEntry + " from old entry " + oldEntry);
        }
        if (newEntry == null) {
            logger.warn("New Entry is null");
            return null;
        }
        UUID id = oldEntry == null ? newEntry.getId() : oldEntry.getId();
        Entry currentEntry = getEntry(id);
        Entry result;
        if (currentEntry == null) {
            logger.info("Current entry not found for passed entry with ID=" + id);
            result = new Entry.Builder(newEntry).build();
        } else {
            Entry.Builder t = new Entry.Builder(currentEntry);
            if (newEntry.getSubject() == null || oldEntry == null || !newEntry.getSubject().equals(oldEntry.getSubject())) {
                t.subject(newEntry.getSubject());
            }
            if (newEntry.getDescription() == null || oldEntry == null || !newEntry.getDescription().equals(oldEntry.getDescription())) {
                t.description(newEntry.getDescription());
            }
            if (newEntry.getStartDate() == null || oldEntry == null || !newEntry.getStartDate().equals(oldEntry.getStartDate())) {
                t.startDate(newEntry.getStartDate());
            }
            if (newEntry.getEndDate() == null || oldEntry == null || !newEntry.getEndDate().equals(oldEntry.getEndDate())) {
                t.endDate(newEntry.getEndDate());
            }
            if (newEntry.getAttenders() == null || oldEntry == null || !newEntry.getAttenders().equals(oldEntry.getAttenders())) {
                t.attenders(newEntry.getAttenders());
            }
            if (newEntry.getNotifications() == null || oldEntry == null || !newEntry.getNotifications().equals(oldEntry.getNotifications())) {
                t.notifications(newEntry.getNotifications());
            }
            result = t.build();
        }
        try {
            fileStore.delete(newEntry.getId());
            fileStore.write(newEntry);
            unIndexEntry(currentEntry);
            addEntryToEntries(result);
        } catch (DataStoreFSException e) {
            throw e;
        }
        return result;
    }

    @Override
    public void setLoadDone() {
        isLoadData.set(false);
        logger.info("Load data done; added " + entries.size() + " entries");
    }

    @Override
    public boolean getIsLoad() {
        return isLoadData.get();
    }

}
