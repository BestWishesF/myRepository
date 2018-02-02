package cn.hotol.app.service.three.invite;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.invite.TdHtMemberInviteDto;

/**
 * Created by LuBin
 * Date 2016-12-22.
 */
public interface MemberInviteService {

    /**
     * @return RetInfo
     * @Purpose 查询用户的邀请码
     * @version 3.0
     * @author lubin
     */
    public RetInfo findMembInviteCode(String token);

}
