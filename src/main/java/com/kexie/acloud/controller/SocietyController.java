package com.kexie.acloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kexie.acloud.controller.form.ApplyEntity;
import com.kexie.acloud.controller.form.CreateSocietyForm;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.SocietyInvitation;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.FormException;
import com.kexie.acloud.exception.SocietyException;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.service.ISocietyService;
import com.kexie.acloud.util.PathUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created : wen
 * DateTime : 2017/5/9 23:36
 * Description :
 */
@RestController
@RequestMapping(value = "/society", produces = {"application/json;charset=UTF-8"})
public class SocietyController {

    @Resource
    private ISocietyService mSocietyService;

    /**
     * 创建社团
     *
     * @param form
     * @param result
     * @param userId
     * @throws FormException
     * @throws SocietyException
     */
    @RequestMapping(method = RequestMethod.POST)
    public void addSociety(@Validated @RequestBody CreateSocietyForm form,
                           BindingResult result,
                           @RequestAttribute("userId") String userId)
            throws FormException, SocietyException {

        if (result.hasErrors()) throw new FormException(result);

        Society society = form.toSociety(userId);

        mSocietyService.add(society, form.getPositions());
    }

    /**
     * 获取学校所有社团的信息
     *
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "school/{schoolId}", method = RequestMethod.GET)
    public List<Society> allSchoolSociety(@PathVariable("schoolId") int schoolId) {

        List<Society> societies = mSocietyService.getSoicetiesBySchoolId(schoolId);

        if (societies == null)
            return new ArrayList<>();

        List<Society> result = new ArrayList<>();

        // 只返回id,name,logo
        societies.forEach(society -> {
            Society s = new Society();
            s.setId(society.getId());
            s.setName(society.getName());
            s.setSocietyLogo(society.getSocietyLogo());
            result.add(s);
        });

        return result;
    }

    /**
     * 获取学院所有社团的信息
     *
     * @param collegeId
     * @return
     */
    @RequestMapping(value = "college/{collegeId}", method = RequestMethod.GET)
    public List<Society> allCollegeSociety(@PathVariable("collegeId") Integer collegeId) {

        List<Society> societies = mSocietyService.getSoicetiesByCollegeId(collegeId);

        if (societies == null)
            return new ArrayList<>();

        List<Society> result = new ArrayList<>();

        // 只返回id,name,logo
        societies.forEach(society -> {
            Society s = new Society();
            s.setId(society.getId());
            s.setName(society.getName());
            s.setSocietyLogo(society.getSocietyLogo());
            result.add(s);
        });

        return result;
    }

    /**
     * 一个社团的详细信息
     *
     * @param societyId
     * @return
     * @throws FormException
     * @throws SocietyException
     */
    @RequestMapping(value = "{societyId}", method = RequestMethod.GET)
    public JSONObject getSociety(@PathVariable("societyId") int societyId) throws FormException, SocietyException {

        Society society = mSocietyService.getSocietyById(societyId);

        List<SocietyPosition> positions = mSocietyService.getSocietyPosition(societyId);
        List<User> members = mSocietyService.getUsersIn(societyId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("society", society);
        jsonObject.put("positions", positions);
        jsonObject.put("members", members);
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        return jsonObject;
    }


    /**
     * 更新社团
     *
     * @param society
     * @param form
     * @throws FormException
     * @throws SocietyException
     */
    @RequestMapping(method = RequestMethod.PUT)
    public void updateSociety(@Validated(Society.Update.class) @RequestBody Society society, BindingResult form) throws FormException, SocietyException {

        if (form.hasErrors()) throw new FormException(form);

        mSocietyService.updateSociety(society);
    }

    /**
     * 获取当前社团的所有用户
     *
     * @param societyId
     * @return
     */
    @RequestMapping(value = "{societyId}/users", method = RequestMethod.GET)
    public List<User> getUser(@PathVariable("societyId") int societyId) {
        return mSocietyService.getUsersIn(societyId);
    }

    /**
     * 获取用户拥有的社团
     *
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public List<Society> getSocietyByUserId(@RequestAttribute("userId") String userId, HttpServletRequest request) {

        return mSocietyService.getSocietiesByUserId(userId);
    }

    /**
     * 上传社团logo
     *
     * @param logo
     * @param societyId
     */
    @RequestMapping(value = "logo", method = RequestMethod.POST)
    public void uploadLogo(HttpServletRequest request, @RequestParam("logo") MultipartFile logo,
                           @RequestParam("societyId") int societyId) throws IOException {

        String realName = logo.getOriginalFilename();
        // UUID作为文件名
        String fileName = UUID.randomUUID().toString() + realName.substring(realName.lastIndexOf("."));

        // 写入本地中
        String systemPath = request.getSession().getServletContext()
                .getRealPath(PathUtil.USER_LOGO_SYSTEM_PATH);
        FileUtils.copyInputStreamToFile(logo.getInputStream(), new File(systemPath, fileName));

        // 保存到数据库
        String relativePath = PathUtil.USER_LOGO_PATH + fileName;
        mSocietyService.updateSocietyLogo(societyId, relativePath);
    }

    /**
     * 社团负责人
     *
     * @param oldUserId
     * @param newUserId
     * @param societyId
     */
    @RequestMapping(value = "change", method = RequestMethod.POST)
    public void changePrincipal(@RequestAttribute("userId") String oldUserId,
                                @RequestParam("newUserId") String newUserId,
                                @RequestParam("societyId") int societyId) throws UserException {

        mSocietyService.changePrincipal(oldUserId, newUserId, societyId);
    }

    /**
     * 搜索社团，模糊查询
     *
     * @param query
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public List<Society> search(@RequestParam("query") String query) {
        System.out.println(query);
        return mSocietyService.searchSocietyByName(query);
    }

    /**
     * 申请加入社团
     *
     * @param body
     * @param form
     * @param userId
     * @throws SocietyException
     * @throws FormException
     */
    @RequestMapping(value = "join", method = RequestMethod.POST)
    public String joinSociety(@Validated @RequestBody ApplyEntity body, BindingResult form,
                              @RequestAttribute("userId") String userId) throws SocietyException, FormException {

        if (form.hasErrors()) throw new FormException(form);

        SocietyApply apply = new SocietyApply(userId, body.getSocietyId(), body.getReason());

        mSocietyService.applyJoinSociety(apply, userId);

        return "申请成功，请耐心等候";
    }

    /**
     * 查询社团的申请请求
     */
    @RequestMapping(value = "join", method = RequestMethod.GET)
    public JSONArray getApplyInfo(@RequestParam("societyId") String societyId,
                                  @RequestAttribute("userId") String userId) throws AuthorizedException, SocietyException {

        List<SocietyApply> applys = mSocietyService.getSocietyApplyIn(societyId, userId);

        JSONArray array = new JSONArray();

        applys.forEach(apply -> {
            JSONObject json = new JSONObject();
            json.put("applyId", apply.getId());
            json.put("applierId", apply.getUser().getUserId());
            json.put("reason", apply.getReason());
            array.add(json);
        });

        return array;
    }

    /**
     * 根据申请id获取申请信息
     *
     * @param userId
     * @param societyApplyId
     * @param identifier
     * @return
     */
    @RequestMapping(value = "/join/{societyApplyId}", method = RequestMethod.GET)
    public SocietyApply getApplyById(@RequestAttribute("userId") String userId,
                                     @PathVariable("societyApplyId") int societyApplyId,
                                     @RequestParam(name = "identifier", required = false) String identifier) {
        return mSocietyService.getSocietyApplyById(societyApplyId, userId, identifier);
    }

    /**
     * 处理一个加入社团的请求
     */
    @RequestMapping(value = "handle", method = RequestMethod.POST)
    public String handleSociety(@RequestParam("applyId") String applyId,
                                @RequestParam("isAllow") boolean isAllow,
                                @RequestAttribute("userId") String userId) throws AuthorizedException, SocietyException {
        mSocietyService.handleSocietyApple(applyId, isAllow, userId);
        return "处理成功";
    }

    /**
     * 获取社团的拥有的职位
     *
     * @param societyId 需要查询的社团Id
     * @return 社团所有的职位
     */
    @RequestMapping(value = "{societyId}/position", method = RequestMethod.GET)
    public JSONArray getPosition(@PathVariable("societyId") Integer societyId) throws SocietyException {

        List<SocietyPosition> positions = mSocietyService.getSocietyPosition(societyId);

        JSONArray result = new JSONArray();
        positions.forEach(position -> {
            JSONObject object = new JSONObject();
            object.put("id", position.getId());
            object.put("name", position.getName());
            object.put("grade", position.getGrade());

            result.add(object);
        });

        return result;
    }

    /**
     * 删除一个成员
     *
     * @param userId
     * @param societyId
     * @param removeUserId
     * @return
     * @throws SocietyException
     */
    @RequestMapping(value = "/remove")
    public String removeMember(@RequestAttribute("userId") String userId,
                               @RequestParam("societyId") int societyId,
                               @RequestParam("removeUserId") String removeUserId) throws SocietyException, AuthorizedException {
        return mSocietyService.removeMember(societyId, userId, removeUserId);
    }

    /**
     * 邀请一个成员加入社团
     *
     * @param societyId    社团Id
     * @param inviteUserId 被邀请人
     * @param inviteMsg    邀请信息
     * @param userId       发起请求的人
     * @throws UserException
     * @throws SocietyException
     */
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public void inviteUser(@RequestParam("societyId") int societyId,
                           @RequestParam("inviteUserId") String inviteUserId,
                           @RequestParam("positionId") int positionId,
                           @RequestParam("inviteMsg") String inviteMsg,
                           @RequestAttribute("userId") String userId) throws UserException, SocietyException, AuthorizedException {

        SocietyInvitation invitation = new SocietyInvitation(inviteUserId, userId, societyId, positionId, inviteMsg);

        mSocietyService.addSocietyInvitation(invitation);
    }

    /**
     * 获取我的社团邀请
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public List<SocietyInvitation> getInvitation(@RequestAttribute("userId") String userId) {
        return mSocietyService.getUserInvitation(userId);
    }

    /**
     * 处理是否同意这个社团邀请
     *
     * @param inviteId
     * @param isAllow
     * @param userId
     */
    @RequestMapping(value = "/invite/handle", method = RequestMethod.POST)
    public void handleInvitation(@RequestParam("inviteId") int inviteId,
                                 @RequestParam("isAllow") boolean isAllow,
                                 @RequestAttribute("userId") String userId) throws AuthorizedException, SocietyException {
        mSocietyService.handleSocietyInvitation(inviteId, isAllow, userId);
    }
}
