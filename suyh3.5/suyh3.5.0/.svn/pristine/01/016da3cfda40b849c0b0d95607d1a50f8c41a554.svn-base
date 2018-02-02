package cn.hotol.app.controller.three.price;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.price.MembCalculationPriceDto;
import cn.hotol.app.service.three.price.PriceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2016-12-30.
 */
@Controller
public class PriceController {

    @Resource
    private PriceService priceService;

    /**
     * @param tdHtExpressOrderCollectDto
     * @return
     * @Purpose 计算订单价格
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/calculationPrice", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo calculationPrice(@RequestBody TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        return priceService.calculationPrice(tdHtExpressOrderCollectDto);
    }

    /**
     * @param membCalculationPriceDto
     * @return
     * @Purpose 估算用户价格
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/membCalculationPrice", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo membCalculationPrice(@RequestBody MembCalculationPriceDto membCalculationPriceDto, @RequestHeader(value = "token", required = true) String token) {
        return priceService.membCalculationPrice(membCalculationPriceDto, token);
    }

}
