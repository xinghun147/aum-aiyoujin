package com.hjgj.aiyoujin.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.OrderMessageService;

/**
 * Created by Lin on 2017/9/5.
 */
@Controller
@RequestMapping(value ="orderMessage")
public class OrderMessageController extends BaseController{

    @Autowired
    private OrderMessageService orderMessageService;

    /**
     * 活期产品列表
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("orderMessage.html")
    public ModelAndView queryProduct(ModelMap modelMap , OrderMessage orderMessage, Integer pageNum , Integer pageSize) throws Exception{
        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        Page<OrderMessage> page  = orderMessageService.queryPageProductMessage(orderMessage,pageNum, pageSize);
        ModelAndView mav = getModelAndView();
        mav.addObject("page", page);
        mav.addObject("product",orderMessage);
        mav.setViewName("orderMessage/orderMessage");
        return mav;
    }

    
    @RequestMapping("orderMessageInfo.html")
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
