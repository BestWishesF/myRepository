package cn.hotol.app.controller.three.dictionary;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TsHtDict;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.service.three.dictionary.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2016-12-02.
 */

@Controller
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    /**
     * @return RetInfo
     * @Purpose 根据字典类别查询字典数据
     * @version 3.0
     * @author lubin
     * @Param tsHtDict
     */
    @RequestMapping(value = "/app/3/token/findDictByType", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findDictByType(@RequestBody TsHtDict tsHtDict) {
        return dictionaryService.findDictByType(tsHtDict.getCode_type());
    }

    /**
     * @return RetInfo
     * @Purpose 获取开通快递公司数据
     * @version 3.0
     * @author lubin
     * @Param courierDto
     */
    @RequestMapping(value = "/app/3/token/findExpressCompany", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExpressCompany(@RequestBody CourierDto courierDto) {
        return dictionaryService.findExpressCompany(courierDto);
    }

    /**
     * @return RetInfo
     * @Purpose 获取上门时间数据
     * @version 3.0
     * @author lubin
     * @Param code_type
     */
    @RequestMapping(value = "/app/3/token/findCollectTime", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findCollectTime(@RequestBody TsHtDict tsHtDict, @RequestHeader(value = "version", required = true) Integer version) {
        return dictionaryService.findCollectTime(tsHtDict.getCode_type(), version);
    }

}
