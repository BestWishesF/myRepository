package cn.hotol.app.service.three.green;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;

/**
 * Created by Administrator on 2017-03-07.
 */
public interface GreenService {

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的参与活动记录
     * @version 3.0
     * @author lubin
     */
    public RetInfo findGreenMailByMemb(String token);

    /**
     * @param tdHtGreenMailDto
     * @return RetInfo
     * @Purpose 保存用户接收礼品信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo saveGreenAddress(TdHtGreenMailDto tdHtGreenMailDto);

}
