package cn.hotol.app.controller.three.message;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.message.TwSmsSendHisDto;
import cn.hotol.app.service.three.message.MessageService;
import cn.jiguang.common.utils.StringUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */

@Controller
public class MessageController {

    @Resource
    private MessageService messageService;
    @Resource
    private Producer captchaProducer;

    /**
     * @param twSmsSendHisDto
     * @return RetInfo
     * @Purpose 用户注册时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/sendRegValidateCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendRegValidateCode(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendRegValidateCode(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

    /**
     * @param twSmsSendHisDto
     * @return RetInfo
     * @Purpose 用户忘记密码时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/sendForgetPassValidateCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendForgetPassValidateCode(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendForgetPassValidateCode(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

    /**
     * @param twSmsSendHisDto
     * @return RetInfo
     * @Purpose 代理人注册时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/sendAgentRegCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendAgentRegCode(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendAgentRegCode(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

    /**
     * @param twSmsSendHisDto
     * @return RetInfo
     * @Purpose 代理人忘记密码时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/sendAgentForgetPassCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendAgentForgetPassCode(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendAgentForgetPassCode(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

    /**
     * @param twSmsSendHisDto
     * @return
     * @Purpose 代理人修改备用手机时发送验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/sendAgentSparePhoneCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendAgentSparePhoneCode(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendAgentSparePhoneCode(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

    /**
     * @param twSmsSendHisDto
     * @return
     * @Purpose 第三方绑定手机号发送验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/sendWeChatBinding", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendWeChatBinding(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendWeChatBinding(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

    /**
     * @return
     * @Purpose 获取图片验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/getKaptchaImage")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /**
     * @return
     * @Purpose 验证图片验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping("/app/5/checkForgetPassCode")
    @ResponseBody
    public RetInfo checkForgetPassCode(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession();
        String memb_phone=(String) map.get("memb_phone");
        String submitCode = (String) map.get("img_verify");
        //从session中获取系统生成的验证码
        String kaptchaExpected = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //进行比较
        if (StringUtils.isEmpty(submitCode) || kaptchaExpected.equalsIgnoreCase(submitCode)) {
            retInfo = messageService.sendForgetPassValidateCode(memb_phone);
        } else {
            retInfo.setMark("1");
            retInfo.setTip("验证码错误.");
        }
        return retInfo;
    }

    /**
     * @return
     * @Purpose 验证图片验证码
     * @version 3.0
     * @author lubin
     */
    @RequestMapping("/app/5/checkRegCode")
    @ResponseBody
    public RetInfo checkRegCode(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession();
        String memb_phone=(String) map.get("memb_phone");
        String submitCode = (String) map.get("img_verify");
        //从session中获取系统生成的验证码
        String kaptchaExpected = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //进行比较
        if (StringUtils.isEmpty(submitCode) || kaptchaExpected.equalsIgnoreCase(submitCode)) {
            retInfo = messageService.sendRegValidateCode(memb_phone);
        } else {
            retInfo.setMark("1");
            retInfo.setTip("验证码错误.");
        }
        return retInfo;
    }

    /**
     * @Purpose   手机验证码登陆获取验证码
     * @version   3.0
     * @author    lubin
     * @time      2017-04-02
     * @param     twSmsSendHisDto  手机号
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/sendVerificaLoginCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendVerificaLoginCode(@Valid @RequestBody TwSmsSendHisDto twSmsSendHisDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return messageService.sendVerificaLoginCode(twSmsSendHisDto.getPhoneno());
        }
        return retInfo;
    }

}
