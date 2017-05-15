package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.service.IMeetingService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/11 20:43
 * Description :
 */
@RestController
@RequestMapping(value = "meeting", produces = {"application/json;charset=UTF-8"})
public class MeetingController {

    @Resource
    private IMeetingService mMeetingService;

    /**
     * 创建会议
     */
    @RequestMapping(method = RequestMethod.POST)
    public Room createMeeting(@Validated @RequestBody Meeting meeting, BindingResult form) throws FormException {

        if (form.hasErrors()) throw new FormException(form);

        // 创建会议
        return mMeetingService.createMeeting(meeting);
    }

}
