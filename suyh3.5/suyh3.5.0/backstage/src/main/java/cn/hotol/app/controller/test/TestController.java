package cn.hotol.app.controller.test;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.service.test.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2017-01-17.
 */
@Controller
public class TestController {

    @Resource
    private TestService testService;


    /*@RequestMapping(value="/testAPI")
    @ResponseBody
    public RetInfo test(){
        RetInfo retInfo =testService.findUser();
        return retInfo;
    }

    @RequestMapping(value="/testAgentAPI")
    @ResponseBody
    public RetInfo testAgentAPI(){
        RetInfo retInfo =testService.findAgent();
        return retInfo;
    }

    @RequestMapping(value="/testAgentNo")
    @ResponseBody
    public RetInfo testAgentNo(){
        RetInfo retInfo =testService.findTest();
        return retInfo;
    }*/

    @RequestMapping(value="/clearMemberCacheAPI")
    @ResponseBody
    public RetInfo clearMemberCache(){
        RetInfo retInfo =testService.clearMemberCache();
        return retInfo;
    }
}
