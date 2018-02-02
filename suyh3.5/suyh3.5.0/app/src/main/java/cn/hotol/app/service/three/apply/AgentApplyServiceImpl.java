package cn.hotol.app.service.three.apply;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.apply.TdHtAgentApplyDto;
import cn.hotol.app.bean.dto.dict.TsHtDictIndex;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtAgentApplyRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2016-12-26.
 */

@Service
public class AgentApplyServiceImpl implements AgentApplyService {

    private static Logger logger = Logger.getLogger(AgentApplyServiceImpl.class);

    @Resource
    private TdHtAgentApplyRepository tdHtAgentApplyRepository;
    @Resource
    private TdHtAgentRepository tdHtAgentRepository;

    /**
     * @param tdHtAgentApplyDto
     * @param token
     * @return RetInfo
     * @Purpose 代理人申请
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo agentApply(TdHtAgentApplyDto tdHtAgentApplyDto, String token) {
        String logInfo = this.getClass().getName() + ":agentLogin:";
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto tdHtAgentDto = (TdHtAgentDto) MemcachedUtils.get(token);

            boolean verification = false;
            String errMessage = "";

            //从字典中获取省市区数据
            Map<String, TsHtDictIndex> dicts = (Map<String, TsHtDictIndex>) MemcachedUtils.get(MemcachedKey.DICT_INDEX_MAP);
            TsHtDictIndex province = dicts.get("" + tdHtAgentApplyDto.getAgent_province());
            TsHtDictIndex city = dicts.get("" + tdHtAgentApplyDto.getAgent_city());
            TsHtDictIndex region = dicts.get("" + tdHtAgentApplyDto.getAgent_region());

            //判断省市区数据的正确性
            if(province == null){
                errMessage = "请选择正确的省市区.";
                verification = true;
            }else {
                if(city == null){
                    errMessage = "请选择正确的省市区.";
                    verification = true;
                }else {
                    if(region == null){
                        errMessage = "请选择正确的省市区.";
                        verification = true;
                    }
                    if(city.getParent_id() != province.getDict_id()){
                        errMessage = "请选择正确的省市区.";
                        verification = true;
                    }
                    if(region != null && region.getParent_id() != city.getDict_id()){
                        errMessage = "请选择正确的省市区.";
                        verification = true;
                    }
                }
            }

            if (!verification) {
                tdHtAgentApplyDto.setAgent_id(tdHtAgentDto.getAgent_id());
                tdHtAgentApplyDto.setApply_state(2);
                tdHtAgentApplyDto.setApply_fail_reason("");
                tdHtAgentApplyRepository.insertAgentApply(tdHtAgentApplyDto);

                tdHtAgentDto.setAgent_name(tdHtAgentApplyDto.getAgent_name());
                tdHtAgentDto.setAgent_head_portrait(tdHtAgentApplyDto.getAgent_head_portrait());
                tdHtAgentDto.setAgent_sex(tdHtAgentApplyDto.getAgent_sex());
                tdHtAgentDto.setAgent_id_number(tdHtAgentApplyDto.getAgent_id_number());
                tdHtAgentDto.setAgent_state(2);
                tdHtAgentDto.setArea_id(tdHtAgentApplyDto.getAgent_region());
                tdHtAgentDto.setAgent_birthday(tdHtAgentApplyDto.getAgent_birthday());
                String address = province.getCode_name() + city.getCode_name() + region.getCode_name();//地址
                tdHtAgentDto.setAgent_address(address + tdHtAgentApplyDto.getAgent_address());
                tdHtAgentDto.setAgent_pay_type(tdHtAgentApplyDto.getAgent_pay_type());
                tdHtAgentDto.setAgent_pay_account(tdHtAgentApplyDto.getAgent_pay_account());
                tdHtAgentRepository.updateAgentApplyInfo(tdHtAgentDto);

                MemcachedUtils.replace(token, tdHtAgentDto, new Date(20 * 24 * 60 * 60 * 1000));

                retInfo.setMark("0");
                retInfo.setTip("申请资料提交成功.");
            } else {
                retInfo.setMark("1");
                retInfo.setTip(errMessage);
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
