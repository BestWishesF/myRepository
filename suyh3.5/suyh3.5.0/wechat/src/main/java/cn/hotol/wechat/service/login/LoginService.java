package cn.hotol.wechat.service.login;

import cn.hotol.wechat.domain.model.login.User;
import cn.hotol.wechat.toolutil.modelutil.Result;

/**
 * 登录接口
 * Created by LuBin
 * Date 2016-11-29.
 */
public interface LoginService {

    Result login(User user);

}
