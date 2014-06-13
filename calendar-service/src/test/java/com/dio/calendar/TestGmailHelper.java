package com.dio.calendar;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by iovchynnikov on 5/8/14.
 */
public class TestGmailHelper {
    private GmailHelper helper;

    @Test
    public void testDomainAttenders() throws Exception {
        String inputDomain = "9a911.Hgaw.com";
        String[] inputAttenders = new String[] {"aaa@" + inputDomain, "bbb@" + inputDomain.toLowerCase(), "ccc@" + inputDomain.toUpperCase(), "ddd" + inputDomain.toUpperCase()};
        Entry inputValue = new Entry.Builder().
                attenders(Arrays.asList(inputAttenders)).
                build();

        List<String> resultExpected = Arrays.asList(inputAttenders[0], inputAttenders[1], inputAttenders[2]);

        helper = new GmailHelper();
        List<String> resultReceived = helper.getDomainAttenders(inputValue, inputDomain);

        assertEquals("Test domain attenders positive", resultExpected, resultReceived);
    }


    @Test
    public void testGetDomainAttendersNegative () throws Exception {
        String inputDomain = "gMail.com";
        String[] inputAttenders = new String[] {
                "aaa@" + "gMail com",
                "bbb@" + inputDomain + "a",
                "ccc@1" + inputDomain.toUpperCase(),
                "ddd" + inputDomain,
                "eee" + inputDomain + inputDomain
        };

        Entry inputValue = new Entry.Builder().
                attenders(Arrays.asList(inputAttenders)).
                build();

        List<String> resultExpected = new ArrayList<>();

        helper = new GmailHelper();
        List<String> resultReceived = helper.getDomainAttenders(inputValue, inputDomain);

        assertEquals("Test domain attenders negative", resultExpected, resultReceived);

    }

    @Test
    public void testGetDomainAttendersNullEntry () throws Exception {
        String inputDomain = "gMail.com";
        Entry inputValue = null;

        List<String> resultExpected = new ArrayList<>();

        helper = new GmailHelper();
        List<String> resultReceived = helper.getDomainAttenders(inputValue, inputDomain);

        assertEquals("Test domain attenders null entry", resultExpected, resultReceived);

    }

    @Test
    public void testGetDomainAttendersNullDomain () throws Exception {
        String inputDomain = "gMail.com";
        String[] inputAttenders = new String[] {"aaa" + inputDomain, "bbb@" + inputDomain + "a", "ccc@1" + inputDomain.toUpperCase(), "ddd" + inputDomain.toUpperCase()};

        Entry inputValue = new Entry.Builder().
                attenders(Arrays.asList(inputAttenders)).
                build();

        List<String> resultExpected = new ArrayList<>();

        helper = new GmailHelper();
        List<String> resultReceived = helper.getDomainAttenders(inputValue, null);

        assertEquals("Test gmail attenders null domain", resultExpected, resultReceived);

    }

    @Test
    public void testGetGmailAttenders () throws Exception {
        String inputDomain = "gMail.com";
        String[] inputAttenders = new String[] {"aaa@" + inputDomain, "bbb@" + inputDomain + "a", "ccc@1" + inputDomain.toUpperCase(), "ddd" + inputDomain.toUpperCase()};

        Entry inputValue = new Entry.Builder().
                attenders(Arrays.asList(inputAttenders)).
                build();

        List<String> resultExpected = new ArrayList<>(Arrays.asList(inputAttenders[0]));

        helper = new GmailHelper();
        List<String> resultReceived = helper.getGmailAttenders(inputValue);

        assertEquals("Test Gmail attenders", resultExpected, resultReceived);

    }

}
