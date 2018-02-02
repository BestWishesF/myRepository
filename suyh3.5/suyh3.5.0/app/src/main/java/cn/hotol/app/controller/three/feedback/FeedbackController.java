package cn.hotol.app.controller.three.feedback;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.feedback.TdHtFeedbackDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.feedback.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    /**
     * @param tdHtFeedbackDto
     * @return RetInfo
     * @Purpose 添加意见反馈
     * @version 3.0
     * @author lizhun
     */
    @RequestMapping(value = "/app/3/token/member/feedback")
    @ResponseBody
    public RetInfo recharge(@Valid @RequestBody TdHtFeedbackDto tdHtFeedbackDto, BindingResult result,
                            @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
            tdHtFeedbackDto.setUser_cate("1");
            tdHtFeedbackDto.setUser_id(tdHtMemberDto.getMemb_id());
            return feedbackService.insertFeedback(tdHtFeedbackDto);
        }
        return retInfo;
    }

    /**
     * @param tdHtFeedbackDto
     * @return
     * @Purpose 代理人意见反馈
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentFeedback")
    @ResponseBody
    public RetInfo agentFeedback(@Valid @RequestBody TdHtFeedbackDto tdHtFeedbackDto, BindingResult result,
                                 @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            tdHtFeedbackDto.setUser_cate("2");
            tdHtFeedbackDto.setUser_id(tdHtAgentDto.getAgent_id());
            return feedbackService.insertFeedback(tdHtFeedbackDto);
        }
        return retInfo;
    }
}
