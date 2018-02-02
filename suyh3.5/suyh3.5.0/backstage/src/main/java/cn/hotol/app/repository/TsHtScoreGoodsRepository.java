package cn.hotol.app.repository;

import cn.hotol.app.bean.dto.goods.ScoreGoodsDto;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */

@Repository
public interface TsHtScoreGoodsRepository {

    /**
     * @Purpose  查询积分商品列表
     * @version  3.0
     * @author   lubin
     * @return   List<TsHtScoreGoodsDto>
     */
    public List<TsHtScoreGoodsDto> findScoreGoods();

    /**
     * @Purpose  查询积分商品数量
     * @version  3.0
     * @author   lubin
     * @param     goods_state
     * @return   Integer
     */
    public Integer findScoreGoodsSize(int goods_state);

    /**
     * @Purpose  分页查询积分商品
     * @version  3.0
     * @author   lubin
     * @param     map
     * @return   List<TsHtScoreGoodsDto>
     */
    public List<ScoreGoodsDto> findScoreGoodsPage(Map<String, Object> map);

    /**
     * @Purpose  修改积分商品状态
     * @version  3.0
     * @author   lubin
     * @param     scoreGoodsDto
     * @return
     */
    public void updateGoodsState(ScoreGoodsDto scoreGoodsDto);

    /**
     * @Purpose  通过id查询积分商品信息
     * @version  3.0
     * @author   lubin
     * @param  goods_id
     * @return ScoreGoodsDto
     */
    public ScoreGoodsDto findScoreGoodsById(int goods_id);

    /**
     * @Purpose  添加新的积分商品
     * @version  3.0
     * @author   lubin
     * @param     scoreGoodsDto
     * @return
     */
    public void insertScoreGoods(ScoreGoodsDto scoreGoodsDto);

    /**
     * @Purpose  修改积分商品信息
     * @version  3.0
     * @author   lubin
     * @param     scoreGoodsDto
     * @return
     */
    public void updateScoreGoods(ScoreGoodsDto scoreGoodsDto);

    /**
     * @Purpose  查询积分商品数量
     * @version  3.0
     * @author   lubin
     * @param     map
     * @return   Integer
     */
    public Integer searchScoreGoodsSize(Map<String, Object> map);

    /**
     * @Purpose  分页查询积分商品
     * @version  3.0
     * @author   lubin
     * @param     map
     * @return   List<TsHtScoreGoodsDto>
     */
    public List<ScoreGoodsDto> searchScoreGoodsPage(Map<String, Object> map);

}
