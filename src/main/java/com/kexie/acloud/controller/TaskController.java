package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Task;
import com.kexie.acloud.service.ITaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    /**
     * 添加一个Task
     */
    @RequestMapping(value = "/add")
    public String addTask(@RequestBody Task task) {
        mTaskService.create(task);
        return "添加成功";
    }

    /**
     * 根据发布者Id获取任务
     */
    @RequestMapping(value = "/publisher/{id}", method = RequestMethod.GET)
    public List<Task> getTaskByPublisherId(@PathVariable("id") String userId) {
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

    /**
     * 更新任务
     */
    @RequestMapping(value = "update")
    public Task updateTask(@Valid @RequestBody Task task, BindingResult result) {
        return mTaskService.update(task);
    }

}
