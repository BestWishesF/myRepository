package cn.hotol.app.service.three.score;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtCoupon;
import cn.hotol.app.bean.TdHtMembScore;
import cn.hotol.app.bean.dto.found.TdHtMembFoundChangeDto;
import cn.hotol.app.bean.dto.goods.TsHtScoreGoodsDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.score.FindMembScoreDto;
import cn.hotol.app.bean.dto.score.MembScoreInfoDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreGoodsDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreTaskDto;
import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by liyafei on 2016/12/12.
 */
@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private TdHtMembScoreRepository tdHtMembScoreRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;
    @Resource
    private TdHtMembScoreTaskRepository tdHtMembScoreTaskRepository;
    @Resource
    private TdHtMembScoreGoodsRepository tdHtMembScoreGoodsRepository;
    @Resource
    private TdHtCouponRepository tdHtCouponRepository;
    @Resource
    private TdHtMembFoundChangeRepository tdHtMembFoundChangeRepository;

    private static Logger logger = Logger.getLogger(ScoreServiceImpl.class);

    /**
     * @param token
     * @param pageDto
     * @return RetInfo
     * @Purpose 查询积分详情
     * @version 3.0
     * @author liyafei
     * @author lubin
     */
    @Override
    public RetInfo findMembScore(String token, PageDto pageDto) {
        String logInfo = this.getClass().getName() + ":findMembScore:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);

            Page<FindMembScoreDto> findMembScoreDtoPage = new Page<FindMembScoreDto>(pageDto.getPage_size(), pageDto.getPage_no());
            pageDto.setId(tdHtMemberDto.getMemb_id());
            pageDto.setLimit_str(findMembScoreDtoPage.getLimitCriterion());
            int count = tdHtMembScoreRepository.findMembScoreCount(tdHtMemberDto.getMemb_id());
            List<FindMembScoreDto> findMembScoreDto = tdHtMembScoreRepository.findMembScore(pageDto);
            findMembScoreDtoPage.setTotalCount(count);

            Map<String, Object> membScore = new HashMap<String, Object>();
            membScore.put("items", findMembScoreDto);
            membScore.put("total_pages", findMembScoreDtoPage.getTotalPages());
            membScore.put("page_no", findMembScoreDtoPage.getPageNo());

            retInfo.setObj(membScore);
            retInfo.setMark("0");
            retInfo.setTip("积分明细查询成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询积分任务详情
     * @version 3.0
     * @author liyafei
     * @author lubin
     */
    @Override
    public RetInfo findScoreTask(String token) {
        String logInfo = this.getClass().getName() + ":findScoreTask:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            //获取积分任务
            List<TsHtScoreTaskDto> scoreTaskList = (List<TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_LIST);

            TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
            tdHtMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
            for (int i = 0; i < scoreTaskList.size(); i++) {
                TsHtScoreTaskDto tsHtScoreTaskDto = scoreTaskList.get(i);
                tdHtMembScoreTaskDto.setSt_id(tsHtScoreTaskDto.getSt_id());
                if (tsHtScoreTaskDto.getSt_type() == Constant.SCORE_NOVICE_TASK) {
                    int noviceTask = tdHtMembScoreTaskRepository.findMembNoviceTaskCount(tdHtMembScoreTaskDto);
                    if (noviceTask > 0) {
                        tsHtScoreTaskDto.setSt_state(1);
                    }
                } else if (tsHtScoreTaskDto.getSt_type() == Constant.SCORE_DAILY_TASK) {
                    int dailyTask = tdHtMembScoreTaskRepository.findMembDailyTaskCount(tdHtMembScoreTaskDto);
                    if (dailyTask > 0) {
                        tsHtScoreTaskDto.setSt_state(1);
                    }
                }
            }
            Map<String, Object> taskInfo = new HashMap<String, Object>();
            taskInfo.put("my_score", tdHtMemberDto.getMemb_score());
            taskInfo.put("score_task", scoreTaskList);

            retInfo.setObj(taskInfo);
            retInfo.setMark("0");
            retInfo.setTip("积分任务查询成功");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询只使用积分兑换的前几个物品
     * @version 3.0
     * @author liyafei
     * @author lubin
     */
    @Override
    public RetInfo findScoreGoods(String token, Integer version) {
        String logInfo = this.getClass().getName() + ":findScoreGoods:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            List<TsHtScoreGoodsDto> tsHtScoreGoodsDtoList = (List<TsHtScoreGoodsDto>) MemcachedUtils.get(MemcachedKey.SCORE_GOODS_LIST);

            List<Map<String, Object>> scoreGoodsDtoList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < tsHtScoreGoodsDtoList.size(); i++) {
                TsHtScoreGoodsDto tsHtScoreGoodsDto = tsHtScoreGoodsDtoList.get(i);
                if (tsHtScoreGoodsDto.getGoods_type() == 1) {
                    TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto = new TdHtMembScoreGoodsDto();
                    tdHtMembScoreGoodsDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembScoreGoodsDto.setGoods_id(tsHtScoreGoodsDto.getGoods_id());
                    int exchangeNum = tdHtMembScoreGoodsRepository.findMembExchangeCount(tdHtMembScoreGoodsDto);
                    if (exchangeNum > 0) {
                        tsHtScoreGoodsDto.setGoods_state(1);
                    }
                    if (tsHtScoreGoodsDto.getGoods_amount().doubleValue() == 0 && scoreGoodsDtoList.size() < 5) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("goods_id", tsHtScoreGoodsDto.getGoods_id());
                        map.put("goods_name", tsHtScoreGoodsDto.getGoods_name());
                        map.put("goods_score", tsHtScoreGoodsDto.getGoods_score());
                        map.put("goods_state", tsHtScoreGoodsDto.getGoods_state());
                        if(version < 6){
                            map.put("goods_img", "http://file.hotol.cn/app/score/goodsIcon/20170331092137545.jpg");
                        }else {
                            map.put("goods_img", tsHtScoreGoodsDto.getGoods_img());
                        }
                        map.put("coupon_money", tsHtScoreGoodsDto.getCoupon_money());
                        map.put("effective_day", tsHtScoreGoodsDto.getEffective_day());
                        scoreGoodsDtoList.add(map);
                    }
                }
            }
            retInfo.setMark("0");
            retInfo.setTip("积分兑换物品查询成功");
            retInfo.setObj(scoreGoodsDtoList);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @return RetInfo
     * @Purpose 查询用户的积分、签到等信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findScoreInfo(String token) {
        String logInfo = this.getClass().getName() + ":findScoreInfo:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);

            MembScoreInfoDto membScoreInfoDto = new MembScoreInfoDto();
            membScoreInfoDto.setMy_score(tdHtMemberDto.getMemb_score());
            //查询用户表数据总数
            int mebCount = tdHtMemberRepository.findMebCount();
            //根据用户id查询积分小于指定用户积分的数据总数
            int mebCountById = tdHtMemberRepository.findMebCountByScore(tdHtMemberDto);
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后0位
            numberFormat.setMaximumFractionDigits(0);
            String percentage = numberFormat.format((float) mebCountById / (float) mebCount * 100) + "%";
            membScoreInfoDto.setRanking(percentage);
            //获取签到奖励积分
            Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
            TsHtScoreTaskDto signTask = scoreTask.get(Constant.SIGN_TASK_ID);
            membScoreInfoDto.setSign_score(signTask.getSt_score());
            //判断用户是否已签到
            TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
            tdHtMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
            tdHtMembScoreTaskDto.setSt_id(Integer.valueOf(Constant.SIGN_TASK_ID));
            int signRecordNum = tdHtMembScoreTaskRepository.findMembDailyTaskCount(tdHtMembScoreTaskDto);
            if (signRecordNum == 0) {
                membScoreInfoDto.setIs_sign(0);
            } else {
                membScoreInfoDto.setIs_sign(1);
            }

            retInfo.setMark("0");
            retInfo.setTip("数据查询成功.");
            retInfo.setObj(membScoreInfoDto);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @return RetInfo
     * @Purpose 用户签到
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo memberSign(String token) {
        String logInfo = this.getClass().getName() + ":memberSign:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);

            //判断用户是否已签到
            TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
            tdHtMembScoreTaskDto.setMemb_id(tdHtMemberDto.getMemb_id());
            tdHtMembScoreTaskDto.setSt_id(Integer.valueOf(Constant.SIGN_TASK_ID));
            int signRecordNum = tdHtMembScoreTaskRepository.findMembDailyTaskCount(tdHtMembScoreTaskDto);
            if (signRecordNum == 0) {
                //获取签到奖励积分
                Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
                TsHtScoreTaskDto signTask = scoreTask.get(Constant.SIGN_TASK_ID);
                int nowScore = tdHtMemberDto.getMemb_score() + signTask.getSt_score();

                TdHtMembScore tdHtMembScore = new TdHtMembScore();
                tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                tdHtMembScore.setScore_change_point(signTask.getSt_score());
                tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                tdHtMembScore.setScore_back_point(nowScore);
                tdHtMembScore.setScore_month(CommonUtil.getMonth());
                tdHtMembScore.setScore_time(new Date());
                tdHtMembScore.setScore_reason(signTask.getSt_name());
                tdHtMembScore.setScore_type(1);
                tdHtMembScoreRepository.insertMembScore(tdHtMembScore);
                //修改用户表用户积分数据
                tdHtMemberDto.setMemb_score(nowScore);
                tdHtMemberRepository.updateMembScore(tdHtMemberDto);

                tdHtMembScoreTaskDto.setSt_state(signTask.getSt_state());
                tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                tdHtMembScoreTaskDto.setSt_img(signTask.getSt_img());
                tdHtMembScoreTaskDto.setSt_name(signTask.getSt_name());
                tdHtMembScoreTaskDto.setSt_score(signTask.getSt_score());
                tdHtMembScoreTaskDto.setSt_synopsis(signTask.getSt_synopsis());
                tdHtMembScoreTaskDto.setSt_type(signTask.getSt_type());
                tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                //查询用户表数据总数
                int mebCount = tdHtMemberRepository.findMebCount();
                //根据用户id查询积分小于指定用户积分的数据总数
                int mebCountById = tdHtMemberRepository.findMebCountByScore(tdHtMemberDto);
                // 创建一个数值格式化对象
                NumberFormat numberFormat = NumberFormat.getInstance();
                // 设置精确到小数点后0位
                numberFormat.setMaximumFractionDigits(0);
                String percentage = numberFormat.format((float) mebCountById / (float) mebCount * 100) + "%";

                MembScoreInfoDto membScoreInfoDto = new MembScoreInfoDto();
                membScoreInfoDto.setRanking(percentage);
                membScoreInfoDto.setIs_sign(1);
                membScoreInfoDto.setSign_score(signTask.getSt_score());
                membScoreInfoDto.setMy_score(nowScore);

                MemcachedUtils.replace(token, tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));

                retInfo.setMark("0");
                retInfo.setTip("签到成功.");
                retInfo.setObj(membScoreInfoDto);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("您已经签到过了.");
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @param tsHtScoreGoodsDto
     * @return RetInfo
     * @Purpose 用户兑换积分商品
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo exchangeGoods(TsHtScoreGoodsDto tsHtScoreGoodsDto, String token) {
        String logInfo = this.getClass().getName() + ":exchangeGoods:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            //缓存中取出积分兑换商品列表
            Map<String, TsHtScoreGoodsDto> scoreGoods = (Map<String, TsHtScoreGoodsDto>) MemcachedUtils.get(MemcachedKey.SCORE_GOODS_MAP);
            TsHtScoreGoodsDto scoreGoodsDto = scoreGoods.get(String.valueOf(tsHtScoreGoodsDto.getGoods_id()));

            if (scoreGoodsDto.getGoods_score() > tdHtMemberDto.getMemb_score()) {
                retInfo.setMark("1");
                retInfo.setTip("您的积分不足.");
            } else {
                TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto = new TdHtMembScoreGoodsDto();
                tdHtMembScoreGoodsDto.setMemb_id(tdHtMemberDto.getMemb_id());
                tdHtMembScoreGoodsDto.setGoods_id(tsHtScoreGoodsDto.getGoods_id());
                int exchangeNum = tdHtMembScoreGoodsRepository.findMembExchangeCount(tdHtMembScoreGoodsDto);
                if (scoreGoodsDto.getGoods_type() == 1 && exchangeNum > 0) {
                    retInfo.setMark("2");
                    retInfo.setTip("您已经兑换过了.");
                } else {
                    Long now = System.currentTimeMillis();
                    int nowScore = tdHtMemberDto.getMemb_score() - scoreGoodsDto.getGoods_score();
                    TdHtMembScore tdHtMembScore = new TdHtMembScore();
                    tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembScore.setScore_change_point(scoreGoodsDto.getGoods_score());
                    tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                    tdHtMembScore.setScore_back_point(nowScore);
                    tdHtMembScore.setScore_month(CommonUtil.getMonth());
                    tdHtMembScore.setScore_time(new Date(now));
                    tdHtMembScore.setScore_reason("用户兑换" + scoreGoodsDto.getGoods_name());
                    tdHtMembScore.setScore_type(2);
                    tdHtMembScoreRepository.insertMembScore(tdHtMembScore);
                    //修改用户表用户积分数据
                    tdHtMemberDto.setMemb_score(nowScore);
                    tdHtMemberRepository.updateMembScore(tdHtMemberDto);

                    tdHtMembScoreGoodsDto.setCoupon_money(scoreGoodsDto.getCoupon_money());
                    tdHtMembScoreGoodsDto.setScore_id(tdHtMembScore.getScore_id());
                    tdHtMembScoreGoodsDto.setExchange_time(new Timestamp(now));
                    tdHtMembScoreGoodsDto.setGoods_img(scoreGoodsDto.getGoods_img());
                    tdHtMembScoreGoodsDto.setGoods_name(scoreGoodsDto.getGoods_name());
                    tdHtMembScoreGoodsDto.setGoods_score(scoreGoodsDto.getGoods_score());
                    tdHtMembScoreGoodsDto.setGoods_state(0);
                    tdHtMembScoreGoodsDto.setEffective_day(scoreGoodsDto.getEffective_day());
                    tdHtMembScoreGoodsDto.setGoods_type(scoreGoodsDto.getGoods_type());
                    tdHtMembScoreGoodsDto.setGoods_img_detail(scoreGoodsDto.getGoods_img_detail());
                    tdHtMembScoreGoodsDto.setGoods_subhead(scoreGoodsDto.getGoods_subhead());
                    tdHtMembScoreGoodsDto.setGoods_introduce(scoreGoodsDto.getGoods_introduce());
                    tdHtMembScoreGoodsDto.setGoods_amount(scoreGoodsDto.getGoods_amount());
                    tdHtMembScoreGoodsDto.setRegion_id(scoreGoodsDto.getRegion_id());
                    tdHtMembScoreGoodsDto.setExpress_source(scoreGoodsDto.getExpress_source());
                    tdHtMembScoreGoodsDto.setExpress_id(scoreGoodsDto.getExpress_id());
                    tdHtMembScoreGoodsDto.setCou_limit_amount(scoreGoodsDto.getCou_limit_amount());
                    tdHtMembScoreGoodsDto.setCou_discount(scoreGoodsDto.getCou_discount());
                    tdHtMembScoreGoodsRepository.insertMembExchangeRecord(tdHtMembScoreGoodsDto);

                    Timestamp endTime = new Timestamp(now + scoreGoodsDto.getEffective_day() * 24 * 60 * 60 * 1000L);
                    TdHtCoupon tdHtCoupon = new TdHtCoupon();
                    tdHtCoupon.setCou_amount(scoreGoodsDto.getCoupon_money());
                    tdHtCoupon.setCou_discount(scoreGoodsDto.getCou_discount());
                    tdHtCoupon.setCou_limit_amount(scoreGoodsDto.getCou_limit_amount());
                    tdHtCoupon.setCou_limit_time(endTime);
                    tdHtCoupon.setCou_name(scoreGoodsDto.getGoods_name());
                    tdHtCoupon.setCou_receive_time(new Timestamp(now));
                    tdHtCoupon.setCou_user_time(new Timestamp(now));
                    tdHtCoupon.setExp_ord_id(0);
                    tdHtCoupon.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtCoupon.setState(0);
                    tdHtCoupon.setMemb_type(scoreGoodsDto.getExpress_source());
                    tdHtCoupon.setExpress_id(scoreGoodsDto.getExpress_id());
                    tdHtCoupon.setRegion_id(scoreGoodsDto.getRegion_id());
                    tdHtCoupon.setLimit_exp_ord_id(0);
                    tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                    //查询用户表数据总数
                    int mebCount = tdHtMemberRepository.findMebCount();
                    //根据用户id查询积分小于指定用户积分的数据总数
                    int mebCountById = tdHtMemberRepository.findMebCountByScore(tdHtMemberDto);
                    // 创建一个数值格式化对象
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    // 设置精确到小数点后0位
                    numberFormat.setMaximumFractionDigits(0);
                    String percentage = numberFormat.format((float) mebCountById / (float) mebCount * 100) + "%";

                    Map<String, Object> membScoreInfo = new HashMap<String, Object>();
                    membScoreInfo.put("ranking", percentage);
                    membScoreInfo.put("my_score", nowScore);

                    MemcachedUtils.replace(token, tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));

                    retInfo.setMark("0");
                    retInfo.setTip("商品兑换成功.");
                    retInfo.setObj(membScoreInfo);
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param goodsId
     * @return RetInfo
     * @Purpose 通过积分商品id查询商品信息
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findScoreGoodsById(int goodsId, String token) {
        String logInfo = this.getClass().getName() + ":findScoreGoodsById:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);

            //缓存中取出积分兑换商品列表
            Map<String, TsHtScoreGoodsDto> scoreGoods = (Map<String, TsHtScoreGoodsDto>) MemcachedUtils.get(MemcachedKey.SCORE_GOODS_MAP);
            TsHtScoreGoodsDto scoreGoodsDto = scoreGoods.get("" + goodsId);

            if (scoreGoodsDto.getGoods_type() == 1) {
                TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto = new TdHtMembScoreGoodsDto();
                tdHtMembScoreGoodsDto.setMemb_id(member.getMemb_id());
                tdHtMembScoreGoodsDto.setGoods_id(scoreGoodsDto.getGoods_id());
                int exchangeNum = tdHtMembScoreGoodsRepository.findMembExchangeCount(tdHtMembScoreGoodsDto);
                if (exchangeNum > 0) {
                    scoreGoodsDto.setGoods_state(1);
                }
            }

            String use_restrictions = "";
            Map<String, Object> dataConfigMap = (Map<String, Object>) MemcachedUtils.get(MemcachedKey.DATA_CONFIG_MAP);
            if(scoreGoodsDto.getExpress_source() == 2){
                use_restrictions = dataConfigMap.get(Constant.GOODS_RESTRICTED_APP).toString();
            }
            if(scoreGoodsDto.getExpress_source() == 1){
                use_restrictions = dataConfigMap.get(Constant.GOODS_RESTRICTED_WECHAT).toString();
            }

            if(scoreGoodsDto.getRegion_id() > 0){
                if(!"".equals(use_restrictions)){
                    use_restrictions = ";" + use_restrictions + scoreGoodsDto.getAddress();
                }else {
                    use_restrictions = "仅限" + use_restrictions + scoreGoodsDto.getAddress();
                }
            }

            if(scoreGoodsDto.getExpress_id() > 0){
                if(!"".equals(use_restrictions)){
                    use_restrictions = ";" + use_restrictions + scoreGoodsDto.getExpress_name();
                }else {
                    use_restrictions = "仅限" + use_restrictions + scoreGoodsDto.getExpress_name();
                }
            }

            if(!"".equals(use_restrictions)){
                use_restrictions = use_restrictions + "使用";
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goods_id", scoreGoodsDto.getGoods_id());
            map.put("goods_name", scoreGoodsDto.getGoods_name());
            map.put("goods_score", scoreGoodsDto.getGoods_score());
            map.put("effective_day", scoreGoodsDto.getEffective_day());
            map.put("goods_img_detail", scoreGoodsDto.getGoods_img_detail());
            map.put("goods_subhead", scoreGoodsDto.getGoods_subhead());
            map.put("goods_introduce", scoreGoodsDto.getGoods_introduce());
            map.put("goods_amount", scoreGoodsDto.getGoods_amount());
            map.put("goods_state", scoreGoodsDto.getGoods_state());
            map.put("use_restrictions", use_restrictions);

            retInfo.setMark("0");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param token
     * @param params
     * @return RetInfo
     * @Purpose 新的用户兑换积分商品
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo newExchangeGoods(Map<String, Integer> params, String token, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":newExchangeGoods:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto member = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto tdHtMemberDto = tdHtMemberRepository.findMembByMembId(member.getMemb_id());
            //缓存中取出积分兑换商品列表
            Map<String, TsHtScoreGoodsDto> scoreGoods = (Map<String, TsHtScoreGoodsDto>) MemcachedUtils.get(MemcachedKey.SCORE_GOODS_MAP);
            TsHtScoreGoodsDto scoreGoodsDto = scoreGoods.get(String.valueOf(params.get("goods_id")));

            if (scoreGoodsDto.getGoods_score() > tdHtMemberDto.getMemb_score()) {
                retInfo.setMark("1");
                retInfo.setTip("您的积分不足.");
            } else {
                Long now = System.currentTimeMillis();
                TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto = new TdHtMembScoreGoodsDto();
                tdHtMembScoreGoodsDto.setMemb_id(tdHtMemberDto.getMemb_id());
                tdHtMembScoreGoodsDto.setGoods_id(params.get("goods_id"));
                tdHtMembScoreGoodsDto.setCoupon_money(scoreGoodsDto.getCoupon_money());
                tdHtMembScoreGoodsDto.setExchange_time(new Timestamp(now));
                tdHtMembScoreGoodsDto.setGoods_img(scoreGoodsDto.getGoods_img());
                tdHtMembScoreGoodsDto.setGoods_name(scoreGoodsDto.getGoods_name());
                tdHtMembScoreGoodsDto.setGoods_score(scoreGoodsDto.getGoods_score());
                tdHtMembScoreGoodsDto.setEffective_day(scoreGoodsDto.getEffective_day());
                tdHtMembScoreGoodsDto.setGoods_type(scoreGoodsDto.getGoods_type());
                tdHtMembScoreGoodsDto.setGoods_img_detail(scoreGoodsDto.getGoods_img_detail());
                tdHtMembScoreGoodsDto.setGoods_subhead(scoreGoodsDto.getGoods_subhead());
                tdHtMembScoreGoodsDto.setGoods_introduce(scoreGoodsDto.getGoods_introduce());
                tdHtMembScoreGoodsDto.setGoods_amount(scoreGoodsDto.getGoods_amount());
                tdHtMembScoreGoodsDto.setRegion_id(scoreGoodsDto.getRegion_id());
                tdHtMembScoreGoodsDto.setExpress_source(scoreGoodsDto.getExpress_source());
                tdHtMembScoreGoodsDto.setExpress_id(scoreGoodsDto.getExpress_id());
                tdHtMembScoreGoodsDto.setCou_limit_amount(scoreGoodsDto.getCou_limit_amount());
                tdHtMembScoreGoodsDto.setCou_discount(scoreGoodsDto.getCou_discount());
                int exchangeNum = tdHtMembScoreGoodsRepository.findMembExchangeCount(tdHtMembScoreGoodsDto);
                if (scoreGoodsDto.getGoods_type() == 1 && exchangeNum > 0) {
                    retInfo.setMark("2");
                    retInfo.setTip("您已经兑换过了.");
                } else {
                    int nowScore = tdHtMemberDto.getMemb_score() - scoreGoodsDto.getGoods_score();
                    if (scoreGoodsDto.getGoods_amount().doubleValue() == 0) {
                        //纯积分支付
                        TdHtMembScore tdHtMembScore = new TdHtMembScore();
                        tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtMembScore.setScore_change_point(scoreGoodsDto.getGoods_score());
                        tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                        tdHtMembScore.setScore_back_point(nowScore);
                        tdHtMembScore.setScore_month(CommonUtil.getMonth());
                        tdHtMembScore.setScore_time(new Date(now));
                        tdHtMembScore.setScore_reason("用户兑换" + scoreGoodsDto.getGoods_name());
                        tdHtMembScore.setScore_type(2);
                        tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                        //修改用户表用户积分数据
                        tdHtMemberDto.setMemb_score(nowScore);
                        tdHtMemberRepository.updateMembScore(tdHtMemberDto);

                        tdHtMembScoreGoodsDto.setGoods_state(0);
                        tdHtMembScoreGoodsDto.setScore_id(tdHtMembScore.getScore_id());
                        tdHtMembScoreGoodsRepository.insertMembExchangeRecord(tdHtMembScoreGoodsDto);

                        Timestamp endTime = new Timestamp(now + scoreGoodsDto.getEffective_day() * 24 * 60 * 60 * 1000L);
                        TdHtCoupon tdHtCoupon = new TdHtCoupon();
                        tdHtCoupon.setCou_amount(scoreGoodsDto.getCoupon_money());
                        tdHtCoupon.setCou_discount(scoreGoodsDto.getCou_discount());
                        tdHtCoupon.setCou_limit_amount(scoreGoodsDto.getCou_limit_amount());
                        tdHtCoupon.setCou_limit_time(endTime);
                        tdHtCoupon.setCou_name(scoreGoodsDto.getGoods_name());
                        tdHtCoupon.setCou_receive_time(new Timestamp(now));
                        tdHtCoupon.setCou_user_time(new Timestamp(now));
                        tdHtCoupon.setExp_ord_id(0);
                        tdHtCoupon.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtCoupon.setState(0);
                        tdHtCoupon.setMemb_type(scoreGoodsDto.getExpress_source());
                        tdHtCoupon.setExpress_id(scoreGoodsDto.getExpress_id());
                        tdHtCoupon.setRegion_id(scoreGoodsDto.getRegion_id());
                        tdHtCoupon.setLimit_exp_ord_id(0);
                        tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                        MemcachedUtils.replace(token, tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));
                        retInfo.setMark("0");
                        retInfo.setTip("商品兑换成功.");
                    } else {
                        TdHtMembFoundChangeDto tdHtMembFoundChangeDto = new TdHtMembFoundChangeDto();
                        tdHtMembFoundChangeDto.setExp_ord_id(0);
                        tdHtMembFoundChangeDto.setMemb_id(tdHtMemberDto.getMemb_id());
                        tdHtMembFoundChangeDto.setMfchg_time(new Timestamp(now));
                        tdHtMembFoundChangeDto.setMfchg_month(CommonUtil.getMonth());
                        tdHtMembFoundChangeDto.setMfchg_number(CommonUtil.getOrderNub());
                        tdHtMembFoundChangeDto.setMfchg_name("积分商品支付");
                        tdHtMembFoundChangeDto.setMemb_mon_bill_id(0);
                        tdHtMembFoundChangeDto.setMemb_ivc_his_id(0);

                        //判断是否需要第三方支付(3：钱包支付 ；1、2：需要进行第三方支付)
                        if (params.get("pay_type") == 3) {
                            if (tdHtMemberDto.getMemb_balance().compareTo(scoreGoodsDto.getGoods_amount()) < 0) {
                                retInfo.setMark("1");
                                retInfo.setTip("余额不足.");
                            } else {
                                tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                                tdHtMembFoundChangeDto.setMfchg_change_amount(scoreGoodsDto.getGoods_amount());
                                tdHtMembFoundChangeDto.setMfchg_back_amount((tdHtMemberDto.getMemb_balance().subtract(scoreGoodsDto.getGoods_amount())).setScale(2, BigDecimal.ROUND_HALF_UP));
                                tdHtMembFoundChangeDto.setMfchg_channel(3);
                                tdHtMembFoundChangeDto.setMfchg_state(0);
                                tdHtMembFoundChangeDto.setMfchg_type(2);

                                if (scoreGoodsDto.getGoods_score() > 0) {
                                    TdHtMembScore tdHtMembScore = new TdHtMembScore();
                                    tdHtMembScore.setMemb_id(tdHtMemberDto.getMemb_id());
                                    tdHtMembScore.setScore_change_point(scoreGoodsDto.getGoods_score());
                                    tdHtMembScore.setScore_front_point(tdHtMemberDto.getMemb_score());
                                    tdHtMembScore.setScore_back_point(nowScore);
                                    tdHtMembScore.setScore_month(CommonUtil.getMonth());
                                    tdHtMembScore.setScore_time(new Date(now));
                                    tdHtMembScore.setScore_reason("用户兑换" + scoreGoodsDto.getGoods_name());
                                    tdHtMembScore.setScore_type(2);
                                    tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                                    tdHtMembScoreGoodsDto.setScore_id(tdHtMembScore.getScore_id());

                                    //修改用户表用户积分数据
                                    tdHtMemberDto.setMemb_score(nowScore);
                                } else {
                                    tdHtMembScoreGoodsDto.setScore_id(0);
                                }
                                tdHtMembScoreGoodsDto.setGoods_state(0);
                                tdHtMembScoreGoodsRepository.insertMembExchangeRecord(tdHtMembScoreGoodsDto);

                                tdHtMembFoundChangeDto.setMemb_goods_id(tdHtMembScoreGoodsDto.getMemb_goods_id());
                                tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                                Timestamp endTime = new Timestamp(now + scoreGoodsDto.getEffective_day() * 24 * 60 * 60 * 1000);
                                TdHtCoupon tdHtCoupon = new TdHtCoupon();
                                tdHtCoupon.setCou_amount(scoreGoodsDto.getCoupon_money());
                                tdHtCoupon.setCou_discount(scoreGoodsDto.getCou_discount());
                                tdHtCoupon.setCou_limit_amount(scoreGoodsDto.getCou_limit_amount());
                                tdHtCoupon.setCou_limit_time(endTime);
                                tdHtCoupon.setCou_name(scoreGoodsDto.getGoods_name());
                                tdHtCoupon.setCou_receive_time(new Timestamp(now));
                                tdHtCoupon.setCou_user_time(new Timestamp(now));
                                tdHtCoupon.setExp_ord_id(0);
                                tdHtCoupon.setMemb_id(tdHtMemberDto.getMemb_id());
                                tdHtCoupon.setState(0);
                                tdHtCoupon.setMemb_type(scoreGoodsDto.getExpress_source());
                                tdHtCoupon.setExpress_id(scoreGoodsDto.getExpress_id());
                                tdHtCoupon.setRegion_id(scoreGoodsDto.getRegion_id());
                                tdHtCoupon.setLimit_exp_ord_id(0);
                                tdHtCouponRepository.insertMembCoupon(tdHtCoupon);

                                tdHtMemberDto.setMemb_balance(tdHtMembFoundChangeDto.getMfchg_back_amount());
                                tdHtMemberRepository.updateMemberBanlanceAndScore(tdHtMemberDto);

                                MemcachedUtils.replace(token, tdHtMemberDto, new Date(20 * 24 * 60 * 60 * 1000));
                                retInfo.setMark("0");
                                retInfo.setTip("商品兑换成功.");
                            }
                        } else {
                            tdHtMembScoreGoodsDto.setScore_id(0);
                            tdHtMembScoreGoodsDto.setGoods_state(1);
                            tdHtMembScoreGoodsRepository.insertMembExchangeRecord(tdHtMembScoreGoodsDto);

                            tdHtMembFoundChangeDto.setMfchg_front_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_back_amount(tdHtMemberDto.getMemb_balance());
                            tdHtMembFoundChangeDto.setMfchg_type(3);
                            tdHtMembFoundChangeDto.setMfchg_state(1);
                            tdHtMembFoundChangeDto.setMfchg_change_amount(scoreGoodsDto.getGoods_amount());
                            tdHtMembFoundChangeDto.setMfchg_channel(params.get("pay_type"));
                            tdHtMembFoundChangeDto.setMemb_goods_id(tdHtMembScoreGoodsDto.getMemb_goods_id());
                            tdHtMembFoundChangeRepository.insertMemberFoundChange(tdHtMembFoundChangeDto);

                            Pingpp.apiKey = Constant.Live_Secret_Key;
                            Map<String, Object> chargeParams = new HashMap<String, Object>();
                            chargeParams.put("order_no", tdHtMembFoundChangeDto.getMfchg_number());
                            chargeParams.put("amount", scoreGoodsDto.getGoods_amount().multiply(new BigDecimal(100)).intValue());
                            Map<String, String> app = new HashMap<String, String>();
                            app.put("id", Constant.MEMBER_APP_ID);
                            chargeParams.put("app", app);
                            if (tdHtMembFoundChangeDto.getMfchg_channel() == 1) {
                                chargeParams.put("channel", "wx");  //微信wx
                            }
                            if (tdHtMembFoundChangeDto.getMfchg_channel() == 2) {
                                chargeParams.put("channel", "alipay");  //支付宝alipay
                            }
                            chargeParams.put("currency", "cny");
                            chargeParams.put("client_ip", Ip.getIpAddr(request));
                            chargeParams.put("subject", "商品订单支付");
                            chargeParams.put("body", "商品订单支付");
                            Charge charge = Charge.create(chargeParams);

                            Map<String, Object> chargeMap = new HashMap<>();
                            chargeMap.put("charge", charge);

                            retInfo.setMark("0");
                            retInfo.setTip("下单成功");
                            retInfo.setObj(chargeMap);
                        }
                    }
                }
            }
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @return RetInfo
     * @Purpose 查询积分兑换物品列表
     * @version 3.0
     * @author liyafei
     */
    @Override
    public RetInfo findScoreGoodList(String token) {
        String logInfo = this.getClass().getName() + ":findScoreGoodList:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            List<TsHtScoreGoodsDto> tsHtScoreGoodsDtoList = (List<TsHtScoreGoodsDto>) MemcachedUtils.get(MemcachedKey.SCORE_GOODS_LIST);

            List<Map<String, Object>> scoreGoodsList = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < tsHtScoreGoodsDtoList.size(); i++) {
                TsHtScoreGoodsDto tsHtScoreGoodsDto = tsHtScoreGoodsDtoList.get(i);
                if (tsHtScoreGoodsDto.getGoods_type() == 1) {
                    TdHtMembScoreGoodsDto tdHtMembScoreGoodsDto = new TdHtMembScoreGoodsDto();
                    tdHtMembScoreGoodsDto.setMemb_id(tdHtMemberDto.getMemb_id());
                    tdHtMembScoreGoodsDto.setGoods_id(tsHtScoreGoodsDto.getGoods_id());
                    int exchangeNum = tdHtMembScoreGoodsRepository.findMembExchangeCount(tdHtMembScoreGoodsDto);
                    if (exchangeNum > 0) {
                        tsHtScoreGoodsDto.setGoods_state(1);
                    }
                }

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("goods_id", tsHtScoreGoodsDto.getGoods_id());
                map.put("goods_img", tsHtScoreGoodsDto.getGoods_img());
                map.put("goods_name", tsHtScoreGoodsDto.getGoods_name());
                map.put("goods_score", tsHtScoreGoodsDto.getGoods_score());
                map.put("goods_state", tsHtScoreGoodsDto.getGoods_state());
                map.put("goods_subhead", tsHtScoreGoodsDto.getGoods_subhead());
                map.put("goods_amount", tsHtScoreGoodsDto.getGoods_amount());
                scoreGoodsList.add(map);
            }
            retInfo.setMark("0");
            retInfo.setTip("积分兑换物品查询成功");
            retInfo.setObj(scoreGoodsList);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
