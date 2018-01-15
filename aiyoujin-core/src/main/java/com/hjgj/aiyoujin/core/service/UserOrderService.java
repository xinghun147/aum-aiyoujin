package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderLogMapper;
import com.hjgj.aiyoujin.core.dao.UserOrderMapper;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderExample;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import com.hjgj.aiyoujin.core.model.vo.Page;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserOrderService {

    protected final Logger logger = LoggerFactory.getLogger(UserOrderService.class);

    @Autowired
    private UserOrderMapper userOrderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 查询用户所有的订单
     *
     * @param openId
     * @param types
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<OrderWebVo> getUserAllOrders(String openId, String types, Integer pageNum, Integer pageSize) {
        User user = userService.getUserByOpenId(openId);
        if (user == null) {
            return null;
        } else {
            Page<OrderWebVo> page = new Page<>(pageNum, pageSize, true);
            RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
            int count = userOrderMapper.countUserAllOrdersByUserId(openId, types, rowBounds);
            if (count > 0) {
                List<OrderWebVo> orderVOList = userOrderMapper.getUserAllOrdersByUserId(openId, types, rowBounds);
                page.setList(orderVOList);
                page.setTotal(count);
            }
            return page;
        }
    }

    /**
     * 由我方订单号查询订单详情
     *
     * @param orderno
     * @return
     */
    public Order getUserOrderBySelfOrderno(String orderno) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(orderno).andDeletedEqualTo(Integer.valueOf(0));
        List<Order> orders = userOrderMapper.selectByExample(example);
        if (orders != null && orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新订单表,订单记录表
     *
     * @param orderNo
     * @param orderState
     * @return
     */
    public int updateOrderByCodeState(String orderNo, Integer orderState) {
        logger.info("updateOrderByCodeState执行,参数为{" + orderNo + "},{" + orderState + "}");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderNo", orderNo);
        hashMap.put("status", orderState);
        hashMap.put("updateTime", new Date());
        int updateResult = userOrderMapper.updateOrderByMap(hashMap);
        logger.info("updateOrderByCodeState执行,参数为{" + orderNo + "},{" + orderState + "},更新结果为" + updateResult);
        return updateResult;
    }

    /**
     * 更新订单表,订单记录表
     *
     * @param updateParam
     * @return
     */
    @Transactional
    public int updateOrderAndOrderLogByMap(Map<String, Object> updateParam) {
        int orderByMap = userOrderMapper.updateOrderByMap(updateParam);
        int logByMap = orderLogMapper.updateOrderLogByMap(updateParam);
        if (orderByMap > 0 && logByMap > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 获取订单,产品,用户,记录表资料
     *
     * @param orderId
     * @return
     */
    public Map getOrderProduct(String orderId) {
        List<Map<String, Object>> orderProducts = userOrderMapper.getOrderProduct(orderId);
        if (orderProducts != null) {
            Map<String, Object> map = orderProducts.get(0);
            return map;
        } else {
            return null;
        }
    }

    public void insertTwoOrder(Order fromOrder, Order toOrder) {
        int fromOrderResult = userOrderMapper.insert(fromOrder);
        int toOrderResult = userOrderMapper.insert(toOrder);
    }

    public int insertFromOrder(Order order) {
        int fromOrderResult = userOrderMapper.insert(order);
        return fromOrderResult;
    }
    
    public int batchInsertOrder(List<Order> orderList){
        
        
        return 0;
    }
}
