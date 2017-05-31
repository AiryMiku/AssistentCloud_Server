package com.kexie.acloud.dao;

import com.kexie.acloud.config.AppConfig;
import com.kexie.acloud.service.ISocietyService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zojian on 2017/4/26.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
//@TransactionConfiguration(defaultRollback = true)
//@Transactional
public class BaseTest {

    @Autowired
    protected ISocietyDao societyDao;

    @Resource
    protected ISocietyService mSocietyService;

    @Autowired
    protected IUserDao userDao;

}
