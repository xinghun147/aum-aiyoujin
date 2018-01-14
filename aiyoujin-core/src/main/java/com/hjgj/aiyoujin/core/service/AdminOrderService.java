package com.hjgj.aiyoujin.core.service;

import com.hjgj.aiyoujin.core.dao.OrderMapper;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.vo.OrderRequestVo;
import com.hjgj.aiyoujin.core.model.vo.OrderVO;
import com.hjgj.aiyoujin.core.model.vo.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
     *  TODO 后台系统 变现订单主页
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
     *  TODO 后台系统 转送订单主页
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
     *  TODO 后台系统 提货订单主页
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
}