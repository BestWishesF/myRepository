package cn.hotol.app.service.three.sendexpress;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.expressorder.ExpressDto;

/**
 * 寄件
 * Created by liyafei on 2016/12/3.
 */
public interface ExpressService {

    /**
     * @param token
     * @param expressDto
     * @return RetInfo
     * @Purpose 用户寄单个件
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendOneExpress(ExpressDto expressDto, String token, int client_type, int version);

    /**
     * @param token
     * @param expressDto
     * @return RetInfo
     * @Purpose 用户批量寄件
     * @version 3.0
     * @author lubin
     */
    public RetInfo sendMultiExpress(ExpressDto expressDto, String token, int client_type, int version);

    /**
     * @param courierDto
     * @return RetInfo
     * @Purpose 查询用户寄件界面字典数据
     * @version 3.0
     * @author lubin
     */
    public RetInfo findSendExpInfo(CourierDto courierDto, String token, int version);

    /**
     * @param token
     * @param expressDto
     * @return RetInfo
     * @Purpose 大客户寄件
     * @version 3.0
     * @author lubin
     */
    public RetInfo bigClientsSendExp(ExpressDto expressDto, String token, int client_type);

}
