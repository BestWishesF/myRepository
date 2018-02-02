package cn.hotol.app.controller.three.location;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.dict.TsHtDictAddressDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.service.three.location.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */

@Controller
public class LocationController {

    @Resource
    private LocationService locationService;

    /**
     * @return RetInfo
     * @Purpose 查询省市区
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findProvincialCity", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAllProvincialCity() {
        return locationService.findAllProvincialCity();
    }

    /**
     * @return RetInfo
     * @Purpose 查询省市区
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/findProCity", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findProCity() {
        return locationService.findAllProvincialCity();
    }

    /**
     * @return RetInfo
     * @Purpose 查询省市区
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/findAllProData", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAllProData() {
        return locationService.findAllProvincialCity();
    }

    /**
     * @Purpose   查询所有城市按照城市首字母返回
     * @version   3.0
     * @author    lubin
     * @time      2017-04-08
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/findAllCityByInitials", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAllCityByInitials() {
        return locationService.findAllCityByInitials();
    }

    /**
     * @Purpose   通过字典名称查询字典
     * @version   3.0
     * @author    lubin
     * @time      2017-04-10
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/findDictByName", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findDictByName(@RequestBody TsHtDictAddressDto tsHtDictAddressDto) {
        return locationService.findDictByName(tsHtDictAddressDto);
    }

    /**
     * @Purpose   通过经纬度查询省市区
     * @version   3.0
     * @author    lubin
     * @time      2017-04-10
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/token/findDictByLatLng", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findDictByLatLng(@RequestBody TdHtMembAddressDto tdHtMembAddressDto) {
        return locationService.findDictByLatLng(tdHtMembAddressDto);
    }

    /**
     * @Purpose   查询区域内的划分区域
     * @version   3.0
     * @author    lubin
     * @time      2017-04-10
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/agentToken/findDivideByRegion", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findDivideByRegion(@RequestBody TsHtDictDto tsHtDictDto) {
        return locationService.findDivideByRegion(tsHtDictDto);
    }

}
