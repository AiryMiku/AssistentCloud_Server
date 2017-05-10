package com.kexie.acloud.domain.JsonSerializer;

import com.kexie.acloud.domain.User;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created : wen
 * DateTime : 2017/5/10 23:11
 * Description :
 */
@Component
public class UserConvert implements Converter<String, User> {
    @Override
    public User convert(String source) {
        if (source == null || "".equals(source))
            return null;
        User user = new User();
        user.setUserId(source);
        return user;
    }
}
