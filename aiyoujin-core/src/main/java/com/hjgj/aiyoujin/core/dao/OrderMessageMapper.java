package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.OrderMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMessageMapper {
    int countByExample(OrderMessageExample example);

    int deleteByExample(OrderMessageExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderMessage record);

    int insertSelective(OrderMessage record);

    OrderMessage selectOneByExample(OrderMessageExample example);

    List<OrderMessage> selectByExample(OrderMessageExample example);

    OrderMessage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderMessage record, @Param("example") OrderMessageExample example);

    int updateByExample(@Param("record") OrderMessage record, @Param("example") OrderMessageExample example);

    int updateByPrimaryKeySelective(OrderMessage record);

    int updateByPrimaryKey(OrderMessage record);
}