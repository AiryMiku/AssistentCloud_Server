package com.kexie.acloud.service;

import com.kexie.acloud.dao.ISocietyDao;
import com.kexie.acloud.dao.IUserDao;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.SocietyInvitation;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.SocietyException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.SendPushMsgRunnable;
import com.kexie.acloud.util.SendRealTImePushMsgRunnable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/5/9 23:47
 * Description :
 */
@Service
@Transactional
public class SocietyService implements ISocietyService {

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private ISocietyDao mSocietyDao;

    @Autowired
    private IUserDao mUserDao;

    @Override
    public void add(Society society, List<SocietyPosition> positions) throws SocietyException {

        User principal = society.getPrincipal();

        // 学院是否有同名
        if (mSocietyDao.hasSociety(society.getName(), society.getCollege().getId()))
            throw new SocietyException("社团已经存在啦");

        // 添加这个社团
        mSocietyDao.add(society);

        // 保存职位
        positions.forEach(position -> mSocietyDao.addPosition(society, position));

        // 发布者为最高的职位
        SocietyPosition topPosition = positions.stream()
                .max((s1, s2) -> s1.getGrade() - s2.getGrade())
                .get();

        // 添加发布者到当前社团中
        mSocietyDao.addMember(topPosition, principal.getUserId());
    }

    @Override
    public void updateSociety(Society society) {
        mSocietyDao.update(society);
    }

    @Override
    public Society getSocietyById(int society_id) throws SocietyException {
        Society society = mSocietyDao.getSocietyById(society_id);
        if (society == null)
            throw new SocietyException("社团不存在");
        return society;
    }

    /**
     * 获取社团中的成员
     *
     * @param society_id
     * @return
     */
    @Override
    public List<User> getUsersIn(int society_id) {
        return mUserDao.getUserBySociety(society_id);
    }

    @Override
    public List<Society> getSoicetiesBySchoolId(int schoolId) {
        return mSocietyDao.getSocietiesBySchoolId(schoolId);
    }

    @Override
    public List<Society> getSoicetiesByCollegeId(int collegeId) {
        return mSocietyDao.getSocietiesByCollegeId(collegeId);
    }

    @Override
    public List<Society> getSocietiesByUserId(String userId) {
        return mUserDao.getSocietiesByUserId(userId);
    }

    @Override
    public void updateSocietyLogo(int societyId, String relativePath) {
        Society society = new Society();
        society.setId(societyId);
        society.setSocietyLogo(relativePath);
        mSocietyDao.update(society);
    }

    @Override
    public void changePrincipal(String oldUserId, String newUserId, int societyId) throws UserException {

        if (mUserDao.getUser(newUserId) == null)
            throw new UserException("新负责人不存在");

        List<Society> societies = mUserDao.getSocietiesByUserId(newUserId);

        boolean exist = false;
        for (Society society : societies) {
            if (societyId == society.getId()) {
                exist = true;
                break;
            }
        }
        if (!exist)
            throw new UserException("新负责人不存在这个社团中");

        Society society = new Society();
        User newPrincipal = new User();

        newPrincipal.setUserId(newUserId);
        society.setId(societyId);
        society.setPrincipal(newPrincipal);
        mSocietyDao.update(society);
    }

    @Override
    public List<Society> searchSocietyByName(String query) {
        return mSocietyDao.getSocietiesByName(query);
    }

    /**
     * 申请加入社团
     *
     * @param apply
     */
    @Override
    public void applyJoinSociety(SocietyApply apply, String userId) throws SocietyException {
        if (!mSocietyDao.hasSociety(apply.getSociety().getId()))
            throw new SocietyException("社团不存在");
        apply.setSociety(getSocietyById(apply.getSociety().getId()));

        if (mSocietyDao.isInSociety(apply.getSociety().getId(), apply.getUser().getUserId())) {
            throw new SocietyException("你已经在这个社团啦");
        }

        if (apply.getSociety().getPrincipal().getUserId().equals(userId))
            throw new SocietyException("你是该社团的负责人!");
        // 判断是否已经加入
        List<User> members = apply.getSociety().getMembers();
        if (members != null) {
            for (User user : members) {
                if (user.getUserId().equals(userId)) {
                    throw new SocietyException("你已经在该社团中");
                }
            }
        }
        // 判断重复申请
        if (mSocietyDao.getApplyByUserIdAndSocietyId(userId, apply.getSociety().getId()).size() > 0) {
            throw new SocietyException("你已经申请过了，请不要重复申请");
        }
        // 添加一条申请记录
        mSocietyDao.addApply(apply);

        // 向在线社团负责人发送申请通知
        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                apply.getId(),
                "你有一条新成员申请，快去查看吧❤️",
                apply.getUser().getUserId()+"("+apply.getUser().getNickName()+")"
                        + "申请加入" + apply.getSociety().getName(),
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
                apply.getUser().getUserId()+"("+apply.getUser().getNickName()+")"
                        + "申请加入" + apply.getSociety().getName(),
                new ArrayList<User>() {
                    {
                        add(new User(apply.getSociety().getPrincipal().getUserId()));
                    }
                }));
    }

    /**
     * 获取社团所有的申请请求
     *
     * @param societyId
     * @param userId
     * @return
     */
    @Override
    public List<SocietyApply> getSocietyApplyIn(String societyId, String userId) throws SocietyException, AuthorizedException {

        SocietyPosition position = mSocietyDao.getSocietyPositionByUserId(userId, new Integer(societyId));

        if (position == null)
            throw new SocietyException("用户社团职位为空");

        if (!position.getName().contains("主席"))
            throw new AuthorizedException("用户没有权限查询 社团申请(职位没有包含主席");

        if (!mSocietyDao.hasSociety(new Integer(societyId)))
            throw new SocietyException("社团不存在");

        return mSocietyDao.getAllSocietyApply(new Integer(societyId));
    }

    /**
     * 处理社团申请
     *
     * @param applyId 申请号
     * @param isAllow 是否通过
     * @param userId  处理人
     * @throws SocietyException    申请号不存在，处理人社团职位为空"
     * @throws AuthorizedException 处理人没有权限处理社团申请(职位没有包含主席"
     */
    @Override
    public void handleSocietyApple(String applyId, boolean isAllow, String userId) throws SocietyException, AuthorizedException {

        SocietyApply societyApply = mSocietyDao.getSocietyApply(new Integer(applyId));

        if (societyApply == null)
            throw new SocietyException("当前申请不存在");

        // 处理人职位
        SocietyPosition position = mSocietyDao.getSocietyPositionByUserId(userId, societyApply.getSociety().getId());

        if (position == null)
            throw new SocietyException("处理人社团职位为空");

        if (!position.getName().contains("主席"))
            if (!position.getName().contains("会长"))
                throw new AuthorizedException("处理人没有权限处理社团申请(职位没有包含主席");

        SocietyApply apply = mSocietyDao.getSocietyApplyById(Integer.parseInt(applyId), null, null);

        // TODO: 2017/5/30 推送到用户中
        if (isAllow) {

            SocietyPosition lowestPosition = mSocietyDao.getLowestPosition(societyApply.getSociety());

            mSocietyDao.addMember(lowestPosition, societyApply.getUser().getUserId());

            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    applyId,
                    apply.getSociety().getName() + "接受了你的申请❤️",
                    "恭喜你已经是" + apply.getSociety().getName() + "的一员了",
                    new ArrayList<User>() {
                        {
                            add(apply.getUser());
                        }
                    }));

        } else {
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    applyId,
                    apply.getSociety().getName() + "拒绝了你的申请(；′⌒`)",
                    "不哭，摸摸头，再试一次",
                    new ArrayList<User>() {
                        {
                            add(apply.getUser());
                        }
                    }));
        }

        // 删除这条申请
        mSocietyDao.deleteSocietyApply(new Integer(applyId));
    }

    /**
     * 社团负责人移除成员
     *
     * @param societyId
     * @param userId
     * @param removeUserId
     * @throws SocietyException
     */
    @Override
    public String removeMember(int societyId, String userId, String removeUserId) throws SocietyException, AuthorizedException {

        Society society = mSocietyDao.getSocietyById(societyId);
        // 处理人职位
        SocietyPosition position = mSocietyDao.getSocietyPositionByUserId(userId, societyId);

        if (position == null)
            throw new SocietyException("用户社团职位为空");

        if (!position.getName().contains("主席"))
            throw new AuthorizedException("用户没有权限查询 社团申请(职位没有包含主席");

        // 不能移除自己
        if (userId.equals(removeUserId))
            throw new SocietyException("你想干嘛？");

        if (inSociety(societyId, removeUserId)) {
            mSocietyDao.deleteMember(societyId, removeUserId);
            // 给被移除的成员发送通知
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    0,
                    "你退出了" + society.getName(),
                    "很遗憾，一个悲伤的消息，你离开了" + society.getName(),
                    new ArrayList<User>() {
                        {
                            add(new User(removeUserId));
                        }
                    }));
            return "移除成功";
        } else {
            throw new SocietyException("成员不在该社团中");
        }
    }

    @Override
    public List<SocietyPosition> getSocietyPosition(int societyId) throws SocietyException {

        if (!mSocietyDao.hasSociety(societyId))
            throw new SocietyException("社团不存在");

        return mSocietyDao.getSocietyPosition(societyId);
    }

    @Override
    public SocietyApply getSocietyApplyById(int societyApplyId, String userId, String identifier) {

        return mSocietyDao.getSocietyApplyById(societyApplyId, userId, identifier);
    }

    /**
     * 判断成员是否在社团中
     *
     * @param societyId
     * @param userId
     * @return
     * @throws SocietyException
     */
    @Override
    public boolean inSociety(int societyId, String userId) throws SocietyException {
        List<Society> societies = getSocietiesByUserId(userId);
        if (societies != null) {
            for (Society society : societies) {
                if (society.getId() == societyId) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * 邀请一个用户加入一个社团
     *
     * @param invitation
     * @throws SocietyException
     * @throws UserException
     * @throws AuthorizedException
     */
    @Override
    public void addSocietyInvitation(SocietyInvitation invitation) throws SocietyException, UserException, AuthorizedException {

        // 是否有这个社团
        if (!mSocietyDao.hasSociety(invitation.getSociety().getId())) {
            throw new SocietyException("没有这个社团");
        }

        // 是否有这个邀请人
        if (!mUserDao.hasUserById(invitation.getInvitaUser().getUserId())) {
            throw new UserException("没有这个用户");
        }

        // 判断邀请人是否有权利邀请别人
        SocietyPosition handlerPosition = mSocietyDao.getSocietyPositionByUserId(
                invitation.getHandleUser().getUserId(), invitation.getSociety().getId());
        if (handlerPosition == null)
            throw new SocietyException("用户社团职位为空,你不属于这个社团哦");
        if (!handlerPosition.getName().contains("主席"))
            throw new AuthorizedException("用户没有权限查询 社团申请(职位没有包含主席");

        // 判断是否已经重复邀请
        if (mSocietyDao.hasInvitation(invitation)) {
            throw new SocietyException("当前社团已经发送过邀请了");
        }

        // 判断职位是否高于自己
        // FIXME: 2017/6/10 解决bug
        SocietyPosition invitePosition = mSocietyDao.getPositionByPositionId(invitation.getPosition().getId());
        if (invitePosition == null) {
            throw new SocietyException("你邀请的社团职位不存在");
        }
        if (invitePosition.getSociety().getId() != invitation.getSociety().getId()) {
            throw new SocietyException("你邀请的社团职位不属于这个社团");
        }
        if (invitePosition.getGrade() > handlerPosition.getGrade()) {
            throw new AuthorizedException("你不能赋予别人比自己还高的职位");
        }

        Society society = mSocietyDao.getSocietyById(invitation.getSociety().getId());
        invitation.setSociety(society);

        mSocietyDao.addInvitation(invitation);

        // TODO: 2017/6/10 推送到userid这个用户，有一个邀请
        taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                invitation.getInvitationId(),
                invitation.getSociety().getName() + "邀请你加入他们❤️",
                invitation.getSociety().getName()+":"+invitation.getMessage(),
                new ArrayList<User>() {
                    {
                        add(invitation.getInvitaUser());
                    }
                }));
    }

    /**
     * 处理是否同意这个社团邀请
     *
     * @param inviteId
     * @param isAllow
     * @param userId
     */
    @Override
    public void handleSocietyInvitation(int inviteId, boolean isAllow, String userId) throws SocietyException, AuthorizedException {

        // 是否有这个申请
        SocietyInvitation invitation = mSocietyDao.getInvitation(inviteId);

        if (invitation == null) {
            throw new SocietyException("这条邀请不存在");
        }

        // 当前用户是否是被邀请人
        if (!invitation.getInvitaUser().getUserId().equals(userId)) {
            throw new AuthorizedException("你不是这条邀请的被邀请人");
        }

        // 处理邀请
        if (isAllow) {
            mSocietyDao.addMember(invitation.getPosition(), invitation.getInvitaUser().getUserId());
            // 通知被邀请的人已经成功加入社团
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    inviteId,
                    "欢迎加入"+invitation.getSociety().getName() + "❤️",
                    "恭喜你已经是" + invitation.getSociety().getName() + "的一员了",
                    new ArrayList<User>() {
                        {
                            add(invitation.getInvitaUser());
                        }
                    }));

            // 通知邀请发起人 邀请成功
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    inviteId,
                    invitation.getInvitaUser().getUserId()+"("+invitation.getInvitaUser().getNickName()+")"+
                            " 接受你的邀请加入"+invitation.getSociety().getName() + "❤️",
                    "快去调戏小鲜肉吧",
                    new ArrayList<User>() {
                        {
                            add(invitation.getHandleUser());
                        }
                    }));

        }
        else{
            // 通知被邀请的人加入社团失败
            taskExecutor.execute(new SendRealTImePushMsgRunnable(jedisConnectionFactory.getJedis(),
                    inviteId,
                    "很遗憾"+invitation.getInvitaUser().getUserId()+"("+invitation.getInvitaUser().getNickName()+")"
                            + "拒绝了你的邀请(；′⌒`)",
                    "下次把他的腿给打断",
                    new ArrayList<User>() {
                        {
                            add(invitation.getHandleUser());
                        }
                    }));
        }

        // 删除这条邀请
        mSocietyDao.deleteInvitation(invitation.getInvitationId());
    }

    /**
     * 获取用户的邀请记录
     *
     * @param userId
     */
    @Override
    public List<SocietyInvitation> getUserInvitation(String userId) {
        return mSocietyDao.getInvitationByUserId(userId);
    }
}
