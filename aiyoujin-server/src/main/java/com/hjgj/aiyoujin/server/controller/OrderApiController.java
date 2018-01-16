package com.hjgj.aiyoujin.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjgj.aiyoujin.core.common.OrderStatusEnum;
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
    @ApiOperation(value = "查询用户礼物列表")
    @ResponseBody
    @RequestMapping(value = "/getMyGiftCards", method = RequestMethod.POST)
    public ResultModel getUserAllOrdersByOpenId(@ApiParam(value = "用户ID", required = true) @RequestParam String userId,
                                                @ApiParam(value = "礼品类型(1持有中,2已送出,3已变现,4已提货)", required = true) @RequestParam String types,
                                                @ApiParam(value = "第多少页", required = true) @RequestParam Integer pageNum,
                                                @ApiParam(value = "每页多少", required = true) @RequestParam Integer pageSize) {
    	 Assert.notNull(userId, "userId 不可为空");
    	 Assert.notNull(types, "types 不可为空");
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 50 : pageSize;
        Page<OrderWebVo> allOrders = userOrderService.getUserAllOrders(userId, types, pageNum, pageSize);
        if (allOrders != null) {
            return ResultModel.ok(allOrders);
        } else {
            return ResultModel.ok(ResultStatus.ERROR_NOT_FIND_DATA);
        }
    }

    @ApiOperation(value = "赠送礼物")
    @ResponseBody
    @RequestMapping(value = "/sendGift", method = RequestMethod.POST)
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
            return ResultModel.ok();
        } else {
            return ResultModel.error(ResultStatus.ORDER_NOT_EXIST);
        }

    }

    @ApiOperation(value = "领取礼物")
    @ResponseBody
    @RequestMapping(value = "/receiveGift", method = RequestMethod.POST)
    public ResultModel receiveGiftCard(@ApiParam(value = "领取礼品用户OpenId", required = true) @RequestParam String openId,
                                       @ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(openId, "openId不可为空");
        Assert.notNull(orderId, "orderId 不可为空");
        User byUser = userService.getUserByOpenId(openId);
        Order orderBy = userOrderService.getOrderById(orderId);
        Order fromOrder = new Order();
        fromOrder.setUserId(byUser.getId());
        fromOrder.setBuyAmount(orderBy.getBuyAmount());
        fromOrder.setProductId(orderBy.getProductId());
        fromOrder.setFromOrderId(orderId);
        fromOrder.setSourceOrderId(orderId);
        // 3送出待收、4已退回、5送出成功、6领取成功
        fromOrder.setStatus(OrderStatusEnum.ORDER_STATUS_RECEIVED.getCode());
        try {
           userOrderService.receiveOrder(fromOrder);
          return ResultModel.ok();
        } catch (Exception e) {
        	e.printStackTrace();
            return ResultModel.error(ResultStatus.ORDER_RECEIVE_FAIL);
        }
    }

    @ApiOperation(value = "查看订单支付状态")
    @ResponseBody
    @RequestMapping(value = "/getOrderStatus", method = RequestMethod.GET)
    public ResultModel checkOrderStatus(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(orderId, "orderId 不可为空");
        Order order = userOrderService.getOrderById(orderId);
        if(order != null && order.getStatus() == OrderStatusEnum.ORDER_STATUS_PAY_PAID.getCode()){
        	return ResultModel.ok();
        }
        return ResultModel.error(ResultStatus.ORDER_PAY_UNPAID);
    }

}
