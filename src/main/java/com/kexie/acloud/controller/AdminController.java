package com.kexie.acloud.controller;

import com.kexie.acloud.domain.School;
import com.kexie.acloud.service.ISchoolService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by zojian on 2017/4/26.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ISchoolService schoolService;

    @RequestMapping(method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public String index(){
       return "admin-index";
    }

    @ResponseBody
    @RequestMapping(value = "/schools",method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public String getAllSchool(){
        return schoolService.getAllSchool().toString();
    }

    @ResponseBody
    @RequestMapping(value = "/schools/{id}",method = RequestMethod.GET
            ,produces = {"application/json;charset=UTF-8"})
    public String getSchoolById(@PathVariable int id){
        return schoolService.getSchoolById(id).toString();
    }

    @RequestMapping(value = "/schools",method = RequestMethod.POST
            ,produces = {"application/json;charset=UTF-8"})
    public String addSchools(School school){
        schoolService.addSchool(school);
        return "redirect:/admin/schools";
    }

    @ResponseBody
    @RequestMapping(value = "/schools/excel",method = RequestMethod.POST
            ,produces = {"application/json;charset=UTF-8"})
    public String  addSchoolsFromExcel(@RequestParam(value = "school_excel",required = false) MultipartFile school_excel){
        System.out.println(school_excel);
        CommonsMultipartFile cf = (CommonsMultipartFile)school_excel;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        try {
            school_excel.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        schoolService.addSchoolsFromExcel(file);
        return "success!excel";
    }


}
