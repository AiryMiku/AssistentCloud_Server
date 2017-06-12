package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kexie.acloud.controller.form.CreateTaskForm;
import com.kexie.acloud.controller.form.UpdateTaskForm;
import com.kexie.acloud.domain.Task;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.service.ITaskService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.List;

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
     * @param taskForm
     * @param result
     * @param userId
     * @throws FormException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addTask(@Validated @RequestBody CreateTaskForm taskForm, BindingResult result,
                        @RequestAttribute("userId") String userId) throws FormException, AuthenticationException {

        if (result.hasErrors())
            throw new FormException(result);

        Task task = taskForm.toTask();
        task.setPublisher(new User(userId));

        mTaskService.create(task);
    }

    /**
     * 更新任务
     *
     * @param taskForm
     * @param form
     * @return
     * @throws FormException
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Task updateTask(@Validated @RequestBody UpdateTaskForm taskForm, BindingResult form) throws FormException {

        if (form.hasErrors())
            throw new FormException(form);

        Task task = taskForm.toTask();

        // 更新
        Task result = mTaskService.update(task);

        return result;
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
                        @RequestParam("taskId") String taskId) throws AuthorizedException {
        mTaskService.archive(taskId, userId);
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
    public List<Task> getTaskByUserId(@RequestAttribute("userId") String userId) {
        return mTaskService.getTaskByUserId(userId);
    }

    /**
     * 任务详情
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "{taskId}", method = RequestMethod.GET)
    public Task getTaskByTaskId(@PathVariable("taskId") String taskId,
                                @RequestParam(name = "identifier",required = false) String identifier,
                                @RequestAttribute("userId") String userId) {
        return mTaskService.getTaskByTaskId(taskId,userId,identifier);
    }

    /**
     * 获取社团的所有任务
     *
     * @param societyId
     * @return
     */
    @RequestMapping(value = "/society", method = RequestMethod.GET)
    public JSONArray getTaskBySocietyId(@RequestParam("societyId") int societyId) {

        List<Task> tasks = mTaskService.getTaskBySocietyId(societyId);

        JSONArray result = new JSONArray();
        tasks.forEach(task -> {
            JSONObject object = new JSONObject();

            object.put("id", task.getId());
            object.put("title", task.getTitle());
            object.put("msg", "你还要什么字段啊");

            result.add(object);
        });

        return result;
    }


}
