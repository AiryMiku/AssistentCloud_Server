package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zojian on 2017/5/8.
 */
@RestController
@RequestMapping(value = "/notices", produces = {"application/json;charset=UTF-8"})
public class NoticeController {
    @Autowired
    INoticeService noticeService;

    /**
     * 发布公告
     * @param notice
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String addNotice(@RequestBody Notice notice){
        if(noticeService.addNotice(notice)){
            return "发布公告成功！";
        }
        else{
            return "发布公告失败";
        }
    }

    /**
     * 根据公告ID删除公告
     * @param notice_id
     * @return
     */
    @RequestMapping(value = "/{notice_id}",method = RequestMethod.DELETE)
    public String deleteNotice(@PathVariable int notice_id){
        if(noticeService.deleteNotice(notice_id)){
            return "公告删除成功！";
        }
        else{
            return "公告删除失败！";
        }
    }

    /**
     * 分页获取当前用户发布的公告（不区分社团）
     * @param publisher_id 用户ID
     * @param page 页数
     * @param pageSize 每页数据数目
     * @return
     */
    @RequestMapping(value = "/publisher/{publisher_id}/",method = RequestMethod.GET)
    public List<Notice> getNoticesByPublisherId(@PathVariable String publisher_id,@RequestParam int page, @RequestParam int pageSize){
        return noticeService.getNoticesByPublisherId(publisher_id,page,pageSize);

    }

    /**
     * 分页获取该用户可见的所有公告
     * @param user_id
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/{user_id}/",method = RequestMethod.GET)
    public List<Notice> getNoticesByUserId(@PathVariable String user_id,@RequestParam int page, @RequestParam int pageSize){
        return noticeService.getNoticesByUserId(user_id,page,pageSize);
    }

    /**
     * 分页获取该用户在某个社团内可见的公告
     * @param user_id
     * @param society_id
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/{user_id}/society/{society_id}/",method = RequestMethod.GET)
    public List<Notice> getNoticesByUserId(@PathVariable String user_id,@PathVariable int society_id, @RequestParam int page, @RequestParam int pageSize){
        return noticeService.getNoticesByUserIdAndSocietyId(user_id,society_id,page,pageSize);
    }

}
