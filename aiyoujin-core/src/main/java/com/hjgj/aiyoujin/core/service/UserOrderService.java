package com.hjgj.aiyoujin.core.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.common.OrderStatusEnum;
import com.hjgj.aiyoujin.core.common.exception.BusinessException;
import com.hjgj.aiyoujin.core.common.utils.CommonUtils;
import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.dao.OrderLogMapper;
import com.hjgj.aiyoujin.core.dao.OrderMapper;
import com.hjgj.aiyoujin.core.dao.OrderMessageMapper;
import com.hjgj.aiyoujin.core.dao.UserOrderMapper;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderExample;
import com.hjgj.aiyoujin.core.model.OrderLog;
import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.User;
import com.hjgj.aiyoujin.core.model.vo.OrderWebVo;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.model.vo.ProductVo;

@Service
public class UserOrderService {

    protected final Logger logger = LoggerFactory.getLogger(UserOrderService.class);

    @Autowired
    private UserOrderMapper userOrderMapper;
    
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderLogMapper orderLogMapper;
    
    @Autowired
    private OrderMessageMapper orderMessageMapper;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderMessageService orderMessageService;
    
    @Autowired
    private WxMPService wxMPService;

    
    
    @Transactional
    public int createOrder(Order order, OrderLog orderLog, OrderMessage orderMessage) {
        int count = userOrderMapper.insert(order);
        if(count > 0){
        	orderLogMapper.insert(orderLog);
        	orderMessageMapper.insert(orderMessage);
        	//扣减库存
        	try {
				productService.updateQuantity(order.getProductId(),-1);
			} catch (BusinessException e) {
				logger.error("创建订单扣减库存异常", e.getMsg());
			}
        }
        return count;
    }
    
    /**
     * 
     * @Title:        updateStauts 
     * @Description:  更新订单状态
     * @param:        @param orderId
     * @param:        @param orderStatus
     * @param:        @return    
     * @return:       int    
     * @throws 			
     * @author        ailiming@gold32.com
     * @Date          2018年1月16日 上午11:55:59
     */
    public int updateOrderStauts(String orderId,Integer orderStatus) {
    	if(StringUtils.isBlank(orderId) || orderStatus == null){
    		logger.error("更新订单状态参数不能问空！");
    		return 0;
    	}
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(orderStatus);
        order.setUpdateTime(new Date());
        return 	orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 查询用户所有的订单
     *
     * @param openId
     * @param types
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<OrderWebVo> getUserAllOrders(String userId, String types, Integer pageNum, Integer pageSize) {
        Page<OrderWebVo> page = new Page<>(pageNum, pageSize, true);
        RowBounds rowBounds = new RowBounds(page.getStartRow(), pageSize);
        int count = userOrderMapper.countUserAllOrdersByUserId(userId, types, rowBounds);
        if (count > 0) {
            List<OrderWebVo> orderVOList = userOrderMapper.getUserAllOrdersByUserId(userId, types, rowBounds);
            page.setList(orderVOList);
            page.setTotal(count);
        }
        return page;
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
        hashMap.put("orderStatus", orderState);
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

    public int batchInsertOrder(List<Order> orderList) {


        return 0;
    }

    public Order getOrderById(String orderId) {
        return userOrderMapper.selectByPrimaryKey(orderId);
    }
    
    
    public OrderWebVo queryOrderDetail(String orderId) throws Exception{
    	Order order = userOrderMapper.selectByPrimaryKey(orderId);
    	ProductVo product = productService.queryGoodsDetail(order.getProductId());
    	Order orderFrom = userOrderMapper.selectByPrimaryKey(order.getSourceOrderId());
    	User userFrom  = userService.getUserByUserId(orderFrom.getUserId());
    	OrderWebVo orderVo = new OrderWebVo();
    	orderVo.setSellAmount(order.getSellAmount());
    	orderVo.setProductName(product.getName());
    	orderVo.setProductId(order.getProductId());
    	orderVo.setFromNickName(userFrom.getNickname());
    	orderVo.setFromAvatar(userFrom.getAvatar());
    	orderVo.setLargePictures(product.getLargePictures());
    	orderVo.setOrderStatus(OrderStatusEnum.switchOrderStateName(order.getStatus()));
    	orderVo.setOrderId(order.getId());
    	orderVo.setUserId(order.getUserId());
    	OrderMessage om = orderMessageService.queryMessage(order.getId());
    	if(om != null){
    		orderVo.setMessage(om.getContent());
    		orderVo.setVideoUrl(om.getVideoUrl());
    		orderVo.setImageUrl(om.getImageUrl());
    	}
    	return orderVo;
    }

    public int insertOrder(Order order){
        int fromOrderResult = userOrderMapper.insert(order);
        return fromOrderResult;
    }
    
    
    @Transactional
    public int receiveOrder(Order order) throws Exception{
    	String orderNo = CommonUtils.generateOrderNo("TF");
    	order.setId(UUIDGenerator.generate());
    	order.setCreateTime(new Date());
    	order.setDeleted(Constants.DelFlag.NO.ordinal());
    	order.setCode(orderNo);
    	//添加新订单
       int count = userOrderMapper.insert(order);
        if(count > 0){
        	//更新订单状态为  领取成功
        	this.updateOrderStauts(order.getSourceOrderId(),OrderStatusEnum.ORDER_STATUS_SEND_SUCCESS.getCode());
        }
       return count;
    }
    
    /**
     * 
     * @Title:        giftToCash 
     * @Description:  f
     * @param:        @param order
     * @param:        @return
     * @param:        @throws Exception    
     * @return:       int    
     * @throws 			
     * @author        ailiming@gold32.com
     * @Date          2018年1月16日 下午4:54:30
     */
    @Transactional
    public Map giftToCash(Order order,String openId) throws Exception{
    	Map map = null;;
        OrderLog log = new OrderLog();
    	try {
    		//变现单位为：分
    		DecimalFormat df = new DecimalFormat("#");
            String money = df.format(order.getSellAmount().multiply(new BigDecimal(100)));
    		 map = wxMPService.promotionTransfers(openId,UUIDGenerator.generate(),order.getCode(),money,"商户向用户微信打款");
    		if(map.get("code").equals("0")){
    			//1、修改订单状态为  ：已变现
    			this.updateOrderStauts(order.getId(), OrderStatusEnum.ORDER_STATUS_CASH_SUCCESS.getCode());
    			log.setStatus(1);
    		}else{
    			this.updateOrderStauts(order.getId(), OrderStatusEnum.ORDER_STATUS_CASH_FAIL.getCode());
    			log.setStatus(2);
    		}
		} finally {
	        if(map != null){
	        	log.setCreateTime(new Date());
	        	log.setId(UUIDGenerator.generate());
	        	log.setOrderId(order.getId());
	        	log.setPayId(map.get("wxpayOrderNo").toString());
	        	log.setPayResultMsg(map.get("msg").toString());
	        	log.setPayType(0);
	        	log.setReqTime(new Date());
	        	log.setRespTime(new Date());
	        	log.setCreateBy("System");
	        	orderLogMapper.insertSelective(log);
	        }
		}
    	return map;
   
    }
}
