package cn.hotol.app.controller.three.comment;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.comment.TdHtCommentDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.comment.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by LuBin
 * Date 2016-12-10.
 */
@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * @Purpose  添加评价
     * @version  3.0
     * @author   lubin
     * @param    tdHtCommentDto
     * @param    token
     */
    @RequestMapping(value = "/app/3/token/insertComment", method = {RequestMethod.GET,RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo insertComment(@Valid @RequestBody TdHtCommentDto tdHtCommentDto, BindingResult result,
                                 @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return commentService.insertComment(tdHtCommentDto, token);
        }
        return retInfo;
    }

}
