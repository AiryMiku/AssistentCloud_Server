package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.util.*;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/24 21:40
 * Description :
 */
@Repository
public class SocietyDao extends HibernateDaoSupport implements ISocietyDao {

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Autowired
    TaskExecutor taskExecutor;

    @Resource
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public Society getSocietyById(int society_id) {
        return getHibernateTemplate().get(Society.class, society_id);
    }

    @Override
    public List<Society> getSocietiesBySchoolId(int schoolId) {
        return (List<Society>) getHibernateTemplate()
                .find("from Society where college.school.id = ?", schoolId);
    }

    @Override
    public List<Society> getSocietiesByCollegeId(int collegeId) {
        return (List<Society>) getHibernateTemplate().find("from Society where college.id = ?", collegeId);
    }

    @Override
    public void update(Society society) {
        Society s = getHibernateTemplate().get(Society.class, society.getId());
        BeanUtil.copyProperties(society, s);
        getHibernateTemplate().update(s);
    }

    @Override
    public List<Society> getSocietiesByName(String query) {
        return (List<Society>) getHibernateTemplate().find("from Society  where name like ?", "%" + query + "%");
    }

    @Override
    public SocietyPosition getSocietyPositionByUserId(String userId, int societyId) {
        User u = getHibernateTemplate().get(User.class, userId);
        for (SocietyPosition position : u.getSocietyPositions()) {
            if (position.getSociety().getId() == societyId)
                return position;
        }
        return null;
    }

    @Override
    public boolean isInSociety(int societyId, String userId) {
        User u = getHibernateTemplate().get(User.class, userId);
        for (SocietyPosition position : u.getSocietyPositions()) {
            if (position.getSociety().getId() == societyId)
                return true;
        }
        return false;
    }

    @Override
    public boolean isInSociety(int societyId, List<User> users) {
        for (User user : users) {
            if (!isInSociety(societyId, user.getUserId()))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasSociety(String societyName, int collegeId) {
        return getHibernateTemplate()
                .find("from Society where name = ? and college.id = ?",
                        societyName, collegeId)
                .size() > 0;
    }

    @Override
    public boolean hasSociety(int societyId) {
        return getHibernateTemplate().get(Society.class, societyId) != null;
    }

    @Override
    public void add(Society society) {
        getHibernateTemplate().save(society);
    }

    /**
     * 添加成员
     *
     * @param position
     * @param userId
     */
    @Override
    public void addMember(SocietyPosition position, String userId) {
        User user = getHibernateTemplate().load(User.class, userId);
        user.getSocietyPositions().add(position);
    }

    @Override
    public void addApply(SocietyApply apply) {
        // 添加一个加入社团的申请
        getHibernateTemplate().save(apply);
        // 向在线社团负责人发送申请通知
        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                apply.getId(),
                "你有一条新成员申请，快去查看吧❤️",
                apply.getUser().getUserId() + "申请加入" + apply.getSociety().getName(),
                new ArrayList<User>() {
                    {
                        add(new User(apply.getSociety().getPrincipal().getUserId()));
                    }
                }));
        // 向社团负责人发送申请通知
        taskExecutor.execute(new SendPushMsgRunnable(jedisConnectionFactory.getJedis(),
                "apply",
                apply.getId(),
                "你有一条新成员申请，快去查看吧❤️",
                apply.getUser().getUserId() + "申请加入" + apply.getSociety().getName(),
                new ArrayList<User>() {
                    {
                        add(new User(apply.getSociety().getPrincipal().getUserId()));
                    }
                }));

    }

    @Override
    public List<SocietyApply> getAllSocietyApply(Integer societyId) {
        return (List<SocietyApply>) getHibernateTemplate().find("from society_apply where society_id = ?", societyId);
    }

    /**
     * 根据ID获取社团申请
     *
     * @param societyApplyId
     * @return
     */
    @Override
    public SocietyApply getSocietyApplyById(int societyApplyId, String userId, String identifier) {
        if (identifier != null) {
            RedisUtil.deleteMsg(jedisConnectionFactory.getJedis(),
                    userId,
                    identifier,
                    "apply");
        }
        return getHibernateTemplate().get(SocietyApply.class, societyApplyId);
    }


    @Override
    public SocietyApply getSocietyApply(int applyId) {
        return getHibernateTemplate().get(SocietyApply.class, applyId);
    }

    @Override
    public void deleteSocietyApply(int applyId) {
        getHibernateTemplate().flush();
        getHibernateTemplate().clear();
        SocietyApply societyApply = new SocietyApply();
        societyApply.setId(applyId);
        getHibernateTemplate().delete(societyApply);
    }

    @Override
    public SocietyPosition getLowestPosition(Society society) {
        return (SocietyPosition) getHibernateTemplate()
                .find("from society_position sp where sp.society = ? and sp.grade = 0", society)
                .get(0);
    }

    @Override
    public List<SocietyPosition> getSocietyPosition(int societyId) {
        return (List<SocietyPosition>) getHibernateTemplate()
                .find("from society_position  where society_id = ?", societyId);
    }

    @Override
    public void deleteMember(int societyId, String societyName, String userId) {
        User user = getHibernateTemplate().load(User.class, userId);
        List<SocietyPosition> positions = user.getSocietyPositions();
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).getSociety().getId() == societyId) {
                positions.remove(i);
                break;
            }
        }
        user.setSocietyPositions(positions);
        getHibernateTemplate().update(user);
        // 给被移除的成员发送通知
        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                0,
                "你退出了" + societyName,
                "很遗憾，一个悲伤的消息，你离开了" + societyName,
                new ArrayList<User>() {
                    {
                        add(new User(userId));
                    }
                }));
    }

    public List<SocietyApply> getApplyByUserIdAndSocietyId(String userId, int societyId) {
        return (List<SocietyApply>) getHibernateTemplate().find("from society_apply where user_id=? and society_id=?", userId, societyId);
    }

    /**
     * 根据职位ID获取职位信息
     *
     * @param positionId
     * @return
     */
    @Override
    public SocietyPosition getPositionByPositionId(int positionId) {
        return getHibernateTemplate().get(SocietyPosition.class, positionId);
    }

    /**
     * 向社团中添加一个职位
     *
     * @param society  社团
     * @param position 社团职位
     */
    @Override
    public void addPosition(Society society, SocietyPosition position) {
        position.setSociety(society);
        getHibernateTemplate().save(position);
    }
}
