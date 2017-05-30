package com.kexie.acloud.config;

/**
 * Created by zojian on 2017/4/25.
 */

import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                // Spring 基本配置
                AppConfig.class,
                // Spring Security 配置
                SecurityConfig.class
                , RedisConfig.class};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new OpenSessionInViewFilter()
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


}
