package com.hjgj.aiyoujin.server.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hjgj.aiyoujin.core.common.OrderStatusEnum;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.UserOrderService;
import com.hjgj.aiyoujin.core.service.UserService;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;
import com.hjgj.aiyoujin.server.common.redis.RedisLockUtil;
import com.hjgj.aiyoujin.server.webBiz.WeixinPush;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/order")
public class OrderApiController {
	
	private  Logger logger = LoggerFactory.getLogger(OrderApiController.class);

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private WeixinPush weixinPush;
	
	@Autowired
	public RedisLockUtil redisLockUtil;
	
	
    

    /**
     * 查询用户的所有订单详情
     *
     * @param userId
     * @param types
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询用户礼物列表")
    @ResponseBody
    @RequestMapping(value = "/getMyGiftCards", method = RequestMethod.GET)
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
    public ResultModel sendGiftCard(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId,
    								@ApiParam(value = "当前用户ID", required = true) @RequestParam String userId,
						    		@ApiParam(value = "留言内容") @RequestParam(required=false) String content,
									@ApiParam(value = "图片地址") @RequestParam(required=false) String imageUrl,
									@ApiParam(value = "视频地址") @RequestParam(required=false) String videoUrl) {
        Assert.notNull(orderId, "orderId 不可为空");
        Assert.notNull(userId, "userId 不可为空");
        if(!redisLockUtil.acquireLock(orderId+"_"+userId+"_sendGiftCard", 3 * 1000)){
 			logger.error("频繁调用赠送礼物接口,"+orderId+"_"+userId+"_sendGiftCard");
 			return ResultModel.error(ResultStatus.ERROR_OPERATION_FREQUENT);
     	}
        
        //校验状态是否可赠送
        Order tempOrder = userOrderService.getOrderById(orderId);
        if(!(tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_PAY_PAID.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_RETURN.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_RECEIVED.getCode()
        		)){
        	return ResultModel.error(ResultStatus.ORDER_NOT_TO_RECEIVE);
        }
        
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
        Order order = new Order();
        order.setId(orderId);
        OrderMessage msg = new OrderMessage();
        msg.setContent(content);
        msg.setImageUrl(imageUrl);
        msg.setUserId(userId);
        msg.setVideoUrl(videoUrl);
		try {
			  userOrderService.sendGiftCard(order, msg);
		      return ResultModel.ok();
		} catch (Exception e) {
			 return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
       

    }

    @ApiOperation(value = "领取礼物")
    @ResponseBody
    @RequestMapping(value = "/receiveGift", method = RequestMethod.POST)
    public ResultModel receiveGiftCard(@ApiParam(value = "领取礼品用户OpenId", required = true) @RequestParam String openId,
                                       @ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(openId, "openId不可为空");
        Assert.notNull(orderId, "orderId 不可为空");
        
//        if(!redisLockUtil.acquireLock(orderId+"_"+openId+"receiveGift", 3 * 1000)){
//        	logger.error("频繁调用领取礼物接口,"+orderId+"_"+openId+"receiveGift");
//			return ResultModel.error(ResultStatus.ERROR_OPERATION_FREQUENT);
//    	}
//        
        //校验状态是否可领取
        Order order = userOrderService.getOrderById(orderId);
        if(order.getStatus() != OrderStatusEnum.ORDER_STATUS_UNRECEIVE.getCode()){
        	return ResultModel.error(ResultStatus.ORDER_TO_RECEIVE_RECEIVED);
        }
        try {
        	  if(redisLockUtil.acquireLock(orderId+"_"+"receiveGift", 300 * 1000)){
	        	User byUser = userService.getUserByOpenId(openId);
	        	Order orderBy = userOrderService.getOrderById(orderId);
	        	Order fromOrder = new Order();
	        	fromOrder.setUserId(byUser.getId());
	        	fromOrder.setBuyAmount(orderBy.getBuyAmount());
	        	fromOrder.setSellAmount(orderBy.getSellAmount());
	        	fromOrder.setProductId(orderBy.getProductId());
	        	fromOrder.setFromOrderId(orderId);
	        	fromOrder.setSourceOrderId(orderId);
	        	// 3送出待收、4已退回、5送出成功、6领取成功
	        	fromOrder.setStatus(OrderStatusEnum.ORDER_STATUS_RECEIVED.getCode());
	            String oid = userOrderService.receiveOrder(fromOrder);
           		return ResultModel.ok(oid);
        	  }else{
        		  return ResultModel.error(ResultStatus.ORDER_TO_RECEIVE_RECEIVED);
        	  }
        } catch (Exception e) {
        	e.printStackTrace();
            return ResultModel.error(ResultStatus.ORDER_RECEIVE_FAIL);
        }finally {
        	redisLockUtil.releaseLock(orderId+"_"+"receiveGift");
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
    
    @ApiOperation(value = "查看订单详情")
    @ResponseBody
    @RequestMapping(value = "/queryOrderDetail", method = RequestMethod.GET)
    public ResultModel queryOrderDetail(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId,
    									@ApiParam(value = "用户ID", required = true) @RequestParam String userId) {
        Assert.notNull(orderId, "orderId 不可为空");
        Assert.notNull(userId, "userId 不可为空");
		try {
			
		  	//查询订单是否已被领取
			Order  o = userOrderService.getOrderBySourceOrderId(orderId,userId);
			if(o != null){
				if(!o.getUserId().equals(userId)){//查看用户非领取用户
					return ResultModel.error(ResultStatus.ORDER_TO_RECEIVE_RECEIVED);
				}
				orderId=o.getId();
			}
			OrderWebVo order = userOrderService.queryOrderDetail(orderId,userId);
			return ResultModel.ok(order);
		} catch (Exception e) {
			return ResultModel.error(ResultStatus.ORDER_NOT_EXIST);
		}
    }
    
    
    @ApiOperation(value = "礼品变现")
    @ResponseBody
    @RequestMapping(value = "/giftToCash", method = RequestMethod.POST)
    public ResultModel giftToCash(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId,
    							@ApiParam(value = "微信提交formId", required = true) @RequestParam String formId) {
        Assert.notNull(orderId, "orderId 不可为空");
        Assert.notNull(formId, "formId 不可为空");
        if(!redisLockUtil.acquireLock(orderId+"_"+formId+"_giftToCash", 3 * 1000)){
			logger.error("频繁调用礼品变现方法,"+orderId+"_"+formId+"_giftToCash");
			return ResultModel.error(ResultStatus.ERROR_OPERATION_FREQUENT);
		}
        
        //校验状态是否可赠送
        Order tempOrder = userOrderService.getOrderById(orderId);
        if(!(tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_PAY_PAID.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_RETURN.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_RECEIVED.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_CASH_FAIL.getCode()
        		)){
        	return ResultModel.error(ResultStatus.ORDER_NOT_TO_CASH);
        }
        
		try {
			Order order = userOrderService.getOrderById(orderId);
			if(order == null){
				return ResultModel.error(ResultStatus.ORDER_NOT_EXIST);
			}
			User user = userService.getUserByUserId(order.getUserId());
			Map map = userOrderService.giftToCash(order,user.getOpenId());
			logger.info("调用变现接口返回，请求参数openId:{},Map:{}",user.getOpenId(),JSON.toJSONString(map));
			if(map.get("code").equals("0")){
	            //给用户推送通知 
				weixinPush.payResultNotifySell(order.getSellAmount().setScale(2).toString(),"微信零钱",new Date(), formId, user.getOpenId());
				return ResultModel.ok();
			}else{
				userOrderService.updateOrderStauts(order.getId(), OrderStatusEnum.ORDER_STATUS_CASH_FAIL.getCode());
				return  ResultModel.error(ResultStatus.ORDER_TO_CASH_FAIL);
			}
		} catch (Exception e) {
			logger.error("调用变现接口异常", e);
			return ResultModel.error(ResultStatus.ORDER_TO_CASH_FAIL);
		}
    }
    
    
    @ApiOperation(value = "提货申请")
    @ResponseBody
    @RequestMapping(value = "/takeDelivery", method = RequestMethod.POST)
    public ResultModel takeDelivery(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId,
    							@ApiParam(value = "地址ID", required = true) @RequestParam String address) {
        Assert.notNull(orderId, "orderId 不可为空");
        Assert.notNull(address, "地址不可为空");
        
        //校验状态是否可赠送
        Order tempOrder = userOrderService.getOrderById(orderId);
        if(!(tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_PAY_PAID.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_RETURN.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_RECEIVED.getCode()
        		||tempOrder.getStatus() == OrderStatusEnum.ORDER_STATUS_CASH_FAIL.getCode()
        		)){
        	return ResultModel.error(ResultStatus.ORDER_NOT_PICKPROCESSING);
        }
        
		try {
			Order order = userOrderService.getOrderById(orderId);
			if(order == null){
				return ResultModel.error(ResultStatus.ORDER_NOT_EXIST);
			}
			order.setAddress(address);
			userOrderService.takeDelivery(order);
			return ResultModel.ok();
		} catch (Exception e) {
			return ResultModel.error(ResultStatus.ORDER_TO_CASH_FAIL);
		}
    }
    
    
    @ApiOperation(value = "确认收货")
    @ResponseBody
    @RequestMapping(value = "/confirmReceipt", method = RequestMethod.POST)
    public ResultModel confirmReceipt(@ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        Assert.notNull(orderId, "orderId 不可为空");
		try {
			Order order = userOrderService.getOrderById(orderId);
			if(order == null){
				return ResultModel.error(ResultStatus.ORDER_NOT_EXIST);
			}
			userOrderService.updateOrderStauts(orderId,OrderStatusEnum.ORDER_STATUS_CONFIRM_RECEIPT.getCode());
			return ResultModel.ok();
		} catch (Exception e) {
			return ResultModel.error(ResultStatus.ORDER_TO_CASH_FAIL);
		}
    }

}
