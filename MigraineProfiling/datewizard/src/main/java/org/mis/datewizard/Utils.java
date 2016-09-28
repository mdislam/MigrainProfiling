package org.mis.datewizard;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mis on 9/28/2016.
 */

public abstract class Utils {
    /**
     * convert to Date. This is one of the many ways.
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year, int month, int day){
        String dateString = String.format("%d-%d-%d", year, month + 1, day);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * get the day of week from the Date based on specific locale.
     * @param date
     * @return
     */
    public static String getWeekDay(Date date){
        String dayOfWeek = "";

        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);

        return dayOfWeek;
    }

    /**
     *
     * @param month
     * @return
     */
    public static String getMonthName(int month){
        String val = "";

        val = new DateFormatSymbols().getMonths()[month];

        return val;
    }

    public static String getMonthName(Calendar cal){
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month = month_date.format(cal.getTime());
        return month;
    }
}
