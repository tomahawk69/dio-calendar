package com.dio.calendar.datastore;

import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.datastore.CalendarDataStoreImpl;
import com.dio.calendar.datastore.DataStoreFSImpl;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * Created by iovchynnikov on 5/8/14.
 */
public class CalendarDataStoreImplTest {
    private CalendarDataStoreImpl data;
    private DataStoreFSImpl fs;

    private String getMessage(String what, String where, String condition) {
        return String.format("%s %s test %s %s", this.getClass(), what, where, condition);
    }

    private static Entry getTestEntry(String subject) {
        return new Entry.Builder().subject(subject).build();
    }

    private static Entry setTestEntry(Entry source, String subject) {
        return new Entry.Builder(source).subject(subject).build();
    }


    @Before
    public void setUp() {
        fs = mock(DataStoreFSImpl.class);
        data = new CalendarDataStoreImpl(fs);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(fs);
    }
//    remove tests

    @Test
    public void testRemoveEntry() throws Exception {
        // initialize variable inputs
        Entry testEntry = getTestEntry(null);

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(testEntry);
        reset(fs);
        data.removeEntry(testEntry.getId());

        Entry resultReceived = data.getEntry(testEntry.getId());

        // assert return value
        Assert.assertNull(getMessage("removeEntry", "positive", ""), resultReceived);

        // verify mock expectations
        verify(fs).delete(testEntry.getId());
    }

    @Test
    public void testRemoveEntryNull() throws Exception {
        // initialize variable inputs
        Entry testEntry = null;

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.removeEntry(null);

        // assert return value
        //assertNull(getMessage("removeEntry", "positive", ""), resultReceived);

        // verify mock expectations
    }

//    getEntries testing

    @Test
    public void testGetEntriesAfterInsert() throws Exception {
        // initialize variable inputs
        Entry inputEntry1 = getTestEntry("Input subject 1");
        Entry inputEntry2 = getTestEntry("Input subject 2");

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        reset(fs);
        Entry resultEntry1 = data.getEntry(inputEntry1.getId());
        Entry resultEntry2 = data.getEntry(inputEntry2.getId());

        Set<Entry> expectedEntrySet = new HashSet<>(Arrays.asList(resultEntry1, resultEntry2));

        Collection<Entry> resultEntries = data.getEntries();

        // assert return value
        // assert result size
        Assert.assertEquals(getMessage("getEntries", "after insert", "result size"), expectedEntrySet.size(), resultEntries.size());
        // assert entries entryExists
        Assert.assertTrue(getMessage("getEntries", "after insert", "all entries"), resultEntries.containsAll(expectedEntrySet));
        Assert.assertTrue(getMessage("getEntries", "after insert", "entry 1"), resultEntries.contains(inputEntry1));
        Assert.assertTrue(getMessage("getEntries", "after insert", "entry 2"), resultEntries.contains(inputEntry2));

        // verify mock expectations
    }

    @Test
    public void testGetEntriesAfterUpdate() throws Exception {
        // initialize variable inputs
        Entry inputEntry1 = getTestEntry("Input subject 1");
        Entry inputEntry2 = getTestEntry("Input subject 2");

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);

        Entry inputEntry3 = setTestEntry(inputEntry1, "Input subject 2 update");
        data.updateEntry(inputEntry3, inputEntry1);
        reset(fs);

        Entry resultEntry1 = data.getEntry(inputEntry2.getId());
        Entry resultEntry2 = data.getEntry(inputEntry3.getId());

        Set<Entry> expectedEntrySet = new HashSet<>(Arrays.asList(resultEntry1, resultEntry2));

        Collection<Entry> resultEntries = data.getEntries();

        // assert return value
        // assert result size
        Assert.assertEquals(getMessage("getEntries", "after update", "result size"), expectedEntrySet.size(), resultEntries.size());
        // assert entries entryExists
        Assert.assertTrue(getMessage("getEntries", "after update", "all entries"), resultEntries.containsAll(expectedEntrySet));
        Assert.assertTrue(getMessage("getEntries", "after update", "entry 1 updated"), resultEntries.contains(inputEntry3));
        Assert.assertTrue(getMessage("getEntries", "after update", "entry 2"), resultEntries.contains(inputEntry2));

        // verify mock expectations
    }

    @Test
    public void testGetEntriesAfterRemove() throws Exception {
        // initialize variable inputs
        Entry inputEntry1 = getTestEntry("Input subject 1");
        Entry inputEntry2 = getTestEntry("Input subject 2");

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        reset(fs);

        data.removeEntry(inputEntry2.getId());

        Entry resultEntry1 = data.getEntry(inputEntry1.getId());
        Entry resultEntry2 = data.getEntry(inputEntry2.getId());

        Set<Entry> expectedEntrySet = new HashSet<>(Arrays.asList(resultEntry1));

        Collection<Entry> resultEntries = data.getEntries();

        // assert return value
        // assert result size
        Assert.assertEquals(getMessage("Get Entries", "after remove", "result size"), expectedEntrySet.size(), resultEntries.size());
        // assert entries entryExists
        Assert.assertTrue(getMessage("Get Entries", "after remove", "all entries"), resultEntries.containsAll(expectedEntrySet));
        Assert.assertTrue(getMessage("Get Entries", "after remove", "entry 1"), resultEntries.contains(inputEntry1));
        Assert.assertFalse(getMessage("Get Entries", "after remove", "entry 2"), resultEntries.contains(inputEntry2));
        // assert removed element
        Assert.assertNull(getMessage("Get Entries", "after remove", "entry 2 is null"), resultEntry2);

        // verify mock expectations
        verify(fs).delete(inputEntry2.getId());
    }

    @Test
    public void testGetNotifications() throws Exception {
        // initialize variable inputs





        // initialize mocks



        // initialize class to test



        // invoke method on class to test



        // assert return value



        // verify mock expectations
    }

//    entryExists method

//    entryExists tests

    @Test
    public void testEntryExists() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(null);

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry);
        reset(fs);
        boolean resultReceived = data.entryExists(inputEntry.getId());

        // assert return value
        Assert.assertTrue(getMessage("entryExists", "", ""), resultReceived);
        // verify mock expectations
    }

    @Test
    public void testEntryExistsNegative() throws Exception {
        // initialize variable inputs
        UUID emptyId = UUID.randomUUID();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.removeEntry(emptyId);
        boolean resultReceived = data.entryExists(emptyId);

        // assert return value
        Assert.assertFalse(getMessage("entryExists", "", "negative"), resultReceived);
        // verify mock expectations
    }

//    getEntry method

    @Test
    public void testGetEntry() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry("Input subject");

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry);
        reset(fs);
        Entry resultEntry = data.getEntry(inputEntry.getId());

        // assert return value
        Assert.assertEquals(inputEntry, resultEntry);

        // verify mock expectations
    }

    @Test
    public void testGetEntryNegative() throws Exception {
        // initialize variable inputs
        UUID emptyId = UUID.randomUUID();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.removeEntry(emptyId);
        Entry resultReceived = data.getEntry(emptyId);

        // assert return value
        Assert.assertNull(resultReceived);

        // verify mock expectations
    }

    @Test
    public void testGetEntryByAttender() throws Exception {
        // initialize variable inputs
        String inputAttender1 = "test@gmail.com";
        String inputAttender2 = "test1@gmail.com";
        Entry inputEntry1 = new Entry.Builder().build();
        Entry inputEntry2 = new Entry.Builder().attenders(Arrays.asList(inputAttender1, inputAttender2)).build();
        Entry inputEntry3 = new Entry.Builder().attenders(Arrays.asList(inputAttender1)).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        data.addEntry(inputEntry3);
        reset(fs);

        List<Entry> resultExpected = new ArrayList<>(Arrays.asList(inputEntry2, inputEntry3));
        List<Entry> resultReceived = data.getEntryByAttender(inputAttender1);

        // assert return value
        //assertEquals(resultExpected, resultReceived);
        Assert.assertTrue(resultExpected.containsAll(resultReceived));
        Assert.assertTrue(resultReceived.containsAll(resultExpected));

        // verify mock expectations
    }

    @Test
    public void testGetEntryByAttenderAfterUpdate() throws Exception {
        // initialize variable inputs
        String inputAttender1 = "test@gmail.com";
        String inputAttender2 = "test1@gmail.com";
        Entry inputEntry1 = new Entry.Builder().build();
        Entry inputEntry2 = new Entry.Builder().attenders(Arrays.asList(inputAttender1, inputAttender2)).build();
        Entry inputEntry3 = new Entry.Builder().attenders(Arrays.asList(inputAttender1)).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        data.addEntry(inputEntry3);
        reset(fs);
        Entry inputEntry3_upd = new Entry.Builder(inputEntry3).attenders(Arrays.asList(inputAttender1)).build();
        data.updateEntry(inputEntry3_upd, inputEntry3);

        List<Entry> resultExpected = new ArrayList<>(Arrays.asList(inputEntry2, inputEntry3, inputEntry3_upd));
        List<Entry> resultReceived = data.getEntryByAttender(inputAttender1);

        // assert return value
        //assertEquals(resultExpected, resultReceived);
        Assert.assertTrue(resultExpected.containsAll(resultReceived));
        Assert.assertTrue(resultReceived.containsAll(resultExpected));

        // verify mock expectations
        InOrder order = inOrder(fs);
        order.verify(fs).delete(inputEntry3_upd.getId());
        order.verify(fs).write(inputEntry3_upd);
    }

    @Ignore
    @Test
    public void testGetEntryByAttenderAfterRemove() throws Exception {
        // initialize variable inputs
        String inputAttender1 = "test@gmail.com";
        String inputAttender2 = "test1@gmail.com";
        Entry inputEntry1 = new Entry.Builder().build();
        Entry inputEntry2 = new Entry.Builder().attenders(Arrays.asList(inputAttender1, inputAttender2)).build();
        Entry inputEntry3 = new Entry.Builder().attenders(Arrays.asList(inputAttender1)).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        data.addEntry(inputEntry3);
        data.removeEntry(inputEntry3.getId());
        reset(fs);

        List<Entry> resultExpected = new ArrayList<>(Arrays.asList(inputEntry2));
        List<Entry> resultReceived = data.getEntryByAttender(inputAttender1);

        // assert return value
        // assertEquals(resultExpected, resultReceived);
        Assert.assertTrue(resultExpected.containsAll(resultReceived));
        Assert.assertTrue(resultReceived.containsAll(resultExpected));

        // verify mock expectations
    }

    @Test
    public void testGetEntryByNullAttender() throws Exception {
        // initialize variable inputs
        String inputAttender1 = "test@gmail.com";
        String inputAttender2 = "test1@gmail.com";
        Entry inputEntry1 = new Entry.Builder().build();
        Entry inputEntry2 = new Entry.Builder().attenders(Arrays.asList(inputAttender1, inputAttender2)).build();
        Entry inputEntry3 = new Entry.Builder().attenders(Arrays.asList(inputAttender1)).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        data.addEntry(inputEntry3);
        reset(fs);

        List<Entry> resultExpected = new ArrayList<>();
        List<Entry> resultReceived = data.getEntryByAttender(null);

        // assert return value
        Assert.assertEquals(resultExpected, resultReceived);

        // verify mock expectations
    }

    @Test
    public void testGetEntryByEmptyAttender() throws Exception {
        // initialize variable inputs
        String inputAttender1 = "test@gmail.com";
        String inputAttender2 = "test1@gmail.com";
        Entry inputEntry1 = new Entry.Builder().build();
        Entry inputEntry2 = new Entry.Builder().attenders(Arrays.asList(inputAttender1, inputAttender2)).build();
        Entry inputEntry3 = new Entry.Builder().attenders(Arrays.asList(inputAttender1)).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry1);
        data.addEntry(inputEntry2);
        data.addEntry(inputEntry3);
        reset(fs);

        List<Entry> resultExpected = new ArrayList<>();
        List<Entry> resultReceived = data.getEntryByAttender("");

        // assert return value
        Assert.assertEquals(resultExpected, resultReceived);

        // verify mock expectations
    }

//    addEntry testing

    @Test
    public void testAddEntry() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(null);

        // initialize mocks

        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputEntry);

        Entry resultEntry = data.getEntry(inputEntry.getId());
        boolean resultReceived = data.entryExists(inputEntry.getId());
        //when(fs.write(inputEntry)).thenReturn(true);


        // assert return value
        Assert.assertEquals(getMessage("addEntry", "", "equals"), inputEntry, resultEntry);
        Assert.assertTrue(getMessage("addEntry", "", "exists"), resultReceived);


        // verify mock expectations
        verify(fs).write(inputEntry);

    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();


    @Test
    public void testAddEntryTwoTimes() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(null);

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        thrown.expect(CalendarKeyViolation.class);
        data.addEntry(inputEntry);
        reset(fs);
        data.addEntry(inputEntry);

        // assert return value


        // verify mock expectations
    }

    @Test
    public void testAddNullEntry() throws Exception {
        // initialize variable inputs
        Entry inputEntry = null;

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        //thrown.expect(NullPointerException.class);
        Entry resultEntry = data.addEntry(inputEntry);

        //throw new NullPointerException();

        // assert return value
        Assert.assertNull(getMessage("addEntry", "Null", ""), resultEntry);
        // verify mock expectations
    }

//    updateEntry testing

    @Test
    public void testUpdateEntryWhenEntryNotExistsInDataStore() throws Exception {
        // initialize variable inputs
        Entry inputInitEntry = getTestEntry("Init");
        Entry inputEditEntry = setTestEntry(inputInitEntry, "Edit");
        Entry expectedEntry = new Entry.Builder(inputEditEntry).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        Entry resultReceivedEntry = data.updateEntry(inputEditEntry, inputInitEntry);
        boolean resultReceived =  data.entryExists(inputEditEntry.getId());

        // assert return value
        Assert.assertEquals(getMessage("updateEntry", "not entryExists in datastore", "equals"), inputEditEntry, resultReceivedEntry);
        Assert.assertTrue(getMessage("updateEntry", "not entryExists in datastore", "exists"), resultReceived);

        // verify mock expectations
        InOrder order = inOrder(fs);
        order.verify(fs).delete(expectedEntry.getId());
        order.verify(fs).write(expectedEntry);
    }

    @Test
    public void testUpdateEntryWhenEntryExistsInDataStore() throws Exception {
        // initialize variable inputs
        Entry inputInitEntry = getTestEntry("Init");
        Entry inputEditEntry = setTestEntry(inputInitEntry, "Edit");
        Entry expectedEntry = new Entry.Builder(inputEditEntry).build();

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        data.addEntry(inputInitEntry);
        reset(fs);
        Entry resultReceivedEntry = data.updateEntry(inputEditEntry, inputInitEntry);
        boolean resultReceived = data.entryExists(inputEditEntry.getId());

        // assert return value
        Assert.assertEquals(getMessage("updateEntry", "entryExists in datastore", "equals"), inputEditEntry, resultReceivedEntry);
        Assert.assertTrue(getMessage("updateEntry", "entryExists in datastore", "exists"), resultReceived);

        // verify mock expectations
        InOrder order = inOrder(fs);
        order.verify(fs).delete(expectedEntry.getId());
        order.verify(fs).write(expectedEntry);
    }

    @Test
    public void testUpdateEntryWhenOldEntryIsNull() throws Exception {
        // initialize variable inputs
        Entry inputInitEntry = getTestEntry("Init");
        Entry inputEditEntry = setTestEntry(inputInitEntry, "Edit");

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        Entry resultReceivedEntry = data.updateEntry(inputEditEntry, null);

        // assert return value
        Assert.assertEquals(getMessage("updateEntry", "old entry is null", "equals"), inputEditEntry, resultReceivedEntry);

        // verify mock expectations
        InOrder order = inOrder(fs);
        order.verify(fs).delete(inputEditEntry.getId());
        order.verify(fs).write(inputEditEntry);
    }

    @Test
    public void testUpdateEntryWhenNewEntryIsNull() throws Exception {
        // initialize variable inputs
        Entry inputInitEntry = null;

        // initialize mocks
        // initialize class to test
        // invoke method on class to test
        Entry resultReceivedEntry = data.updateEntry(inputInitEntry, null);

        // assert return value
        Assert.assertNull(getMessage("updateEntry", "new entry is null", "null"), resultReceivedEntry);

        // verify mock expectations
    }



}
