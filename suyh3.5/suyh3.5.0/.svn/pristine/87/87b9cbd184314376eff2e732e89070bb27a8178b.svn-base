package cn.hotol.app.service.goods;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.goods.ScoreGoodsDto;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import cn.hotol.app.bean.dto.location.*;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TsHtDictRepository;
import cn.hotol.app.repository.TsHtScoreGoodsRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-17.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static Logger logger = Logger.getLogger(GoodsServiceImpl.class);

    @Resource
    private TsHtScoreGoodsRepository tsHtScoreGoodsRepository;
    @Resource
    private TsHtDictRepository tsHtDictRepository;

    /**
     * @return List<TsHtScoreGoodsDto>
     * @Purpose 查询积分商品列表
     * @version 3.0
     * @author lubin
     */
    @Override
    public List<TsHtScoreGoodsDto> findScoreGoods() {
        String logInfo = this.getClass().getName() + ":findScoreGoods:";
        logger.info("======" + logInfo + "begin======");
        List<TsHtScoreGoodsDto> scoreGoodsDtoList = new ArrayList<TsHtScoreGoodsDto>();
        try {
            scoreGoodsDtoList = tsHtScoreGoodsRepository.findScoreGoods();
            for (int i = 0; i < scoreGoodsDtoList.size(); i++) {
                TsHtScoreGoodsDto tsHtScoreGoodsDto = scoreGoodsDtoList.get(i);
                if (tsHtScoreGoodsDto.getRegion_id() > 0) {
                    TsHtDictList areaTsHtDict = tsHtDictRepository.findDictById(tsHtScoreGoodsDto.getRegion_id());
                    TsHtDictList cityTsHtDict = tsHtDictRepository.findDictById(areaTsHtDict.getParent_id());
                    TsHtDictList proTsHtDict = tsHtDictRepository.findDictById(cityTsHtDict.getParent_id());
                    tsHtScoreGoodsDto.setAddress(proTsHtDict.getCode_name() + cityTsHtDict.getCode_name() + areaTsHtDict.getCode_name());
                }
                if (tsHtScoreGoodsDto.getExpress_id() > 0) {
                    TsHtDictList expTsHtDict = tsHtDictRepository.findDictById(tsHtScoreGoodsDto.getExpress_id());
                    tsHtScoreGoodsDto.setExpress_name(expTsHtDict.getCode_name());
                }
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return scoreGoodsDtoList;
    }

    /**
     * @param currentPage,pageSize
     * @return RetInfo
     * @Purpose 按条件分页查询积分商品
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findGoodsPage(int currentPage, int pageSize, int goods_state) {
        String logInfo = this.getClass().getName() + ":findGoodsPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("goods_state", goods_state);

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);
            params.put("express_company", openRegionExpCompany);
            List<ProvinceDto> provinceDtoList = tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtoList);
            List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDtoList.get(0).getDict_id());
            params.put("city", cityDtoList);
            List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDtoList.get(0).getDict_id());
            params.put("area", areaDtoList);

            int totalRecord = tsHtScoreGoodsRepository.findScoreGoodsSize(goods_state);
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.putAll(params);
                List<ScoreGoodsDto> tsHtScoreGoodsDtoList = tsHtScoreGoodsRepository.findScoreGoodsPage(map);
                for(int i=0;i<tsHtScoreGoodsDtoList.size();i++){
                    ScoreGoodsDto scoreGoodsDto=tsHtScoreGoodsDtoList.get(i);
                    if(scoreGoodsDto.getRegion_id()>0){
                        TsHtDictList areaTsHtDict = tsHtDictRepository.findDictById(scoreGoodsDto.getRegion_id());
                        TsHtDictList cityTsHtDict = tsHtDictRepository.findDictById(areaTsHtDict.getParent_id());
                        TsHtDictList provinceTsHtDict = tsHtDictRepository.findDictById(cityTsHtDict.getParent_id());
                        String address=provinceTsHtDict.getCode_name()+cityTsHtDict.getCode_name()+areaTsHtDict.getCode_name();
                        scoreGoodsDto.setAddress("仅限"+address+"使用");
                    }else {
                        scoreGoodsDto.setAddress("不限制");
                    }
                    if(scoreGoodsDto.getExpress_id()>0){
                        scoreGoodsDto.setExpress_company("仅限"+dicts.get("" + scoreGoodsDto.getExpress_id()).getCode_name()+"使用");
                    }else {
                        scoreGoodsDto.setExpress_company("不限制");
                    }
                }
                map.put("currentPage", currentPage);
                map.put("goods", tsHtScoreGoodsDtoList);

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param scoreGoodsDto
     * @return
     * @Purpose 新增积分商品
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo insertScoreGoods(ScoreGoodsDto scoreGoodsDto) {
        String logInfo = this.getClass().getName() + ":insertScoreGoods:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            scoreGoodsDto.setGoods_state(0);
            tsHtScoreGoodsRepository.insertScoreGoods(scoreGoodsDto);
            retInfo.setMark("0");
            retInfo.setTip("成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param scoreGoodsDto
     * @return
     * @Purpose 修改积分商品
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo updateScoreGoods(ScoreGoodsDto scoreGoodsDto) {
        String logInfo = this.getClass().getName() + ":updateScoreGoods:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtScoreGoodsRepository.updateScoreGoods(scoreGoodsDto);
            retInfo.setMark("0");
            retInfo.setTip("成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  上下架积分商品
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return
     */
    @Override
    public RetInfo updateScoreGoodsState(ScoreGoodsDto scoreGoodsDto) {
        String logInfo = this.getClass().getName() + ":updateScoreGoodsState:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            tsHtScoreGoodsRepository.updateGoodsState(scoreGoodsDto);
            retInfo.setMark("0");
            retInfo.setTip("修改成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  积分商品页面跳转
     * @version  3.0
     * @author   lubin
     * @param goods_id
     * @return
     */
    @Override
    public RetInfo jump(int goods_id) {
        String logInfo = this.getClass().getName() + ":updateScoreGoodsState:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);
            params.put("express_company", openRegionExpCompany);
            List<ProvinceDto> provinceDtoList = tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtoList);
            if (goods_id > 0) {
                ScoreGoodsDto scoreGoodsDto=tsHtScoreGoodsRepository.findScoreGoodsById(goods_id);
                if(scoreGoodsDto!=null){
                    if(scoreGoodsDto.getRegion_id()>0){
                        TsHtDictList areaTsHtDict = tsHtDictRepository.findDictById(scoreGoodsDto.getRegion_id());
                        List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(areaTsHtDict.getParent_id());
                        TsHtDictList cityTsHtDict = tsHtDictRepository.findDictById(areaTsHtDict.getParent_id());
                        List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(cityTsHtDict.getParent_id());
                        params.put("region_id", scoreGoodsDto.getRegion_id());
                        params.put("area", areaDtoList);
                        params.put("city_id", areaTsHtDict.getParent_id());
                        params.put("city", cityDtoList);
                        params.put("province_id", cityTsHtDict.getParent_id());
                    }else {
                        List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDtoList.get(0).getDict_id());
                        params.put("city", cityDtoList);
                        List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDtoList.get(0).getDict_id());
                        params.put("area", areaDtoList);
                    }
                    params.put("goods",scoreGoodsDto);

                    retInfo.setMark("0");
                    retInfo.setObj(params);
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("商品不存在.");
                }
            } else {
                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDtoList.get(0).getDict_id());
                params.put("city", cityDtoList);
                List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDtoList.get(0).getDict_id());
                params.put("area", areaDtoList);

                retInfo.setMark("0");
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  搜索积分商品页面跳转
     * @version  3.0
     * @author   lubin
     * @param scoreGoodsDto
     * @return
     */
    @Override
    public RetInfo jumpSearch(ScoreGoodsDto scoreGoodsDto) {
        String logInfo = this.getClass().getName() + ":jumpSerch:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("goods_state", scoreGoodsDto.getGoods_state());
            params.put("goods_type", scoreGoodsDto.getGoods_type());
            params.put("region_id", scoreGoodsDto.getRegion_id());
            params.put("express_id", scoreGoodsDto.getExpress_id());

            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            List<TsHtDictDto> openRegionExpCompany = (List<TsHtDictDto>) MemcachedUtils.get(MemcachedKey.OPEN_REGION_EXP_COMPANY);
            params.put("express_company", openRegionExpCompany);
            List<ProvinceDto> provinceDtoList = tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtoList);
            if (scoreGoodsDto.getRegion_id() > 0) {
                TsHtDictList areaTsHtDict = tsHtDictRepository.findDictById(scoreGoodsDto.getRegion_id());
                List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(areaTsHtDict.getParent_id());
                TsHtDictList cityTsHtDict = tsHtDictRepository.findDictById(areaTsHtDict.getParent_id());
                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(cityTsHtDict.getParent_id());
                params.put("area", areaDtoList);
                params.put("city_id", areaTsHtDict.getParent_id());
                params.put("city", cityDtoList);
                params.put("province_id", cityTsHtDict.getParent_id());
            } else {
                List<CityDto> cityDtoList = tsHtDictRepository.findCityByProvince(provinceDtoList.get(0).getDict_id());
                params.put("city", cityDtoList);
                List<AreaDto> areaDtoList = tsHtDictRepository.findAreaByCity(cityDtoList.get(0).getDict_id());
                params.put("area", areaDtoList);
                params.put("city_id", cityDtoList.get(0).getDict_id());
                params.put("province_id", provinceDtoList.get(0).getDict_id());
            }

            int totalRecord = tsHtScoreGoodsRepository.searchScoreGoodsSize(params);
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, scoreGoodsDto.getCurrent_page(), scoreGoodsDto.getPage_size());
                map.putAll(params);
                List<ScoreGoodsDto> tsHtScoreGoodsDtoList = tsHtScoreGoodsRepository.searchScoreGoodsPage(map);
                for(int i=0;i<tsHtScoreGoodsDtoList.size();i++){
                    ScoreGoodsDto goodsDto=tsHtScoreGoodsDtoList.get(i);
                    if(goodsDto.getRegion_id()>0){
                        TsHtDictList areaTsHtDict = tsHtDictRepository.findDictById(goodsDto.getRegion_id());
                        TsHtDictList cityTsHtDict = tsHtDictRepository.findDictById(areaTsHtDict.getParent_id());
                        TsHtDictList provinceTsHtDict = tsHtDictRepository.findDictById(cityTsHtDict.getParent_id());
                        String address=provinceTsHtDict.getCode_name()+cityTsHtDict.getCode_name()+areaTsHtDict.getCode_name();
                        goodsDto.setAddress("仅限"+address+"使用");
                    }else {
                        goodsDto.setAddress("不限制");
                    }
                    if(goodsDto.getExpress_id()>0){
                        goodsDto.setExpress_company("仅限"+dicts.get("" + goodsDto.getExpress_id()).getCode_name()+"使用");
                    }else {
                        goodsDto.setExpress_company("不限制");
                    }
                }
                map.put("currentPage", scoreGoodsDto.getCurrent_page());
                map.put("goods", tsHtScoreGoodsDtoList);

                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTsHtScoreGoodsRepository(TsHtScoreGoodsRepository tsHtScoreGoodsRepository) {
        this.tsHtScoreGoodsRepository = tsHtScoreGoodsRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }
}
