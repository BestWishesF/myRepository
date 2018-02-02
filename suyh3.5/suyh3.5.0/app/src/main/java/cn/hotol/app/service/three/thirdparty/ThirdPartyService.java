package cn.hotol.app.service.three.thirdparty;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.thirdlogin.BindingPhoneDto;
import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;

/**
 * Created by LuBin
 * Date 2017-01-03.
 */
public interface ThirdPartyService {

    /**
     * @param third_id
     * @param code
     * @param ip
     * @return RetInfo
     * @Purpose 微信用户登录
     * @version 3.0
     * @author lubin
     */
    public RetInfo obtainWXPersonal(String third_id, String code, String ip, int version);

    /**
     * @param tsHtThirdDto
     * @return RetInfo
     * @Purpose 查询微信公众号配置
     * @version 3.0
     * @author lubin
     */
    public RetInfo findTsHtThirdDto(TsHtThirdDto tsHtThirdDto);

    /**
     * @param bindingPhoneDto
     * @return RetInfo
     * @Purpose 绑定手机号
     * @version 3.0
     * @author lubin
     */
    public RetInfo bindingPhone(BindingPhoneDto bindingPhoneDto);

    /**
     * @param appid
     * @param http_url
     * @return RetInfo
     * @Purpose 微信使用权限签名
     * @version 3.0
     * @author lubin
     */
    public RetInfo obtainJsSdk(String appid,String http_url);


}
