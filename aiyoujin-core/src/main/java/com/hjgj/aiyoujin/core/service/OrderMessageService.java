package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.OrderMessageMapper;
import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.OrderMessageExample;
import com.hjgj.aiyoujin.core.model.OrderMessageExample.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class OrderMessageService extends BaseService {

    @Autowired
    private OrderMessageMapper orderMessageMapper;


    public int insert(OrderMessage orderMessage) {
        orderMessage.setId(UUIDGenerator.generate());
        orderMessage.setCreateTime(new Date());
        orderMessage.setUpdateTime(new Date());
        return orderMessageMapper.insertSelective(orderMessage);
    }


    public OrderMessage queryMessage(String orderId) {
        OrderMessageExample example = new OrderMessageExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(orderId)) {
            criteria.andOrderIdEqualTo(orderId);
        }
        return orderMessageMapper.selectOneByExample(example);
    }

}
