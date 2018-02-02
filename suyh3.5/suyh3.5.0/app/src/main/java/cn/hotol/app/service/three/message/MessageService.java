package cn.hotol.app.service.three.message;

import cn.hotol.app.base.RetInfo;

/**
 * Created by LuBin
 * Date 2016-12-06.
 */
public interface MessageService {

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 用户注册时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendRegValidateCode(String phoneno);

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 用户忘记密码时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendForgetPassValidateCode(String phoneno);

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 代理人注册时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendAgentRegCode(String phoneno);

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 代理人忘记密码时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendAgentForgetPassCode(String phoneno);

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 代理人修改备用手机号时发送手机验证码
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendAgentSparePhoneCode(String phoneno);

    /**
     * @param phoneno
     * @return RetInfo
     * @Purpose 第三方绑定手机号发送验证码
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendWeChatBinding(String phoneno);

    /**
     * @Purpose   手机验证码登陆获取验证码
     * @version   3.0
     * @author    lubin
     * @time      2017-04-02
     * @param     phoneno  手机号
     * @return    RetInfo
     */
    public RetInfo sendVerificaLoginCode(String phoneno);

}
