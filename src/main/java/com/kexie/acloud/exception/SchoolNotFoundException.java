package com.kexie.acloud.exception;

/**
 * Created by zojian on 2017/4/27.
 */
public class SchoolNotFoundException extends RuntimeException {
    private int schoolId;

    public SchoolNotFoundException(int schoolId){
        this.schoolId = schoolId;
    }

    public int getSchoolId(){
        return schoolId;
    }
}
