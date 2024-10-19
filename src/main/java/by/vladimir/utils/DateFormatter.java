package by.vladimir.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    private static final DateFormatter INSTANCE = new DateFormatter();

    private DateFormatter() {
    }

    public static DateFormatter getInstance() {
        return INSTANCE;
    }

    public Date formatterStrToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Неверный формат даты");
            throw new RuntimeException(e);
        }
        return date;
    }

    public java.sql.Date convertSqlToUtil(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date);
        return java.sql.Date.valueOf(formattedDate);
    }
}
