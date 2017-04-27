package com.kexie.acloud.config;

/**
 * Created by zojian on 2017/4/25.
 */
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;

@Configuration
@ComponentScan(basePackages = {"com.kexie.acloud"},
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@Import(DBConfig.class)
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter  {
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    //配置multipart解析器
    @Bean
    public MultipartResolver multipartResolver()throws IOException{
        return new StandardServletMultipartResolver();
    }
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
