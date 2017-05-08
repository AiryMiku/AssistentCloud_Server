package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Task;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.service.ITaskService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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

    /**
     * 添加一个Task
     */
    @RequestMapping(value = "/add")
    public void addTask(@Validated @RequestBody Task task, BindingResult result,
                        @RequestAttribute("userId") String userId) throws FormException {

        if (result.hasErrors())
            throw new FormException(result);

        mTaskService.create(task, userId);
    }

    /**
     * 更新任务
     */
    @RequestMapping(value = "/update")
    public Task updateTask(@RequestBody Task task, BindingResult result) throws FormException {
        if (result.hasErrors()) throw new FormException(result);
        return mTaskService.update(task);
    }

    /**
     * 完成一条任务
     */
    @RequestMapping(value = "/archive")
    public void archive(@RequestAttribute("userId") String userId,
                        @RequestParam("taskId") String taskId) {
        mTaskService.archive(taskId);
    }

    /**
     * 删除一条任务
     */
    @RequestMapping(value = "/delete")
    public void delete(@RequestAttribute("userId") String userId,
                       @RequestParam("taskId") String taskId) throws FormException {
        mTaskService.delete(taskId);
    }

    /**
     * 根据发布者Id获取任务
     */
    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public List<Task> getTaskByPublisherId(@RequestParam("publishId") String userId) {
        return mTaskService.getTaskByPublisherId(userId);
    }

    /**
     * 根据用户Id获取任务
     */
    @RequestMapping(value = "/user")
    public List<Task> getTaskByUserId(@RequestParam("userId") String userId) {
        return mTaskService.getTaskByUserId(userId);
    }

    /**
     * 根据任务Id获取任务
     */
    @RequestMapping(value = "/task")
    public Task getTaskByTaskId(@RequestParam("taskId") String taskId) {
        return mTaskService.getTaskByTaskId(taskId);
    }

    /**
     * 根据社团Id获取任务
     */
    @RequestMapping(value = "/society")
    public List<Task> getTaskBySocietyId(@RequestParam("societyId") int societyId) {
        return mTaskService.getTaskBySocietyId(societyId);
    }


}
