package cn.hotol.app.service.member;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.coupon.TdHtCouponDto;
import cn.hotol.app.bean.dto.gem.GemDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.sendhis.TwSmsSendHisDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lizhun on 2016/12/17.
 */
public class MemberServiceImpl implements  MemberService {

    private static Logger logger = Logger.getLogger(MemberServiceImpl.class);
    private TdHtMemberRepository tdHtMemberRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;
    private TdHtCouponRepository tdHtCouponRepository;
    private TwSmsSendHisRepository twSmsSendHisRepository;

    @Override
    public RetInfo memberPage(int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":memberPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();

            params.put("province", tdHtAdminDto.getProvince_id());
            params.put("city", tdHtAdminDto.getCity_id());
            params.put("region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            int totalRecord = tdHtMemberRepository.findMemberSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtMemberDto> tdHtMemberDtos = tdHtMemberRepository.findMemberPage(map);
                map.put("currentPage", currentPage);
                map.put("members", tdHtMemberDtos);
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

    @Override
    public RetInfo lock(TdHtMemberDto tdHtMemberDto) {
        String logInfo = this.getClass().getName() + ":lock:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //更新用户状态
            tdHtMemberRepository.updateMemberLock(tdHtMemberDto);

            //如果是锁定用户清除用户登录缓存
            if (tdHtMemberDto.getMemb_islock() == 0) {
                MemcachedUtils.delete(tdHtMemberDto.getToken());
            }

            retInfo.setMark("0");
            retInfo.setTip("成功");

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo memberSearchPage(TdHtMemberDto tdHtMemberDto, int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":memberSearchPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("memb_id", tdHtMemberDto.getMemb_id());
            params.put("memb_phone", tdHtMemberDto.getMemb_phone());
            params.put("memb_nick_name", tdHtMemberDto.getMemb_nick_name());
            params.put("province", tdHtAdminDto.getProvince_id());
            params.put("city", tdHtAdminDto.getCity_id());
            params.put("region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            int totalRecord = tdHtMemberRepository.SearchMemberSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtMemberDto> tdHtMemberDtos = tdHtMemberRepository.SearchMemberPage(map);
                map.put("currentPage", currentPage);
                map.put("members", tdHtMemberDtos);
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
     * @Purpose  修改用户角色
     * @version  3.0
     * @author   lubin
     * @author   tdHtMemberDto
     * @return   RetInfo
     */
    @Override
    public RetInfo updateRole(TdHtMemberDto tdHtMemberDto) {
        String logInfo = this.getClass().getName() + ":memberSearchPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            if(tdHtMemberDto.getMemb_role()==2&&tdHtMemberDto.getMemb_discount().intValue()>=1){
                retInfo.setMark("1");
                retInfo.setTip("月结用户折扣应该小于1.");
            }else if(tdHtMemberDto.getMemb_role()==1&&tdHtMemberDto.getMemb_discount().doubleValue()>1){
                retInfo.setMark("1");
                retInfo.setTip("普通用户折扣应该小于等于1.");
            }else{
                tdHtMemberRepository.updateMemberRole(tdHtMemberDto);

                MemcachedUtils.delete(tdHtMemberDto.getToken());

                retInfo.setMark("0");
                retInfo.setTip("用户角色修改成功.");
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
     * @Purpose  赠送用户优惠券
     * @version  3.0
     * @author   lubin
     * @time     2017-04-13
     * @param
     * @return   RetInfo
     */
    @Override
    public RetInfo giveMemberCoupon() {
        String logInfo = this.getClass().getName() + ":findGiveCouActMemb:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {

            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            int day = Integer.valueOf(dataConfigMap.get(Constant.GIVE_MEMB_COU_ACT_DAYS).toString());
            Long time = System.currentTimeMillis() - day * 24 * 60 * 60 * 1000L;

            TdHtMemberDto tdHtMemberDto = new TdHtMemberDto();
            tdHtMemberDto.setMemb_register_time(new Timestamp(time));

            List<TdHtMemberDto> tdHtMemberDtoList = tdHtMemberRepository.findMembByRegTime(tdHtMemberDto);

            if(tdHtMemberDtoList.size() > 0){
                String moneyStr = dataConfigMap.get(Constant.GIVE_MEMB_COU_ACT_MONEY).toString();
                String chanceStr = dataConfigMap.get(Constant.GIVE_MEMB_COU_ACT_CHANCE).toString();

                List<GemDto> gemDtoList = new ArrayList<GemDto>();
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

                if(money.doubleValue() > 0) {
                    TdHtCouponDto tdHtCouponDto = new TdHtCouponDto();
                    Long now = System.currentTimeMillis();
                    tdHtCouponDto.setCou_limit_amount(new BigDecimal(0));
                    tdHtCouponDto.setCou_limit_time(new Timestamp(now + 20 * 24 * 60 * 60 * 1000));
                    tdHtCouponDto.setCou_discount(new BigDecimal(1));
                    tdHtCouponDto.setCou_receive_time(new Timestamp(now));
                    tdHtCouponDto.setState(0);
                    tdHtCouponDto.setCou_user_time(new Timestamp(now));
                    tdHtCouponDto.setExp_ord_id(0);
                    tdHtCouponDto.setRegion_id(0);
                    tdHtCouponDto.setMemb_type(0);
                    tdHtCouponDto.setExpress_id(0);

                    int isSend = Integer.valueOf(dataConfigMap.get(Constant.GIVE_MEMB_COU_ACT_IS_SEND).toString());
                    String pushMes = dataConfigMap.get(Constant.GIVE_MEMB_COU_ACT_PUSH_MES).toString();
                    String mobileMes = dataConfigMap.get(Constant.GIVE_MEMB_COU_ACT_PHONE_MES).toString();

                    for (int i = 0; i < tdHtMemberDtoList.size(); i++) {
                        TdHtMemberDto memberDto = tdHtMemberDtoList.get(i);
                        int sendCount = tdHtExpressOrderRepository.findExpOrdSizeByMemb(memberDto.getMemb_id());

                        tdHtCouponDto.setMemb_id(memberDto.getMemb_id());
                        if(total == 0){
                            tdHtCouponDto.setCou_amount(money);
                        }else {
                            tdHtCouponDto.setCou_amount(RandomPrizeUtil.getGumByList(total, gemDtoList));
                        }
                        tdHtCouponDto.setCou_name(tdHtCouponDto.getCou_amount().intValue() + dataConfigMap.get(Constant.GIVE_MEMB_COUPON_NAME).toString());
                        pushMes = pushMes.replace("##", String.valueOf(tdHtCouponDto.getCou_amount().intValue()));
                        mobileMes = mobileMes.replace("##", String.valueOf(tdHtCouponDto.getCou_amount().intValue()));

                        //用户已发件
                        if (isSend == 1 && sendCount > 0) {
                            giveCoupon(memberDto, tdHtCouponDto, pushMes, mobileMes);
                        }

                        //用户未发件
                        if (isSend == 2 && sendCount == 0) {
                            giveCoupon(memberDto, tdHtCouponDto, pushMes, mobileMes);
                        }

                        //无发件要求
                        if (isSend == 0) {
                            giveCoupon(memberDto, tdHtCouponDto, pushMes, mobileMes);
                        }

                        Thread.sleep(500);
                    }
                    retInfo.setMark("0");
                }else {
                    retInfo.setMark("1");
                    retInfo.setTip("活动未开启.");
                }
            }else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的数据.");
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
     * @Purpose  赠送用户优惠券
     * @version  3.0
     * @author   lubin
     * @time     2017-04-13
     * @param    memberDto  需要赠送优惠券的用户信息
     * @param    tdHtCouponDto  需要赠送的优惠券信息
     * @param    pushMes  推送内容
     * @param    mobileMes  短信内容
     * @return   RetInfo
     */
    public void giveCoupon(TdHtMemberDto memberDto, TdHtCouponDto tdHtCouponDto, String pushMes, String mobileMes){
        String logInfo = this.getClass().getName() + ":giveCoupon:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        try {
            if (memberDto.getPush_type() == 1) {
                PushUtil.pushAndroidMember(memberDto.getPush_token(), pushMes, "15", "", memberDto.getApp_version());
            } else if (memberDto.getPush_type() == 2) {
                PushUtil.pushIosMember(memberDto.getPush_token(), pushMes, "15", "default", memberDto.getApp_version());
            }

            boolean is_ok = SendMessage.sendMessage(memberDto.getMemb_phone(), mobileMes);
            //保存发送短信记录
            TwSmsSendHisDto twSmsSendHisDto = new TwSmsSendHisDto();
            if(is_ok){
                twSmsSendHisDto.setTemplate_id(0);
            }else {
                twSmsSendHisDto.setTemplate_id(1);
            }
            twSmsSendHisDto.setPhoneno(memberDto.getMemb_phone());
            twSmsSendHisDto.setSend_time(new Date());
            twSmsSendHisDto.setSms_provider_id(0);
            twSmsSendHisDto.setSms_type(8);
            twSmsSendHisDto.setYyyymmdd(new SimpleDateFormat("yyyyMMdd").format(twSmsSendHisDto.getSend_time()));
            twSmsSendHisDto.setSend_content(mobileMes);
            twSmsSendHisRepository.insertSMSRecord(twSmsSendHisDto);

            tdHtCouponRepository.insertMemberCoupon(tdHtCouponDto);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
    }

    public void setTdHtMemberRepository(TdHtMemberRepository tdHtMemberRepository) {
        this.tdHtMemberRepository = tdHtMemberRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }

    public void setTdHtCouponRepository(TdHtCouponRepository tdHtCouponRepository) {
        this.tdHtCouponRepository = tdHtCouponRepository;
    }

    public void setTwSmsSendHisRepository(TwSmsSendHisRepository twSmsSendHisRepository) {
        this.twSmsSendHisRepository = twSmsSendHisRepository;
    }
}
