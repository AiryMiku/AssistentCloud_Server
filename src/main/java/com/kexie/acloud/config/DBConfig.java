package com.kexie.acloud.config;

/**
 * Created by zojian on 2017/4/25.
 */

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class DBConfig {

    @Autowired
    private Environment env;

    @Bean
    public HibernateTemplate hibernateTemplate() throws PropertyVetoException {
        return new HibernateTemplate(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() throws PropertyVetoException {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(getDataSource());
        lsfb.setPackagesToScan("com.kexie.acloud");
        lsfb.setHibernateProperties(hibernateProperties());
        try {
            lsfb.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lsfb.getObject();
    }

    @Bean
    public DataSource getDataSource() throws PropertyVetoException {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(env.getProperty("database.driver"));
//        dataSource.setUrl(env.getProperty("database.url"));
//        dataSource.setUsername(env.getProperty("database.root"));
//        dataSource.setPassword(env.getProperty("database.password"));
//        // 连接池启动时创建的连接数量
//        dataSource.setInitialSize(30);
//        // 连接池里最多可有50个活动连接数
//        dataSource.setMaxTotal(50);
//        // 连接池中最少有5个空闲的连接
//        dataSource.setMinIdle(5);
//        return dataSource;
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl(env.getProperty("database.url"));
        dataSource.setUser(env.getProperty("database.root"));
        dataSource.setPassword(env.getProperty("database.password"));
        //设置连接池的最大连接数
        dataSource.setMaxPoolSize(100);
        //设置连接池的最小连接数
        dataSource.setMinPoolSize(2);
        //设置连接池的初始连接数
        dataSource.setInitialPoolSize(10);
        //设置连接池的缓存Statement的最大数
        dataSource.setMaxStatements(180);
        //设置连接池每次增加的连接数
        dataSource.setAcquireIncrement(5);
        //设置最大空闲时间,60秒内未使用则连接被丢弃
        dataSource.setMaxIdleTime(60);
        //设置连接关闭时默认将所有未提交的操作回滚
        dataSource.setAutoCommitOnClose(true);
        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        //properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.dialect.storage_engine", env.getProperty("hibernate.dialect.storage_engine"));
        properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        return properties;
    }
}

