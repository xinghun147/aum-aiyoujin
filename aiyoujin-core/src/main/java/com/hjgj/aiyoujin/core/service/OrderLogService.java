package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderLogMapper;
import com.hjgj.aiyoujin.core.model.OrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLogService {


    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 新增订单日志
     * @param orderLog
     * @return
     */
    public int insertOrderLog(OrderLog orderLog){
        int insert = orderLogMapper.insert(orderLog);
        return insert;
    }
}
