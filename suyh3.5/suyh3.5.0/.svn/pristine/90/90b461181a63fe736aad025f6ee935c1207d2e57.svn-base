package cn.hotol.app.service.goods;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.goods.ScoreGoodsDto;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;

import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
public interface GoodsService {

    /**
     * @Purpose  查询积分商品列表
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtScoreGoodsDto>
     */
    public List<TsHtScoreGoodsDto> findScoreGoods();

    /**
     * @Purpose  按条件分页查询积分商品
     * @version  3.0
     * @author   lubin
     * @param currentPage,pageSize
     * @return   RetInfo
     */
    public RetInfo findGoodsPage(int currentPage, int pageSize, int goods_state);

    /**
     * @Purpose  新增积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return
     */
    public RetInfo insertScoreGoods(ScoreGoodsDto scoreGoodsDto);

    /**
     * @Purpose  修改积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return
     */
    public RetInfo updateScoreGoods(ScoreGoodsDto scoreGoodsDto);

    /**
     * @Purpose  上下架积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return
     */
    public RetInfo updateScoreGoodsState(ScoreGoodsDto scoreGoodsDto);

    /**
     * @Purpose  积分商品页面跳转
     * @version  3.0
     * @author   lubin
     * @param goods_id
     * @return
     */
    public RetInfo jump(int goods_id);

    /**
     * @Purpose  搜索积分商品页面跳转
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return
     */
    public RetInfo jumpSearch(ScoreGoodsDto scoreGoodsDto);

}
