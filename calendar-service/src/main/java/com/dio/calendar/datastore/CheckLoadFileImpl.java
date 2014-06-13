package com.dio.calendar.datastore;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by yur on 04.06.2014.
 */
public class CheckLoadFileImpl implements CheckLoadFile {
    private final List<Future> futures;
    private final CalendarDataStore data;

    public CheckLoadFileImpl(List<Future> futures, CalendarDataStore data) {
        this.futures = futures;
        this.data = data;
    }

    @Override
    public void run() {
        Boolean result;
        do {
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            result = true;
            for (Future future : futures) {
                if (!future.isDone()) {
                    result = false;
                    break;
                }
            }
        } while (!result);

        data.setLoadDone();


    }
}
