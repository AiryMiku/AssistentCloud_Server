package com.kexie.acloud.domain;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/29 22:52
 * Description :
 */
public class UserIdListDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        List<String> ids = parser.parseArray(String.class);
        List<User> users = new ArrayList<>();

        ids.forEach(id -> users.add(new User(id, null)));
        return (T) users;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
