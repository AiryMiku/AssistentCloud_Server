package com.kexie.acloud.service;

import com.kexie.acloud.dao.BaseTest;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created : wen
 * DateTime : 2017/5/31 20:10
 * Description :
 */
@Transactional
public class SocietyServiceTest extends BaseTest {
    @Test
    public void searchSocietyByName() throws Exception {
        System.out.println(mSocietyService.searchSocietyByName("daf"));
    }

    @Test
    public void getSocietyPosition() throws Exception {
        System.out.println(mSocietyService.getSocietyPosition(1));
    }

}