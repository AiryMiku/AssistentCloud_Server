package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.kexie.acloud.domain.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/29 22:52
 * Description :
 */
public class UserIdListSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        List<User> users = (List<User>) object;
        List<String> ids = new ArrayList<>();
        users.forEach(user -> ids.add(user.getUserId()));
        serializer.write(ids);
    }
}
