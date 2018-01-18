package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderNotifyMapper;
import com.hjgj.aiyoujin.core.model.OrderNotify;
import com.hjgj.aiyoujin.core.model.OrderNotifyExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderNotifyService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderNotifyMapper orderNotifyMapper;


    public int insertOrderNotify(OrderNotify orderNotify) {
        logger.info("new insert order notify,the notify`s is:" + orderNotify);
        int insert = orderNotifyMapper.insert(orderNotify);
        logger.info("new insert order notify,the result is:" + insert);
        return insert;
    }

    public OrderNotify getOrderNotifyByOrderNo(String orderNo,Integer orderType) {
        logger.info("select order notify,the notify`s is:" + orderNo+" ,orderType is :"+orderType);
        OrderNotifyExample example = new OrderNotifyExample();
        OrderNotifyExample.Criteria criteria = example.createCriteria();
        criteria.andOrderCodeEqualTo(orderNo);
        List<OrderNotify> notifyList = orderNotifyMapper.selectByExample(example);
        if (notifyList != null && notifyList.size() > 0) {
            logger.info("select order notify,the notifyList`s size: "+notifyList.size());
            return notifyList.get(0);
        } else {
            logger.info("select order notify,the notifyList`s size: 0");
            return null;
        }
    }

    public int updateOrderNotify(OrderNotify orderNotify){
        logger.info("update order notify,the notify`s is:" + orderNotify);
        int update = orderNotifyMapper.updateByPrimaryKeySelective(orderNotify);
        logger.info("update order notify,the result is:" + update);
        return update;
    }
}
