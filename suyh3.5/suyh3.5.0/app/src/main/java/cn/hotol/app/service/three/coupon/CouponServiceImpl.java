package cn.hotol.app.service.three.coupon;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.dto.coupon.TdHtCouponDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.gem.GemDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.common.util.Page;
import cn.hotol.app.common.util.RandomPrizeUtil;
import cn.hotol.app.repository.TdHtCouponRepository;
import cn.hotol.app.repository.TdHtExpressOrderRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LuBin
 * Date 2016-12-13.
 */

@Service
public class CouponServiceImpl implements CouponService {

    private Logger logger = Logger.getLogger(CouponServiceImpl.class);

    @Resource
    private TdHtCouponRepository tdHtCouponRepository;
    @Resource
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @param pageDto
     * @param token
     * @Purpose 查询用户的优惠券
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findCouponByMember(PageDto pageDto, String token) {
        String logInfo = this.getClass().getName() + ":findCouponByMember:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);

            Page<TdHtCouponDto> couponPage = new Page<TdHtCouponDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(member.getMemb_id());
            Integer count = tdHtCouponRepository.findCouponByMebamount(pageDto);
            pageDto.setLimit_str(couponPage.getLimitCriterion());
            List<TdHtCouponDto> tdHtCouponDtoList = tdHtCouponRepository.findCouponByMeb(pageDto);
            Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
            for(int i=0;i<tdHtCouponDtoList.size();i++){
                TdHtCouponDto tdHtCouponDto=tdHtCouponDtoList.get(i);
                if(tdHtCouponDto.getExpress_id()>0){
                    tdHtCouponDto.setExpress_name(dicts.get(""+tdHtCouponDto.getExpress_id()).getCode_name());
                }
                if(tdHtCouponDto.getRegion_id()>0){
                    tdHtCouponDto.setRegion_name(dicts.get(""+tdHtCouponDto.getRegion_id()).getCode_name());
                }
            }
            couponPage.setTotalCount(count);

            Map<String, Object> coupon = new HashMap<String, Object>();
            coupon.put("items", tdHtCouponDtoList);
            coupon.put("total_pages", couponPage.getTotalPages());
            coupon.put("page_no", couponPage.getPageNo());

            retInfo.setMark("0");
            retInfo.setTip("优惠券查询成功.");
            retInfo.setObj(coupon);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param exp_ord_id
     * @param token
     * @Purpose 查询用户的该次订单支付可使用优惠券
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findCouponByCondition(int exp_ord_id, String token, int client_type) {
        String logInfo = this.getClass().getName() + ":findCouponByCondition:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtExpressOrderDto tdHtExpressOrderDto = tdHtExpressOrderRepository.findTdHtExpressOrderById(exp_ord_id);
            TdHtCoupon tdHtCoupon = new TdHtCoupon();
            tdHtCoupon.setMemb_id(member.getMemb_id());
            tdHtCoupon.setExpress_id(tdHtExpressOrderDto.getExpress_id());
            if (client_type == 3) {
                tdHtCoupon.setMemb_type(1);
            } else {
                tdHtCoupon.setMemb_type(2);
            }
            tdHtCoupon.setRegion_id(tdHtExpressOrderDto.getAdd_region());
            tdHtCoupon.setLimit_exp_ord_id(tdHtExpressOrderDto.getExp_ord_id());
            List<TdHtCouponDto> tdHtCouponDtoList = tdHtCouponRepository.findCouponByCondition(tdHtCoupon);
            for (int i = 0; i < tdHtCouponDtoList.size() ; i++){
                TdHtCouponDto tdHtCouponDto = tdHtCouponDtoList.get(i);

                if(tdHtCouponDto.getCou_limit_amount().doubleValue() > tdHtExpressOrderDto.getExp_ord_amount()){
                    tdHtCouponDtoList.remove(i);
                }
            }
            retInfo.setMark("0");
            retInfo.setTip("优惠券查询成功.");
            retInfo.setObj(tdHtCouponDtoList);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo receiveCoupon(String token) {
        String logInfo = this.getClass().getName() + ":receiveCoupon:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            String moneyStr = dataConfigMap.get(Constant.OLD_MEMB_MONEY).toString();
            String chanceStr = dataConfigMap.get(Constant.OLD_MEMB_CHANCE).toString();
            int day = Integer.valueOf(dataConfigMap.get(Constant.OLD_MEMB_TIME).toString());
            String name = dataConfigMap.get(Constant.OLD_MEMB_NAME).toString();
            int is_send = Integer.valueOf(dataConfigMap.get(Constant.OLD_MEMB_IS_SEND).toString());
            String start = dataConfigMap.get(Constant.OLD_MEMB_START_TIME).toString() + " 00:00:00";
            String end = dataConfigMap.get(Constant.OLD_MEMB_END_TIME).toString() + " 23:59:59";
            Date start_time = sdf.parse(start);
            Date end_time = sdf.parse(end);
            Long now = System.currentTimeMillis();
            //判断时间是否开始
            boolean flag = start_time.getTime() < now && end_time.getTime() > now;
            //判断是否需要判断已发过件
            if (flag && is_send == 1) {
                //判断用户是否已发过件
                TdHtExpressOrderDto orderDto = tdHtExpressOrderRepository.findExpressOrderIsEndByMembId(tdHtMemberDto.getMemb_id());
                if (orderDto == null) {
                    flag = false;
                }
            }
            //判断活动是否开始及符合条件
            if (flag){
                //判断是否已参与该活动
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("memb_id", tdHtMemberDto.getMemb_id());
                TdHtCouponDto couponDto = tdHtCouponRepository.findCouponByName(map);
                if (couponDto == null) {
                    //符合条件
                    List<GemDto> gemDtoList = new ArrayList<>();
                    int total = 0;
                    BigDecimal money = new BigDecimal(0);
                    if(moneyStr.indexOf("#") > 0 && chanceStr.indexOf("#") > 0){
                        String[] moneyStrs = moneyStr.split("#");
                        String[] chanceStrs = chanceStr.split("#");
                        for (int i = 0 ; i < moneyStrs.length ; i++){
                            GemDto gemDto = new GemDto(new BigDecimal(moneyStrs[i]), Integer.valueOf(chanceStrs[i]));
                            gemDtoList.add(gemDto);
                            total = total + Integer.valueOf(chanceStrs[i]);
                        }
                        money = gemDtoList.get(0).getName();
                    }else {
                        money = new BigDecimal(moneyStr);
                    }
                    //添加优惠券相关信息
                    TdHtCoupon tdHtCouponDto = new TdHtCoupon();

                    tdHtCouponDto.setCou_limit_amount(new BigDecimal(0));
                    tdHtCouponDto.setCou_limit_time(new Timestamp(System.currentTimeMillis() + day * 24 * 60 * 60 * 1000));
                    tdHtCouponDto.setCou_discount(new BigDecimal(1));
                    tdHtCouponDto.setCou_receive_time(new Timestamp(now));
                    tdHtCouponDto.setState(0);
                    tdHtCouponDto.setCou_user_time(new Timestamp(now));
                    tdHtCouponDto.setExp_ord_id(0);
                    tdHtCouponDto.setRegion_id(0);
                    tdHtCouponDto.setMemb_type(0);
                    tdHtCouponDto.setExpress_id(0);
                    tdHtCouponDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    if(total == 0){
                        tdHtCouponDto.setCou_amount(money);
                    }else {
                        tdHtCouponDto.setCou_amount(RandomPrizeUtil.getGumByList(total, gemDtoList));
                    }
                    tdHtCouponDto.setCou_name(name);
                    tdHtCouponRepository.insertMembCoupon(tdHtCouponDto);
                    retInfo.setMark("0");
                    retInfo.setTip("领取成功");
                    retInfo.setObj(tdHtCouponDto.getCou_amount());
                    System.out.println("4:" + retInfo.getMark());
                } else {
                    //已参与
                    retInfo.setMark("2");
                    retInfo.setTip("已参与");
                    System.out.println("3:" + retInfo.getMark());
                }
            } else {
                //不符合条件
                retInfo.setMark("1");
                retInfo.setTip("不符合条件");
                System.out.println("2:" + retInfo.getMark());
            }
            System.out.println("1:" + retInfo.getMark());
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo ineligible(String token) {
        String logInfo = this.getClass().getName() + ":receiveCoupon:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            String name = dataConfigMap.get(Constant.OLD_MEMB_NAME).toString();
            int is_send = Integer.valueOf(dataConfigMap.get(Constant.OLD_MEMB_IS_SEND).toString());
            String start = dataConfigMap.get(Constant.OLD_MEMB_START_TIME).toString() + " 00:00:00";
            String end = dataConfigMap.get(Constant.OLD_MEMB_END_TIME).toString() + " 23:59:59";
            Date start_time = sdf.parse(start);
            Date end_time = sdf.parse(end);
            Long now = System.currentTimeMillis();
            //判断时间是否开始
            boolean flag = start_time.getTime() < now && end_time.getTime() > now;
            //判断是否是折扣用户
            if (tdHtMemberDto.getMemb_discount().compareTo(new BigDecimal(1)) != 0) {
                flag = false;
            }
            //判断是否需要判断已发过件
            if (flag && is_send == 1) {
                //判断用户是否已发过件
                TdHtExpressOrderDto orderDto = tdHtExpressOrderRepository.findExpressOrderIsEndByMembId(tdHtMemberDto.getMemb_id());
                if (orderDto == null) {
                    flag = false;
                }
            }
            //判断活动是否开始及符合条件
            if (flag){
                //判断是否已参与该活动
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("memb_id", tdHtMemberDto.getMemb_id());
                TdHtCouponDto couponDto = tdHtCouponRepository.findCouponByName(map);
                if (couponDto == null) {
                    //符合条件
                    retInfo.setMark("0");
                    retInfo.setTip("符合条件");
                } else {
                    //已参与
                    retInfo.setMark("2");
                    retInfo.setTip("已参与");
                }
            } else {
                //不符合条件
                retInfo.setMark("1");
                retInfo.setTip("不符合条件");
            }

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
