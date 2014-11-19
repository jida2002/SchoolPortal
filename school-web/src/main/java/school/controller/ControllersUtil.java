package school.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;

public class ControllersUtil {
    public static Date addOrDelDays(Date fch, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, days);
        return new Date(cal.getTimeInMillis());
    }

    public static Date dateProceed(String date, SimpleDateFormat format,
            int days, boolean forwardFlag) {
        Date generatedDate;

        try {
            if (date == null) {
                if (forwardFlag) {
                    generatedDate = ControllersUtil.addOrDelDays(new Date(),
                            -days);
                } else {
                    generatedDate = ControllersUtil.addOrDelDays(new Date(),
                            days);
                }
                return generatedDate;
            }
            generatedDate = format.parse(date);
        } catch (ParseException e) {
            if (forwardFlag) {
                generatedDate = ControllersUtil.addOrDelDays(new Date(), -days);
            } else {
                generatedDate = ControllersUtil.addOrDelDays(new Date(), days);
            }
        }
        return generatedDate;
    }
}