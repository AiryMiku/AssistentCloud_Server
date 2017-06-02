package com.kexie.acloud.util;

import com.kexie.acloud.domain.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
