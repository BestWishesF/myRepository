package cn.hotol.app.controller.three.agent;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.AgentEmailDto;
import cn.hotol.app.bean.dto.agent.AgentSparePhoneDto;
import cn.hotol.app.bean.dto.agent.AgentStateDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.found.TdHtAgentFoundChangeDto;
import cn.hotol.app.bean.dto.member.ForgetPwdDto;
import cn.hotol.app.bean.dto.member.MemberPwdDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.service.three.agent.AgentService;
import cn.hotol.app.service.three.login.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */
@Controller
public class AgentController {

    @Resource
    private LoginService loginThreeService;
    @Resource
    private AgentService agentService;

    /**
     * @param tdHtAgentDto
     * @return
     * @Purpose 代理人登录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agentLogin(@Valid @RequestBody TdHtAgentDto tdHtAgentDto, BindingResult result, HttpServletRequest request,
                              @RequestHeader(value = "client_type", required = true) Integer client_type,
                              @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();

        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            tdHtAgentDto.setPush_type(client_type);
            tdHtAgentDto.setApp_version(version);
            return loginThreeService.agentLogin(tdHtAgentDto, Ip.getIpAddr(request));
        }
        return retInfo;
    }

    /**
     * @param forgetPwdDto
     * @return
     * @Purpose 代理人忘记密码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/forgetAgentPwd", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo forgetAgentPwd(@Valid @RequestBody ForgetPwdDto forgetPwdDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return agentService.forgetAgentPwd(forgetPwdDto);
        }
        return retInfo;
    }

    /**
     * @param memberPwdDto
     * @return
     * @Purpose 代理人修改密码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/modifyAgentPwd", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyAgentPwd(@Valid @RequestBody MemberPwdDto memberPwdDto, BindingResult result,
                                  @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return agentService.modifyAgentPwd(memberPwdDto, token);
        }
        return retInfo;
    }

    /**
     * @param token
     * @return
     * @Purpose 查询代理人信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/findAgentInfo", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAgentInfo(@RequestHeader(value = "token", required = true) String token) {
        return agentService.findAgentInfo(token);
    }

    /**
     * @param agentEmailDto
     * @return
     * @Purpose 修改代理人邮箱
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/modifyAgentEmail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyAgentEmail(@Valid @RequestBody AgentEmailDto agentEmailDto, BindingResult result,
                                    @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return agentService.modifyAgentEmail(agentEmailDto, token);
        }
        return retInfo;
    }

    /**
     * @param agentSparePhoneDto
     * @return
     * @Purpose 修改代理人备用手机号
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/modifyAgentSparePhone", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyAgentSparePhone(@Valid @RequestBody AgentSparePhoneDto agentSparePhoneDto, BindingResult result,
                                         @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return agentService.modifyAgentSparePhone(agentSparePhoneDto, token);
        }
        return retInfo;
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 查询代理人收支明细及余额
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/getAgentBalacne", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo getAgentBalacne(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return agentService.getAgentBalacne(pageDto, token);
    }

    /**
     * @param token
     * @return
     * @Purpose 代理人退出登录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentExitLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo exitLogin(@RequestHeader(value = "token", required = true) String token) {
        return agentService.exitLogin(token);
    }

    /**
     * @param token
     * @return
     * @Purpose 查询代理人我的界面数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/findAgentData", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAgentData(@RequestHeader(value = "token", required = true) String token) {
        return agentService.findAgentData(token);
    }

    /**
     * @param tdHtAgentFoundChangeDto,token
     * @return RetInfo
     * @Purpose 代理人提现
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentWithdrawals", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agentWithdrawals(@RequestBody TdHtAgentFoundChangeDto tdHtAgentFoundChangeDto,
                                    @RequestHeader(value = "token", required = true) String token) {
        return agentService.agentWithdrawals(tdHtAgentFoundChangeDto,token);
    }

    /**
     * @param agentStateDto,token
     * @return RetInfo
     * @Purpose 更新代理人状态
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentSetWork", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agentSetWork(@RequestBody AgentStateDto agentStateDto,
                                    @RequestHeader(value = "token", required = true) String token) {
        return agentService.agentSetWork(agentStateDto,token);
    }

    /**
     * @Purpose   通过推广码查询代理人推广数量
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     params  推广码
     * @return    RetInfo  推广数量
     */
    @RequestMapping(value = "/app/6/findExtensionNum", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExtensionNum(@RequestBody Map<String, String> params) {
        return agentService.findExtensionNum(params);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询代理人入账记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/7/agentToken/findAgentEntryRecordPage", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAgentEntryRecordPage(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return agentService.findAgentEntryRecordPage(pageDto, token);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询代理人提现记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/7/agentToken/findAgentWithdrawalsPage", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAgentWithdrawalsPage(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return agentService.findAgentWithdrawalsPage(pageDto, token);
    }

}
