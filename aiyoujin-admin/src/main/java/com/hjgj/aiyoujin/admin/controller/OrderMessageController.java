package com.hjgj.aiyoujin.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.OrderMessageService;
import com.hjgj.aiyoujin.core.service.UserOrderService;

/**
 * Created by Lin on 2017/9/5.
 */
@Controller
@RequestMapping(value ="orderMessage")
public class OrderMessageController extends BaseController{

    @Autowired
    private OrderMessageService orderMessageService;
    
    @Autowired
    private UserOrderService userOrderService;

    /**
     * 活期产品列表
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/orderMessage.html")
    public ModelAndView queryProduct(ModelMap modelMap ,String orderNo, Integer pageNum , Integer pageSize) throws Exception{
        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        OrderMessage orderMessage = new OrderMessage();
        if(StringUtils.isNotBlank(orderNo)){
        	orderMessage.setOrderNo(orderNo);
        }
        Page<OrderMessage> page  = orderMessageService.queryPageProductMessage(orderMessage,pageNum, pageSize);
        ModelAndView mav = getModelAndView();
        mav.addObject("page", page);
        mav.addObject("orderNo",orderNo);
        mav.setViewName("orderMessage/orderMessage");
        return mav;
    }

    
    @RequestMapping("/orderMessageInfo.html")
    public ModelAndView orderInfo(String id){
    	ModelAndView mav = getModelAndView();
        try {
        	if(StringUtils.isNotBlank(id)){
        		OrderMessage orderMessage = orderMessageService.findProduct(id);
        		mav.addObject("prod",orderMessage);
        	}
        	 mav.setViewName("productMessage/productMessageInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return mav;
    }
    
    

}
