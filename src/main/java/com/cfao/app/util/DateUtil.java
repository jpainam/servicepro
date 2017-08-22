package com.cfao.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by JP on 8/6/2017.
 */
public class DateUtil {
    public static final DateTimeFormatter currentForme = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter sqlForme = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String age(LocalDate date) {
        int a = LocalDate.now().getYear() - date.getYear();
        return a + (a > 1 ? " ans" : " an");
    }
}
