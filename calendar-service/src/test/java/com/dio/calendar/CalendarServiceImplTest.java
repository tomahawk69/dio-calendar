package com.dio.calendar;

import com.dio.calendar.datastore.CalendarDataStoreImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.dio.calendar.Utils.constructDate;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.*;

public class CalendarServiceImplTest {
    private CalendarDataStoreImpl data;
    private CalendarService service;

    private static final String testMessageTemplate = "Calendar Service test %s %s %s";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static String getTestMessage(String group, String type, String scope) {
        return String.format(testMessageTemplate, group, type, scope);
    }

    private Entry getTestEntry(CalendarService source) throws CalendarEntryBadAttribute, RemoteException {
        String testSubject, testDescription;
        Date testStartDate, testEndDate;
        List<String> testAttenders;
        List<Notification> testNotifications;

        // initialize variable inputs
        testSubject = "Subject";
        testDescription = "Description";
        testStartDate = constructDate(2013, 11, 1, 10, 0, 0);
        testEndDate = constructDate(2013, 11, 10, 10, 0, 0);
        testAttenders = new ArrayList<>();
        testNotifications = new ArrayList<>();

        return source.newEntry(testSubject, testDescription, testStartDate, testEndDate, testAttenders, testNotifications);
    }

    @Before
    public void setUp() {
        data = mock(CalendarDataStoreImpl.class);
        service = new CalendarServiceImpl(data);
    }

    @Test
    public void testGetFreeTimeLine() throws Exception {

        // initialize variable inputs

        // initialize mocks

        // initialize class to test

        // invoke method on class to test

        // assert return value

        // verify mock expectations


    }

    @Test
    public void testConstructDate() throws Exception {
        // initialize variable inputs
        Date result;
        String resultExpected, resultReceived;
        Integer year = 2013, month = 11, day = 1, hour = 10, minute = 13, seconds = 13;
        resultExpected = "Fri Nov 01 10:13:13 EET 2013";
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        result = constructDate(year, month, day, hour, minute, seconds);
        resultReceived = result.toString();

        // assert return value
        assertEquals(getTestMessage("constructDate", "positive", null), resultReceived, resultExpected);
        // verify mock expectations

    }

    @Test
    public void testAddNull() throws Exception {

        String testSubject, testDescription;
        Date testStartDate, testEndDate;
        List<String> testAttenders;
        List<Notification> testNotifications;

        // initialize variable inputs
        testSubject = "Subject";
        testDescription = "Description";
        testStartDate = constructDate(2013, 11, 1, 10, 0, 0);
        testEndDate = constructDate(2013, 11, 10, 10, 0, 0);
        testAttenders = new ArrayList<>();
        testNotifications = new ArrayList<>();

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        //thrown.expect(CalendarEntryBadAttribute.class);

        //thrown.expectMessage(CoreMatchers.containsString("subject"));
        Entry test = service.newEntry(null, testDescription, testStartDate, testEndDate, testAttenders, testNotifications);
        service.addEntry(test);
/*
        thrown.expectMessage(CoreMatchers.containsString("description"));
        service.newEntry(testSubject, null, testStartDate, testEndDate, testAttenders, testNotifications);

        thrown.expectMessage(CoreMatchers.containsString("startDate"));
        service.newEntry(testSubject, testDescription, null, testEndDate, testAttenders, testNotifications);

        thrown.expectMessage(CoreMatchers.containsString("endDate"));
        service.newEntry(testSubject, testDescription, testStartDate, null, testAttenders, testNotifications);

        thrown.expectMessage(both(CoreMatchers.containsString("startDate")).and(CoreMatchers.containsString("endDate")));
        service.newEntry(testSubject, testDescription, testEndDate, testStartDate, testAttenders, testNotifications);

        thrown.expectMessage(CoreMatchers.containsString("attenders"));
        service.newEntry(testSubject, testDescription, testStartDate, testEndDate, null, testNotifications);

        thrown.expectMessage(CoreMatchers.containsString("notifications"));
        service.newEntry(testSubject, testDescription, testStartDate, testEndDate, testAttenders, null);
*/
        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testAddBadDate() throws Exception {

        String testSubject, testDescription;
        Date testStartDate, testEndDate;
        List<String> testAttenders;
        List<Notification> testNotifications;

        // initialize variable inputs
        testSubject = "Subject";
        testDescription = "Description";
        testStartDate = constructDate(2013, 11, 1, 10, 0, 0);
        testEndDate = constructDate(2013, 11, 10, 10, 0, 0);
        testAttenders = new ArrayList<>();
        testNotifications = new ArrayList<>();

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);

        thrown.expectMessage(both(containsString("startDate")).and(containsString("endDate")));
        service.newEntry(testSubject, testDescription, testEndDate, testStartDate, testAttenders, testNotifications);

        // assert return value

        // verify mock expectations

    }

    @Test
    public void testEdit() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);

        String testSubject = inputEntry.getSubject() + "test";
        String testDescription = inputEntry.getDescription() + "description";
        Date testStartDate = new Date(inputEntry.getStartDate().getTime() - 1);
        Date testEndDate = new Date(inputEntry.getEndDate().getTime() + 1);
        List<String> testAttenders = new ArrayList<>();
        List<Notification> testNotifications = new ArrayList<>();

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        Entry resultEntry = service.editSubject(inputEntry, testSubject);
        resultEntry = service.editDescription(resultEntry, testDescription);
        resultEntry = service.editStartDate(resultEntry, testStartDate);
        resultEntry = service.editEndDate(resultEntry, testEndDate);
        resultEntry = service.editAttenders(resultEntry, testAttenders);
        resultEntry = service.editNotifications(resultEntry, testNotifications);

        // assert return value
        assertEquals(getTestMessage("edit", "positive", "subject"), testSubject, resultEntry.getSubject());
        assertEquals(getTestMessage("add", "positive", "description"), testDescription, resultEntry.getDescription());
        assertEquals(getTestMessage("add", "positive", "startDate"), testStartDate, resultEntry.getStartDate());
        assertEquals(getTestMessage("add", "positive", "endDate"), testEndDate, resultEntry.getEndDate());
        assertEquals(getTestMessage("add", "positive", "attenders"), testAttenders, resultEntry.getAttenders());
        assertEquals(getTestMessage("add", "positive", "notifications"), testNotifications, resultEntry.getNotifications());

        // verify mock expectations

    }


    @Test
    public void testEditSubject() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);
        String inputSubject = inputEntry.getSubject();
        inputSubject += inputSubject;
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        Entry resultEntry = service.editSubject(inputEntry, inputSubject);

        // assert return value
        assertEquals(getTestMessage("edit", "positive", "subject"), inputSubject, resultEntry.getSubject());
        assertThat(resultEntry, not(inputEntry));
        //Assert.  assertNotEquals(getTestMessage("edit immutable", "negative", "description"), inputEntry, resultEntry);

        // verify mock expectations

    }

    @Test
    public void testEditDescription() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);
        String inputDescription = inputEntry.getDescription();
        inputDescription += inputDescription;
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        Entry resultEntry = service.editDescription(inputEntry, inputDescription);

        // assert return value
        assertEquals(getTestMessage("edit", "positive", "description"), inputDescription, resultEntry.getDescription());
        assertThat(resultEntry, is(not(inputEntry)));
        //assertNotEquals(getTestMessage("edit immutable", "negative", "description"), inputEntry, resultEntry);

        // verify mock expectations

    }

    @Test
    public void testEditStartDate() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);

        Date inputStartDate = new Date(inputEntry.getStartDate().getTime() - 1);
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        Entry resultEntry = service.editStartDate(inputEntry, inputStartDate);

        // assert return value
        assertEquals(getTestMessage("edit", "positive", "startDate"), inputStartDate, resultEntry.getStartDate());
        assertThat(resultEntry, is(not(inputEntry)));

        //assertNotEquals(getTestMessage("edit immutable", "negative", "startDate"), inputEntry, resultEntry);

        // verify mock expectations

    }

    //@Test
    public void testEditStartDateNegative() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);
        Date inputStartDate = new Date(inputEntry.getEndDate().getTime() + 1);
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);
        service.editStartDate(inputEntry, inputStartDate);

        // assert return value

        // verify mock expectations

    }

    @Test
    public void testEditEndDate() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);
        Date inputEndDate = new Date(inputEntry.getEndDate().getTime() + 1);
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        Entry resultEntry = service.editEndDate(inputEntry, inputEndDate);

        // assert return value
        assertEquals(getTestMessage("edit", "positive", "endDate"), inputEndDate, resultEntry.getEndDate());
        assertThat(resultEntry, is(not(inputEntry)));

        //assertNotEquals(getTestMessage("edit immutable", "negative", "endDate"), inputEntry, resultEntry);

        // verify mock expectations

    }

    //@Test
    public void testEditEndDateNegative() throws Exception {
        // initialize variable inputs
        Entry inputEntry = getTestEntry(service);
        Date inputEndDate = new Date(inputEntry.getStartDate().getTime() - 1);
        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);
        service.editEndDate(inputEntry, inputEndDate);

        // assert return value

        // verify mock expectations

    }

    @Test
    public void testEditAttenders() throws Exception {

        // initialize variable inputs

        // initialize mocks

        // initialize class to test

        // invoke method on class to test

        // assert return value

        // verify mock expectations

    }

    @Test
    public void testEditNotifications() throws Exception {

        // initialize variable inputs

        // initialize mocks

        // initialize class to test

        // invoke method on class to test

        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testEditSubjectNull() throws Exception {
        // initialize variable inputs
        Entry testEntry = getTestEntry(service);

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);

        thrown.expectMessage(containsString("subject"));
        service.editSubject(testEntry, null);

        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testEditDescriptionNull() throws Exception {

        // initialize variable inputs
        Entry testEntry = getTestEntry(service);

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);
        thrown.expectMessage(containsString("description"));
        service.editDescription(testEntry, null);

        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testEditStartDateNull() throws Exception {

        // initialize variable inputs
        Entry testEntry = getTestEntry(service);

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);

        thrown.expectMessage(containsString("startDate"));
        service.editStartDate(testEntry, null);

        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testEditEndDateNull() throws Exception {

        // initialize variable inputs
        Entry testEntry = getTestEntry(service);

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);
        thrown.expectMessage(containsString("endDate"));
        service.editEndDate(testEntry, null);

        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testEditAttendersNull() throws Exception {

        // initialize variable inputs
        Entry testEntry = getTestEntry(service);

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);
        thrown.expectMessage(containsString("attenders"));
        service.editAttenders(testEntry, null);

        // assert return value

        // verify mock expectations

    }

    //@Test
    public void testEditNotificationsNull() throws Exception {

        // initialize variable inputs
        Entry testEntry = getTestEntry(service);

        // initialize mocks

        // initialize class to test

        // invoke method on class to test
        thrown.expect(CalendarEntryBadAttribute.class);
        thrown.expectMessage(containsString("notifications"));
        service.editNotifications(testEntry, null);

        // assert return value

        // verify mock expectations

    }

    @Test
    public void testSaveAddData() throws Exception {
        // initialize variable inputs
        CalendarDataStoreImpl dataMock = mock(CalendarDataStoreImpl.class);
        CalendarService serviceMock = new CalendarServiceImpl(dataMock);

        Entry testInput = getTestEntry(service);

        // initialize mocks
        when(dataMock.addEntry((Entry) notNull())).thenReturn(testInput);

        // initialize class to test

        // invoke method on class to test
        Entry testResult = serviceMock.addEntry(testInput);


        // assert return value
        assertEquals(getTestMessage("Save Add mock", "positive", ""), testInput, testResult);

        // verify mock expectations
        verify(dataMock).addEntry((Entry) notNull());
        //verify(dataMock).addEntry(isA(Entry.class));
    }

    @Test
    public void testEditData() throws Exception {
        // initialize variable inputs

        Entry testInput = getTestEntry(service);
        Entry newInput = service.editSubject(testInput, "New subject");

        // initialize mocks
        when(data.updateEntry(isA(Entry.class), isA(Entry.class))).thenReturn(testInput);
//        when(data.updateEntry((Entry) notNull(), (Entry) notNull())).thenReturn(testInput);
        when(data.getEntry((UUID) notNull())).thenReturn(testInput);

        // initialize class to test

        // invoke method on class to test
        Entry testResult = service.updateEntry(newInput, testInput);

        // assert return value
        assertEquals(getTestMessage("Save Edit mock", "positive", ""), testInput, testResult);

        // verify mock expectations
        //verify(dataMock).addEntry(isA(Entry.class));
        verify(data).updateEntry((Entry) notNull(), (Entry) notNull());
        verify(data).getEntry((UUID) notNull());
    }

    @Test
    public void testRemove() throws Exception {

        // initialize variable inputs
        Entry testInput = getTestEntry(service);

        // initialize mocks
        when(data.removeEntry((UUID) notNull())).thenReturn(testInput);

        // initialize class to test

        // invoke method on class to test
        Entry testResult = service.removeEntry(testInput);

        // assert return value
        assertEquals(getTestMessage("Remove mock", "positive", ""), testInput, testResult);

        // verify mock expectations
        //verify(dataMock).addEntry(isA(Entry.class));
        verify(data).removeEntry((UUID) notNull());

    }
}