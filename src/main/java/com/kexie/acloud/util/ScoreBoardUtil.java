package com.kexie.acloud.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zojian on 2017/6/7.
 */
public class ScoreBoardUtil {
    public static final int NOTICE = 10;
    public static final int TASK = 10;
    public static final int MEETING = 10;

    /**
     * 5天内分数有效
     * @param date
     * @param score
     * @return
     */
    public static int getScore(Date date,int score){
        Calendar currentCalendar = Calendar.getInstance();
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(date);
        if(currentCalendar.get(currentCalendar.MONTH)!=lastCalendar.get(lastCalendar.get(Calendar.MONTH))){
            return 0;
        }
        int day1 = lastCalendar.get(lastCalendar.DAY_OF_YEAR);
        int day2 = currentCalendar.get(currentCalendar.DAY_OF_YEAR);

        return score-(day2-day1)*2;
    }

    public static void main(String[] args) {
        System.out.println(getScore(new Date(1493740800000L),ScoreBoardUtil.MEETING));
    }
}
