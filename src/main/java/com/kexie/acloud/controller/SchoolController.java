package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSON;
import com.kexie.acloud.domain.School;
import com.kexie.acloud.exception.SchoolNotFoundException;
import com.kexie.acloud.service.ISchoolService;
import com.kexie.acloud.util.ExcelUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by zojian on 2017/4/27.
 */
@RestController
@RequestMapping("/admin/schools")
public class SchoolController {

    @Autowired
    private ISchoolService schoolService;

    //获取所有学校信息

    @RequestMapping(method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public String getAllSchool(){
        return JSON.toJSONString(schoolService.getAllSchool());
    }

    //通过ID获取学校信息
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<String> getSchoolById(@PathVariable int id){
        School school = schoolService.getSchoolById(id);
        if(school==null){
          throw new SchoolNotFoundException(id);
        }
        return new ResponseEntity<String>(JSON.toJSONString(school),HttpStatus.OK);

//      return JSON.toJSONString(schoolService.getSchoolById(id));
    }

    //导入一个学校信息
    @RequestMapping(method = RequestMethod.POST
            ,produces = {"application/json;charset=UTF-8"})
    public String addSchools(School school){
        schoolService.addSchool(school);
        return "redirect:/admin/schools";
    }

    //通过Excel表导入学校信息
    //Excel格式
    //|编号|学校名称|
    @RequestMapping(value = "/excel",method = RequestMethod.POST
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

    @ExceptionHandler(SchoolNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String schoolNotFound(SchoolNotFoundException e){
        int id = e.getSchoolId();
        return new String("School [ "+id+" ] Not Found");
    }

}
