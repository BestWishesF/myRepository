package cn.hotol.app.service.three.apply;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.apply.TdHtAgentApplyDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */
public interface AgentApplyService {

    /**
     * @param tdHtAgentApplyDto
     * @param token
     * @return RetInfo
     * @Purpose 代理人申请
     * @version 3.0
     * @author lubin
     */
    public RetInfo agentApply(TdHtAgentApplyDto tdHtAgentApplyDto, String token);

}
