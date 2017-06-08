package com.kexie.acloud.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zojian on 2017/6/7.
 */
public class DateUtil {
    public static String formatDate(Calendar calendar){
        return  ""+calendar.get(calendar.YEAR)+(calendar.get(calendar.MONTH)+1)+calendar.get(calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间
     * YYYYMMDD
     * @return
     */
    public static String formatCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return  formatDate(calendar);
    }

    /**
     * 获取本周的日期
     * @param calendar
     * @return
     */
    public static List<String> getDateOfThisWeek(Calendar calendar){
        List<String> result = new ArrayList<>();
        result.add(formatDate(calendar));
        while(true){
            calendar.add(calendar.DAY_OF_WEEK,-1);
            if(calendar.get(calendar.DAY_OF_WEEK)!=1){
                result.add(formatDate(calendar));
            }
            else
                break;
        }
        return result;
    }

    /**
     * 获取本月的日期
     * @param calendar
     * @return
     */
    public static List<String> getDateOfThisMonth(Calendar calendar){
        List<String> result = new ArrayList<>();
        result.add(formatDate(calendar));
        while (true){
            calendar.add(calendar.DAY_OF_MONTH,-1);
            if(calendar.get(calendar.DAY_OF_MONTH)!=1){
                result.add(formatDate(calendar));
            }
            else{
                result.add(formatDate(calendar));
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(calendar.DAY_OF_MONTH));
        //System.out.println(getDateOfThisWeek(calendar));
        System.out.println(getDateOfThisMonth(calendar));
    }
}
