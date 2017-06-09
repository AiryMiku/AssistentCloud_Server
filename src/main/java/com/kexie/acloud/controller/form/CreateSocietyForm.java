package com.kexie.acloud.controller.form;

import com.kexie.acloud.domain.College;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created : wen
 * DateTime : 2017/6/9 22:59
 * Description : 接收创建社团的数据
 */
public class CreateSocietyForm {

    // 社团名字
    @NotBlank(message = "社团名字不能为空")
    private String name;

    // 社团职位
    @NotBlank(message = "社团名字不能为空")
    private String summary = "这个社团很懒，什么都没有说";

    // 学院Id
    @Min(value = 1, message = "学院ID出错")
    private int collegeId;

    // 社团职位
    @NotEmpty(message = "怎么社团没有职位的呢")
    private List<SocietyPosition> positions;

    public Society toSociety(String userId) {
        Society society = new Society();
        College college = new College();
        college.setId(collegeId);

        society.setName(name);
        society.setSummary(summary);
        society.setCollege(college);
        society.setPrincipal(new User(userId));
        society.setCreateTime(new Date());

        return society;
    }

    // TODO: 2017/6/9 如何验证Position里面的数据呢


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public List<SocietyPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<SocietyPosition> positions) {
        this.positions = positions;
    }
}
