package com.kexie.acloud.config;

/**
 * Created by zojian on 2017/4/25.
 */

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;

import org.apache.log4j.pattern.MessagePatternConverter;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.kexie.acloud"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import(DBConfig.class)
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

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

//    @Bean
//    public FastJsonHttpMessageConverter httpMessageConverter() {
//
//        List<MediaType> mediaTypes = new ArrayList<>();
//
//        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//
//        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//        converter.setSupportedMediaTypes(mediaTypes);
//        return converter;
//    }

    //    @Bean
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("/WEB-INF/i18/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//    @Bean
//    public LocaleResolver localeResolver(){
//        CookieLocaleResolver resolver = new CookieLocaleResolver();
//        resolver.setDefaultLocale(new Locale("en"));
//        resolver.setCookieName("myLocaleCookie");
//        resolver.setCookieMaxAge(4800);
//        return resolver;
//    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        interceptor.setParamName("mylocale");
//        registry.addInterceptor(interceptor);
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
