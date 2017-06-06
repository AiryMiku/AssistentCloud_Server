package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.NoticeException;
import com.kexie.acloud.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    public String addNotice(@Validated @RequestBody Notice notice,
                            BindingResult result,
                            @RequestAttribute("userId") String userId)throws FormException{
        //System.out.println("s======="+System.currentTimeMillis());
        if(result.hasErrors()){
            throw new FormException(result);
        }
        if(noticeService.addNotice(notice,userId)){
          //  System.out.println("e======="+System.currentTimeMillis());
            return "发布公告成功！";
        }
        else{
           // System.out.println("e======="+System.currentTimeMillis());
            return "发布公告失败";
        }

    }

    /**
     * 根据公告ID获取公告详细信息
     * @param notice_id
     * @return
     */
    @RequestMapping(value = "/{notice_id}",method = RequestMethod.GET)
    public Notice getNoticeByNoticeId(@PathVariable int notice_id,
                                      @RequestAttribute("userId") String userId,
                                      @RequestParam(value = "identifier",required = false) String identifier) throws NoticeException {
        return noticeService.getNoticeByNoticeId(notice_id,userId,identifier);
    }

    /**
     * 根据公告ID删除公告
     * @param notice_id
     * @return
     */
    @RequestMapping(value = "/{notice_id}",method = RequestMethod.DELETE)
    public String deleteNotice(@PathVariable int notice_id,
                               @RequestAttribute("userId") String userId){
        if(noticeService.deleteNotice(notice_id,userId)){
            return "公告删除成功！";
        }
        else{
            return "公告删除失败！";
        }
    }

    /**
     * 根据公告ID更新公告
     * @param notice
     * @param result
     * @return
     * @throws FormException
     */
    @RequestMapping(value = "/{notice_id}",method = RequestMethod.PUT)
    public String updateNotice(@PathVariable int notice_id,
                               @RequestBody Notice notice,
                               BindingResult result,
                               @RequestAttribute("userId") String userId)throws FormException{
        if(result.hasErrors()){
            throw new FormException(result);
        }
        if(noticeService.updateNotice(notice_id, notice,userId)){
            return "更新公告成功";
        }
        else{
            return "更新公告失败";
        }
    }

    /**
     * 分页获取当前用户发布的公告（不区分社团）
     * @param page 页数
     * @param pageSize 每页数据数目
     * @return
     */
    @RequestMapping(value = "/publisher",method = RequestMethod.GET)
    public List<Notice> getNoticesByPublisherId(@RequestParam int page,
                                                @RequestParam int pageSize,
                                                @RequestAttribute("userId") String userId){
        return noticeService.getNoticesByPublisherId(userId,page,pageSize);

    }

    /**
     * 分页获取该用户可见的所有公告
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public List<Notice> getNoticesByUserId(@RequestParam int page,
                                           @RequestParam int pageSize,
                                           @RequestAttribute("userId") String userId){
        return noticeService.getNoticesByUserId(userId,page,pageSize);
    }

    /**
     * 分页获取该用户在某个社团内可见的公告
     * @param userId
     * @param society_id
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/society/{society_id}/",method = RequestMethod.GET)
    public List<Notice> getNoticesByUserId(@PathVariable int society_id,
                                           @RequestParam int page,
                                           @RequestParam int pageSize,
                                           @RequestAttribute("userId") String userId){
        return noticeService.getNoticesByUserIdAndSocietyId(userId,society_id,page,pageSize);
    }

    /**
     * 获取该公告的浏览者列表
     * @param notice_id 公告ID
     * @param userId
     * @return
     */
    @RequestMapping(value = "/visitor/{notice_id}",method = RequestMethod.GET)
    public Set<String> getVisitor(@PathVariable int notice_id,
                                  @RequestAttribute("userId") String userId) throws NoticeException {
        return noticeService.getNoticeVisitorByNoticeId(notice_id,userId);
    }
}
