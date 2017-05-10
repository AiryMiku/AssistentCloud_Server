package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.kexie.acloud.domain.College;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/5/10 22:58
 * Description :
 */
public class CollegeSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        College value = (College) object;
        if (value == null)
            return;
        out.writeInt(value.getId());
    }
}
