package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.expresssdk.TsHtExpressSdkDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-06.
 */
public interface TsHtExpressSdkRepository {

    /**
     * @param eoa_id
     * @Purpose 通过快递公司开通区域表id查询快递接口配置
     * @version  3.0
     * @author   lubin
     * @return   TsHtExpressSdkDto
     */
    public List<TsHtExpressSdkDto> findExpressSdkByEoa(int eoa_id);

    /**
     * @param tsHtExpressSdkDto
     * @Purpose 通过接口类型和开通快递区域表id查询快递接口配置
     * @version  3.0
     * @author   lubin
     * @return   TsHtExpressSdkDto
     */
    public TsHtExpressSdkDto findExpressSdkByBen(TsHtExpressSdkDto tsHtExpressSdkDto);

    /**
     * @param map
     * @Purpose 根据快递公司开通区域查询快递公司接口配置数量
     * @version  3.0
     * @author   lubin
     * @return   Integer
     */
    public Integer findExpressSdkSize(Map<String, Object> map);

    /**
     * @param map
     * @Purpose 根据快递公司开通区域查询快递公司接口配置信息
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtExpressSdkDto>
     */
    public List<TsHtExpressSdkDto> findExpressSdkPage(Map<String, Object> map);

    /**
     * @param exp_sdk_id
     * @Purpose 通过id查询快递公司接口配置
     * @version  3.0
     * @author   lubin
     * @return   TsHtExpressSdkDto
     */
    public TsHtExpressSdkDto findExpressSdkById(int exp_sdk_id);

    /**
     * @Purpose   通过开通区域id和接口类别查询接口配置信息
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  查询条件
     * @return    TsHtExpressSdkDto  查询结果
     */
    public TsHtExpressSdkDto findExpSdkByEoaType(TsHtExpressSdkDto tsHtExpressSdkDto);

    /**
     * @Purpose   新增快递接口配置
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  快递接口配置信息
     * @return
     */
    public void insertExpressSdk(TsHtExpressSdkDto tsHtExpressSdkDto);

    /**
     * @Purpose   修改快递接口配置
     * @version   3.0
     * @author    lubin
     * @time      2017-03-31
     * @param     tsHtExpressSdkDto  快递接口配置信息
     * @return
     */
    public void updateExpressSdk(TsHtExpressSdkDto tsHtExpressSdkDto);

}
