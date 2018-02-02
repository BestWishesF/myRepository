package cn.hotol.app.service.funcion;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.bean.dto.funcion.TdHtFuncionDto;
import cn.hotol.app.bean.dto.funcion.TdHtFuncroleRelDto;
import cn.hotol.app.bean.dto.role.TdHtRoleDto;
import cn.hotol.app.repository.TdHtFuncionRepository;
import cn.hotol.app.repository.TdHtFuncroleRelRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizhun on 2017/4/12.
 */
public class FuncionServiceImpl implements FuncionService {

    private static Logger logger = Logger.getLogger(FuncionService.class);

    private TdHtFuncionRepository tdHtFuncionRepository;
    private TdHtFuncroleRelRepository tdHtFuncroleRelRepository;

    @Override
    public RetInfo findFatherFuncion(TdHtAdminDto tdHtAdmin) {
        String logInfo = this.getClass().getName() + ":findFatherFuncion:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> result = new HashMap<>();
            List<TdHtFuncionDto> tdHtFuncionDtos = tdHtFuncionRepository.findFatherFuncion(tdHtAdmin.getRole_id());
            result.put("fatherFuncion", tdHtFuncionDtos);
            Map<String, Object> params = new HashMap<>();
            params.put("father_id", tdHtFuncionDtos.get(0).getFunc_id());
            params.put("role_id", tdHtAdmin.getRole_id());
            List<TdHtFuncionDto> sonFuncion = getFuncions(params);

            result.put("sonFuncion", sonFuncion);
            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(result);

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo findSonFuncion(TdHtAdminDto tdHtAdmin, int father_id) {
        String logInfo = this.getClass().getName() + ":findSonFuncion:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String, Object> result = new HashMap<>();
            Map<String, Object> params = new HashMap<>();
            params.put("father_id", father_id);
            params.put("role_id", tdHtAdmin.getRole_id());
            List<TdHtFuncionDto> sonFuncion = getFuncions(params);
            result.put("sonFuncion", sonFuncion);
            result.put("father_id", father_id);
            retInfo.setMark("0");
            retInfo.setTip("成功");
            retInfo.setObj(result);

        } catch (Exception e) {
            retInfo.setMark("-1");
            retInfo.setTip("出错了");
            logger.error("======error:" + e.toString() + "======");
        }
        logger.info("======" + logInfo + "end======");
        return retInfo;
    }

    @Override
    public RetInfo findFuncionByRole(TdHtAdminDto tdHtAdmin, TdHtRoleDto tdHtRoleDto) {
        String logInfo = this.getClass().getName() + ":findFuncionByRole:";
        // 类路径+方法名
        logger.info("======" + logInfo + "begin======");
        RetInfo retInfo = new RetInfo();
        try {
            Map<String,Integer> map = new HashMap<>();
            if (tdHtRoleDto != null) {
                map.put("role_id", tdHtRoleDto.getRole_id());
            }


            List<TdHtFuncionDto> tdHtFuncionDtos = tdHtFuncionRepository.findFatherFuncion(tdHtAdmin.getRole_id());
            for (int i = 0 ; i < tdHtFuncionDtos.size() ; i ++ ) {
                Map<String, Object> params = new HashMap<>();
                params.put("father_id", tdHtFuncionDtos.get(i).getFunc_id());
                params.put("role_id", tdHtAdmin.getRole_id());
                List<TdHtFuncionDto> sonFuncion = tdHtFuncionRepository.findSonFuncion(params);
                if (sonFuncion != null && sonFuncion.size() > 0) {
                    tdHtFuncionDtos.get(i).setIs_father(0);
                    for (int j = 0 ; j < sonFuncion.size() ; j ++ ) {
                        params.put("father_id", sonFuncion.get(j).getFunc_id());
                        List<TdHtFuncionDto> sonSonFuncion = tdHtFuncionRepository.findSonFuncion(params);
                        if (sonSonFuncion != null && sonSonFuncion.size() > 0) {
                            sonFuncion.get(j).setIs_father(0);
                            for (int q = 0 ; q < sonSonFuncion.size() ; q ++ ) {
                                params.put("father_id", sonSonFuncion.get(q).getFunc_id());
                                List<TdHtFuncionDto> sonSonSonFuncion = tdHtFuncionRepository.findSonFuncion(params);
                                if (sonSonSonFuncion != null && sonSonSonFuncion.size() > 0) {
                                    sonSonFuncion.get(q).setIs_father(0);
                                    for (int r = 0 ; r < sonSonSonFuncion.size() ; r ++ ) {
                                        params.put("father_id", sonSonSonFuncion.get(r).getFunc_id());
                                        List<TdHtFuncionDto> sonSonSonSonFuncion = tdHtFuncionRepository.findSonFuncion(params);
                                        if (sonSonSonSonFuncion != null && sonSonSonSonFuncion.size() > 0) {
                                            sonSonSonFuncion.get(r).setIs_father(0);
                                            for (int p = 0 ; p < sonSonSonSonFuncion.size() ; p ++ ) {
                                                params.put("father_id", sonSonSonSonFuncion.get(p).getFunc_id());
                                                List<TdHtFuncionDto> sonSonSonSonSonFuncion = tdHtFuncionRepository.findSonFuncion(params);
                                                if (sonSonSonSonSonFuncion != null && sonSonSonSonSonFuncion.size() > 0) {
                                                    sonSonSonSonFuncion.get(p).setIs_father(0);

                                                    sonSonSonSonFuncion.get(p).setTdHtFuncionDtos(sonSonSonSonSonFuncion);
                                                }
                                                //判断该角色是否有该项权限
                                                if (tdHtRoleDto != null) {
                                                    map.put("func_id", sonSonSonSonFuncion.get(p).getFunc_id());
                                                    TdHtFuncroleRelDto funcroleRelDto = tdHtFuncroleRelRepository.findFuncroleRelByRoleIdAndFuncId(map);
                                                    if (funcroleRelDto != null) {
                                                        sonSonSonSonFuncion.get(p).setIs_check(0);
                                                    }
                                                }
                                            }

                                            sonSonSonFuncion.get(r).setTdHtFuncionDtos(sonSonSonSonFuncion);
                                        }
                                        //判断该角色是否有该项权限
                                        if (tdHtRoleDto != null) {
                                            System.out.println("循环获取权限id:" + sonSonSonFuncion.get(r).getFunc_id());
                                            map.put("func_id", sonSonSonFuncion.get(r).getFunc_id());
                                            TdHtFuncroleRelDto funcroleRelDto = tdHtFuncroleRelRepository.findFuncroleRelByRoleIdAndFuncId(map);
                                            if (funcroleRelDto != null) {
                                                sonSonSonFuncion.get(r).setIs_check(0);
                                            }
                                        }
                                    }

                                    sonSonFuncion.get(q).setTdHtFuncionDtos(sonSonSonFuncion);
                                }
                                //判断该角色是否有该项权限
                                if (tdHtRoleDto != null) {
                                    map.put("func_id", sonSonFuncion.get(q).getFunc_id());
                                    TdHtFuncroleRelDto funcroleRelDto = tdHtFuncroleRelRepository.findFuncroleRelByRoleIdAndFuncId(map);
                                    if (funcroleRelDto != null) {
                                        sonSonFuncion.get(q).setIs_check(0);
                                    }
                                }
                            }

                            sonFuncion.get(j).setTdHtFuncionDtos(sonSonFuncion);
                        }
                        //判断该角色是否有该项权限
                        if (tdHtRoleDto != null) {
                            map.put("func_id", sonFuncion.get(j).getFunc_id());
                            TdHtFuncroleRelDto funcroleRelDto = tdHtFuncroleRelRepository.findFuncroleRelByRoleIdAndFuncId(map);
                            if (funcroleRelDto != null) {
                                sonFuncion.get(j).setIs_check(0);
                            }
                        }
                    }

                    tdHtFuncionDtos.get(i).setTdHtFuncionDtos(sonFuncion);
                }
                //判断该角色是否有该项权限
                if (tdHtRoleDto != null) {
                    map.put("func_id", tdHtFuncionDtos.get(i).getFunc_id());
                    TdHtFuncroleRelDto funcroleRelDto = tdHtFuncroleRelRepository.findFuncroleRelByRoleIdAndFuncId(map);
                    if (funcroleRelDto != null) {
                        tdHtFuncionDtos.get(i).setIs_check(0);
                    }
                }
            }

            retInfo.setObj(tdHtFuncionDtos);

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

    public  List<TdHtFuncionDto> getFuncions(Map<String, Object> params) {
        List<TdHtFuncionDto> sonFuncion = tdHtFuncionRepository.findSonFuncion(params);
        for ( int i = 0 ; i < sonFuncion.size() ; i ++ ) {
            params.put("father_id", sonFuncion.get(i).getFunc_id());
            List<TdHtFuncionDto> funcionDtos = tdHtFuncionRepository.findSonFuncion(params);
            if (funcionDtos != null && funcionDtos.size() > 0) {
                sonFuncion.get(i).setIs_father(0);
                sonFuncion.get(i).setTdHtFuncionDtos(funcionDtos);
            }

        }
        return sonFuncion;
    }

    public void setTdHtFuncionRepository(TdHtFuncionRepository tdHtFuncionRepository) {
        this.tdHtFuncionRepository = tdHtFuncionRepository;
    }

    public void setTdHtFuncroleRelRepository(TdHtFuncroleRelRepository tdHtFuncroleRelRepository) {
        this.tdHtFuncroleRelRepository = tdHtFuncroleRelRepository;
    }
}
