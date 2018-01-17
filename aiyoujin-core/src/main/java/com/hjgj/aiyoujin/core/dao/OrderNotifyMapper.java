package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.OrderNotify;
import com.hjgj.aiyoujin.core.model.OrderNotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderNotifyMapper {
    int countByExample(OrderNotifyExample example);

    int deleteByExample(OrderNotifyExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderNotify record);

    int insertSelective(OrderNotify record);

    OrderNotify selectOneByExample(OrderNotifyExample example);

    List<OrderNotify> selectByExample(OrderNotifyExample example);

    OrderNotify selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderNotify record, @Param("example") OrderNotifyExample example);

    int updateByExample(@Param("record") OrderNotify record, @Param("example") OrderNotifyExample example);

    int updateByPrimaryKeySelective(OrderNotify record);

    int updateByPrimaryKey(OrderNotify record);
}