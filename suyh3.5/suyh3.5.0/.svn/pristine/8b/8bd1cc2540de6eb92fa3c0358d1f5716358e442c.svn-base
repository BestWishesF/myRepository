package cn.hotol.app.controller.upload.excel;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.admin.TdHtAdminDto;
import cn.hotol.app.common.Constant;
import cn.hotol.app.common.util.ExcelUtil;
import cn.hotol.app.service.funcion.FuncionService;
import cn.hotol.app.service.upload.excel.UploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuBin
 * Date 2017-01-23.
 */
@Controller
public class UploadController {

    @Resource
    private UploadService uploadService;
    @Resource
    private FuncionService funcionService;

    /**
     * @return mv
     * @Purpose 跳转到表格导入页面
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value="/admin/express/order/upload/excel")
    public ModelAndView print(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("/upload/excel/upload");
        HttpSession session = request.getSession(false);
        TdHtAdminDto tdHtAdmin = (TdHtAdminDto) session.getAttribute(Constant.USERLOGINSESSION);
        mv.addObject("funcions", funcionService.findSonFuncion(tdHtAdmin, Integer.valueOf(request.getParameter("father_id"))));
        return mv;
    }

    /**
     * @param request
     * @return RetInfo
     * @Purpose 导入表格数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value="/admin/express/order/upload/excel/file")
    @ResponseBody
    public RetInfo uploadExcelModeOne(HttpServletRequest request) throws IOException {
        RetInfo retInfo=new RetInfo();

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        String exp_ord_time=multipartRequest.getParameter("exp_ord_time");
        String agent_phone=multipartRequest.getParameter("agent_phone");
        String member_phone=multipartRequest.getParameter("member_phone");
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        if(file!=null&&!file.isEmpty()){
            String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
            if ("xls".equals(postfix) || "xlsx".equals(postfix)) {
                List<Map<String, Object>> mapList=new ArrayList<>();
                if ("xls".equals(postfix)) {
                    RetInfo dataRetInfo=ExcelUtil.readXls(file.getInputStream(),exp_ord_time);
                    if("0".equals(dataRetInfo.getMark())){
                        mapList=(List<Map<String, Object>>)dataRetInfo.getObj();
                        retInfo=uploadService.uploadExpOrdData(mapList,exp_ord_time,agent_phone,member_phone);
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip(dataRetInfo.getTip());
                    }
                } else if ("xlsx".equals(postfix)) {
                    RetInfo dataRetInfo=ExcelUtil.readXlsx(file.getInputStream(),exp_ord_time);
                    if("0".equals(dataRetInfo.getMark())){
                        mapList=(List<Map<String, Object>>)dataRetInfo.getObj();
                        retInfo=uploadService.uploadExpOrdData(mapList,exp_ord_time,agent_phone,member_phone);
                    }else {
                        retInfo.setMark("1");
                        retInfo.setTip(dataRetInfo.getTip());
                    }
                }
                retInfo.setObj(mapList);
            } else {
                retInfo.setMark("1");
                retInfo.setTip("文件格式不正确.");
            }
        }else {
            retInfo.setMark("1");
            retInfo.setTip("请选择上传的文件.");
        }
        return retInfo;
    }
}
