package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.kexie.acloud.domain.Major;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/4/30 10:53
 * Description :
 */
public class MajorSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        Major value = (Major) object;
        if (value == null)
            return;
        out.writeInt(value.getId());
    }
}
