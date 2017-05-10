package com.kexie.acloud.domain.FormConvert;

import com.kexie.acloud.domain.College;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created : wen
 * DateTime : 2017/5/10 23:11
 * Description :
 */
@Component
public class CollegeConvert implements Converter<String, College> {
    @Override
    public College convert(String source) {
        if (source == null || "".equals(source))
            return null;
        College college = new College();
        college.setId(new Integer(source));
        return college;
    }
}
