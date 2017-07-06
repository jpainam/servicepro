package com.cfao.app.util;

import java.time.format.DateTimeFormatter;

/**
 + * Created by Communication on 21/06/2017.
 + */


public class FormatDate {
    public static final DateTimeFormatter currentForme = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter sqlForme = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}