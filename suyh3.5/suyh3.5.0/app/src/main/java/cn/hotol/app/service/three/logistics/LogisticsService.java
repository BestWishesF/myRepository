package cn.hotol.app.service.three.logistics;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.logistics.LogisticCodeDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;

/**
 * Created by LuBin
 * Date 2016-12-01.
 */
public interface LogisticsService {

    /**
     * @param logisticCodeDto
     * @return RetInfo
     * @Purpose 根据快递单号查询快递公司
     * @version 3.0
     * @author lubin
     */
    public RetInfo findExpressCodeByWaybill(LogisticCodeDto logisticCodeDto);

    /**
     * @param logisticCodeDto
     * @param tdHtMemberDto   登录用户
     * @return RetInfo
     * @Purpose 通过快递公司和单号查询物流信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo findExpressLogisticByWaybill(LogisticCodeDto logisticCodeDto, TdHtMemberDto tdHtMemberDto);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询前十条的查件记录
     * @version 3.0
     * @author lubin
     */
    public RetInfo findFirstTenExpSearch(String token);

}
