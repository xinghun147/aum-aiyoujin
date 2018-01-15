package com.hjgj.aiyoujin.server.controller;

import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.BaseService;
import com.hjgj.aiyoujin.core.service.UserOrderService;
import com.hjgj.aiyoujin.core.service.UserService;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "order")
public class OrderApiController{

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private UserService userService;

    /**
     * 查询用户的所有订单详情
     * @param openId
     * @param types
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询用户的所有礼品卡详情")
    @ResponseBody
    @RequestMapping(value = "getMyGiftCards",method = RequestMethod.POST)
    public ResultModel getUserAllOrdersByOpenId(@ApiParam(value = "用户OpenId", required = true)  @RequestParam String openId,
                                                @ApiParam(value = "礼品类型", required = true)  @RequestParam String types,
                                                @ApiParam(value = "第多少页", required = true)  @RequestParam Integer pageNum,
                                                @ApiParam(value = "每页多少", required = true) @RequestParam Integer pageSize) {
        pageNum = pageNum == null ? 1:pageNum;
        pageSize = pageSize == null ? 50:pageSize;
        Page<OrderWebVo> allOrders = userOrderService.getUserAllOrders(openId, types,pageNum, pageSize);
        if (allOrders != null) {
             return ResultModel.ok(allOrders);
        } else {
            return ResultModel.ok(ResultStatus.ERROR_NOT_FIND_DATA);
        }
    }

    @ApiOperation(value = "发送礼品卡")
    @ResponseBody
    @RequestMapping(value = "sendGiftCard",method = RequestMethod.POST)
    public void test1(@ApiParam(value = "用户OpenId", required = true) @RequestParam String openId,
                      @ApiParam(value = "订单ID", required = true) @RequestParam String orderId) {
        User byOpenId = userService.getUserByOpenId(openId);

    }

}
