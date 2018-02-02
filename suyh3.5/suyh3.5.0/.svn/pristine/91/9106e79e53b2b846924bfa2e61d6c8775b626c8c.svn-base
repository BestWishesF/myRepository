package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.member.MemberPwdDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.push.PushDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lizhun on 2016/11/30.
 */
@Repository
public interface TdHtMemberRepository {
    /**
     * @param memb_phone
     * @return TdHtMemberDto
     * @Purpose 根据手机号码查找用户信息
     * @version 3.0
     * @author lizhun
     */
    public TdHtMemberDto findMemberByMembPhone(String memb_phone);

    /**
     * @param tdHtMemberDto
     * @return void
     * @Purpose 更新用户token, 推送标示, 推送类别
     * @version 3.0
     * @author lizhun
     */
    public void updateMemberLogin(TdHtMemberDto tdHtMemberDto);

    /**
     * @param tdHtMemberDto
     * @return int
     * @Purpose 根据推送信息查询用户id
     * @version 3.0
     * @author lubin
     */
    public List<TdHtMemberDto> findMemberIdByPush(TdHtMemberDto tdHtMemberDto);

    /**
     * @param tdHtMemberDto
     * @return void
     * @Purpose 更新用户已存在的推送信息
     * @version 3.0
     * @author lubin
     */
    public void updateExistingPushToken(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memb_phone
     * @return TdHtMemberDto
     * @Purpose 根据手机号查询用户id和是否锁定
     * @version 3.0
     * @author lubin
     */
    public TdHtMemberDto findMebIdAndLockByPhone(String memb_phone);

    /**
     * @param tdHtMemberDto
     * @return
     * @Purpose 保存新的注册用户
     * @version 3.0
     * @author lubin
     */
    public void insertMember(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memberPwdDto
     * @return
     * @Purpose 修改用户密码
     * @version 3.0
     * @author lubin
     */
    public void updateMemberPwd(MemberPwdDto memberPwdDto);

    /**
     * @param tdHtMemberDto
     * @return
     * @Purpose 修改用户信息
     * @version 3.0
     * @author lubin
     */
    public void updatePersonalInfo(TdHtMemberDto tdHtMemberDto);

    /**
     * @param tdHtMemberDto
     * @return
     * @Purpose 修改用户积分信息
     * @version 3.0
     * @author liyafei
     */
    public void updateMembScore(TdHtMemberDto tdHtMemberDto);

    /**
     * @param
     * @return
     * @Purpose 查询用户表数据总数
     * @version 3.0
     * @author liyafei
     */
    public int findMebCount();

    /**
     * @param tdHtMemberDto
     * @return
     * @Purpose 根据id查询积分小于指定用户积分的数据总数
     * @version 3.0
     * @author liyafei
     */
    public int findMebCountByScore(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memb_id
     * @return TdHtMemberDto
     * @Purpose 根据用户id查找用户余额
     * @version 3.0
     * @author lizhun
     */
    public TdHtMemberDto findMemberBanlance(int memb_id);

    /**
     * @param tdHtMemberDto
     * @return TdHtMemberDto
     * @Purpose 根据用户id更新用户余额
     * @version 3.0
     * @author lizhun
     */
    public void updateMemberBanlance(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memb_invite_code
     * @return int
     * @Purpose 通过生成的邀请码查询存在条数
     * @version 3.0
     * @author lubin
     */
    public int findMembNumByInvite(String memb_invite_code);

    /**
     * @param tdHtMemberDto
     * @Purpose 修改用户的邀请码
     * @version 3.0
     * @author lubin
     */
    public void updateMembInviteCode(TdHtMemberDto tdHtMemberDto);

    /**
     * @param tdHtMemberDto
     * @Purpose 更新用户的头像地址
     * @version 3.0
     * @author lubin
     */
    public void updateMembHeadPortrait(TdHtMemberDto tdHtMemberDto);

    /**
     * @param tdHtMemberDto
     * @Purpose 更新用户的余额和积分
     * @version 3.0
     * @author lubin
     */
    public void updateMemberBanlanceAndScore(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memb_id
     * @return PushDto
     * @Purpose 查询用户的推送信息
     * @version 3.0
     * @author lubin
     */
    public PushDto findPushInfoByMembId(int memb_id);

    /**
     * @param memb_id
     * @return TdHtMemberDto
     * @Purpose 通过id查询用户信息
     * @version 3.0
     * @author lubin
     */
    public TdHtMemberDto findMembByMembId(int memb_id);

    /**
     * @param tdHtMemberDto
     * @Purpose 修改用户第三方登录信息
     * @version 3.0
     * @author lubin
     */
    public void updateMembThirdInfo(TdHtMemberDto tdHtMemberDto);

    /**
     * @param tdHtMemberDto
     * @Purpose 第三方绑定手机号
     * @version 3.0
     * @author lubin
     */
    public void updateMemberBindingPhone(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memb_id
     * @Purpose 删除用户信息
     * @version 3.0
     * @author lubin
     */
    public void deleteThirdMemberInfo(int memb_id);

    /**
     * @param tdHtMemberDto
     * @Purpose 修改注册用户信息
     * @version 3.0
     * @author lubin
     */
    public void updateRegMember(TdHtMemberDto tdHtMemberDto);

    /**
     * @param memb_role
     * @return List<TdHtMemberDto>
     * @Purpose 通过用户角色查询不同角色用户集合
     * @version 3.0
     * @author lubin
     */
    public List<TdHtMemberDto> findMemberByRole(int memb_role);

    /**
     * @Purpose   修改用户设备号
     * @version   3.0
     * @author    lubin
     * @time      2017-04-06
     * @param     device_number  设备号
     * @return
     */
    public void updateMembDevice(String device_number);
}
