package cn.hotol.app.controller.three.thirdparty;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.thirdlogin.BindingPhoneDto;
import cn.hotol.app.bean.dto.thirdparty.TsHtThirdDto;
import cn.hotol.app.common.util.Ip;
import cn.hotol.app.service.three.thirdparty.ThirdPartyService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-04.
 */
@Controller
public class ThirdPartyController {

    @Resource
    private ThirdPartyService thirdPartyService;

    /**
     * @param map
     * @return
     * @Purpose 微信登录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/obtainWXPersonal", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo obtainWXPersonal(@RequestBody Map<String, Object> map, HttpServletRequest request,
                                    @RequestHeader(value = "version", required = true) Integer version) {
        String code = (String) map.get("code");
        String third_id = (String) map.get("third_id");
        RetInfo retInfo = thirdPartyService.obtainWXPersonal(third_id, code, Ip.getIpAddr(request), version);
        return retInfo;
    }

    /**
     * @param tsHtThirdDto
     * @Purpose 查询微信公众号配置
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/findTsHtThirdDto", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findTsHtThirdDto(@RequestBody TsHtThirdDto tsHtThirdDto) {
        return thirdPartyService.findTsHtThirdDto(tsHtThirdDto);
    }

    /**
     * @param bindingPhoneDto
     * @return
     * @Purpose 绑定手机号
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/bindingPhone", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo bindingPhone(@Valid @RequestBody BindingPhoneDto bindingPhoneDto, BindingResult result,
                                @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            bindingPhoneDto.setApp_version(version);
            return thirdPartyService.bindingPhone(bindingPhoneDto);
        }
        return retInfo;
    }

    /**
     * @param param
     * @return
     * @Purpose 微信使用权限签名
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/obtainJsSdk", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo obtainJsSdk(@RequestBody Map<String, String> param) throws Exception {
        String appid=param.get("appid");
        String http_url=param.get("http_url");
        return thirdPartyService.obtainJsSdk(appid,http_url);
    }

}
