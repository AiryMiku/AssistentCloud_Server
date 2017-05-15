package com.kexie.acloud.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created : wen
 * DateTime : 2017/5/15 11:29
 * Description :
 */
@Entity
public class MeetingQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String question;

    private String answer;
}
