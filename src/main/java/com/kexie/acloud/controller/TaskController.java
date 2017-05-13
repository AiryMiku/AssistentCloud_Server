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
     * 添加任务
     *
     * @param task
     * @param result
     * @param userId
     * @throws FormException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addTask(@Validated @RequestBody Task task, BindingResult result,
                        @RequestAttribute("userId") String userId) throws FormException {

        if (result.hasErrors())
            throw new FormException(result);

        mTaskService.create(task, userId);
    }

    /**
     * 更新任务
     *
     * @param task
     * @param result
     * @return
     * @throws FormException
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Task updateTask(@RequestBody Task task, BindingResult result) throws FormException {
        if (result.hasErrors()) throw new FormException(result);
        return mTaskService.update(task);
    }

    /**
     * 归档
     *
     * @param userId
     * @param taskId
     * @deprecated 使用更新接口就好啦
     */
    @RequestMapping(value = "/archive", method = RequestMethod.PUT)
    @Deprecated
    public void archive(@RequestAttribute("userId") String userId,
                        @RequestParam("taskId") String taskId) {
        mTaskService.archive(taskId);
    }

    /**
     * 删除一条任务
     *
     * @param taskId
     * @throws FormException
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestParam("taskId") String taskId) throws FormException {
        mTaskService.delete(taskId);
    }

    /**
     * 获取发布者发布的全部任务
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public List<Task> getTaskByPublisherId(@RequestParam("publishId") String userId) {
        return mTaskService.getTaskByPublisherId(userId);
    }

    /**
     * 用户拥有的所有任务
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public List<Task> getTaskByUserId(@RequestParam("userId") String userId) {
        return mTaskService.getTaskByUserId(userId);
    }

    /**
     * 任务详情
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "{taskId}", method = RequestMethod.GET)
    public Task getTaskByTaskId(@PathVariable("taskId") String taskId) {
        return mTaskService.getTaskByTaskId(taskId);
    }

    /**
     * 获取社团的所有任务
     *
     * @param societyId
     * @return
     */
    @RequestMapping(value = "/society", method = RequestMethod.GET)
    public List<Task> getTaskBySocietyId(@RequestParam("societyId") int societyId) {
        return mTaskService.getTaskBySocietyId(societyId);
    }


}
