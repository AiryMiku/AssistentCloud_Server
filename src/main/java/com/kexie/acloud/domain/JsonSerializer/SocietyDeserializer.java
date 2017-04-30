package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.kexie.acloud.domain.Society;

import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/4/28 20:56
 * Description :
 */
public class SocietyDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        int societyId = parser.parseObject(int.class);
        Society society = new Society();
        society.setId(societyId);
        return (T) society;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
