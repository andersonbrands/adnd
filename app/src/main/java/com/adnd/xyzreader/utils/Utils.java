package com.adnd.xyzreader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getFormattedPublishedDate(String publishedDate) {
        Date date = parsePublishedDate(publishedDate);

        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM d, YYYY");

        return outputFormat.format(date);
    }

    private static Date parsePublishedDate(String publishedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
        try {
            return dateFormat.parse(publishedDate);
        } catch (ParseException ex) {
            return new Date();
        }
    }
}
