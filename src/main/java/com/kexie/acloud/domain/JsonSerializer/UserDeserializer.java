package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.kexie.acloud.domain.User;

import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/4/28 20:54
 * Description :
 */
public class UserDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String userId = parser.parseObject(String.class);
        User user = new User();
        user.setUserId(userId);
        return (T) user;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
