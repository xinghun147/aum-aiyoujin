package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderLogMapper;
import com.hjgj.aiyoujin.core.dao.OrderMapper;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderLog;
import com.hjgj.aiyoujin.core.model.OrderLogExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderLogService {

    @Autowired
    private OrderLogMapper orderLogMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 新增订单日志
     *
     * @param orderLog
     * @return
     */
    public int insertOrderLog(OrderLog orderLog) {
        int insert = orderLogMapper.insert(orderLog);
        return insert;
    }

    @Transactional
    public int updateOrderAndLog(OrderLog orderLog, Order order) {
        int updateOrder = orderMapper.updateByPrimaryKeySelective(order);
        int updateLog = orderLogMapper.updateByPrimaryKeySelective(orderLog);
        if (updateLog > 0 && updateOrder > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public OrderLog getOrderLogByorderid(String orderId) {
        OrderLogExample example = new OrderLogExample();
        OrderLogExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        OrderLog orderLog = orderLogMapper.selectOneByExample(example);
        if (orderId != null) {
            return orderLog;
        }else {
            return null;
        }
    }


}
