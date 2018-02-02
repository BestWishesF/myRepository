package cn.hotol.app.service.role;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.DivideDto;
import cn.hotol.app.bean.dto.funcion.TdHtFuncroleRelDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.TdHtFuncroleRelRepository;
import cn.hotol.app.repository.TdHtRoleRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2017/4/22.
 */
public class RoleServiceImpl implements RoleService {
    private static Logger logger = Logger.getLogger(RoleServiceImpl.class);

    private TdHtRoleRepository tdHtRoleRepository;
    private TdHtFuncroleRelRepository tdHtFuncroleRelRepository;
    @Override
    public RetInfo insertRole(TdHtAdminDto tdHtAdmin, TdHtRoleDto tdHtRoleDto) {
        String logInfo = this.getClass().getName() + ":insertRole:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //添加角色
            tdHtRoleDto.setCreater(tdHtAdmin.getUser_name());
            tdHtRoleDto.setIs_valid(0);
            tdHtRoleRepository.insertRole(tdHtRoleDto);

            //添加角色权限
            for (int a = 0; a < tdHtRoleDto.getFunctions().length; a++) {
                TdHtFuncroleRelDto tdHtFuncroleRelDto = new TdHtFuncroleRelDto();
                tdHtFuncroleRelDto.setCreater(tdHtAdmin.getUser_name());
                tdHtFuncroleRelDto.setFunc_id(tdHtRoleDto.getFunctions()[a]);
                tdHtFuncroleRelDto.setIs_valid(0);
                tdHtFuncroleRelDto.setRole_id(tdHtRoleDto.getRole_id());

                tdHtFuncroleRelRepository.insertFuncroleRel(tdHtFuncroleRelDto);
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

    @Override
    public RetInfo updateRole(TdHtAdminDto tdHtAdmin, TdHtRoleDto tdHtRoleDto) {
        String logInfo = this.getClass().getName() + ":updateRole:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            //修改角色
            tdHtRoleRepository.updateRole(tdHtRoleDto);
            //修改角色权限全为不可用
            tdHtFuncroleRelRepository.updateFuncroleRelByRoleId(tdHtRoleDto.getRole_id());
            //循环添加角色 先判断是否存在存在改为可用不存在添加
            //添加角色权限
            Map<String,Integer> map = new HashMap<>();
            map.put("role_id", tdHtRoleDto.getRole_id());
            for (int a = 0; a < tdHtRoleDto.getFunctions().length; a++) {

                map.put("func_id", tdHtRoleDto.getFunctions()[a]);
                TdHtFuncroleRelDto funcroleRelDto = tdHtFuncroleRelRepository.findFuncroleRelByRoleIdAndFuncId(map);
                if (funcroleRelDto != null) {
                    funcroleRelDto.setIs_valid(0);
                    tdHtFuncroleRelRepository.updateFuncroleRel(funcroleRelDto);
                } else {
                    TdHtFuncroleRelDto tdHtFuncroleRelDto = new TdHtFuncroleRelDto();
                    tdHtFuncroleRelDto.setCreater(tdHtAdmin.getUser_name());
                    tdHtFuncroleRelDto.setFunc_id(tdHtRoleDto.getFunctions()[a]);
                    tdHtFuncroleRelDto.setIs_valid(0);
                    tdHtFuncroleRelDto.setRole_id(tdHtRoleDto.getRole_id());

                    tdHtFuncroleRelRepository.insertFuncroleRel(tdHtFuncroleRelDto);
                }

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

    @Override
    public List<TdHtRoleDto> findRoles(TdHtAdminDto tdHtAdmin) {
        String logInfo = this.getClass().getName() + ":findRoles:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        List<TdHtRoleDto> tdHtRoleDtos = null;
        try {

            tdHtRoleDtos = tdHtRoleRepository.findAllRole(tdHtAdmin);
            if (tdHtRoleDtos != null && tdHtRoleDtos.size() > 0) {
                for (int i =0 ; i < tdHtRoleDtos.size() ; i ++) {
                    Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                    Map<String, Object> divideMap =(Map<String, Object>) MemcachedUtils.get(MemcachedKey.DIVIDE_DATA_MAP);
                    String address = "";
                    if (tdHtRoleDtos.get(i).getProvince_id() == 0) {
                        address = "全国";
                    }
                    if (tdHtRoleDtos.get(i).getProvince_id() != 0) {
                        String province = dicts.get("" + tdHtRoleDtos.get(i).getProvince_id()).getCode_name();
                        address = province;

                    }
                    if (tdHtRoleDtos.get(i).getCity_id() != 0) {
                        String city = dicts.get("" + tdHtRoleDtos.get(i).getCity_id()).getCode_name();
                        address = address + city;
                    }
                    if (tdHtRoleDtos.get(i).getRegion_id() != 0) {
                        String area = dicts.get("" + tdHtRoleDtos.get(i).getRegion_id()).getCode_name();
                        address = address + area;
                    }
                    if (tdHtRoleDtos.get(i).getDivide_id() != 0) {
                        DivideDto divideDto = (DivideDto)divideMap.get("" + tdHtRoleDtos.get(i).getDivide_id());
                        String divide = divideDto.getDivide_name();
                        address = address + divide;
                    }
                    tdHtRoleDtos.get(i).setAddress(address);
                }
            }

        } catch (Exception e) {

            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return tdHtRoleDtos;
    }

    @Override
    public TdHtRoleDto findRoleById(int role_id) {
        return tdHtRoleRepository.findRoleById(role_id);
    }

    public void setTdHtRoleRepository(TdHtRoleRepository tdHtRoleRepository) {
        this.tdHtRoleRepository = tdHtRoleRepository;
    }

    public void setTdHtFuncroleRelRepository(TdHtFuncroleRelRepository tdHtFuncroleRelRepository) {
        this.tdHtFuncroleRelRepository = tdHtFuncroleRelRepository;
    }
}
