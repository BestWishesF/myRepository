package cn.hotol.app.service.express.sdk;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;

import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-09.
 */
public interface ExpressSdkService {

    /**
     * @Purpose  查询快递公司的SDK
     * @version  3.0
     * @author   lubin
     * @return   Map
     */
    public Map<String, Map<String, TsHtExpressSdkDto>> findExpressSdk();

    /**
     * @param eoa_id,currentPage,pageSize
     * @Purpose  分页查询快递公司接口配置
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    public RetInfo findExpressSdkPage(int eoa_id, int currentPage, int pageSize);

    /**
     * @param exp_sdk_id
     * @Purpose  通过id查询快递公司接口配置
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    public RetInfo findExpressSdkById(int exp_sdk_id);

    /**
     * @Purpose   新增快递sdk配置
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  快递sdk数据
     * @return    RetInfo
     */
    public RetInfo insertExpressSdk(TsHtExpressSdkDto tsHtExpressSdkDto);

    /**
     * @Purpose   修改快递sdk配置
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  快递sdk数据
     * @return    RetInfo
     */
    public RetInfo updateExpressSdk(TsHtExpressSdkDto tsHtExpressSdkDto);

}
