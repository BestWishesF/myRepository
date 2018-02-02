package cn.hotol.app.repository;

import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.TdHtMembThirdLogin;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.express.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.express.number.TsHtExpressOpenNumberDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.test.*;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-17.
 */
public interface TestRepository {

    public List<User> findUser();

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

    public List<Push> findPushByUser(Long user_id);

    public void insertMember(TdHtMemberDto tdHtMemberDto);

    public Double findBalanceByUser(Long user_id);

    public WeUser findWeUserByUser(String mobilenum);

    public void insertMembThirdLogin(TdHtMembThirdLogin tdHtMembThirdLogin);

    public List<SendAddress> findSendAddress(Long user_id);

    public List<RevAddress> findRevAddress(Long user_id);

    public void insertAddress(TdHtMembAddress tdHtMembAddress);

    public List<ExpressRequest> findExpressRequestBuUser(Long user_id);

    public List<Express> findExpressByRequest(Long express_request_id);

    public void insertExpressOrder(TdHtExpressOrderDto tdHtExpressOrderDto);

    public void insertExpressOrderCollect(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto);

    public List<Agency> findAgency();

    public List<Push> findPushByAgency(int agency_id);

    public void insertAgent(TdHtAgentDto tdHtAgentDto);

    public void updateExpOrdAgent(TdHtAgentDto tdHtAgentDto);

    public List<ExpressNo> findTT();

    public List<ExpressNo> findDB();

    public void insertExpressNo(TsHtExpressOpenNumberDto tsHtExpressOpenNumberDto);

    public List<TdHtMembAddressDto> findMembAddress();

    public void updateMembAddress(TdHtMembAddressDto tdHtMembAddressDto);

    public List<TdHtMemberDto> findAllMember();

}
