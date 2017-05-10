package com.kexie.acloud.domain.JsonSerializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.kexie.acloud.domain.College;

import java.lang.reflect.Type;

/**
 * Created : wen
 * DateTime : 2017/5/10 22:59
 * Description :
 */
public class CollegeDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        int collegeId = parser.parseObject(int.class);
        College college = new College();
        college.setId(collegeId);
        return (T) college;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
