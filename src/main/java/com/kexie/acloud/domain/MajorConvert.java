package com.kexie.acloud.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created : wen
 * DateTime : 2017/4/30 11:07
 * Description :
 */
@Component
public class MajorConvert implements Converter<String, Major> {
    @Override
    public Major convert(String source) {
        Major major = new Major();
        major.setId(new Integer(source));
        return major;
    }
}
