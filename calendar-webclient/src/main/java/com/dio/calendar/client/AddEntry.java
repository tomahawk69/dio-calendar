package com.dio.calendar.client;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Created by iovchynnikov on 6/25/2014.
 */
@ManagedBean
@RequestScoped
public class AddEntry extends EntryWrapper {
    private final ClientWrapperImpl localService;
    private Entry oldEntry;

    @Autowired
    public AddEntry(ClientWrapperImpl localService) {
        this.localService = localService;
    }




}
