package com.trainibit.middleware.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static Timestamp convertStringToTimestamp(String dateString, String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(dateString);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error al convertir la fecha: '" + dateString + "' con formato: '" + format + "'", e);
        }
    }

    public static Timestamp convertStringToTimestampDefaultFormat(String dateString) {
        try {
            Date date = DATE_FORMATTER.get().parse(dateString);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Error al convertir la fecha: '" + dateString + "' con formato predeterminado 'yyyy-MM-dd'", e);
        }
    }
}
