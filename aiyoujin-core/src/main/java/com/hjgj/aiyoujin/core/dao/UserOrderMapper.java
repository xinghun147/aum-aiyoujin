package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderExample;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface UserOrderMapper {

    /**
     * TODO 统计礼物数目
     * @param userId
     * @param types
     * @param rowBounds
     * @return
     */
    int countUserAllOrdersByUserId(@Param("userId") String userId, @Param("types")String types, RowBounds rowBounds);


    /**
     * TODO 用户端礼物
     * @param userId
     * @param types
     * @param rowBounds
     * @return
     */
    List<OrderWebVo> getUserAllOrdersByUserId(@Param("userId") String userId, @Param("types")String types, RowBounds rowBounds);

    /**
     * 更新订单 由map型参数
     * @param param
     * @return
     */
    int updateOrderByMap(Map param);

    /**
     * 由example 查询
     * @param example
     * @return
     */
    List<Order> selectByExample(OrderExample example);

    /**
     * 由订单id 查询订单产品,记录
     * @param orderId
     * @return
     */
    List<Map<String,Object>> getOrderProduct(@Param("orderId")String orderId);

    /**
     * 新增
     * @param order
     * @return
     */
    int insert(Order order);

    /**
     * 批量新增
     * @param orderList
     * @return
     */
    int batchInsertOrder(List<Order> orderList);
}
