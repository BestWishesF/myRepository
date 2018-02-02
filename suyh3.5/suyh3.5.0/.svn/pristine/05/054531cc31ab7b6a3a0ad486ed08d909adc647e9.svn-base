package cn.hotol.app.service.three.location;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.dict.TsHtDictAddressDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */
public interface LocationService {

    /**
     * @return RetInfo
     * @Purpose 查询省市区
     * @version 3.0
     * @author lubin
     */
    public RetInfo findAllProvincialCity();

    /**
     * @Purpose   查询所有城市按照城市首字母返回
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @return    RetInfo
     */
    public RetInfo findAllCityByInitials();

    /**
     * @Purpose   通过字典名称查询字典
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @param     tsHtDictAddressDto
     * @return    RetInfo
     */
    public RetInfo findDictByName(TsHtDictAddressDto tsHtDictAddressDto);

    /**
     * @Purpose   通过经纬度查询省市区
     * @version   3.0
     * @author    lubin
     * @time      2017-04-18
     * @param     tdHtMembAddressDto
     * @return    RetInfo
     */
    public RetInfo findDictByLatLng(TdHtMembAddressDto tdHtMembAddressDto);

    /**
     * @Purpose   查询区域内的划分区域
     * @version   3.0
     * @author    lubin
     * @time      2017-04-18
     * @param     tsHtDictDto
     * @return    RetInfo
     */
    public RetInfo findDivideByRegion(TsHtDictDto tsHtDictDto);

}
