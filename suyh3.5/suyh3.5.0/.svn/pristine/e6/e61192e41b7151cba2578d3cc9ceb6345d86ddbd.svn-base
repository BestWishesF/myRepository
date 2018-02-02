package cn.hotol.wechat.controller.login;

import cn.hotol.wechat.domain.model.login.User;
import cn.hotol.wechat.service.login.LoginService;
import cn.hotol.wechat.toolutil.modelutil.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * login: Lewisw
 * Date: 13-11-2
 * Time: 下午4:27
 */
@Controller
@RequestMapping(headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     * 作者：陆斌
     * 时间：2016-11-29 11：16
     * 版本：1.0
     * @param user      用户帐号和密码
     * @param request   其他信息
     * @return           返回信息
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public Result login(@RequestBody User user, HttpServletRequest request) {
        return loginService.login(user);
    }

}
