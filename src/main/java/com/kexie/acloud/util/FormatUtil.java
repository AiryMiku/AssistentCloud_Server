package com.kexie.acloud.util;

import com.kexie.acloud.domain.User;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by zojian on 2017/6/2.
 */
public class FormatUtil {
    public static Set<String> formatUserId(List<User> users){
        Set<String> executors = new HashSet<>();
        for (User user: users) {
            executors.add(user.getUserId());
        }
        return executors;
    }

    public static Map<String,Integer> formatTuple(Set<Tuple> tuples){
        Map<String,Integer> result = new HashMap<>();
        for (Tuple t:tuples){
            result.put(t.getElement(),(int)t.getScore());
        }
        return result;
    }

    public static List<Integer> formatJsonList(String jsonList){
        String a = jsonList.substring(1,jsonList.length()-1);
        String[] b = a.split(",");
        List<Integer> result = new ArrayList<>();
        for(String c : b){
            result.add(Integer.parseInt(c));
        }
        return result;
    }
}
