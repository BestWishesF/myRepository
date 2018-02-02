package cn.hotol.app.service.three.comment;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembScore;
import cn.hotol.app.bean.dto.comment.TdHtCommentDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.score.TdHtMembScoreTaskDto;
import cn.hotol.app.bean.dto.task.TsHtScoreTaskDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtCommentRepository;
import cn.hotol.app.repository.TdHtMembScoreRepository;
import cn.hotol.app.repository.TdHtMembScoreTaskRepository;
import cn.hotol.app.repository.TdHtMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-10.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private Logger logger = Logger.getLogger(CommentServiceImpl.class);

    @Resource
    private TdHtCommentRepository tdHtCommentRepository;
    @Resource
    private TdHtMembScoreTaskRepository tdHtMembScoreTaskRepository;
    @Resource
    private TdHtMembScoreRepository tdHtMembScoreRepository;
    @Resource
    private TdHtMemberRepository tdHtMemberRepository;

    /**
     * @param tdHtCommentDto
     * @param token
     * @Purpose 添加评价
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo insertComment(TdHtCommentDto tdHtCommentDto, String token) {
        String logInfo = this.getClass().getName() + ":insertComment:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //从缓存中取出用户信息
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            TdHtMemberDto member = tdHtMemberRepository.findMembByMembId(tdHtMemberDto.getMemb_id());

            tdHtCommentDto.setMemb_id(member.getMemb_id());
            tdHtCommentDto.setCom_month(CommonUtil.getMonth());
            tdHtCommentDto.setCom_time(new Timestamp(new Date().getTime()));
            if(tdHtCommentDto.getCom_content() == null){
                tdHtCommentDto.setCom_content("");
            }
            tdHtCommentRepository.insertComment(tdHtCommentDto);

            //获取每日评价奖励积分
            Map<String, TsHtScoreTaskDto> scoreTask = (Map<String, TsHtScoreTaskDto>) MemcachedUtils.get(MemcachedKey.SCORE_TASK_MAP);
            TsHtScoreTaskDto evaluateScoreTask = scoreTask.get(Constant.EVALUATE_TASK_ID);

            TdHtMembScoreTaskDto evaluateMembScoreTaskDto = new TdHtMembScoreTaskDto();
            evaluateMembScoreTaskDto.setMemb_id(member.getMemb_id());
            evaluateMembScoreTaskDto.setSt_id(evaluateScoreTask.getSt_id());
            int dailyTask = tdHtMembScoreTaskRepository.findMembDailyTaskCount(evaluateMembScoreTaskDto);
            if (dailyTask == 0) {
                //插入用户积分变化记录表
                TdHtMembScore tdHtMembScore = new TdHtMembScore();
                tdHtMembScore.setMemb_id(member.getMemb_id());
                tdHtMembScore.setScore_change_point(evaluateScoreTask.getSt_score());
                tdHtMembScore.setScore_front_point(member.getMemb_score());
                tdHtMembScore.setScore_back_point(member.getMemb_score() + evaluateScoreTask.getSt_score());
                tdHtMembScore.setScore_month(CommonUtil.getMonth());
                tdHtMembScore.setScore_time(new Date());
                tdHtMembScore.setScore_reason(evaluateScoreTask.getSt_name());
                tdHtMembScore.setScore_type(1);
                tdHtMembScoreRepository.insertMembScore(tdHtMembScore);

                //插入用户积分任务完成记录表
                TdHtMembScoreTaskDto tdHtMembScoreTaskDto = new TdHtMembScoreTaskDto();
                tdHtMembScoreTaskDto.setMemb_id(member.getMemb_id());
                tdHtMembScoreTaskDto.setSt_id(evaluateScoreTask.getSt_id());
                tdHtMembScoreTaskDto.setSt_state(evaluateScoreTask.getSt_state());
                tdHtMembScoreTaskDto.setScore_id(tdHtMembScore.getScore_id());
                tdHtMembScoreTaskDto.setSt_img(evaluateScoreTask.getSt_img());
                tdHtMembScoreTaskDto.setSt_name(evaluateScoreTask.getSt_name());
                tdHtMembScoreTaskDto.setSt_score(evaluateScoreTask.getSt_score());
                tdHtMembScoreTaskDto.setSt_synopsis(evaluateScoreTask.getSt_synopsis());
                tdHtMembScoreTaskDto.setSt_type(evaluateScoreTask.getSt_type());
                tdHtMembScoreTaskDto.setTask_time(new Timestamp(System.currentTimeMillis()));
                tdHtMembScoreTaskRepository.insertMembTaskRecord(tdHtMembScoreTaskDto);

                //更新用户积分
                member.setMemb_score(tdHtMembScore.getScore_back_point());
                tdHtMemberRepository.updateMembScore(member);

                MemcachedUtils.replace(token, member, new java.sql.Date(20 * 24 * 60 * 60 * 1000));
            }

            retInfo.setMark("0");
            retInfo.setTip("评价成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("JSON数据错误.");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }
}
