package cn.hotol.app.service.three.register;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.VerificationLoginDto;
import cn.hotol.app.bean.dto.register.RegisterDto;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public interface RegisterService {

    /**
     * @param registerDto
     * @return RetInfo
     * @Purpose 用户注册
     * @version 3.0
     * @author lubin
     */
    public RetInfo register(RegisterDto registerDto, String ip);

    /**
     * @param membId
     * @param frequency
     * @return RetInfo
     * @Purpose 用户注册获取邀请码
     * @version 3.0
     * @author lubin
     */
    public void createInviteCode(int membId, int frequency);

    /**
     * @param registerDto
     * @return RetInfo
     * @Purpose 代理人注册
     * @version 3.0
     * @author lubin
     */
    public RetInfo agentRegister(RegisterDto registerDto, String ip);

    /**
     * @Purpose   用户短信验证码登录
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     verificationLoginDto  登录信息
     * @return    RetInfo
     */
    public RetInfo verificationLogin(VerificationLoginDto verificationLoginDto, String ip);

}
