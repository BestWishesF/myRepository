package cn.hotol.app.service.three.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.*;

/**
 * Created by lizhun on 2016/12/21.
 */
public interface MemberService {
    /**
     * @param token
     * @return RetInfo
     * @Purpose 用户查询余额
     * @version 3.0
     * @author lizhun
     */
    public RetInfo getMembBalacne(String token);

    /**
     * @param forgetPwdDto
     * @return
     * @Purpose 用户忘记密码
     * @version 3.0
     * @author lubin
     */
    public RetInfo forgetPwd(ForgetPwdDto forgetPwdDto);

    /**
     * @param memberPwdDto
     * @return
     * @Purpose 用户修改密码
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyPwd(MemberPwdDto memberPwdDto, String token);

    /**
     * @param memberInfoDto
     * @param token
     * @return
     * @Purpose 用户修改个人信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo modifyPersonalInfo(MemberInfoDto memberInfoDto, String token);

    /**
     * @param token
     * @return
     * @Purpose 用户查询个人信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo findPersonalInfo(String token);

    /**
     * @param tdHtMemberDto
     * @return
     * @Purpose 用户修改头像
     * @version 3.0
     * @author lubin
     */
    public RetInfo uploadHeadFile(TdHtMemberDto tdHtMemberDto);

    /**
     * @Purpose   用户设置密码
     * @version   3.0
     * @author    lubin
     * @time      2017-04-01
     * @param     membSetPassDto  用户设置的密码
     * @return    RetInfo
     */
    public RetInfo setMemberPass(MembSetPassDto membSetPassDto);

}
