package by.vladimir.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public Date formatterStrToDate(String dateString){
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
}
