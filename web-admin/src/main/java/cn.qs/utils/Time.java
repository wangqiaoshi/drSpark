package cn.qs.utils;

import java.util.Calendar;

/**
 * Created by wqs on 16/11/6.
 */
public class Time {


    public static Long convertToHour(Long inputTime) {
        Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(inputTime);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
