package cn.hotol.app.service.admin;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.admin.divide.DivideDto;
import cn.hotol.app.bean.dto.location.TsHtDictDto;
import cn.hotol.app.bean.dto.log.TdHtLoginLogDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.CommonUtil;
import cn.hotol.app.common.util.MD5;
import cn.hotol.app.common.util.MemcachedKey;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.repository.*;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2016/12/9.
 */
public class AdminServiceImpl implements AdminService {
    private static Logger logger = Logger.getLogger(AdminServiceImpl.class);
    private TdHtAdminRepository tdHtAdminRepository;
    private TdHtLoginLogRepository tdHtLoginLogRepository;
    private TdHtAdminDivideRepository tdHtAdminDivideRepository;
    private TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository;
    private TdHtRoleRepository tdHtRoleRepository;
    private TsHtDictRepository tsHtDictRepository;
    private TdHtExpressOrderRepository tdHtExpressOrderRepository;

    @Transactional
    @Override
    public RetInfo login(TdHtAdminDto tdHtAdminDto, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":login:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        HttpSession session = request.getSession(false);
        try {
            TdHtAdminDto admin = tdHtAdminRepository.findAdminByUserName(tdHtAdminDto.getUser_name());

            if (admin != null) {
                TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                tdHtLoginLogDto.setLogin_ip(tdHtAdminDto.getLogin_ip());
                tdHtLoginLogDto.setLogin_type(4);
                tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                tdHtLoginLogDto.setUser_id(admin.getAdmin_id());
                tdHtLoginLogDto.setUser_name(admin.getUser_name());
                tdHtLoginLogDto.setUser_type(3);
                if (admin.getUser_pass().equals(MD5.code(tdHtAdminDto.getUser_pass()))) {
                    //更新用户登录ip
                    admin.setLogin_ip(tdHtAdminDto.getLogin_ip());
                    admin.setLogin_client(4);
                    tdHtAdminRepository.updateAdminLoginIp(admin);
                    //存放session
                    tdHtLoginLogDto.setIs_success('0');
                    tdHtLoginLogDto.setLogin_failer_desc("登陆成功");
                    session.setAttribute(Constant.USERLOGINSESSION, admin);
                    retInfo.setMark("0");
                    retInfo.setTip("登陆成功");
                } else {

                    retInfo.setMark("1");
                    retInfo.setTip("密码不对");
                    tdHtLoginLogDto.setIs_success('1');
                    tdHtLoginLogDto.setLogin_failer_desc("密码不对");
                }
                //添加登录记录
                tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("账号不存在");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo updatePsd(TdHtAdminDto tdHtAdminDto, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":updatePsd:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            TdHtAdminDto admin = tdHtAdminRepository.findAdminByUserName(tdHtAdminDto.getUser_name());
            if (admin.getUser_pass().equals(MD5.code(tdHtAdminDto.getOld_user_pass()))) {
                admin.setUser_pass(MD5.code(tdHtAdminDto.getUser_pass()));
                tdHtAdminRepository.updateAdminPsd(admin);
                retInfo.setMark("0");
                retInfo.setTip("成功");
            } else {
                retInfo.setMark("1");
                retInfo.setTip("原密码不对");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtAdminDto
     * @return RetInfo
     * @Purpose app端登陆
     * @version 3.0
     * @author lubin
     */
    @Transactional
    @Override
    public RetInfo appLogin(TdHtAdminDto tdHtAdminDto, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":appLogin:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        HttpSession session = request.getSession(true);
        try {
            TdHtAdminDto admin = tdHtAdminRepository.findAdminByUserName(tdHtAdminDto.getUser_name());

            if (admin != null) {
                TdHtLoginLogDto tdHtLoginLogDto = new TdHtLoginLogDto();
                tdHtLoginLogDto.setLogin_date(CommonUtil.getMonth());
                tdHtLoginLogDto.setLogin_ip(tdHtAdminDto.getLogin_ip());
                tdHtLoginLogDto.setLogin_type(tdHtAdminDto.getLogin_client());
                tdHtLoginLogDto.setLogin_time(new Timestamp(System.currentTimeMillis()));
                tdHtLoginLogDto.setUser_id(admin.getAdmin_id());
                tdHtLoginLogDto.setUser_name(admin.getUser_name());
                tdHtLoginLogDto.setUser_type(3);
                if (admin.getUser_pass().equals(MD5.code(tdHtAdminDto.getUser_pass()))) {
                    MemcachedUtils.delete(admin.getToken());
                    //更新用户登录ip
                    admin.setLogin_ip(tdHtAdminDto.getLogin_ip());
                    admin.setLogin_client(tdHtAdminDto.getLogin_client());
                    admin.setToken(CommonUtil.getToken());
                    tdHtAdminRepository.updateAdminLoginIp(admin);
                    //存放session
                    tdHtLoginLogDto.setIs_success('0');
                    tdHtLoginLogDto.setLogin_failer_desc("登陆成功");
                    MemcachedUtils.add(admin.getToken(), admin, new Date(20 * 24 * 60 * 60 * 1000));
                    retInfo.setMark("0");
                    retInfo.setTip("登陆成功");

                    Map map = new HashMap();
                    map.put("token", admin.getToken());
                    map.put("role", admin.getRole_id());
                    if (admin.getRole_id() == 1 || admin.getRole_id() == 2) {
                        map.put("dict_id", 0);
                    } else if (admin.getRole_id() == 3) {
                        map.put("dict_id", admin.getProvince_id());
                    } else if (admin.getRole_id() == 4) {
                        map.put("dict_id", admin.getCity_id());
                    } else if (admin.getRole_id() == 5) {
                        map.put("dict_id", admin.getRegion_id());
                    } else if (admin.getRole_id() == 6) {
                        map.put("dict_id", admin.getDivide_id());
                    }
                    retInfo.setObj(map);
                } else {
                    retInfo.setMark("1");
                    retInfo.setTip("密码不对");
                    tdHtLoginLogDto.setIs_success('1');
                    tdHtLoginLogDto.setLogin_failer_desc("密码不对");
                }
                //添加登录记录
                tdHtLoginLogRepository.insertLoginLog(tdHtLoginLogDto);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("账号不存在");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtAdminDto
     * @return RetInfo
     * @Purpose app端修改密码
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo appUpdatePsd(TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":appUpdatePsd:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            TdHtAdminDto admin = tdHtAdminRepository.findAdminByUserName(tdHtAdminDto.getUser_name());
            if (admin.getUser_pass().equals(MD5.code(tdHtAdminDto.getOld_user_pass()))) {
                admin.setUser_pass(MD5.code(tdHtAdminDto.getUser_pass()));
                tdHtAdminRepository.updateAdminPsd(admin);
                retInfo.setMark("0");
                retInfo.setTip("成功");
            } else {
                retInfo.setMark("1");
                retInfo.setTip("原密码不对");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param currentPage
     * @return RetInfo
     * @Purpose 分页查询管理员帐号
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo findAdminPage(int currentPage, int pageSize, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":findAdminPage:";
        RetInfo retInfo = new RetInfo();
        HttpSession session = request.getSession(false);
        logger.info("======" + logInfo + "begin======");
        try {
            TdHtAdminDto adminDto = (TdHtAdminDto)session.getAttribute(Constant.USERLOGINSESSION);
            int totalRecord = tdHtAdminRepository.findAllAdminSize(adminDto);//总条数
            if (totalRecord > 0) {
                Map<String, Object> map = CommonUtil.page(totalRecord, currentPage, pageSize);

                map.put("admin_id", adminDto.getAdmin_id());
                map.put("city_id", adminDto.getCity_id());
                map.put("divide_id", adminDto.getDivide_id());
                map.put("region_id", adminDto.getRegion_id());
                map.put("province_id", adminDto.getProvince_id());

                List<TdHtAdminDto> tdHtAdminDtos = tdHtAdminRepository.findAllAdminPage(map);

                for (int i =0 ; i < tdHtAdminDtos.size() ; i ++) {
                    tdHtAdminDtos.get(i).setRole_name(tdHtRoleRepository.findRoleById(tdHtAdminDtos.get(i).getRole_id()).getRole_name());
                    Map<String, TsHtDictDto> dicts = (Map<String, TsHtDictDto>) MemcachedUtils.get(MemcachedKey.DICTS_MAP);
                    Map<String, Object> divideMap =(Map<String, Object>) MemcachedUtils.get(MemcachedKey.DIVIDE_DATA_MAP);
                    String address = "";
                    if (tdHtAdminDtos.get(i).getProvince_id() == 0) {
                        address = "全国";
                    }
                    if (tdHtAdminDtos.get(i).getProvince_id() != 0) {
                        String province = dicts.get("" + tdHtAdminDtos.get(i).getProvince_id()).getCode_name();
                        address = province;

                    }
                    if (tdHtAdminDtos.get(i).getCity_id() != 0) {
                        String city = dicts.get("" + tdHtAdminDtos.get(i).getCity_id()).getCode_name();
                        address = address + city;
                    }
                    if (tdHtAdminDtos.get(i).getRegion_id() != 0) {
                        String area = dicts.get("" + tdHtAdminDtos.get(i).getRegion_id()).getCode_name();
                        address = address + area;
                    }
                    if (tdHtAdminDtos.get(i).getDivide_id() != 0) {
                        DivideDto divideDto = (DivideDto)divideMap.get("" + tdHtAdminDtos.get(i).getDivide_id());
                        String divide = divideDto.getDivide_name();
                        address = address + divide;
                    }
                    tdHtAdminDtos.get(i).setAddress(address);
                }
                map.put("admins", tdHtAdminDtos);
                map.put("currentPage", currentPage);
                retInfo.setMark("0");
                retInfo.setTip("成功");
                retInfo.setObj(map);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("暂无您要查找的结果");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("totalPage", 0);
                params.put("totalRecord", 0);
                params.put("currentPage", 1);
                retInfo.setObj(params);
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtAdminDto
     * @return RetInfo
     * @Purpose 创建新的管理员帐号
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo insertAdmin(TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":insertAdmin:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {


            TdHtAdminDto adminDto = tdHtAdminRepository.findAdminByUserName(tdHtAdminDto.getUser_name());
            tdHtAdminDto.setUser_pass(MD5.code(tdHtAdminDto.getUser_pass()));
            tdHtAdminDto.setIs_valid(0);
            tdHtAdminDto.setToken(CommonUtil.getToken());
            TdHtRoleDto tdHtRoleDto = tdHtRoleRepository.findRoleById(tdHtAdminDto.getRole_id());
            tdHtAdminDto.setProvince_id(tdHtRoleDto.getProvince_id());
            tdHtAdminDto.setCity_id(tdHtRoleDto.getCity_id());
            tdHtAdminDto.setRegion_id(tdHtRoleDto.getRegion_id());
            tdHtAdminDto.setDivide_id(tdHtRoleDto.getDivide_id());
            if (adminDto == null) {
                tdHtAdminDto.setLogin_ip("");
                tdHtAdminDto.setLogin_client(4);
                tdHtAdminDto.setReg_time(new Timestamp(System.currentTimeMillis()));
                tdHtAdminDto.setLogin_time(tdHtAdminDto.getReg_time());
                tdHtAdminRepository.insertAdmin(tdHtAdminDto);
                retInfo.setMark("0");
                retInfo.setTip("创建成功.");
            } else {
                if (adminDto.getIs_valid() == 0) {
                    retInfo.setMark("1");
                    retInfo.setTip("帐号已存在.");
                } else if (adminDto.getIs_valid() == 1) {
                    tdHtAdminDto.setAdmin_id(adminDto.getAdmin_id());
                    tdHtAdminRepository.updateAdmin(tdHtAdminDto);
                    MemcachedUtils.delete(tdHtAdminDto.getToken());
                    retInfo.setMark("0");
                    retInfo.setTip("创建成功.");
                }
            }

        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param tdHtAdminDto
     * @return RetInfo
     * @Purpose 修改管理员帐号
     * @version 3.0
     * @author lubin
     */
    @Override
    public RetInfo updateAdmin(TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":updateAdmin:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            TdHtAdminDto adminDto = tdHtAdminRepository.findAdminById(tdHtAdminDto.getAdmin_id());
            if (adminDto != null) {
                TdHtRoleDto tdHtRoleDto = tdHtRoleRepository.findRoleById(tdHtAdminDto.getRole_id());
                tdHtAdminDto.setProvince_id(tdHtRoleDto.getProvince_id());
                tdHtAdminDto.setCity_id(tdHtRoleDto.getCity_id());
                tdHtAdminDto.setRegion_id(tdHtRoleDto.getRegion_id());
                tdHtAdminDto.setDivide_id(tdHtRoleDto.getDivide_id());

                tdHtAdminDto.setUser_pass(adminDto.getUser_pass());
                tdHtAdminDto.setIs_valid(0);
                tdHtAdminDto.setToken(CommonUtil.getToken());
                tdHtAdminDto.setUser_name(adminDto.getUser_name());
                tdHtAdminRepository.updateAdmin(tdHtAdminDto);
                MemcachedUtils.delete(tdHtAdminDto.getToken());
                retInfo.setMark("0");
                retInfo.setTip("修改成功.");

            } else {
                retInfo.setMark("1");
                retInfo.setTip("帐号不存在.");
            }
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @param admin_id
     * @return RetInfo
     * @Purpose 跳转操作管理员帐号页面
     * @version 3.0
     * @author lubin
     */
    @Override
    public TdHtAdminDto findAdminById(int admin_id, HttpServletRequest request) {
        String logInfo = this.getClass().getName() + ":findAdminByid:";
        RetInfo retInfo = new RetInfo();
        TdHtAdminDto admin = null;
        logger.info("======" + logInfo + "begin======");
        try {
            admin = tdHtAdminRepository.findAdminById(admin_id);
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return admin;
    }

    /**
     * @Purpose  重置管理员帐号密码
     * @version  3.0
     * @author   lubin
     * @param    tdHtAdminDto
     * @return   RetInfo
     */
    @Override
    public RetInfo resetPass(TdHtAdminDto tdHtAdminDto) {
        String logInfo = this.getClass().getName() + ":resetPass:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            tdHtAdminDto.setUser_pass(MD5.code(tdHtAdminDto.getUser_pass()));
            tdHtAdminRepository.updateAdminPsd(tdHtAdminDto);
            retInfo.setMark("0");
            retInfo.setTip("成功.");
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    /**
     * @Purpose  注销帐号
     * @version  3.0
     * @author   lubin
     * @param    admin_id
     * @return   RetInfo
     */
    @Override
    public RetInfo cancelAccounts(int admin_id) {
        String logInfo = this.getClass().getName() + ":resetPass:";
        RetInfo retInfo = new RetInfo();
        logger.info("======" + logInfo + "begin======");
        try {
            TdHtAdminDto adminDtoDB=tdHtAdminRepository.findAdminById(admin_id);
            tdHtAdminRepository.updateAdminValid(admin_id);
            if(adminDtoDB.getDivide_id()>0){
                tdHtAdminDivideRepository.updateDivideState(adminDtoDB.getDivide_id());
                tdHtAdminDivideGridRepository.updateGridStateByDivide(adminDtoDB.getDivide_id());
            }
            retInfo.setMark("0");
            retInfo.setTip("成功.");
        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");
            retInfo.setMark("-1");
            retInfo.setTip("系统错误");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public TdHtAdminDto findAdminByName(String user_name) {
        String logInfo = this.getClass().getName() + ":findAdminByName:";
        TdHtAdminDto tdHtAdminDto = null;
        logger.info("======" + logInfo + "begin======");
        try {
            tdHtAdminDto = tdHtAdminRepository.findAdminByUserName(user_name);

        } catch (Exception e) {
            logger.error("======error:" + e.toString() + "======");

        }
        logger.info("======" + logInfo + "end======");
        return tdHtAdminDto;
    }

    public void setTdHtAdminRepository(TdHtAdminRepository tdHtAdminRepository) {
        this.tdHtAdminRepository = tdHtAdminRepository;
    }

    public void setTdHtLoginLogRepository(TdHtLoginLogRepository tdHtLoginLogRepository) {
        this.tdHtLoginLogRepository = tdHtLoginLogRepository;
    }

    public void setTdHtAdminDivideRepository(TdHtAdminDivideRepository tdHtAdminDivideRepository) {
        this.tdHtAdminDivideRepository = tdHtAdminDivideRepository;
    }

    public void setTdHtAdminDivideGridRepository(TdHtAdminDivideGridRepository tdHtAdminDivideGridRepository) {
        this.tdHtAdminDivideGridRepository = tdHtAdminDivideGridRepository;
    }

    public void setTdHtRoleRepository(TdHtRoleRepository tdHtRoleRepository) {
        this.tdHtRoleRepository = tdHtRoleRepository;
    }

    public void setTsHtDictRepository(TsHtDictRepository tsHtDictRepository) {
        this.tsHtDictRepository = tsHtDictRepository;
    }

    public void setTdHtExpressOrderRepository(TdHtExpressOrderRepository tdHtExpressOrderRepository) {
        this.tdHtExpressOrderRepository = tdHtExpressOrderRepository;
    }
}
