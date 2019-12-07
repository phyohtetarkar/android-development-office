package com.team.androidpos.model;


import androidx.room.TypeConverter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class TypeConverters {

    @TypeConverter
    public static long fromDateTime(LocalDateTime dateTime) {
        return dateTime.toDateTime(DateTimeZone.getDefault()).getMillis();
    }

    @TypeConverter
    public static LocalDateTime toDateTime(long value) {
        return new DateTime(value, DateTimeZone.getDefault()).toLocalDateTime();
    }

}
