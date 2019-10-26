package com.team.androidpos.model;


import androidx.room.TypeConverter;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

public class TypeConverters {

    @TypeConverter
    public static String fromDateTime(LocalDateTime dateTime) {
        return dateTime.toString("MMM dd, yyyy hh:mm a");
    }

    @TypeConverter
    public static LocalDateTime toDateTime(String value) {
        return LocalDateTime.parse(value, DateTimeFormat.forPattern("MMM dd, yyyy hh:mm a"));
    }

}
