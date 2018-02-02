package cn.hotol.app.service.three.invite;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.invite.TdHtMemberInviteDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtMemberInviteRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by LuBin
 * Date 2016-12-22.
 */

@Service
public class MemberInviteServiceImpl implements MemberInviteService {

    private Logger logger = Logger.getLogger(MemberInviteServiceImpl.class);

    @Resource
    private TdHtMemberInviteRepository tdHtMemberInviteRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;

    /**
     * @return RetInfo
     * @Purpose 查询用户的邀请码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findMembInviteCode(String token) {
        String logInfo = this.getClass().getName() + ":findMembInviteCode:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto=tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            retInfo.setMark("0");
            retInfo.setTip("邀请码查询成功.");
            retInfo.setObj(tdHtMemberDto.getMemb_invite_code());
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
