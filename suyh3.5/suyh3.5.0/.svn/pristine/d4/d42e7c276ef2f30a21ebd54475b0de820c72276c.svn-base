package cn.hotol.app.controller.three.score;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.service.three.score.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/13.
 */
@Controller
public class ScoreController {
    @Resource
    private ScoreService scoreService;

    /**
     * @param pageDto
     * @Purpose 分页查询积分明细
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/findMembScore", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMembScore(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return scoreService.findMembScore(token, pageDto);
    }

    /**
     * @Purpose 查询积分任务
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/findScoreTask", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findScoreTask(@RequestHeader(value = "token", required = true) String token) {
        return scoreService.findScoreTask(token);
    }

    /**
     * @Purpose 查询只使用积分兑换的前几个物品
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/findScoreGoods", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findScoreGoods(@RequestHeader(value = "token", required = true) String token,
                                  @RequestHeader(value = "version", required = true) Integer version) {
        return scoreService.findScoreGoods(token, version);
    }

    /**
     * @Purpose 查询用户积分、签到等信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findScoreInfo", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findScoreInfo(@RequestHeader(value = "token", required = true) String token) {
        return scoreService.findScoreInfo(token);
    }

    /**
     * @Purpose 用户签到
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/memberSign", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo memberSign(@RequestHeader(value = "token", required = true) String token) {
        return scoreService.memberSign(token);
    }

    /**
     * @Purpose 用户兑换积分商品
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/exchangeGoods", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo exchangeGoods(@RequestBody TsHtScoreGoodsDto tsHtScoreGoodsDto, @RequestHeader(value = "token", required = true) String token) {
        return scoreService.exchangeGoods(tsHtScoreGoodsDto, token);
    }

    /**
     * @Purpose 通过积分商品id查询商品信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/findScoreGoodsById", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findScoreGoodsById(@RequestBody TsHtScoreGoodsDto tsHtScoreGoodsDto,
                                      @RequestHeader(value = "token", required = true) String token) {
        return scoreService.findScoreGoodsById(tsHtScoreGoodsDto.getGoods_id(), token);
    }

    /**
     * @Purpose 新的用户兑换积分商品
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/newExchangeGoods", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo newExchangeGoods(@RequestBody Map<String, Integer> params, HttpServletRequest request,
                                    @RequestHeader(value = "token", required = true) String token) {
        return scoreService.newExchangeGoods(params, token, request);
    }

    /**
     * @Purpose 查询积分兑换物品列表
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/6/token/findScoreGoodList", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findScoreGoodList(@RequestHeader(value = "token", required = true) String token) {
        return scoreService.findScoreGoodList(token);
    }
}
