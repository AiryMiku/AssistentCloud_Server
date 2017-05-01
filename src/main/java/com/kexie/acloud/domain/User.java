package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.MajorConvert;
import com.kexie.acloud.domain.JsonSerializer.MajorDeserializer;
import com.kexie.acloud.domain.JsonSerializer.MajorSerializer;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

/**
 * Created : wen
 * DateTime : 16-11-18 上午12:12
 * Description :
 */
@Entity
public class User {

    // TODO: 2017/5/1 用户表单验证
    // TODO: 2017/5/1 应该添加一个UserForm，与数据库实体User分开。
    // TODO: 2017/5/1 应该每个请求对应一个Form类？

    public interface Login {

    }

    @Id
    @Pattern(regexp = "", message = "邮箱格式不正确", groups = {Login.class})
    private String userId;

    @Transient
//    @Length(min = 6, message = "密码要大于6",groups = )
//    @NotNull(message = "密码不能为空")
    private String password;

    private String salt;

    private String hash;

    // 真实姓名
    private String realName;

    // 昵称
//    @Length(min = 1, max = 10, message = "昵称长度：1-10")
    private String nickName;

    // 学号
//    @NotNull(message = "学号不能为空")
    private String stuId;

    // 专业
    @ManyToOne
//    @NotNull(message = "专业不能为空")
    @JSONField(serializeUsing = MajorSerializer.class, deserializeUsing = MajorDeserializer.class)
    @Convert(converter = MajorConvert.class)
    private Major major;

    // 班级
    private String classNum;

    // 电话号码
//    @NotNull(message = "手机号码不能为空")
    private int phone;

    // 性别
//    @NotNull(message = "你是男还是女啊")
    private int gender;

    // 图片Url TODO: 2017/4/30 设置默认值
    private String logoUrl;

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"userId\":\"")
                .append(userId).append('\"');
        sb.append(",\"password\":\"")
                .append(password).append('\"');
        sb.append(",\"salt\":\"")
                .append(salt).append('\"');
        sb.append(",\"hash\":\"")
                .append(hash).append('\"');
        sb.append(",\"realName\":\"")
                .append(realName).append('\"');
        sb.append(",\"nickName\":\"")
                .append(nickName).append('\"');
        sb.append(",\"stuId\":\"")
                .append(stuId).append('\"');
        sb.append(",\"major\":")
                .append(major);
        sb.append(",\"classNum\":\"")
                .append(classNum).append('\"');
        sb.append(",\"phone\":")
                .append(phone);
        sb.append(",\"gender\":")
                .append(gender);
        sb.append(",\"logoUrl\":\"")
                .append(logoUrl).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

