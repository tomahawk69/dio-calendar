package com.dio.calendar.client.old;

import com.dio.calendar.Entry;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * OLD
 * Created by iovchynnikov on 7/8/2014.
 */
@XmlRootElement
public class EntriesWrapper implements Serializable {
    private List<Entry> entries;

    public EntriesWrapper() {
    }

    public EntriesWrapper(List<Entry> entries) {
        this.entries = entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
