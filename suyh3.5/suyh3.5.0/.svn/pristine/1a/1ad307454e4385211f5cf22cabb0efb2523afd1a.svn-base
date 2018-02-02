package cn.hotol.app.controller.three.invite;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.service.three.invite.MemberInviteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2016-12-22.
 */

@Controller
public class MemberInviteController {

    @Resource
    private MemberInviteService memberInviteService;

    /**
     * @Purpose  查询用户邀请码
     * @version  3.0
     * @author   lubin
     * @return   RetInfo
     */
    @RequestMapping(value = "/app/3/token/findMembInviteCode", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMembInviteCode(@RequestHeader(value = "token", required = true) String token) {
        return memberInviteService.findMembInviteCode(token);
    }

}
