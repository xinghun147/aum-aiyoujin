package com.hjgj.aiyoujin.admin.controller;

import com.hjgj.aiyoujin.core.model.vo.OrderRequestVo;
import com.hjgj.aiyoujin.core.model.vo.OrderVO;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "order")
public class OrderController extends BaseController {

    @Autowired
    private AdminOrderService adminOrderService;

    @RequestMapping("/entry.html")
    public String allUsers() {
        return "user/allUsers";
    }


    /**
     * 爱有金 买入订单
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("buyOrderEntry.html")
    public ModelAndView getBuyOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getBuyOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/buyOrder/entry");
        modelAndView.addObject("userName",requestVo.getUserName());
        modelAndView.addObject("orderNo",requestVo.getOrderNo());
        modelAndView.addObject("productName",requestVo.getProductName());
        modelAndView.addObject("page",orderVOMap);
        return modelAndView;
    }

    /**
     * TODO 爱有金 变现订单
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("sellOrderEntry.html")
    public ModelAndView getSellOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getSellOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/sellOrder/entry");
        modelAndView.addObject("vo",requestVo);
        modelAndView.addObject("page",orderVOMap);
        return modelAndView;
    }

    /**
     * TODO 爱有金 转送订单
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("sendOrderEntry.html")
    public ModelAndView getSendOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {

        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getSendOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/sendOrder/entry");
        modelAndView.addObject("vo",requestVo);
        modelAndView.addObject("page",orderVOMap);
        return modelAndView;
    }


    /**
     * TODO 爱有金 提货订单
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("pickOrderEntry.html")
    public ModelAndView getPickOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getPickOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/pickOrder/entry");
        modelAndView.addObject("vo",requestVo);
        modelAndView.addObject("page",orderVOMap);
        return modelAndView;
    }
}