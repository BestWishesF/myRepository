package cn.hotol.app.service.three.score;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import cn.hotol.app.bean.dto.page.PageDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by liyafei on 2016/12/12.
 */
public interface ScoreService {

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询积分详情
     * @version 3.0
     * @author liyafei
     */
    public RetInfo findMembScore(String token, PageDto pageDto);

    /**
     * @return RetInfo
     * @Purpose 查询积分任务详情
     * @version 3.0
     * @author liyafei
     */
    public RetInfo findScoreTask(String token);

    /**
     * @return RetInfo
     * @Purpose 查询只使用积分兑换的前几个物品
     * @version 3.0
     * @author liyafei
     */
    public RetInfo findScoreGoods(String token, Integer version);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的积分、签到等信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo findScoreInfo(String token);

    /**
     * @param token
     * @return RetInfo
     * @Purpose 用户签到
     * @version 3.0
     * @author lubin
     */
    public RetInfo memberSign(String token);

    /**
     * @param token
     * @param tsHtScoreGoodsDto
     * @return RetInfo
     * @Purpose 用户兑换积分商品
     * @version 3.0
     * @author lubin
     */
    public RetInfo exchangeGoods(TsHtScoreGoodsDto tsHtScoreGoodsDto, String token);

    /**
     * @param goodsId
     * @return RetInfo
     * @Purpose 通过积分商品id查询商品信息
     * @version 3.0
     * @author lubin
     */
    public RetInfo findScoreGoodsById(int goodsId, String token);

    /**
     * @param token
     * @param params
     * @return RetInfo
     * @Purpose 新的用户兑换积分商品
     * @version 3.0
     * @author lubin
     */
    public RetInfo newExchangeGoods(Map<String, Integer> params, String token, HttpServletRequest request);

    /**
     * @return RetInfo
     * @Purpose 查询积分兑换物品列表
     * @version 3.0
     * @author liyafei
     */
    public RetInfo findScoreGoodList(String token);

}
