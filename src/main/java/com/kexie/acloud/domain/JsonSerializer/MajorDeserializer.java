package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.kexie.acloud.domain.Major;

import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/4/30 10:53
 * Description :
 */
public class MajorDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        int majorId = parser.parseObject(int.class);
        Major major = new Major();
        major.setId(majorId);
        return (T) major;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
