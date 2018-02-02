package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.score.TdHtMembScoreGoodsDto;
import org.springframework.stereotype.Repository;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
@Repository
public interface TdHtMembScoreGoodsRepository {

    /**
     * @param tdHtMembScoreGoodsDto
     * @return Integer
     * @Purpose 查询用户兑换积分商品的数量
     * @version 3.0
     * @author lubin
     */
    public Integer findMembExchangeCount(TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto);

    /**
     * @param tdHtMembScoreGoodsDto
     * @Purpose 插入用户兑换积分商品记录
     * @version 3.0
     * @author lubin
     */
    public void insertMembExchangeRecord(TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto);

    /**
     * @param memb_goods_id
     * @return TdHtMembScoreGoodsDto
     * @Purpose 通过id查询用户兑换积分商品记录
     * @version 3.0
     * @author lubin
     */
    public TdHtMembScoreGoodsDto findMembScoreGoodsById(int memb_goods_id);

    /**
     * @param tdHtMembScoreGoodsDto
     * @Purpose 修改用户兑换积分商品记录状态
     * @version 3.0
     * @author lubin
     */
    public void updateMembGoodsState(TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto);

}
