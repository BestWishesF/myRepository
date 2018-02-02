package cn.hotol.app.service.agent;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.TdHtAdminDivideDto;
import cn.hotol.app.bean.dto.agent.TdHtAgentDto;
import cn.hotol.app.bean.dto.agent.apply.TdHtAgentApplyDto;
import cn.hotol.app.bean.dto.location.ProvinceDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.location.TsHtDictList;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtAdminDivideGridRepository;
import cn.hotol.app.repository.TdHtAdminDivideRepository;
import cn.hotol.app.repository.TdHtAgentRepository;
import cn.hotol.app.repository.TsHtDictRepository;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/24.
 */
public class AgentServiceImpl implements AgentService {
    private static Logger logger = Logger.getLogger(AgentServiceImpl.class);
    private TdHtAgentRepository tdHtAgentRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtAdminDivideRepository tdHtAdminDivideRepository;
    private TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository;

    @Override
    public RetInfo agentPage(int agent_state, int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":agentPage:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("agent_state", agent_state);

            params.put("province", tdHtAdminDto.getProvince_id());
            params.put("city", tdHtAdminDto.getCity_id());
            params.put("region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            int totalRecord = tdHtAgentRepository.findAgentSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtAgentDto> tdHtAgentDtos = tdHtAgentRepository.findAgentPage(map);
                map.put("currentPage", currentPage);
                map.put("agents", tdHtAgentDtos);
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
    public RetInfo lock(TdHtAgentDto tdHtAgentDto) {
        String logInfo = this.getClass().getName() + ":lock:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //更新用户状态
            tdHtAgentRepository.updateAgentState(tdHtAgentDto);

            //如果是锁定用户清除用户登录缓存
            if (tdHtAgentDto.getAgent_state() == 6) {
                TdHtAgentDto agent=tdHtAgentRepository.findAgentById(tdHtAgentDto.getAgent_id());
                MemcachedUtils.delete(agent.getToken());
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

    /**
     * @Purpose 查询可指派的代理人列表
     * @version  3.0
     * @author   lubin
     * @param agent_phone
     * @return RetInfo
     */
    @Override
    public RetInfo findAssignAgentList(String agent_phone, Integer add_city) {
        String logInfo = this.getClass().getName() + ":findAssignAgentList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("agent_phone", agent_phone);
            params.put("add_city", add_city);

            if(add_city > 0){
                List<TsHtDictDto> tsHtDictDtoList = tsHtDictRepository.findDictByParent(add_city);
                params.put("region", tsHtDictDtoList);
            }

            List<TdHtAgentDto> tdHtAgentDtos = tdHtAgentRepository.findAssignAgentList(params);
            if (tdHtAgentDtos.size() > 0) {
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(tdHtAgentDtos);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
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
     * @Purpose 按条件查询代理人分页
     * @version  3.0
     * @author   lubin
     * @param currentPage pageSize agent_id agent_mobile agent_name
     * @return RetInfo
     */
    @Override
    public RetInfo searchList(int currentPage, int pageSize, int agent_id, String agent_mobile, String agent_name, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":findAssignAgentList:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto tdHtAdminDto = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);

            Map<String, Object> params = new HashMap<>();
            params.put("agent_id", agent_id);
            params.put("agent_mobile", agent_mobile);
            params.put("agent_name", agent_name);

            params.put("province", tdHtAdminDto.getProvince_id());
            params.put("city", tdHtAdminDto.getCity_id());
            params.put("region", tdHtAdminDto.getRegion_id());
            params.put("divide_id", tdHtAdminDto.getDivide_id());

            int totalRecord = tdHtAgentRepository.findAgentByBeanSize(params);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord ,currentPage ,pageSize);
                map.putAll(params);
                List<TdHtAgentDto> tdHtAgentDtos = tdHtAgentRepository.findAgentByBeanPage(map);
                map.put("currentPage", currentPage);
                map.put("agents", tdHtAgentDtos);
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
     * @Purpose 代理人修改页面跳转
     * @version  3.0
     * @author   lubin
     * @param agent_id
     * @return RetInfo
     */
    @Override
    public RetInfo agentUpdateJump(int agent_id) {
        String logInfo = this.getClass().getName() + ":agentUpdateJump:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("agent_id", agent_id);
            TdHtAgentDto tdHtAgentDto=tdHtAgentRepository.findAgentById(agent_id);
            params.put("agent", tdHtAgentDto);
            TsHtDictList area=tsHtDictRepository.findDictById(Integer.parseInt(tdHtAgentDto.getArea_id()));
            params.put("area_id", area.getDict_id());
            TsHtDictList city=tsHtDictRepository.findDictById(area.getParent_id());
            params.put("city_id", city.getDict_id());
            TsHtDictList province=tsHtDictRepository.findDictById(city.getParent_id());
            params.put("province_id", province.getDict_id());
            List<TsHtDictDto> areaList=tsHtDictRepository.findDictByParent(area.getParent_id());
            params.put("area", areaList);
            List<TsHtDictDto> cityList=tsHtDictRepository.findDictByParent(city.getParent_id());
            params.put("city", cityList);
            List<ProvinceDto> provinceDtoList=tsHtDictRepository.findAllProvince();
            params.put("province", provinceDtoList);
            params.put("address", province.getCode_name()+city.getCode_name()+area.getCode_name());
            params.put("divide_id",tdHtAgentDto.getDivide_id());
            List<TdHtAdminDivideDto> tdHtAdminDivideDtoList = tdHtAdminDivideRepository.findAllDivideByRegion(Integer.parseInt(tdHtAgentDto.getArea_id()));
            params.put("divide", tdHtAdminDivideDtoList);

            retInfo.setObj(params);
            retInfo.setMark("0");
            retInfo.setTip("查询成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose 修改代理人信息
     * @version  3.0
     * @author   lubin
     * @param tdHtAgentApplyDto
     * @return RetInfo
     */
    @Override
    public RetInfo updateAgent(TdHtAgentApplyDto tdHtAgentApplyDto) {
        String logInfo = this.getClass().getName() + ":updateAgent:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            TdHtAgentDto agent = tdHtAgentRepository.findAgentById(tdHtAgentApplyDto.getAgent_id());

            agent.setAgent_type(tdHtAgentApplyDto.getAgent_type());
            agent.setArea_id(tdHtAgentApplyDto.getArea_id());
            agent.setDivide_id(tdHtAgentApplyDto.getDivide_id());
            tdHtAgentRepository.updateAgentApply(agent);

            retInfo.setMark("0");
            retInfo.setTip("修改成功.");
        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
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
