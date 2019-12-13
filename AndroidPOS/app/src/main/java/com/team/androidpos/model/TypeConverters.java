package com.team.androidpos.model;


import androidx.room.TypeConverter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class TypeConverters {

    @TypeConverter
    public static long fromDateTime(DateTime dateTime) {
        return dateTime.getMillis();
    }

    @TypeConverter
    public static DateTime toDateTime(long value) {
        return new DateTime(value, DateTimeZone.getDefault());
    }

}
