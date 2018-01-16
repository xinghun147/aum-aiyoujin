package com.hjgj.aiyoujin.server.controller;

import com.hjgj.aiyoujin.core.common.OrderStatusEnum;
import com.hjgj.aiyoujin.core.common.utils.CommonUtils;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.UserOrderService;
import com.hjgj.aiyoujin.core.service.UserService;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = "/order")
public class OrderApiController {

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private UserService userService;

    /**
     * 查询用户的所有订单详情
     *
     * @param openId
     * @param types
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询用户的所有礼品卡详情")
    @ResponseBody
    @RequestMapping(value = "/getMyGiftCards", method = RequestMethod.POST)
    public ResultModel getUserAllOrdersByOpenId(@ApiParam(value = "用户OpenId", required = true) @RequestParam String openId,
                                                @ApiParam(value = "礼品类型", required = true) @RequestParam String types,
                                                @ApiParam(value = "第多少页", required = true) @RequestParam Integer pageNum,
                                                @ApiParam(value = "每页多少", required = true) @RequestParam Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 50 : pageSize;
        Page<OrderWebVo> allOrders = userOrderService.getUserAllOrders(openId, types, pageNum, pageSize);
        if (allOrders != null) {
            return ResultModel.ok(allOrders);
        } else {
            return ResultModel.ok(ResultStatus.ERROR_NOT_FIND_DATA);
        }
    }

    @ApiOperation(value = "发送礼品卡")
    @ResponseBody
    @RequestMapping(value = "/sendGiftCard", method = RequestMethod.POST)
    public ResultModel sendGiftCard(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(orderId, "orderId 不可为空");
//        String orderNo = CommonUtils.generateOrderNo("TF");
//        Date nowDate = new Date();
//        User byOpenId = userService.getUserByOpenId(openId);
//        Order orderById = userOrderService.getOrderById(orderId);
//        Order fromOrder = new Order();
//        fromOrder.setId(UUIDGenerator.generate());
//        fromOrder.setUserId(byOpenId.getId());
//        fromOrder.setBuyAmount(orderById.getBuyAmount());
//        fromOrder.setProductId(orderById.getProductId());
//        fromOrder.setCreateTime(nowDate);
//        fromOrder.setDeleted(0);
//        fromOrder.setFromOrderId(orderId);
//        fromOrder.setCode(orderNo);
        // 3送出待收、4已退回、5送出成功、6领取成功
//        fromOrder.setStatus(Integer.valueOf(3));
        int insertFromOrder = userOrderService.updateOrderStauts(orderId, OrderStatusEnum.ORDER_STATUS_UNRECEIVE.getCode());
        if (insertFromOrder > 0) {
            return ResultModel.ok("赠送成功");
        } else {
            return ResultModel.error(ResultStatus.ERROR_EMPTY_VAL_RETURNED);
        }

    }

    @ApiOperation(value = "接收礼品卡")
    @ResponseBody
    @RequestMapping(value = "/receiveGiftCard", method = RequestMethod.POST)
    public ResultModel receiveGiftCard(@ApiParam(value = "领取礼品用户OpenId", required = true) @RequestParam String openId,
                                       @ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(openId, "openId不可为空");
        Assert.notNull(orderId, "orderId 不可为空");
        String orderNo = CommonUtils.generateOrderNo("TF");
        Date nowDate = new Date();
        User byOpenId = userService.getUserByOpenId(openId);
        Order orderById = userOrderService.getOrderById(orderId);
        Order fromOrder = new Order();
        fromOrder.setId(UUIDGenerator.generate());
        fromOrder.setUserId(byOpenId.getId());
        fromOrder.setBuyAmount(orderById.getBuyAmount());
        fromOrder.setProductId(orderById.getProductId());
        fromOrder.setCreateTime(nowDate);
        fromOrder.setDeleted(0);
        fromOrder.setFromOrderId(orderId);
        fromOrder.setSourceOrderId(orderId);
        fromOrder.setCode(orderNo);
        // 3送出待收、4已退回、5送出成功、6领取成功
        fromOrder.setStatus(OrderStatusEnum.ORDER_STATUS_RECEIVED.getCode());

        try {
           userOrderService.insertOrder(fromOrder);
            userOrderService.updateOrderByCodeState(orderById.getCode(), Integer.valueOf(5));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error(ResultStatus.ERROR_EMPTY_VAL_RETURNED);
        }
        return ResultModel.ok("赠送失败");
    }

    @ApiOperation(value = "查看订单状态")
    @ResponseBody
    @RequestMapping(value = "/getOrderStatus", method = RequestMethod.GET)
    public ResultModel checkOrderStatus(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(orderId, "orderId 不可为空");
        Order orderById = userOrderService.getOrderById(orderId);
        if (orderById == null) {
            return ResultModel.error(ResultStatus.ERROR_NOT_FIND_DATA);
        } else {
            Integer status = orderById.getStatus();
            return ResultModel.ok(status);
        }
    }

}
