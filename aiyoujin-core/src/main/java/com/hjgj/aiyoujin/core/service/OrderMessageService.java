package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.OrderMessageMapper;
import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.OrderMessageExample;
import com.hjgj.aiyoujin.core.model.OrderMessageExample.Criteria;
import com.hjgj.aiyoujin.core.model.vo.Page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


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
    
    
    public int update(OrderMessage orderMessage) {
    	return orderMessageMapper.updateByPrimaryKeySelective(orderMessage);
    }


    public OrderMessage queryMessage(String orderId,String userId) {
        OrderMessageExample example = new OrderMessageExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(orderId)) {
            criteria.andOrderIdEqualTo(orderId);
        }
        if (StringUtils.isNotBlank(userId)) {
        	criteria.andUserIdEqualTo(userId);
        }
        return orderMessageMapper.selectOneByExample(example);
    }
	
	public Page<OrderMessage> queryPageProductMessage(OrderMessage orderMessage,Integer pageNum, Integer pageSize){
		Page<OrderMessage> page = new Page<OrderMessage>(pageNum,pageSize,true);
		OrderMessageExample example = new OrderMessageExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(orderMessage.getOrderId())){
			criteria.andOrderIdEqualTo(orderMessage.getOrderId());
		}
		if(StringUtils.isNotBlank(orderMessage.getOrderNo())){
			criteria.andOrderNoEqualTo(orderMessage.getOrderNo());
		}
		example.setOrderByClause("create_time desc");
		int total = orderMessageMapper.countByExample(example);
		if(total > 0){
			example.setLimitOffset(page.getStartRow());
			example.setLimitRows(pageSize);
			List<OrderMessage>  list = orderMessageMapper.selectByExample(example);
			page.setTotal(total);
			page.setList(list);
		}
		return page;
	}
	public OrderMessage findProduct(String id){
		return orderMessageMapper.selectByPrimaryKey(id);
	}
}
