package com.dio.calendar;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by iovchynnikov on 5/8/14.
 */
public class GmailHelper {

    private static Logger logger = Logger.getLogger(GmailHelper.class);
    private final Pattern emailPattern = Pattern.compile("^[^@]+?@([\\.\\dA-z]+)$");
    private Map<String, Pattern> patterns = new HashMap();

    public List<String> getGmailAttenders(Entry inputValue) {
        return getDomainAttenders(inputValue, "gmail.com");
    }

    public List<String> getDomainAttenders(Entry inputEntry, String domain) {
        List<String> result = new LinkedList<>();
        if (inputEntry == null) {
            logger.warn("Null entry parameter passed");
            return result;
        }
        if (domain == null) {
            logger.warn("Null domain parameter passed");
            return result;
        }
        if (domain == "") {
            logger.warn("Empty domain parameter passed");
            return result;
        }
        Pattern domainPattern = emailPattern;//patterns.get(domain.toLowerCase());
        if (domainPattern == null) {
            domainPattern = Pattern.compile("^[^@]+?@(.+)$");
            patterns.put(domain.toLowerCase(), domainPattern);
        }
        inputEntry.getAttenders();
//        if (!isEmpty(inputEntry.getAttenders())) {
//            Matcher m;
//            for (String item : inputEntry.getAttenders()) {
//                m = domainPattern.matcher(item);
//                if (m.find()) {
//                    if (m.group(1).toString().equalsIgnoreCase(domain)) {
//                        result.add(item);
//                    }
//                }
//            }
//        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("getDomainAttenders: passed %s, received %s", inputEntry.getAttenders(), result));
        }
        return result;
    }
}
