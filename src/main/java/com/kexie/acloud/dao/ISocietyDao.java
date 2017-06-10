package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.SocietyInvitation;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/25 19:19
 * Description :
 */
public interface ISocietyDao {

    /**
     * 数据库中是否有社团
     *
     * @param societyName 社团名字
     * @param collegeId   学院Id
     * @return
     */
    boolean hasSociety(String societyName, int collegeId);

    boolean hasSociety(int societyId);

    /**
     * 添加社团
     *
     * @param society
     * @return
     */
    public void add(Society society);

    /**
     * 根据社团ID获取社团信息
     *
     * @param society_id 社团ID
     * @return
     */
    public Society getSocietyById(int society_id);

    List<Society> getSocietiesBySchoolId(int schoolId);

    List<Society> getSocietiesByCollegeId(int collegeId);

    void update(Society society);

    List<Society> getSocietiesByName(String query);

    SocietyPosition getSocietyPositionByUserId(String userId, int societyId);

    /**
     * 用户是否在社团中
     *
     * @param societyId
     * @param userId
     * @return
     */
    boolean isInSociety(int societyId, String userId);

    boolean isInSociety(int societyId, List<User> users);

    void addMember(SocietyPosition position, String userId);

    void addApply(SocietyApply apply);

    List<SocietyApply> getAllSocietyApply(Integer societyId);

    SocietyApply getSocietyApplyById(int societyApplyId, String userId, String identifier);

    SocietyApply getSocietyApply(int applyId);

    void deleteSocietyApply(int applyId);

    SocietyPosition getLowestPosition(Society society);

    List<SocietyPosition> getSocietyPosition(int societyId);

    void deleteMember(int societyId, String userId);

    List<SocietyApply> getApplyByUserIdAndSocietyId(String userId, int societyId);

    SocietyPosition getPositionByPositionId(int positionId);

    /**
     * 向社团中添加一个职位
     *
     * @param society  社团
     * @param position 社团职位
     */
    void addPosition(Society society, SocietyPosition position);

    /**
     * 添加一个邀请
     *
     * @param invitation
     */
    void addInvitation(SocietyInvitation invitation);

    /**
     * 判断数据库中是否有相同的申请记录
     * 既一个社团是否邀请了这个用户
     *
     * @param invitation
     */
    boolean hasInvitation(SocietyInvitation invitation);

    boolean hasInvitation(int inviteId);

    SocietyInvitation getInvitation(int inviteId);

    /**
     * 删除一条邀请
     *
     * @param invitationId
     */
    void deleteInvitation(int invitationId);

    /**
     * 获取用户的社团邀请
     *
     * @param userId
     * @return
     */
    List<SocietyInvitation> getInvitationByUserId(String userId);
}
