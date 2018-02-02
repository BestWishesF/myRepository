package cn.hotol.app.service.green;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.coupon.TdHtCouponDto;
import cn.hotol.app.bean.dto.green.TdHtGreenMailDto;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.repository.TdHtCouponRepository;
import cn.hotol.app.repository.TdHtGreenMailRepository;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-03-07.
 */
public class GreenServiceImpl implements GreenService {

    private static Logger logger = Logger.getLogger(GreenServiceImpl.class);

    private TdHtGreenMailRepository tdHtGreenMailRepository;
    private TdHtCouponRepository tdHtCouponRepository;

    /**
     * @Purpose  根据状态分页查找用户参与活动记录
     * @version  3.0
     * @author   lubin
     * @param    state,currentPage,pageSize
     * @return   RetInfo
     */
    @Override
    public RetInfo findGreenMailPage(int state, int currentPage, int pageSize, String starTime, String endTime, String phone, String name) {
        String logInfo = this.getClass().getName() + ":findGreenMailPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String, Object> params = new HashMap<>();
            params.put("state",state);
            if(starTime!=null&&!"".equals(starTime)&&endTime!=null&&!"".equals(endTime)){
                params.put("starTime",starTime);
                params.put("endTime",endTime);
                params.put("star_time",dateFormat.parse(starTime+" 00:00:00"));
                params.put("end_time",dateFormat.parse(endTime+" 23:59:59"));
            }
            params.put("phone",phone);
            params.put("name",name);
            int totalRecord = tdHtGreenMailRepository.findMembGreenMailSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtGreenMailDto> greenMailDtoList = tdHtGreenMailRepository.findMembGreenMailPage(map);

                map.put("currentPage", currentPage);
                map.put("greenMailList", greenMailDtoList);
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
     * @Purpose  修改用户活动记录状态
     * @version  3.0
     * @author   lubin
     * @param    tdHtGreenMailDto
     * @return   RetInfo
     */
    @Override
    public RetInfo updateGreenMailState(TdHtGreenMailDto tdHtGreenMailDto) {
        String logInfo = this.getClass().getName() + ":updateGreenMailState:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            long now=System.currentTimeMillis();
            TdHtGreenMailDto greenMailDto=tdHtGreenMailRepository.findGreenMailById(tdHtGreenMailDto.getGreen_mail_id());
            if(greenMailDto!=null){
                if(tdHtGreenMailDto.getState()==3){
                    TdHtCouponDto tdHtCouponDto=new TdHtCouponDto();
                    tdHtCouponDto.setCou_amount(greenMailDto.getEvent_reward());
                    tdHtCouponDto.setCou_limit_amount(new BigDecimal(0));
                    tdHtCouponDto.setCou_limit_time(new Timestamp(now+20*24*60*60*1000));
                    tdHtCouponDto.setCou_discount(new BigDecimal(1));
                    tdHtCouponDto.setCou_receive_time(new Timestamp(now));
                    tdHtCouponDto.setMemb_id(greenMailDto.getMemb_id());
                    tdHtCouponDto.setCou_name("绿邮节"+greenMailDto.getEvent_reward().intValue()+"元活动优惠券");
                    tdHtCouponDto.setState(0);
                    tdHtCouponDto.setCou_user_time(new Timestamp(now));
                    tdHtCouponDto.setExp_ord_id(0);
                    tdHtCouponDto.setRegion_id(0);
                    tdHtCouponDto.setMemb_type(0);
                    tdHtCouponDto.setExpress_id(0);
                    tdHtCouponRepository.insertMemberCoupon(tdHtCouponDto);

                    tdHtGreenMailDto.setGive_green_time(greenMailDto.getGive_green_time());
                    tdHtGreenMailDto.setGive_reward_time(new Timestamp(now));
                    retInfo.setTip("优惠券发放成功.");
                }else{
                    tdHtGreenMailDto.setGive_green_time(new Timestamp(now));
                    tdHtGreenMailDto.setGive_reward_time(greenMailDto.getGive_reward_time());
                    retInfo.setTip("成功.");
                }
                tdHtGreenMailRepository.updateGreenMailState(tdHtGreenMailDto);
                retInfo.setMark("0");
            }else {
                retInfo.setMark("1");
                retInfo.setTip("记录不存在.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtGreenMailRepository(TdHtGreenMailRepository tdHtGreenMailRepository) {
        this.tdHtGreenMailRepository = tdHtGreenMailRepository;
    }

    public void setTdHtCouponRepository(TdHtCouponRepository tdHtCouponRepository) {
        this.tdHtCouponRepository = tdHtCouponRepository;
    }
}
