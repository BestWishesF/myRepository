package cn.hotol.app.controller.three.address;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtMembAddress;
import cn.hotol.app.bean.dto.address.SetDefaultAddressDto;
import cn.hotol.app.bean.dto.address.TdHtMembAddressDto;
import cn.hotol.app.bean.dto.member.TdHtMemberDto;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.common.util.MemcachedUtils;
import cn.hotol.app.service.three.address.MembAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by liyafei on 2016/12/2.
 */
@Controller
public class MembAddressController {
    @Resource
    private MembAddressService membAddressService;

    /**
     * @return
     * @Purpose 获取收发件地址信息列表
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/findAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAddress(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        TdHtMemberDto tdHtMemberDto = (TdHtMemberDto) MemcachedUtils.get(token);
        return membAddressService.findAddress(pageDto, tdHtMemberDto.getMemb_id());

    }

    /**
     * @return
     * @Purpose 更新收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/updateAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo updateAddress(@Valid @RequestBody TdHtMembAddress tdHtMembAddress, BindingResult result) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return membAddressService.updateAddress(tdHtMembAddress);
        }
        return retInfo;
    }

    /**
     * @return
     * @Purpose 删除收发件地址信息
     * @version 3.0
     * @author liyafei
     */
    @RequestMapping(value = "/app/3/token/deleteAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo deleteAddress(@RequestBody TdHtMembAddress tdHtMembAddress) {
        return membAddressService.deleteAddress(tdHtMembAddress);

    }

    /**
     * @return
     * @Purpose 设置默认地址
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/setDefault", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo setDefault(@RequestBody SetDefaultAddressDto setDefaultAddressDto, @RequestHeader(value = "token", required = true) String token) {
        return membAddressService.setDefault(setDefaultAddressDto, token);

    }

    /**
     * @param tdHtMembAddress
     * @return
     * @Purpose 保存收发件地址
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/saveAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo saveAddress(@Valid @RequestBody TdHtMembAddress tdHtMembAddress, BindingResult result,
                               @RequestHeader(value = "token", required = true) String token) {
        RetInfo retInfo = new RetInfo();
        if (result.hasErrors()) {
            List<FieldError> err = result.getFieldErrors();
            FieldError fe = err.get(0);
            retInfo.setMark("1");
            retInfo.setTip(fe.getDefaultMessage());
        } else {
            return membAddressService.saveAddress(tdHtMembAddress, token);
        }
        return retInfo;
    }

    /**
     * @param tdHtMembAddressDto
     * @return
     * @Purpose 通过地址id查询地址信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findOneAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findOneAddress(@RequestBody TdHtMembAddressDto tdHtMembAddressDto) {
        return membAddressService.findOneAddress(tdHtMembAddressDto);
    }

    /**
     * @param token
     * @return
     * @Purpose 查询用户的所有发件地址
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/5/token/findAllSendAddress", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAllSendAddress(@RequestHeader(value = "token", required = true) String token) {
        return membAddressService.findAllSendAddress(token);
    }


    /**
     * @Purpose   根据条件查询用户的收件地址
     * @version   3.0
     * @author    lubin
     * @time      2017-04-05
     * @param     pageDto  查询条件
     * @return    RetInfo
     */
    @RequestMapping(value = "/app/7/token/findMembAddByBean", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMembAddByBean(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return membAddressService.findMembAddByBean(pageDto, token);
    }
}
