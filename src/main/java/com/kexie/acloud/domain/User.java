package com.kexie.acloud.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.kexie.acloud.domain.JsonSerializer.MajorConvert;
import com.kexie.acloud.domain.JsonSerializer.MajorDeserializer;
import com.kexie.acloud.domain.JsonSerializer.MajorSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created : wen
 * DateTime : 16-11-18 上午12:12
 * Description : 用户实体
 */
@Entity
public class User {

    // 用户登录表单验证接口,直接写一个空接口就可以了
    public interface LoginForm {
    }

    public interface RegisterForm {
    }

    // 返回用户信息不需要返回的字段
    public static final String[] CLIENT_IGNORE_FIELD = new String[]{
            "password", "salt", "hash"
    };

    // 用户Id
    @Id
    @Email(message = "你确定你写的是邮箱？", groups = {LoginForm.class, RegisterForm.class})
    @NotNull(groups = {LoginForm.class, RegisterForm.class})
    private String userId;

    // 密码
    @Transient
    @Length(min = 6,
            message = "密码要大于6,字母数字你随意", groups = {LoginForm.class, RegisterForm.class})
    @NotNull(groups = {LoginForm.class, RegisterForm.class})
    private String password;

    // 随机盐
    private String salt;

    // MD5(salt + password)
    private String hash;

    // 真实姓名
    private String realName;

    // 昵称
//    @Length(min = 1, max = 10, message = "昵称长度：1-10", groups = RegisterForm.class)
    private String nickName;

    // 学号
//    @Length(min = 1, message = "学号不能为空", groups = RegisterForm.class)
    private String stuId;

    // 拥有的社团职位
    @ManyToMany
    @JoinTable(name = "relation_user_position",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private List<SocietyPosition> societyPositions;

    // 专业
    @ManyToOne
//    @NotNull(message = "专业不能为空", groups = RegisterForm.class)
    @JSONField( deserializeUsing = MajorDeserializer.class)
    @Convert(converter = MajorConvert.class)
    private Major major;

    // 班级
    private String classNum;

    // 电话号码
//    @NotNull(message = "手机号码不能为空", groups = RegisterForm.class)
    private long phone;

    // 性别
//    @NotNull(message = "你是男还是女啊", groups = RegisterForm.class)
    private int gender;

    // 图片Url
    private String logoUrl = "http://i.niupic.com/images/2017/06/06/9q1wfr.jpg";

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public List<SocietyPosition> getSocietyPositions() {
        return societyPositions;
    }

    public void setSocietyPositions(List<SocietyPosition> societyPostions) {
        this.societyPositions = societyPostions;
    }

    public static String[] getClientIgnoreField() {
        return CLIENT_IGNORE_FIELD;
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
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", hash='").append(hash).append('\'');
        sb.append(", realName='").append(realName).append('\'');
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", stuId='").append(stuId).append('\'');
        sb.append(", societyPositions=").append(societyPositions);
        sb.append(", major=").append(major);
        sb.append(", classNum='").append(classNum).append('\'');
        sb.append(", phone=").append(phone);
        sb.append(", gender=").append(gender);
        sb.append(", logoUrl='").append(logoUrl).append('\'');
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
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

