package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderMapper;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderExample;
import com.hjgj.aiyoujin.core.model.vo.OrderRequestVo;
import com.hjgj.aiyoujin.core.model.vo.OrderVO;
import com.hjgj.aiyoujin.core.model.vo.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdminOrderService {

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

    public Page<OrderVO> getAllOrderVOMap(Map param, Integer pageNum, Integer pageSize) {
        Page<OrderVO> page = new Page<>(pageNum, pageSize, true);
        RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
        int count = orderMapper.countAllOrderVoMap(param, rowBounds);
        if (count > 0) {
            List<OrderVO> orderVOList = orderMapper.selectAllOrderVoMap(param, rowBounds);
            page.setList(orderVOList);
            page.setTotal(count);
        }
        return page;
    }

    /**
     * TODO 后台系统 买入订单主页
     *
     * @param orderRequestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<OrderVO> getBuyOrderAllMap(OrderRequestVo orderRequestVo, Integer pageNum, Integer pageSize) {
        Page<OrderVO> page = new Page<>(pageNum, pageSize, true);
        RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
        int count = orderMapper.countBuyOrderVo(orderRequestVo, rowBounds);
        if (count > 0) {
            List<OrderVO> orderVOList = orderMapper.selectBuyOrderVo(orderRequestVo, rowBounds);
            page.setList(orderVOList);
            page.setTotal(count);
        }
        return page;
    }

    /**
     * TODO 后台系统 变现订单主页
     *
     * @param orderRequestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<OrderVO> getSellOrderAllMap(OrderRequestVo orderRequestVo, Integer pageNum, Integer pageSize) {
        Page<OrderVO> page = new Page<>(pageNum, pageSize, true);
        RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
        int count = orderMapper.countSellOrderVo(orderRequestVo, rowBounds);
        if (count > 0) {
            List<OrderVO> orderVOList = orderMapper.selectSellOrderVo(orderRequestVo, rowBounds);
            page.setList(orderVOList);
            page.setTotal(count);
        }
        return page;
    }

    /**
     * TODO 后台系统 转送订单主页
     *
     * @param orderRequestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<OrderVO> getSendOrderAllMap(OrderRequestVo orderRequestVo, Integer pageNum, Integer pageSize) {
        Page<OrderVO> page = new Page<>(pageNum, pageSize, true);
        RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
        int count = orderMapper.countSendOrderVo(orderRequestVo, rowBounds);
        if (count > 0) {
            List<OrderVO> orderVOList = orderMapper.selectSendOrderVo(orderRequestVo, rowBounds);
            page.setList(orderVOList);
            page.setTotal(count);
        }
        return page;
    }

    /**
     * TODO 后台系统 提货订单主页
     *
     * @param orderRequestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<OrderVO> getPickOrderAllMap(OrderRequestVo orderRequestVo, Integer pageNum, Integer pageSize) {
        Page<OrderVO> page = new Page<>(pageNum, pageSize, true);
        RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
        int count = orderMapper.countPickOrderVo(orderRequestVo, rowBounds);
        if (count > 0) {
            List<OrderVO> orderVOList = orderMapper.selectPickOrderVo(orderRequestVo, rowBounds);
            page.setList(orderVOList);
            page.setTotal(count);
        }
        return page;
    }

    /**
     * 添加快递信息到订单
     *
     * @param orderVO
     */
    @Transactional
    public void addExpressToOrder(OrderVO orderVO) {
        if (StringUtils.isBlank(orderVO.getOrderNo())) {
            orderVO.setUpdateTime(new Date());
            orderMapper.addExpressToOrder(orderVO);
        }
    }

    /**
     * 获取未处理的订单
     */
    public List<Order> getUnresolvedOrder(List<Integer> statusList, Date startTime, Date endTime) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        OrderExample.Criteria criteria1 = criteria.andStatusIn(statusList).andDeletedEqualTo(0).andCreateTimeGreaterThanOrEqualTo(startTime);
        if(endTime != null){
            criteria1.andCreateTimeLessThanOrEqualTo(endTime);
        }
        List<Order> ordersList = orderMapper.selectByExample(example);
        return ordersList;
    }

    public int updateOrderbyCondition(Order order) {
        int update = orderMapper.updateByPrimaryKeySelective(order);
        return update;
    }

    public List<Order> getUntransferOrder(List<Integer> statusList, Date updateTime) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andStatusIn(statusList).andDeletedEqualTo(0);
        criteria.andUpdateTimeGreaterThanOrEqualTo(updateTime);
        List<Order> ordersList = orderMapper.selectByExample(example);
        return ordersList;
    }
}
