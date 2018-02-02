package cn.hotol.app.controller.green;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;
import cn.hotol.app.service.green.GreenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017-03-08.
 */

@Controller
public class GreenController {

    @Resource
    private GreenService greenService;

    /**
     * @return ModelAndView
     * @Purpose 快递订单列表
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/admin/member/green/list")
    public ModelAndView list(@RequestParam(value = "currentPage", required = true) Integer currentPage,
                             @RequestParam(value = "state", required = true) Integer state,
                             @RequestParam(value = "starTime", required = true) String starTime,
                             @RequestParam(value = "endTime", required = true) String endTime,
                             @RequestParam(value = "phone", required = true) String phone,
                             @RequestParam(value = "name", required = true) String name,
                             HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/member/green/index");
        mv.addObject("retInfo", greenService.findGreenMailPage(state,currentPage,10,starTime,endTime,phone,name));
        return mv;
    }

    /**
     * @Purpose 通过记录id修改信息
     * @version  3.0
     * @author   lubin
     * @param tdHtGreenMailDto
     * @return RetInfo
     */
    @RequestMapping(value = "/admin/member/green/update")
    @ResponseBody
    public RetInfo findExpOrd(@ModelAttribute TdHtGreenMailDto tdHtGreenMailDto) {
        RetInfo retInfo = greenService.updateGreenMailState(tdHtGreenMailDto);
        return retInfo;
    }

}
