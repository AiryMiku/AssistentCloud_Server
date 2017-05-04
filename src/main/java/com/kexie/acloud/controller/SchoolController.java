package com.kexie.acloud.controller;

import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Major;
import com.kexie.acloud.domain.School;
import com.kexie.acloud.exception.SchoolNotFoundException;
import com.kexie.acloud.service.ISchoolService;
import com.kexie.acloud.util.ExcelUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by zojian on 2017/4/27.
 */
@RestController
@RequestMapping("/admin")
public class SchoolController {

    @Autowired
    private ISchoolService schoolService;

    //获取所有学校信息
    @RequestMapping(value = "/schools", method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public List<School> getAllSchool(){
        return schoolService.getAllSchool();
    }

    //通过ID获取学校信息
    @RequestMapping(value = "/schools/{id}",method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public School getSchoolById(@PathVariable int id){
        School school = schoolService.getSchoolById(id);
        return school;
//        if(school==null){
//            throw new SchoolNotFoundException(id);
//        }
//        return new ResponseEntity<String>(JSON.toJSONString(school),HttpStatus.OK);
    }

    //导入一个学校信息
    @RequestMapping(value = "/schools", method = RequestMethod.POST)
    public String addSchools(@RequestBody School school, HttpServletResponse response){
        if(schoolService.addSchool(school)){
            return "添加学校成功！";
        }
        else{
            return "添加学校失败";
        }

    }

    /**通过Excel表导入学校信息
     * Excel格式
     * |编号|学校名称|
     */
    @RequestMapping(value = "/schools/excel",method = RequestMethod.POST
            ,produces = {"application/json;charset=UTF-8"})
    public String  addSchoolsFromExcel(@RequestParam(value = "school_excel",required = false) MultipartFile school_excel){

        CommonsMultipartFile cf = (CommonsMultipartFile)school_excel;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();

        if(ExcelUtil.validateExcel(fi.getName())==false) {
            return "格式错误！";
        }
        else {
            try {
                school_excel.transferTo(file);
                schoolService.addSchoolsFromExcel(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success!excel";
        }
    }


    /**
     * 通过学校ID获取所有学院信息
     * @param school_id
     * @return
     */
    @RequestMapping(value = "/schools/{school_id}/colleges",method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public List<College> getAllCollege(@PathVariable int school_id){
        return schoolService.getAllCollege(school_id);
    }

    /**
     * 向某所学校添加学院信息
     * @param school_id
     * @param college
     * @return
     */
    @RequestMapping(value = "/schools/{school_id}/colleges",method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public String addCollege(@PathVariable int school_id, @RequestBody College college){
        if(schoolService.getSchoolById(school_id)==null){
            return "添加学院失败！";
        }
        if(schoolService.addCollege(college,school_id)) {
            return "添加学院成功!";
        }
        else{
            return "添加学院失败！";
        }
    }

    /**
     * 获取某个学院的所有专业信息
     * @param college_id 学院ID
     * @return
     */
    @RequestMapping(value = "/colleges/{college_id}/majors",method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public List<Major> getAllMajor(@PathVariable int college_id){
        return schoolService.getAllMajor(college_id);
    }

    /**
     * 向某个学院添加专业
     * @param college_id 学院ID
     * @param major 专业信息
     * @return
     */
    @RequestMapping(value = "/colleges/{college_id}/majors",method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    public String addMajor(@PathVariable int college_id,@RequestBody Major major){
        if(schoolService.getCollegeById(college_id)==null){
            return "添加专业失败";
        }
        if(schoolService.addMajor(major,college_id)){
            return "添加专业成功！";
        }
        else{
            return "添加专业失败";
        }
    }

    @ExceptionHandler(SchoolNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String schoolNotFound(SchoolNotFoundException e){
        int id = e.getSchoolId();
        return new String("School [ "+id+" ] Not Found");
    }

}
