package com.dio.calendar;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by iovchynnikov on 7/22/2014.
 */
public class TestCases {

    private List<TestCase> rules = new LinkedList<>();

    public Boolean isEquals(Map<String, Object> row) {
        for (TestCase rule : rules) {
            if (! rule.equals(row)) {
                return false;
            }
        }
        return true;
    }

}
