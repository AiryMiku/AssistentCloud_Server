package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Task;
import com.kexie.acloud.service.ITaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/24 16:58
 * Description :
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskService mTaskService;

    @RequestMapping(value = "publisher", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public List<Task> getTaskByPublisherId(@RequestParam("publisherId") String userId) {
        return mTaskService.getTaskByPublisherId(userId);
    }

}
