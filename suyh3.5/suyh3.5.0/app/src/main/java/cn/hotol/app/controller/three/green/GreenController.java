package cn.hotol.app.controller.three.green;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;
import cn.hotol.app.service.three.green.GreenService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2017-03-07.
 */

@Controller
public class GreenController {

    @Resource
    private GreenService greenService;

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的参与活动记录
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findGreenMailByMemb", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findGreenMailByMemb(@RequestHeader(value = "token", required = true) String token) {
        return greenService.findGreenMailByMemb(token);
    }

    /**
     * @param tdHtGreenMailDto
     * @return RetInfo
     * @Purpose 保存用户接收礼品信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/saveGreenAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo saveGreenAddress(@Valid @RequestBody TdHtGreenMailDto tdHtGreenMailDto, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return greenService.saveGreenAddress(tdHtGreenMailDto);
        }
        return retInfo;
    }

}
