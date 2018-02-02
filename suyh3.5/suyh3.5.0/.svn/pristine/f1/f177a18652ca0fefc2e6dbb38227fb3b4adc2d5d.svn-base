package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.push.PushDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-15.
 */

@Repository
public interface TdHtAgentRepository {

    /**
     * @param agent_id
     * @return TdHtAgentDto
     * @Purpose 查询代理人信息
     * @version 3.0
     * @author lubin
     */
    public TdHtAgentDto findAgentById(int agent_id);

    /**
     * @param tdHtAgentDto
     * @return TdHtAgentDto
     * @Purpose 通过区域id查询代理人推送信息
     * @version 3.0
     * @author lubin
     */
    public List<PushDto> findPushRegistrationId(TdHtAgentDto tdHtAgentDto);

    /**
     * @param agent_phone
     * @return TdHtAgentDto
     * @Purpose 通过手机号查询代理人帐号
     * @version 3.0
     * @author lubin
     */
    public TdHtAgentDto findAgentByPhone(String agent_phone);

    /**
     * @param tdHtAgentDto
     * @Purpose 代理人注册
     * @version 3.0
     * @author lubin
     */
    public void insertAgent(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @Purpose 修改代理人的密码
     * @version 3.0
     * @author lubin
     */
    public void updateAgentPassword(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @Purpose 修改代理人个人邮箱
     * @version 3.0
     * @author lubin
     */
    public void updateAgentEmail(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @Purpose 修改代理人备用电话
     * @version 3.0
     * @author lubin
     */
    public void updateAgentSparePhone(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @return int
     * @Purpose 通过推送信息查询代理人
     * @version 3.0
     * @author lubin
     */
    public List<TdHtAgentDto> findAgentIdByPush(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @Purpose 修改代理人推送信息
     * @version 3.0
     * @author lubin
     */
    public void updateAgentPushToken(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @Purpose 代理人登录
     * @version 3.0
     * @author lubin
     */
    public void updateAgentLogin(TdHtAgentDto tdHtAgentDto);

    /**
     * @param agent_spare_phone
     * @Purpose 修改代理人备用电话
     * @version 3.0
     * @author lubin
     */
    public int findAgentCountBySpare(String agent_spare_phone);

    /**
     * @param tdHtAgentDto
     * @Purpose 修改代理人信息
     * @version 3.0
     * @author lubin
     */
    public void updateAgentApplyInfo(TdHtAgentDto tdHtAgentDto);

    /**
     * @param agent_id
     * @Purpose 查询代理人状态
     * @version 3.0
     * @author lubin
     */
    public int findAgentStateById(int agent_id);

    /**
     * @param tdHtAgentDto
     * @Purpose 更新代理人的坐标
     * @version 3.0
     * @author lubin
     */
    public void updateAgentCoordinates(TdHtAgentDto tdHtAgentDto);

    /**
     * @return  PushDto
     * @param agent_id
     * @Purpose 查询代理人的推送信息
     * @version 3.0
     * @author lubin
     */
    public PushDto findAgentPushInfo(int agent_id);

    /**
     * @param tdHtAgentDto
     * @Purpose 更新代理人的余额
     * @version 3.0
     * @author lubin
     */
    public void updateAgentBalance(TdHtAgentDto tdHtAgentDto);

    /**
     * @param tdHtAgentDto
     * @Purpose 更新代理人状态
     * @version 3.0
     * @author lubin
     */
    public void updateSetStateAgent(TdHtAgentDto tdHtAgentDto);

    /**
     * @Purpose   通过划分区域id查询代理人推送信息
     * @version   3.0
     * @author    lubin
     * @time      2017-03-24
     * @param     tdHtAgentDto  划分区域id
     * @return    PushDto  推送信息
     */
    public List<PushDto> findAgentPushByDivide(TdHtAgentDto tdHtAgentDto);

}
