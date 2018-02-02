package cn.hotol.app.controller.three.apply;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.apply.TdHtAgentApplyDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.oss.OssUtil;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.apply.AgentApplyService;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by LuBin
 * Date 2016-12-27.
 */

@Controller
public class ApplyController {

    @Resource
    private AgentApplyService agentApplyService;

    /**
     * @param tdHtAgentApplyDto
     * @return
     * @Purpose 代理人申请
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentApply", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RetInfo agentApply(@Valid @RequestBody TdHtAgentApplyDto tdHtAgentApplyDto, BindingResult result,
                              @RequestHeader(value = "token", required = true) String token) throws Exception {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return agentApplyService.agentApply(tdHtAgentApplyDto, token);
        }
        return retInfo;
    }

    /**
     * @param headFile
     * @return
     * @Purpose 代理人上传头像
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentApply/headFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RetInfo applyHeadFile(@RequestParam(value = "headFile", required = true) CommonsMultipartFile headFile,
                                 @RequestHeader(value = "token", required = true) String token) throws Exception {
        RetInfo retInfo = new RetInfo();
        if (headFile != null && headFile.getSize() > 3145728) {
            retInfo.setMark("1");
            retInfo.setTip("个人头像不能大于3M.");
        } else {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
            ObjectMetadata headMetadata = new ObjectMetadata();
            headMetadata.setContentLength(headFile.getSize());
            String headFileName = headFile.getOriginalFilename().substring(headFile.getOriginalFilename().lastIndexOf("."));
            String headSavePath = Constant.FILE_IMG + "/agent/" + tdHtAgentDto.getAgent_id() + "/head/" + formatter.format(new Date(System.currentTimeMillis())) + headFileName;
            OssUtil.uploadInputStream(headFile.getInputStream(), headMetadata, headSavePath);
            retInfo.setMark("0");
            retInfo.setObj(Constant.DOMAIN + "/" + headSavePath);
        }
        return retInfo;
    }

    /**
     * @param frontFile
     * @return
     * @Purpose 代理人上传身份证正面照
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentApply/frontFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RetInfo applyFrontFile(@RequestParam(value = "frontFile", required = true) CommonsMultipartFile frontFile,
                                  @RequestHeader(value = "token", required = true) String token) throws Exception {
        RetInfo retInfo = new RetInfo();
        if (frontFile != null && frontFile.getSize() > 3145728) {
            retInfo.setMark("1");
            retInfo.setTip("身份证正面不能大于3M.");
        } else {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
            ObjectMetadata frontMetadata = new ObjectMetadata();
            frontMetadata.setContentLength(frontFile.getSize());
            String frontFileName = frontFile.getOriginalFilename().substring(frontFile.getOriginalFilename().lastIndexOf("."));
            String frontSavePath = Constant.FILE_IMG + "/agent/" + tdHtAgentDto.getAgent_id() + "/front/" + formatter.format(new Date(System.currentTimeMillis())) + frontFileName;
            OssUtil.uploadInputStream(frontFile.getInputStream(), frontMetadata, frontSavePath);
            retInfo.setMark("0");
            retInfo.setObj(Constant.DOMAIN + "/" + frontSavePath);

        }
        return retInfo;
    }

    /**
     * @param backFile
     * @return
     * @Purpose 代理人上传身份证反面照
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/agentApply/backFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RetInfo applyBackFile(@RequestParam(value = "backFile", required = true) CommonsMultipartFile backFile,
                                 @RequestHeader(value = "token", required = true) String token) throws Exception {
        RetInfo retInfo = new RetInfo();
        if (backFile != null && backFile.getSize() > 3145728) {
            retInfo.setMark("1");
            retInfo.setTip("身份证反面不能大于3M.");
        } else {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssS", Locale.CHINA);
            ObjectMetadata backMetadata = new ObjectMetadata();
            backMetadata.setContentLength(backFile.getSize());
            String backFileName = backFile.getOriginalFilename().substring(backFile.getOriginalFilename().lastIndexOf("."));
            String backSavePath = Constant.FILE_IMG + "/agent/" + tdHtAgentDto.getAgent_id() + "/back/" + formatter.format(new Date(System.currentTimeMillis())) + backFileName;
            OssUtil.uploadInputStream(backFile.getInputStream(), backMetadata, backSavePath);
            retInfo.setMark("0");
            retInfo.setObj(Constant.DOMAIN + "/" + backSavePath);
        }
        return retInfo;
    }

}
