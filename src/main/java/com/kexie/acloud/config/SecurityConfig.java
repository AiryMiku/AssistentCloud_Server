package com.kexie.acloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;


/**
 * Created by zojian on 2017/5/5.
 */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
public class SecurityConfig {
//    @Autowired
//    DataSource dataSource;
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("user").password("password").roles("USER").build());
////        manager.createUser(User.withUsername("admin").password("password").roles("USER", "ADMIN").build());
////        return manager;
////    }
//
////    @Override
////    public UserDetailsService userDetailsServiceBean() throws Exception {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("user").password("password").roles("USER").build());
////        manager.createUser(User.withUsername("admin").password("password").roles("USER", "ADMIN").build());
////        return manager;
////    }
//
//    public static final String DEF_USERS_BY_USERNAME_QUERY =
//            "select username,password" +
//                    "from users" +
//                    "where username = ?";
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .jdbcAuthentication()
////                .dataSource(dataSource)
////                .usersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY)
////                .authoritiesByUsernameQuery()
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
////        super.configure(web);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // 所有的地址都要授权
////        http.authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .and()
////                .httpBasic();
//
////        http.authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                // 验证不成功，跳转到这个界面上
////                // 如何才能获取用户输入的东西呢，怎么样才是验证成功
////                .loginPage("/login")
////                // 	We must grant all users (i.e. unauthenticated users) access to our log in page. The formLogin().permitAll() method allows granting access to all users for all URLs associated with form based log in.
////                // 所有的用户都可以访问
////                .permitAll();
//
//        // 指定特定的URL需要特定的权限
////        http
////                .authorizeRequests()
////                // 任何人都可以访问
////                .antMatchers("/resources/**", "/signup", "/about").permitAll()
////                // 只有特定权限的才能访问
////                .antMatchers("/admin/**").hasRole("ADMIN")
////                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
////                // 对于其他的请求，只需要验证用户就可以了。上面的请求就需要特殊的权限
////                .anyRequest().authenticated()
////                .and()
////                // 没有授权，跳转到这个界面
////                .formLogin()
////                // 不添加下面这句话，就是循环的重定向了
////                // 原因是"/"这个路径没有被授权，然后就被重定向到"/"，一直循环下去了
////                .permitAll();
//
//        // TODO: 2017/5/6 登出操作没有看
//
//
//        http.formLogin()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/**")
//                .authenticated()
//                .anyRequest()
//                .permitAll()
//                .and()
//                .csrf()
//                .disable();
//
//    }
}
