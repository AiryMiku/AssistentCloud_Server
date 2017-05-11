package com.kexie.acloud.config;

/**
 * Created by zojian on 2017/4/25.
 */

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.FormConvert.CollegeConvert;
import com.kexie.acloud.domain.JsonSerializer.MajorConvert;
import com.kexie.acloud.domain.JsonSerializer.UserConvert;
import com.kexie.acloud.exception.GlobalHandlerExceptionResolver;
import com.kexie.acloud.interceptor.CorsInterceptor;
import com.kexie.acloud.interceptor.TokenInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.kexie.acloud"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import(DBConfig.class)
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域请求写到了拦截器上了
        // 不在这里配置的原因是因为在token拦截器拦截未登录的请求之后，不会返回Access-Control-Allow-Origin头部
//        registry.addMapping("/**").allowedOrigins("*");
    }


    // token拦截器
    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    // Cors拦截器
    @Bean
    public CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }

    /**
     * 拦截器的配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/user/**", "/task/**", "/society/user");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter4 = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        fastJsonConfig.setFeatures(Feature.IgnoreNotMatch);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));

        fastJsonHttpMessageConverter4.setFastJsonConfig(fastJsonConfig);
        converters.add(fastJsonHttpMessageConverter4);

        super.configureMessageConverters(converters);
    }

    @Autowired
    MajorConvert mMajorConvert;
    @Autowired
    UserConvert mUserConvert;
    @Autowired
    CollegeConvert mCollegeConvert;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(mMajorConvert);
        registry.addConverter(mUserConvert);
        registry.addConverter(mCollegeConvert);
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    //配置multipart解析器
    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        return new CommonsMultipartResolver();
    }

    @Bean
    public GlobalHandlerExceptionResolver globalHandlerExceptionResolver() {
        return new GlobalHandlerExceptionResolver();
    }

}
