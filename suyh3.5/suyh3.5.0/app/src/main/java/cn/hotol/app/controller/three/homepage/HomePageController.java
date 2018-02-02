package cn.hotol.app.controller.three.homepage;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.homepage.HomePageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class HomePageController {

    @Resource
    private HomePageService homePageService;

    /**
     * @return String
     * @Purpose 首页加载
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/homePage", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo homePage(@RequestHeader(value = "token", required = true) String token) {
        TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
        return homePageService.homePage(tdHtMemberDto);
    }
}
