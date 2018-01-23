package com.hjgj.aiyoujin.task;

import com.hjgj.aiyoujin.core.common.OrderStatusEnum;
import com.hjgj.aiyoujin.core.common.WXProperties;
import com.hjgj.aiyoujin.core.common.utils.DateUtil;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderLog;
import com.hjgj.aiyoujin.core.service.AdminOrderService;
import com.hjgj.aiyoujin.core.service.OrderLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.MchOrderInfoResult;
import weixin.popular.bean.paymch.MchOrderquery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@EnableScheduling
@Component
public class TaskCenter {

    protected final Logger logger = LoggerFactory.getLogger(TaskCenter.class);

    @Autowired
    private AdminOrderService adminOrderService;

    @Autowired
    private OrderLogService orderLogService;

    /**
     *  TODO 处理订单状态为 失败,待支付的订单
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    private void selectWXPayOrder() {
        logger.info("selectWXPayOrder方法执行");
        List<Integer> integers = Arrays.asList(0,2);
        Date nowDate = new Date();
        //Date beforeDate = DateUtil.addDaysToDate(nowDate, -1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -10);
        Date calendarTime = calendar.getTime();
        List<Order> unresolvedOrder = adminOrderService.getUnresolvedOrder(integers, calendarTime, null);
        System.out.println(unresolvedOrder);
        if (unresolvedOrder == null || (unresolvedOrder != null && unresolvedOrder.size() <= 0)) {
            return;
        }
        HashMap<String, String> objectHashMap = new HashMap<>();
        for (Order order : unresolvedOrder) {
            OrderLog orderLog = orderLogService.getOrderLogByorderid(order.getId());
            objectHashMap.put(order.getId(), orderLog.getId());
        }
        ArrayList<String> nopayList = new ArrayList<>();
        for (Order order : unresolvedOrder) {
            MchOrderquery mchOrderquery = new MchOrderquery();
            mchOrderquery.setAppid(WXProperties.WeiXinAppid);
            mchOrderquery.setMch_id(WXProperties.WeiXinMchId);
            mchOrderquery.setNonce_str(UUIDGenerator.generate());
            mchOrderquery.setOut_trade_no(order.getCode());
            MchOrderInfoResult orderInfoResult = PayMchAPI.payOrderquery(mchOrderquery, WXProperties.weixinApiKey);

            String return_code = orderInfoResult.getReturn_code();
            if ("SUCCESS".equals(return_code)) {

                String result_code = orderInfoResult.getResult_code();
                String trade_state = orderInfoResult.getTrade_state();
                if ("SUCCESS".equals(result_code) && "SUCCESS".equals(trade_state)) {
                    order.setStatus(OrderStatusEnum.ORDER_STATUS_PAY_PAID.getCode());
                    order.setUpdateTime(nowDate);
                    OrderLog orderLog = new OrderLog();
                    orderLog.setId(objectHashMap.get(order.getId()));
                    orderLog.setStatus(OrderStatusEnum.ORDER_STATUS_PAY_PAID.getCode());
                    orderLog.setUpdateTime(nowDate);
                    orderLog.setUpdateBy("schedule task");

                    int updateOrderAndLog = orderLogService.updateOrderAndLog(orderLog, order);
                    while (updateOrderAndLog < 0) {
                        updateOrderAndLog = orderLogService.updateOrderAndLog(orderLog, order);
                    }
                    logger.info("更新订单表主键为:" + order.getId() + "----->订单号为:" + order.getStatus());
                } else if ("SUCCESS".equals(result_code) && "NOTPAY".equals(trade_state)) {
                    nopayList.add(orderInfoResult.getOut_trade_no());
                    order.setStatus(OrderStatusEnum.ORDER_STATUS_PAY_ERROR.getCode());
                    order.setUpdateTime(nowDate);
                    OrderLog orderLog = new OrderLog();
                    orderLog.setId(objectHashMap.get(order.getId()));
                    orderLog.setStatus(OrderStatusEnum.ORDER_STATUS_PAY_ERROR.getCode());
                    orderLog.setUpdateTime(nowDate);
                    orderLog.setUpdateBy("schedule task");

                    int updateOrderAndLog = orderLogService.updateProdOrderLog(orderLog, order, order.getProductId(), 1);
                    while (updateOrderAndLog < 0) {
                        updateOrderAndLog = orderLogService.updateProdOrderLog(orderLog, order, order.getProductId(), 1);
                    }
                    logger.info("更新订单表主键为:" + order.getId() + "----->订单号为:" + order.getStatus());
                }
            } else {
                logger.info("通信错误,订单号为:" + order.getCode());
            }
        }
    }


    /**
     * TODO 处理订单状态为 3送出待收
     * TODO 并设置为 已退回
     */
    @Scheduled(fixedRate = 1000 * 1800, initialDelay = -10)
    public void selectTransferOrder() {
        logger.info("selectTransferOrder方法执行");
        List<Integer> integers = Arrays.asList(3);
        Date nowDate = new Date();
        Date beforeDate = DateUtil.addDaysToDate(nowDate, -1);
        List<Order> orderList = adminOrderService.getUntransferOrder(integers, beforeDate);
        for (Order order : orderList) {
            order.setUpdateTime(nowDate);
            order.setStatus(OrderStatusEnum.ORDER_STATUS_RETURN.getCode());
            order.setUpdateTime(nowDate);
            int updateOrderAndLog = adminOrderService.updateOrderbyCondition(order);
            while (updateOrderAndLog < 0) {
                updateOrderAndLog = adminOrderService.updateOrderbyCondition(order);
            }
            logger.info("更新订单表主键为:" + order.getId() + "----->订单号为:" + order.getStatus());
        }

    }
}
