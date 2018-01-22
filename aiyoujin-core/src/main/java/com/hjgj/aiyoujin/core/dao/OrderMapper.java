package com.hjgj.aiyoujin.core.dao;

import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderExample;
import com.hjgj.aiyoujin.core.model.vo.OrderRequestVo;
import com.hjgj.aiyoujin.core.model.vo.OrderVO;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectOneByExample(OrderExample example);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateOrderByCodeState(@Param("orderNo") String orderNo,@Param("orderState") Integer orderState);

    int updateOrderByMap(Map param);

    /**
     * TODO 统计买入订单数目
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    int countBuyOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);

    /**
     * TODO 买入订单
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    List<OrderVO> selectBuyOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);

    /**
     * TODO 统计变现订单数目
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    int countSellOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);

    /**
     * TODO 变现订单
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    List<OrderVO> selectSellOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);


    /**
     * TODO 统计转送订单数目
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    int countSendOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);

    /**
     * TODO 转送订单
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    List<OrderVO> selectSendOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);

    /**
     * TODO 统计提货订单数目
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    int countPickOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);

    /**
     * TODO 提货订单
     *
     * @param orderRequest
     * @param rowBounds
     * @return
     */
    List<OrderVO> selectPickOrderVo(OrderRequestVo orderRequest, RowBounds rowBounds);


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
    List<OrderWebVo> getUserAllOrdersByUserId(@Param("userId") String userId,@Param("types")String types, RowBounds rowBounds);
    
    /**
     * TODO 添加订单物流信息
     * @param orderVO
     */
	void addExpressToOrder(OrderVO orderVO);
}