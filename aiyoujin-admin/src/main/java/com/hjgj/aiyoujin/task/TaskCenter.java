package com.hjgj.aiyoujin.task;

import com.hjgj.aiyoujin.core.common.WXProperties;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import weixin.popular.api.PayMchAPI;
import weixin.popular.bean.paymch.MchOrderInfoResult;
import weixin.popular.bean.paymch.MchOrderquery;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class TaskCenter {

    @Autowired
    private AdminOrderService adminOrderService;

    private void selectWXPayOrder() {
        List<Integer> integers = Arrays.asList(0, 1, 2);
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.DAY_OF_MONTH, -1);
        instance.set(Calendar.SECOND, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.MILLISECOND, 0);
        Date beforeDate = instance.getTime();
        Date nowDate = new Date();
        List<Order> unresolvedOrder = adminOrderService.getUnresolvedOrder(integers, beforeDate, nowDate);
        for (Order order : unresolvedOrder) {
            MchOrderquery mchOrderquery = new MchOrderquery();
            mchOrderquery.setAppid(WXProperties.WeiXinAppid);
            mchOrderquery.setMch_id(WXProperties.WeiXinAppid);
            mchOrderquery.setNonce_str(UUIDGenerator.generate());
            mchOrderquery.setOut_trade_no(order.getCode());
//            mchOrderquery.se
            MchOrderInfoResult orderInfoResult = PayMchAPI.payOrderquery(mchOrderquery, WXProperties.weixinApiKey);
            String return_code = orderInfoResult.getReturn_code();
            if ("SUCCESS".equals(return_code)) {
                String result_code = orderInfoResult.getResult_code();
                String trade_state = orderInfoResult.getTrade_state();

                if ("SUCCESS".equals(result_code) && "SUCCESS".equals(trade_state)){
                    order.setStatus(1);
                    order.setUpdateTime(new Date());
                    int updateOneOrder = adminOrderService.updateOneOrder(order);
                    while (updateOneOrder<0){
                        adminOrderService.updateOneOrder(order);
                    }

                }
            }
        }
    }
}
