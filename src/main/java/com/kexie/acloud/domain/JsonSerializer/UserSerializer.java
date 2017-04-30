package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.kexie.acloud.domain.User;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/4/28 20:52
 * Description :
 */
public class UserSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        User value = (User) object;
        if (value == null)
            return;
        out.writeString(value.getUserId());
    }
}
