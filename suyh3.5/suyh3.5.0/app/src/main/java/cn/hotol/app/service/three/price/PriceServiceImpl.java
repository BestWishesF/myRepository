package cn.hotol.app.service.three.price;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.price.MembCalculationPriceDto;
import cn.hotol.app.bean.dto.price.TsHtRegionPriceDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-30.
 */
@Service
public class PriceServiceImpl implements PriceService {

    private static Logger logger = Logger.getLogger(PriceServiceImpl.class);

    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;

    /**
     * @param tdHtExpressOrderCollectDto
     * @return RetInfo
     * @Purpose 计算价格
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo calculationPrice(TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        String logInfo = this.getClass().getName() + ":calculationPrice:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findSendProvince(tdHtExpressOrderCollectDto.getExp_ord_id());
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtExpressOrderDto.getMemb_id());
            BigDecimal weight = new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height()).setScale(0, BigDecimal.ROUND_UP);
            Map<String, List<TsHtRegionPriceDto>> priceMap = (Map<String, List<TsHtRegionPriceDto>>) MemcachedUtils.get(MemcachedKey.EXP_REGION_PRICE);
            List<TsHtRegionPriceDto> priceDtoList = priceMap.get(String.valueOf(tdHtExpressOrderCollectDto.getExpress_id()));

            BigDecimal price = new BigDecimal(0);
            for (int i = 0; i < priceDtoList.size(); i++) {
                TsHtRegionPriceDto priceDto = priceDtoList.get(i);
                if (priceDto.getSend_province_id() == tdHtExpressOrderDto.getAdd_province() && priceDto.getCollect_province_id() == tdHtExpressOrderCollectDto.getAdd_province()) {
                    if (tdHtExpressOrderCollectDto.getExpress_id() == Constant.DBL) {
                        if (weight.intValue() > 2 && priceDto.getFirst_weight() == 3) {
                            price = priceDto.getFirst_amount();
                            if (weight.intValue() > priceDto.getFirst_weight()) {
                                int addWeight = weight.intValue() - priceDto.getFirst_weight();
                                int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                                if (addWeight % priceDto.getAdd_weight() > 0) {
                                    addWeightMultiple = addWeightMultiple + 1;
                                }
                                BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                                price = price.add(addPrice);
                            }
                        } else if (weight.intValue() < 3 && priceDto.getFirst_weight() == 1) {
                            price = priceDto.getFirst_amount();
                            if (weight.intValue() > priceDto.getFirst_weight()) {
                                int addWeight = weight.intValue() - priceDto.getFirst_weight();
                                int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                                if (addWeight % priceDto.getAdd_weight() > 0) {
                                    addWeightMultiple = addWeightMultiple + 1;
                                }
                                BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                                price = price.add(addPrice);
                            }
                        }
                    } else {
                        price = priceDto.getFirst_amount();
                        if (weight.intValue() > priceDto.getFirst_weight()) {
                            int addWeight = weight.intValue() - priceDto.getFirst_weight();
                            int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                            if (addWeight % priceDto.getAdd_weight() > 0) {
                                addWeightMultiple = addWeightMultiple + 1;
                            }
                            BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                            price = price.add(addPrice);
                        }
                    }
                }
            }

            if (member.getMemb_role() == 1) {
                if (member.getMemb_discount().doubleValue() < 1) {
                    price = price.multiply(member.getMemb_discount());
                }
            } else if (member.getMemb_role() == 2) {
                price = price.multiply(member.getMemb_discount());
            }

            retInfo.setMark("0");
            retInfo.setTip("价格计算成功.");
            retInfo.setObj(price.setScale(2, BigDecimal.ROUND_HALF_UP));
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param membCalculationPriceDto
     * @return RetInfo
     * @Purpose 估算用户价格
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo membCalculationPrice(MembCalculationPriceDto membCalculationPriceDto, String token) {
        String logInfo = this.getClass().getName() + ":membCalculationPrice:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //缓存中获取用户
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());

            Map<String, List<TsHtRegionPriceDto>> priceMap = (Map<String, List<TsHtRegionPriceDto>>) MemcachedUtils.get(MemcachedKey.EXP_REGION_PRICE);
            List<TsHtRegionPriceDto> priceDtoList = priceMap.get(String.valueOf(membCalculationPriceDto.getExpress_id()));
            List<TdHtExpressOrderCollectDto> collectDtoList = membCalculationPriceDto.getCollects();
            if (collectDtoList.size() > 0) {
                BigDecimal total_price = new BigDecimal(membCalculationPriceDto.getExp_ord_gratuity());
                BigDecimal discount_price = new BigDecimal(0);
                if (member.getMemb_role() == 1) {
                    if (member.getMemb_discount().doubleValue() < 1) {
                        discount_price = discount_price.add(new BigDecimal(membCalculationPriceDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()));
                    } else {
                        discount_price = total_price;
                    }
                } else if (member.getMemb_role() == 2) {
                    discount_price = discount_price.add(new BigDecimal(membCalculationPriceDto.getExp_ord_gratuity()).multiply(member.getMemb_discount()));
                }
                for (int i = 0; i < priceDtoList.size(); i++) {
                    TsHtRegionPriceDto priceDto = priceDtoList.get(i);
                    for (int j = 0; j < collectDtoList.size(); j++) {
                        TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto = collectDtoList.get(j);
                        BigDecimal price = new BigDecimal(0);
                        BigDecimal weight = new BigDecimal(tdHtExpressOrderCollectDto.getExp_ord_clt_height()).setScale(0, BigDecimal.ROUND_UP);
                        if (priceDto.getSend_province_id() == membCalculationPriceDto.getSend_province_id() && priceDto.getCollect_province_id() == tdHtExpressOrderCollectDto.getAdd_province()) {
                            if (membCalculationPriceDto.getExpress_id() == Constant.DBL) {
                                if (weight.intValue() > 2 && priceDto.getFirst_weight() == 3) {
                                    price = priceDto.getFirst_amount();
                                    if (weight.intValue() > priceDto.getFirst_weight()) {
                                        int addWeight = weight.intValue() - priceDto.getFirst_weight();
                                        int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                                        if (addWeight % priceDto.getAdd_weight() > 0) {
                                            addWeightMultiple = addWeightMultiple + 1;
                                        }
                                        BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                                        price = price.add(addPrice);
                                    }
                                } else if (weight.intValue() < 3 && priceDto.getFirst_weight() == 1) {
                                    price = priceDto.getFirst_amount();
                                    if (weight.intValue() > priceDto.getFirst_weight()) {
                                        int addWeight = weight.intValue() - priceDto.getFirst_weight();
                                        int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                                        if (addWeight % priceDto.getAdd_weight() > 0) {
                                            addWeightMultiple = addWeightMultiple + 1;
                                        }
                                        BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                                        price = price.add(addPrice);
                                    }
                                }
                            } else {
                                price = priceDto.getFirst_amount();
                                if (weight.intValue() > priceDto.getFirst_weight()) {
                                    int addWeight = weight.intValue() - priceDto.getFirst_weight();
                                    int addWeightMultiple = addWeight / priceDto.getAdd_weight();
                                    if (addWeight % priceDto.getAdd_weight() > 0) {
                                        addWeightMultiple = addWeightMultiple + 1;
                                    }
                                    BigDecimal addPrice = priceDto.getAdd_amount().multiply(new BigDecimal(addWeightMultiple));
                                    price = price.add(addPrice);
                                }
                            }

                            total_price = total_price.add(price);
                            if (member.getMemb_role() == 1) {
                                if (member.getMemb_discount().doubleValue() < 1) {
                                    discount_price = discount_price.add(price.multiply(member.getMemb_discount()));
                                } else {
                                    discount_price = total_price;
                                }
                            } else if (member.getMemb_role() == 2) {
                                discount_price = discount_price.add(price.multiply(member.getMemb_discount()));
                            }
                        }
                    }
                }

                Map<String, Object> map = new HashMap();
                map.put("total_price", total_price.setScale(2, BigDecimal.ROUND_HALF_UP));
                map.put("discount_price", discount_price.setScale(2, BigDecimal.ROUND_HALF_UP));

                retInfo.setMark("0");
                retInfo.setTip("价格计算成功.");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("没有快件信息.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
