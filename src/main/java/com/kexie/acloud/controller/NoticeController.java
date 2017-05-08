package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zojian on 2017/5/8.
 */
@RestController
@RequestMapping("/notices")
public class NoticeController {
    @Autowired
    INoticeService noticeService;

    /**
     * 发布公告
     * @param notice
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public String addNotice(@RequestBody Notice notice){
        if(noticeService.addNotice(notice)){
            return "发布公告成功！";
        }
        else{
            return "发布公告失败";
        }
    }

}
