package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderMapper;
import com.hjgj.aiyoujin.core.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public int insertOneOrder(Order order) {
        int insert = orderMapper.insert(order);
        return insert;
    }

    public int updateOneOrder(Order order) {
        int i = orderMapper.updateByPrimaryKeySelective(order);
        return i;
    }
}
