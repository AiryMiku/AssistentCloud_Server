package com.kexie.acloud.controller;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.SocietyException;
import com.kexie.acloud.service.ISocietyService;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/9 23:36
 * Description :
 */
@RestController
@RequestMapping(value = "/society", produces = {"application/json;charset=UTF-8"})
public class SocietyController {

    @Resource
    private ISocietyService mSocietyService;

    /**
     * 获取学校所有社团的信息
     */
    @RequestMapping(value = "school/{schoolId}", method = RequestMethod.GET)
    public List<Society> allSchoolSociety(@PathVariable("schoolId") int schoolId) {
        List<Society> societies = mSocietyService.getSoicetiesBySchoolId(schoolId);

        if (societies == null)
            return new ArrayList<>();

        List<Society> result = new ArrayList<>();

        // 只返回id,name,logo
        societies.forEach(society -> {
            Society s = new Society();
            s.setId(society.getId());
            s.setName(society.getName());
            s.setSociety_logo(society.getSociety_logo());
            result.add(s);
        });

        return result;
    }

    @RequestMapping(value = "college/{collegeId}", method = RequestMethod.GET)
    public List<Society> allCollegeSociety(@PathVariable("collegeId") Integer collegeId) {

        List<Society> societies = mSocietyService.getSoicetiesByCollegeId(collegeId);

        if (societies == null)
            return new ArrayList<>();

        List<Society> result = new ArrayList<>();

        // 只返回id,name,logo
        societies.forEach(society -> {
            Society s = new Society();
            s.setId(society.getId());
            s.setName(society.getName());
            s.setSociety_logo(society.getSociety_logo());
            result.add(s);
        });

        return result;
    }

    /**
     * 一个社团的详细信息
     */
    @RequestMapping(value = "{societyId}", method = RequestMethod.GET)
    public Society addSociety(@PathVariable("societyId") int societyId) throws FormException, SocietyException {
        return mSocietyService.getSocietyById(societyId);
    }

    /**
     * 添加一个社团
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addSociety(@Validated(Society.Create.class) @RequestBody Society society, BindingResult form) throws FormException, SocietyException {

        if (form.hasErrors()) throw new FormException(form);

        mSocietyService.add(society);
    }

    /**
     * 更新社团
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void updateSociety(@Validated(Society.Update.class) @RequestBody Society society, BindingResult form) throws FormException, SocietyException {

        if (form.hasErrors()) throw new FormException(form);

        mSocietyService.updateSociety(society);
    }

    /**
     * 获取当前社团的所有用户
     */
    @RequestMapping(value = "{societyId}/users", method = RequestMethod.GET)
    public List<User> getUser(@PathVariable("societyId") int societyId) {
        return mSocietyService.getUsersIn(societyId);
    }

    /**
     * 获取用户拥有的社团
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public List<Society> getSocietyByUserId(@RequestAttribute("userId") String userId) {
        return mSocietyService.getSocietiesByUserId(userId);
    }
}
