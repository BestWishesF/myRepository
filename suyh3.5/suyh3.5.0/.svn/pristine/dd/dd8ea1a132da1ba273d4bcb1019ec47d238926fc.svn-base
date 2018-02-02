package cn.hotol.wechat.service.login;

import cn.hotol.wechat.domain.model.login.User;
import cn.hotol.wechat.repository.login.LoginRepository;
import cn.hotol.wechat.toolutil.modelutil.CryptUtils;
import cn.hotol.wechat.toolutil.modelutil.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录方法类
 * Created by LuBin
 * Date 2016-11-29.
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    Logger logger = Logger.getLogger(LoginServiceImpl.class);
    String logInfo = this.getClass().getName();

    /**
     * 用户登录方法
     * 作者：陆斌
     * 时间：2016-11-29 11：16
     * 版本：1.0
     * @param user      用户帐号和密码
     * @return          返回信息
     */
    @Override
    public Result login(User user) {
        //登录方法开始
        logger.info("===================" + logInfo + "：login：begin======================");

        String errMsg = "";       //信息提示
        Boolean success = false;  //是否成功
        try {
            //验证帐号是否存在
            User userDB = loginRepository.validateLogon(user.getMobileNo());
            if (userDB == null) {
                errMsg = "该帐号为注册！";
            }
            //用户密码加密
            String pass = CryptUtils.digest(user.getPassword());
            if (!pass.equals(userDB.getPassword())) {
                errMsg = "密码不正确！";
            } else {
                errMsg = "登录成功！";
                success = true;
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }

        //登录方法结束
        logger.info("===================" + logInfo + "：login：end======================");
        return Result.success(success);
    }
}
