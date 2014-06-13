package com.dio.calendar;

/**
 * Created by iovchynnikov on 4/30/14.
 */
public enum EnumNotificationType {
    EMAIL("email", "By email"),
    POPUP("popup", "Popup window"),
    SMS("SMS", "SMS");

    private final String id;
    private final String caption;

    private EnumNotificationType(String id, String caption) {
        this.id = id;
        this.caption = caption;
    }

}
