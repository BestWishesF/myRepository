package cn.hotol.app.controller.three.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.*;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.oss.OssUtil;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.agent.member.AgentMemberService;
import cn.hotol.app.service.three.login.LoginService;
import cn.hotol.app.service.three.member.MemberService;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class MemberController {

    @Resource
    private LoginService loginThreeService;
    @Resource
    private MemberService memberService;
    @Resource
    private AgentMemberService agentMemberService;

    /**
     * @param tdHtMemberDto
     * @return Integer
     * @Purpose 登陆
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/login", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo login(@Valid @RequestBody TdHtMemberDto tdHtMemberDto, BindingResult result, HttpServletRequest request,
                         @RequestHeader(value = "client_type", required = true) Integer client_type,
                         @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();

        if (version < 5) {
            retInfo.setMark("1");
            retInfo.setTip("您的速邮汇版本过低，请下载最新版本.");
        } else {
            if (result.hasErrors()) {
                List<FieldError> err = result.getFieldErrors();
                FieldError fe = err.get(0);
                retInfo.setMark("1");
                retInfo.setTip(fe.getDefaultMessage());
            } else {
                tdHtMemberDto.setPush_type(client_type);
                return loginThreeService.login(tdHtMemberDto, Ip.getIpAddr(request));
            }
        }

        return retInfo;
    }

    /**
     * @param forgetPwdDto
     * @Purpose 用户忘记密码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/forgetPwd", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo forgetPwd(@Valid @RequestBody ForgetPwdDto forgetPwdDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return memberService.forgetPwd(forgetPwdDto);
        }
        return retInfo;
    }

    /**
     * @param memberPwdDto
     * @Purpose 用户修改密码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/modifyPwd", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyPwd(@Valid @RequestBody MemberPwdDto memberPwdDto, BindingResult result,
                             @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return memberService.modifyPwd(memberPwdDto, token);
        }
        return retInfo;
    }

    /**
     * @param token
     * @Purpose 用户查询个人信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findPersonalInfo", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findPersonalInfo(@RequestHeader(value = "token", required = true) String token) {
        return memberService.findPersonalInfo(token);
    }

    /**
     * @param memberInfoDto
     * @param token
     * @Purpose 用户修改个人信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/modifyPersonalInfo", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyPersonalInfo(@Valid @RequestBody MemberInfoDto memberInfoDto, BindingResult result,
                                      @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return memberService.modifyPersonalInfo(memberInfoDto, token);
        }
        return retInfo;
    }

    /**
     * @param token
     * @Purpose 用户查询余额
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/token/member/balance", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo balance(@RequestHeader(value = "token", required = true) String token) {
        return memberService.getMembBalacne(token);
    }

    /**
     * 上传用户头像
     *
     * @param headFile
     * @return
     */
    @RequestMapping(value = "/app/3/token/uploadHeadFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RetInfo uploadHeadFile(@RequestParam(value = "headFile", required = true) CommonsMultipartFile headFile,
                                  @RequestHeader(value = "token", required = true) String token) throws Exception {
        RetInfo retInfo = new RetInfo();
        if (headFile != null && headFile.getSize() > 3145728) {
            retInfo.setMark("1");
            retInfo.setTip("头像超过3M");
        } else {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(headFile.getSize());
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
            String fileName = headFile.getOriginalFilename().substring(headFile.getOriginalFilename().lastIndexOf("."));
            String savePath = Constant.FILE_IMG + "/member/" + tdHtMemberDto.getMemb_id() + "/head/" + formatter.format(new Date(System.currentTimeMillis())) + fileName;
            OssUtil.uploadInputStream(headFile.getInputStream(), objectMetadata, savePath);
            tdHtMemberDto.setMemb_head_portrait(Constant.DOMAIN + "/" + savePath);
            retInfo = memberService.uploadHeadFile(tdHtMemberDto);
        }
        return retInfo;

    }

    /**
     * @param token
     * @return
     * @Purpose 用户退出登录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/exitLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo exitLogin(@RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        MemcachedUtils.delete(token);
        retInfo.setMark("0");
        retInfo.setTip("退出登录成功.");
        return retInfo;
    }

    /**
     * @param tdHtMemberDto
     * @return Integer
     * @Purpose 登陆
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/monthlyLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo monthlyLogin(@Valid @RequestBody TdHtMemberDto tdHtMemberDto, BindingResult result, HttpServletRequest request,
                                @RequestHeader(value = "client_type", required = true) Integer client_type,
                                @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();

        if (version < 4) {
            retInfo.setMark("1");
            retInfo.setTip("您的速邮汇版本过低，请下载最新版本.");
        } else {
            if (result.hasErrors()) {
                List<FieldError> err = result.getFieldErrors();
                FieldError fe = err.get(0);
                retInfo.setMark("1");
                retInfo.setTip(fe.getDefaultMessage());
            } else {
                tdHtMemberDto.setPush_type(client_type);
                tdHtMemberDto.setApp_version(version);
                retInfo = loginThreeService.monthlyLogin(tdHtMemberDto, Ip.getIpAddr(request));
            }
        }

        return retInfo;
    }
    /**
     * @Purpose  添加代理人用户关联信息表
     * @version  3.2
     * @author   lizhun
     * @param     map
     * @return   RetInfo
     */
    @RequestMapping(value = "/app/5/token/agent", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agent(@RequestBody Map<String, String> map,
            @RequestHeader(value = "token", required = true) String token) {
        return agentMemberService.insertAgentMember(map, token);
    }

    /**
     * @param tdHtMemberDto
     * @return Integer
     * @Purpose 登陆
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/5/webLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo webLogin(@Valid @RequestBody TdHtMemberDto tdHtMemberDto, BindingResult result, HttpServletRequest request,
                                @RequestHeader(value = "client_type", required = true) Integer client_type,
                                @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();

        if (version < 5) {
            retInfo.setMark("1");
            retInfo.setTip("您的速邮汇版本过低，请下载最新版本.");
        } else {
            if (result.hasErrors()) {
                List<FieldError> err = result.getFieldErrors();
                FieldError fe = err.get(0);
                retInfo.setMark("1");
                retInfo.setTip(fe.getDefaultMessage());
            } else {
                tdHtMemberDto.setPush_type(client_type);
                return loginThreeService.monthlyLogin(tdHtMemberDto, Ip.getIpAddr(request));
            }
        }

        return retInfo;
    }

    /**
     * @Purpose   用户设置密码
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     membSetPassDto  用户设置的密码
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/token/setMemberPass", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo setMemberPass(@Valid @RequestBody MembSetPassDto membSetPassDto, BindingResult result,
                                    @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            membSetPassDto.setToken(token);
            return memberService.setMemberPass(membSetPassDto);
        }
        return retInfo;
    }
}
