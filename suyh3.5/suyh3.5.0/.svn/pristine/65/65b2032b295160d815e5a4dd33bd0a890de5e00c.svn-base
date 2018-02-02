package cn.hotol.app.controller.three.appupdate;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.service.three.appupdate.AppUpdateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhun
 * Date 2017-03-09.
 */

@Controller
public class AppUpdateController {

    @Resource
    private AppUpdateService appUpdateService;

    /**
     * @Purpose  用户根据APP类别和系统类别查找最新版本
     * @version  3.2
     * @author   lizhun
     * @param
     * @return   RetInfo
     */
    @RequestMapping(value = "/app/5/token/update", method = {RequestMethod.GET,RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo token( @RequestHeader(value = "client_type", required = true) Integer client_type) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_system_type", client_type);
        map.put("app_type", 1);
        return appUpdateService.findAppUpdateByType(map);
    }
    /**
     * @Purpose  代理人根据APP类别和系统类别查找最新版本
     * @version  3.2
     * @author   lizhun
     * @param
     * @return   RetInfo
     */
    @RequestMapping(value = "/app/5/agenttoken/update", method = {RequestMethod.GET,RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo agenttoken( @RequestHeader(value = "client_type", required = true) Integer client_type) {
        Map<String, Object> map = new HashMap<>();
        map.put("app_system_type", client_type);
        map.put("app_type", 2);
        return appUpdateService.findAppUpdateByType(map);
    }
}
