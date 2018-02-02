package cn.hotol.app.controller.three.register;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.VerificationLoginDto;
import cn.hotol.app.bean.dto.register.RegisterDto;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.service.three.register.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */

@Controller
public class RegisterController {

    @Resource
    private RegisterService registerService;

    /**
     * @param registerDto
     * @return RetInfo
     * @Purpose 用户注册
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/register", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo register(@Valid @RequestBody RegisterDto registerDto, BindingResult result, HttpServletRequest request,
                            @RequestHeader(value = "client_type", required = true) Integer client_type) {
        RetInfo retInfo = new RetInfo();

        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            registerDto.setPush_type(client_type);
            return registerService.register(registerDto, Ip.getIpAddr(request));
        }
        return retInfo;
    }

    /**
     * @param registerDto
     * @return RetInfo
     * @Purpose 代理人注册
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentRegister", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agentRegister(@Valid @RequestBody RegisterDto registerDto, BindingResult result, HttpServletRequest request,
                                 @RequestHeader(value = "client_type", required = true) Integer client_type,
                                 @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();

        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            registerDto.setPush_type(client_type);
            registerDto.setApp_version(version);
            return registerService.agentRegister(registerDto, Ip.getIpAddr(request));
        }
        return retInfo;
    }

    /**
     * @Purpose   用户短信验证码登录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     verificationLoginDto  登录信息
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/verificationLogin", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo verificationLogin(@Valid @RequestBody VerificationLoginDto verificationLoginDto, BindingResult result, HttpServletRequest request,
                                     @RequestHeader(value = "client_type", required = true) Integer client_type,
                                     @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            verificationLoginDto.setPush_type(client_type);
            verificationLoginDto.setApp_version(version);
            retInfo = registerService.verificationLogin(verificationLoginDto, Ip.getIpAddr(request));
        }
        return retInfo;
    }

}
