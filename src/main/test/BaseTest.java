package com.kexie.acloud.dao;

import com.kexie.acloud.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zojian on 2017/4/26.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
//@TransactionConfiguration(defaultRollback = true)
//@Transactional
public class BaseTest{

    @Test
    public void add(){

    }

}
