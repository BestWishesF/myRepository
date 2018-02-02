package cn.hotol.app.common.init;

import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import cn.hotol.app.common.SpringInfo;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.goods.GoodsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
public class GoodsInit {

    private static GoodsService goodsService = (GoodsService) SpringInfo.getBean("goodsService");
    private static Log logger = LogFactory.getLog(BannerInit.class);

    public static void getScoreGoods() {
        List<TsHtScoreGoodsDto> scoreGoodsDtoList=goodsService.findScoreGoods();
        Map<String, TsHtScoreGoodsDto> scoreGoods = new HashMap<String, TsHtScoreGoodsDto>();
        for(int i=0;i<scoreGoodsDtoList.size();i++){
            TsHtScoreGoodsDto tsHtScoreGoodsDto=scoreGoodsDtoList.get(i);
            scoreGoods.put(String.valueOf(tsHtScoreGoodsDto.getGoods_id()),tsHtScoreGoodsDto);
        }
        MemcachedUtils.add(MemcachedKey.SCORE_GOODS_LIST, scoreGoodsDtoList, new Date(0));
        MemcachedUtils.add(MemcachedKey.SCORE_GOODS_MAP, scoreGoods, new Date(0));
    }

    /**
     * 清缓存，重新加载缓存
     */
    public synchronized static void flush() {
        logger.info("********************start flush**************");
        MemcachedUtils.delete(MemcachedKey.SCORE_GOODS_LIST);
        MemcachedUtils.delete(MemcachedKey.SCORE_GOODS_MAP);
        getScoreGoods();
        logger.info("********************end flush**************");
    }

}
