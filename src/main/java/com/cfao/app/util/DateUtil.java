package com.cfao.app.util;

import java.time.LocalDate;

/**
 * Created by JP on 8/6/2017.
 */
public class DateUtil {

    public static String age(LocalDate date) {
        int a = LocalDate.now().getYear() - date.getYear();
        return a + (a > 1 ? " ans" : " an");
    }
}
