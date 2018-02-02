package cn.hotol.app.service.agent.apply;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;
import cn.hotol.app.bean.dto.location.AreaDto;
import cn.hotol.app.bean.dto.location.CityDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.*;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lizhun on 2016/12/24.
 */
public class AgentApplyServiceImpl implements AgentApplyService {

    private static Logger logger = Logger.getLogger(AgentApplyServiceImpl.class);
    private TdHtAgentApplyRepository tdHtAgentApplyRepository;
    private TdHtAgentRepository tdHtAgentRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtAdminDivideRepository tdHtAdminDivideRepository;
    private TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository;
    Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public RetInfo agentAppllyPage(int apply_state, int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":agentAppllyPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("apply_state", apply_state);
            List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtos);
            List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(provinceDtos.get(0).getDict_id());
            params.put("city", cityDtos);
            List<AreaDto> areaDtos = tsHtDictRepository.findAreaByCity(cityDtos.get(0).getDict_id());
            params.put("areaDtos", areaDtos);

            params.put("agent_province", tdHtAdminDto.getProvince_id());
            params.put("agent_city", tdHtAdminDto.getCity_id());
            params.put("agent_region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            int totalRecord = tdHtAgentApplyRepository.findAgentApplySize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);
                map.putAll(params);
                List<TdHtAgentApplyDto> tdHtAgentApplyDtos = tdHtAgentApplyRepository.findAgentApplyPage(map);

                for (int i = 0; i < tdHtAgentApplyDtos.size(); i++) {
                    String province = dicts.get("" + tdHtAgentApplyDtos.get(i).getAgent_province()).getCode_name();
                    String city = dicts.get("" + tdHtAgentApplyDtos.get(i).getAgent_city()).getCode_name();
                    String area = dicts.get("" + tdHtAgentApplyDtos.get(i).getAgent_region()).getCode_name();
                    String address = province + city + area + tdHtAgentApplyDtos.get(i).getAgent_address();
                    tdHtAgentApplyDtos.get(i).setAgent_address(address);
                    tdHtAgentApplyDtos.get(i).setAgent_phone(tdHtAgentRepository.findAgentById(tdHtAgentApplyDtos.get(i).getAgent_id()).getAgent_phone());
                }
                map.put("currentPage", currentPage);
                map.put("applys", tdHtAgentApplyDtos);
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
    @Transactional
    public synchronized RetInfo examine(TdHtAgentApplyDto tdHtAgentApplyDto) {
        String logInfo = this.getClass().getName() + ":examine:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentApplyDto apply = tdHtAgentApplyRepository.findAgentApplyById(tdHtAgentApplyDto.getApply_id());
            TdHtAgentDto agent = tdHtAgentRepository.findAgentById(apply.getAgent_id());
            if (apply.getApply_state() == 2) {
                if (tdHtAgentApplyDto.getApply_state() == 1) {
                    //通过
                    //修改记录表
                    tdHtAgentApplyRepository.updateAgentApply(tdHtAgentApplyDto);
                    //修改代理人信息
                    String province = dicts.get("" + apply.getAgent_province()).getCode_name();
                    String city = dicts.get("" + apply.getAgent_city()).getCode_name();
                    String area = dicts.get("" + apply.getAgent_region()).getCode_name();
                    String address = province + city + area + apply.getAgent_address();
                    String birthdayString = apply.getAgent_id_number().substring(6, 14);
                    Date date = sdf.parse(birthdayString);
                    agent.setAgent_address(address);
                    agent.setAgent_sex(tdHtAgentApplyDto.getAgent_sex());
                    agent.setAgent_head_portrait(apply.getAgent_head_portrait());
                    agent.setAgent_birthday(new Timestamp(date.getTime()));
                    agent.setAgent_id_number(apply.getAgent_id_number());
                    agent.setAgent_name(apply.getAgent_name());
                    agent.setAgent_pay_account(apply.getAgent_pay_account());
                    agent.setAgent_pay_type(apply.getAgent_pay_type());
                    agent.setAgent_state(4);
                    agent.setArea_id(tdHtAgentApplyDto.getArea_id());
                    agent.setDivide_id(tdHtAgentApplyDto.getDivide_id());
                    agent.setAgent_type(tdHtAgentApplyDto.getAgent_type());
                    tdHtAgentRepository.updateAgentApply(agent);

                    //发推送
                    if (agent.getPush_type() == 1) {
                        PushUtil.pushAndroidAgent(agent.getPush_token(), "您的代理人申请已通过.", "8", "", agent.getApp_version());
                    } else if (agent.getPush_type() == 2) {
                        PushUtil.pushIosAgent(agent.getPush_token(), "您的代理人申请已通过.", "8", "default", agent.getApp_version());
                    }

                    retInfo.setMark("0");
                    retInfo.setTip("成功");

                } else {
                    //不通过
                    //修改记录表
                    tdHtAgentApplyRepository.updateAgentApply(tdHtAgentApplyDto);
                    //修改代理人信息
                    agent.setAgent_id(apply.getAgent_id());
                    agent.setAgent_state(3);
                    tdHtAgentRepository.updateAgentState(agent);

                    //发推送
                    if (agent.getPush_type() == 1) {
                        PushUtil.pushAndroidAgent(agent.getPush_token(), "您的代理人申请未通过，原因是：" + tdHtAgentApplyDto.getApply_fail_reason() + ".", "9", "", agent.getApp_version());
                    } else if (agent.getPush_type() == 2) {
                        PushUtil.pushIosAgent(agent.getPush_token(), "您的代理人申请未通过，原因是：" + tdHtAgentApplyDto.getApply_fail_reason() + ".", "9", "default", agent.getApp_version());
                    }

                    retInfo.setMark("0");
                    retInfo.setTip("成功");
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("已审核,请刷新");
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
     * @Purpose  审核代理人页面跳转
     * @version  3.0
     * @author   lubin
     * @param    apply_id
     * @return   RetInfo
     */
    @Override
    public RetInfo agentAppllyJump(int apply_id) {
        String logInfo = this.getClass().getName() + ":examine:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> map=new HashMap<String, Object>();
            TdHtAgentApplyDto tdHtAgentApplyDto = tdHtAgentApplyRepository.findAgentApplyById(apply_id);
            String province = dicts.get("" + tdHtAgentApplyDto.getAgent_province()).getCode_name();
            String city = dicts.get("" + tdHtAgentApplyDto.getAgent_city()).getCode_name();
            String area = dicts.get("" + tdHtAgentApplyDto.getAgent_region()).getCode_name();
            String address = province + city + area;
            map.put("address",address);
            map.put("agent_apply",tdHtAgentApplyDto);
            map.put("apply_id", apply_id);
            List<ProvinceDto> provinceDtos = tsHtDictRepository.findAllProvince();
            map.put("province", provinceDtos);
            List<CityDto> cityDtos = tsHtDictRepository.findCityByProvince(tdHtAgentApplyDto.getAgent_province());
            map.put("city", cityDtos);
            List<AreaDto> areaDtos = tsHtDictRepository.findAreaByCity(tdHtAgentApplyDto.getAgent_city());
            map.put("area", areaDtos);
            List<TdHtAdminDivideDto> divideDtoList=tdHtAdminDivideRepository.findAllDivideByRegion(tdHtAgentApplyDto.getAgent_region());
            map.put("divide", divideDtoList);
            retInfo.setMark("0");
            retInfo.setObj(map);
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    public void setTdHtAgentApplyRepository(TdHtAgentApplyRepository tdHtAgentApplyRepository) {
        this.tdHtAgentApplyRepository = tdHtAgentApplyRepository;
    }

    public void setTdHtAgentRepository(TdHtAgentRepository tdHtAgentRepository) {
        this.tdHtAgentRepository = tdHtAgentRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtAdminDivideRepository(TdHtAdminDivideRepository tdHtAdminDivideRepository) {
        this.tdHtAdminDivideRepository = tdHtAdminDivideRepository;
    }

    public void setTdHtAdminDivideGridRepository(TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository) {
        this.tdHtAdminDivideGridRepository = tdHtAdminDivideGridRepository;
    }
}
