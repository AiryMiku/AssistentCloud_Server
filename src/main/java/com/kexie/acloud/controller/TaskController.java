package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Task;
import com.kexie.acloud.service.ITaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 16:58
 * Description :
 */
@RestController
@RequestMapping(value = "/task", produces = {"application/json;charset=UTF-8"})
public class TaskController {

    @Resource
    private ITaskService mTaskService;

    @RequestMapping(value = "publisher", method = RequestMethod.GET)
    public List<Task> getTaskByPublisherId(@RequestParam("publisherId") String userId) {
        return mTaskService.getTaskByPublisherId(userId);
    }

    /**
     * 根据用户Id获取任务
     */
    @RequestMapping(value = "/user/{userId}")
    public List<Task> getTaskByUserId(@PathVariable("userId") String userId) {
        return mTaskService.getTaskByUserId(userId);
    }

    /**
     * 根据任务Id获取任务
     */
    @RequestMapping(value = "/task/{taskId}")
    public Task getTaskByTaskId(@PathVariable("taskId") String taskId) {
        return mTaskService.getTaskByTaskId(taskId);
    }

    /**
     * 根据社团Id获取任务
     */
    @RequestMapping(value = "/society/{societyId}")
    public List<Task> getTaskBySocietyId(@PathVariable("societyId") int societyId) {
        return mTaskService.getTaskBySocietyId(societyId);
    }


}
