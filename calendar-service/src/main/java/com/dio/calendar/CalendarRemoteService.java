package com.dio.calendar;

import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/11/14.
 */
public interface CalendarRemoteService {

    EntryRemoteWrapper getEntry(String id);
    Entry getEntry(UUID id);
    List<EntryRemoteWrapper> getEntries();

}
