package cn.hotol.app.controller.three.order;

import cn.hotol.app.base.RetInfo;
import cn.hotol.app.bean.TdHtExpressOrder;
import cn.hotol.app.bean.dto.page.PageDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderCollectDto;
import cn.hotol.app.bean.dto.expressorder.TdHtExpressOrderDto;
import cn.hotol.app.bean.dto.storage.WaitStorageOrdDetailDto;
import cn.hotol.app.bean.dto.take.TakeOrderDto;
import cn.hotol.app.service.three.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by LuBin
 * Date 2016-12-07.
 */

@Controller
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * @param pageDto
     * @Purpose 查询待支付订单
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findToBePaidExp", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findToBePaidExp(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.findToBePaidExp(pageDto, token);
    }

    /**
     * @param pageDto
     * @Purpose 查询受理中订单
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findAcceptInExp", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAcceptInExp(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.findAcceptInExp(pageDto, token);
    }

    /**
     * @param pageDto
     * @Purpose 查询投递中快件
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findDeliveryExp", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findDeliveryExp(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.findDeliveryExp(pageDto, token);
    }

    /**
     * @param pageDto
     * @Purpose 查询已完成快件
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findHasBeenFinishExp", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findHasBeenFinishExp(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.findHasBeenFinishExp(pageDto, token);
    }

    /**
     * @param tdHtExpressOrder
     * @Purpose 查询受理中订单明细
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findAcceptOrderDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findAcceptOrderDetail(@RequestBody TdHtExpressOrder tdHtExpressOrder) {
        return orderService.findAcceptOrderDetail(tdHtExpressOrder);
    }

    /**
     * @param tdHtExpressOrderCollectDto
     * @Purpose 查询快件明细
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/findExpDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findExpDetail(@RequestBody TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto) {
        return orderService.findExpDetail(tdHtExpressOrderCollectDto);
    }

    /**
     * @param tdHtExpressOrderDto
     * @Purpose 用户取消为受理的订单
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/cancelOrder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo cancelOrder(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.cancelOrder(tdHtExpressOrderDto);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 代理人查询待接单列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/waitForAccess", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo waitForAccess(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.waitForAccess(pageDto, token);
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 代理人查询待接单订单详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/waitForAccessDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo waitForAccessDetail(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.waitForAccessDetail(tdHtExpressOrderDto.getExp_ord_id());
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 代理人接单
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/accessExpressOder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo accessExpressOder(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.accessExpressOder(tdHtExpressOrderDto.getExp_ord_id(), token);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 代理人查询待揽件列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/waitForTake", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo waitForTake(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.waitForTake(pageDto, token);
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 代理人输入备注
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/writeAgentNote", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo writeAgentNote(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.writeAgentNote(tdHtExpressOrderDto, token);
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 代理人揽件界面数据
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/takeCollectDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo takeCollectDetail(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.takeCollectDetail(tdHtExpressOrderDto.getExp_ord_id());
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 查询待揽件订单详细信息
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/waitForTakeDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo waitForTakeDetail(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.waitForTakeDetail(tdHtExpressOrderDto.getExp_ord_id());
    }

    /**
     * @param takeOrderDto
     * @return
     * @Purpose 代理人揽件
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/takeExpressOrder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo takeExpressOrder(@RequestBody TakeOrderDto takeOrderDto, @RequestHeader(value = "token", required = true) String token,
                                    @RequestHeader(value = "version", required = true) Integer version,
                                    @RequestHeader(value = "client_type", required = true) Integer client_type) {
        return orderService.takeExpressOrder(takeOrderDto, token, version, client_type);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 代理人查询待入库列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/waitForStorage", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo waitForStorage(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.waitForStorage(pageDto, token);
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 代理人查询待入库订单详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/waitStorageDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo waitStorageDetail(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.waitStorageDetail(tdHtExpressOrderDto.getExp_ord_id());
    }

    /**
     * @param waitStorageOrdDetailDto
     * @return
     * @Purpose 代理人入库
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/storageExpressOrder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo storageExpressOrder(@RequestBody WaitStorageOrdDetailDto waitStorageOrdDetailDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.storageExpressOrder(waitStorageOrdDetailDto, token);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 代理人查询已完成列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/findFinishOrder", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findFinishOrder(@RequestBody PageDto pageDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.findFinishOrder(pageDto, token);
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 代理人查询已完成订单详情
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/agentToken/findFinishDetail", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findFinishDetail(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.findFinishDetail(tdHtExpressOrderDto.getExp_ord_id());
    }

    /**
     * @param tdHtExpressOrderDto
     * @return
     * @Purpose 用户修改快递公司
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/modifyExpressCompany", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyExpressCompany(@RequestBody TdHtExpressOrderDto tdHtExpressOrderDto) {
        return orderService.modifyExpressCompany(tdHtExpressOrderDto);
    }

    /**
     * @param tdHtExpressOrderCollectDto
     * @return
     * @Purpose 月结用户修改厚通订单号
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/3/token/modifyExpressHtNumber", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo modifyExpressHtNumber(@RequestBody TdHtExpressOrderCollectDto tdHtExpressOrderCollectDto, @RequestHeader(value = "token", required = true) String token) {
        return orderService.modifyExpressHtNumber(tdHtExpressOrderCollectDto, token);
    }

    /**
     * @param pageDto
     * @return
     * @Purpose 分页查询用户未开票的订单列表
     * @version 3.0
     * @author lubin
     */
    @RequestMapping(value = "/app/6/token/findMembCanInvoiceExpOrd", method = {RequestMethod.GET, RequestMethod.POST}, consumes = "application/json")
    @ResponseBody
    public RetInfo findMembCanInvoiceExpOrd(@RequestBody PageDto pageDto,
                                            @RequestHeader(value = "token", required = true) String token) {
        return orderService.findMembCanInvoiceExpOrd(pageDto, token);
    }
}
