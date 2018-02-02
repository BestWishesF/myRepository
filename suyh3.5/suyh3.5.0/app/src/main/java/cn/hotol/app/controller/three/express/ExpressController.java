package cn.hotol.app.controller.three.express;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.dto.courier.CourierDto;
import cn.hotol.app.bean.dto.expressorder.ExpressDto;
import cn.hotol.app.common.util.ExcelUtil;
import cn.hotol.app.service.three.sendexpress.ExpressService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyafei on 2016/12/3.
 */
@Controller
public class ExpressController {
    @Resource
    private ExpressService expressService;

    /**
     * @param expressDto
     * @return RetInfo
     * @Purpose 单个寄件
     * @version 3.0
     * @author liyafei
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/sendOneExpress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendOneExpress(@RequestBody ExpressDto expressDto, BindingResult result,
                                  @RequestHeader(value = "token", required = true) String token,
                                  @RequestHeader(value = "client_type", required = true) Integer client_type,
                                  @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return expressService.sendOneExpress(expressDto, token, client_type, version);
        }
        return retInfo;
    }

    /**
     * @param expressDto
     * @return RetInfo
     * @Purpose 批量寄件
     * @version 3.0
     * @author liyafei
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/sendMultiExpress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo sendMultiExpress(@RequestBody ExpressDto expressDto, BindingResult result,
                                    @RequestHeader(value = "token", required = true) String token,
                                    @RequestHeader(value = "client_type", required = true) Integer client_type,
                                    @RequestHeader(value = "version", required = true) Integer version) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return expressService.sendMultiExpress(expressDto, token, client_type, version);
        }
        return retInfo;
    }

    /**
     * @param courierDto
     * @return
     * @Purpose 查询用户发件界面数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findSendExpInfo", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findSendExpInfo(@RequestBody CourierDto courierDto, @RequestHeader(value = "token", required = true) String token,
                                   @RequestHeader(value = "version", required = true) Integer version) {
        return expressService.findSendExpInfo(courierDto, token, version);
    }

    /**
     * @param file
     * @return RetInfo
     * @Purpose 导入表格数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value="/app/5/token/uploadExpFile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RetInfo uploadExpFile(@RequestParam(value = "file", required = true) CommonsMultipartFile file,
                                 @RequestHeader(value = "token", required = true) String token) throws Exception {
        RetInfo retInfo=new RetInfo();
        if(file==null||file.isEmpty()){
            retInfo.setMark("1");
            retInfo.setTip("请选择上传的文件.");
        }else{
            String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
            if (!"".equals(postfix)) {
                if ("xls".equals(postfix)) {
                    retInfo=ExcelUtil.readXls(file.getInputStream());
                } else if ("xlsx".equals(postfix)) {
                    retInfo=ExcelUtil.readXlsx(file.getInputStream());
                }
            } else {
                retInfo.setMark("1");
                retInfo.setTip("文件格式不正确.");
            }
        }
        return retInfo;
    }

    /**
     * @param expressDto
     * @return RetInfo
     * @Purpose 大客户寄件
     * @version 3.0
     * @author
     * @author lubin
     */
    @RequestMapping(value = "/app/5/token/bigClientsSendExp", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo bigClientsSendExp(@RequestBody ExpressDto expressDto, BindingResult result,
                                     @RequestHeader(value = "token", required = true) String token,
                                     @RequestHeader(value = "client_type", required = true) Integer client_type) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return expressService.bigClientsSendExp(expressDto, token, client_type);
        }
        return retInfo;
    }
}
