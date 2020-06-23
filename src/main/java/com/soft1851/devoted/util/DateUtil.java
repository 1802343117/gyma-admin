package com.soft1851.devoted.util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 12559
 */
public class DateUtil implements Serializable {

    public DateUtil() {
    }

    public Map<String, Integer> getDate(LocalDateTime localDateTime) {

        Integer year = localDateTime.getYear();

        Integer monthValue = localDateTime.getMonthValue();

        Integer day = localDateTime.getDayOfMonth();

        Integer hour = localDateTime.getHour();

        Integer minute = localDateTime.getMinute();

        Integer second = localDateTime.getSecond();

        Map<String, Integer> map = new HashMap<>();

        map.put("year", year);
        map.put("month", monthValue);
        map.put("day", day);
        map.put("hour", hour);
        map.put("minute", minute);
        map.put("second", second);

        return map;
    }

    public static void main(String[] args) {
        LocalDateTime time=LocalDateTime.now();
        DateUtil dateUtil = new DateUtil();

        System.out.println(dateUtil.getDate(time).get("year"));
    }
}
