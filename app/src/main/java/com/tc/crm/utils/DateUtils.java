package com.tc.crm.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String oneFormatToAnother(String date, String oldFormat, String newFormat) {
        try {

            SimpleDateFormat originalFormat = new SimpleDateFormat(oldFormat,
                    Locale.US);
            SimpleDateFormat targetFormat = new SimpleDateFormat(newFormat,
                    Locale.ENGLISH);
            Date d = originalFormat.parse(date);
            return targetFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("oneForToAn Exception", e.toString());
            Log.e("oneForToAn Exception", newFormat);
            Log.e("oneForToAn Exception", oldFormat);
            return null;
        }
    }
}
