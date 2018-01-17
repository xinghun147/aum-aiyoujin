package com.hjgj.aiyoujin.admin.controller;

import com.hjgj.aiyoujin.admin.common.utils.SessionUtil;
import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.model.ProductMessage;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.ProductMessageService;
import com.hjgj.permissions.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lin on 2017/9/5.
 */
@Controller
@RequestMapping(value = "/productMessage")
public class ProductMessageController extends BaseController {

    @Autowired
    private ProductMessageService productMessageService;

    /**
     * 活期产品列表
     *
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/productMessage.html")
    public ModelAndView queryProduct(ModelMap modelMap, ProductMessage productMessage, Integer pageNum, Integer pageSize) throws Exception {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : pageSize;
        productMessage.setDeleted(Constants.DelFlag.NO.ordinal());
        Page<ProductMessage> page = productMessageService.queryPageProductMessage(productMessage, pageNum, pageSize);
        ModelAndView mav = getModelAndView();
        mav.addObject("page", page);
        mav.addObject("product", productMessage);
        mav.setViewName("productMessage/productMessage");
        return mav;
    }

    /**
     * 跳转添加产品页面
     *
     * @param id
     * @param version
     * @return
     */
    @RequestMapping("/productMessageAdd.html")
    public ModelAndView addProduct(String id) {
        ModelAndView mav = getModelAndView();
        try {
            if (StringUtils.isNotBlank(id)) {
                ProductMessage productMessage = productMessageService.findProduct(id);
                mav.addObject("prod", productMessage);
            }
            mav.setViewName("productMessage/productMessageEdit");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 删除添加产品页面
     *
     * @param id
     * @param version
     * @return
     */
    @RequestMapping("/productMessageDel.html")
    public String productMessageDel(String id) {
        ModelAndView mav = getModelAndView();
        try {
            if (StringUtils.isNotBlank(id)) {
                ProductMessage productMessage = productMessageService.findProduct(id);
                productMessage.setDeleted(Constants.DelFlag.YES.ordinal());
                productMessageService.insertOrUpdate(productMessage);
                mav.addObject("prod", productMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:productMessage.html";
    }

    /**
     * 修改产品数据
     *
     * @param request
     * @param modelMap
     * @param recycleProduct
     * @param product
     * @return
     */
    @RequestMapping("/productMessageEdit.html")
    public String fixedProductEdit(HttpServletRequest request, ModelMap modelMap, ProductMessage productMessage) {
        User user = SessionUtil.getSessionUser(request);
        try {
            productMessageService.insertOrUpdate(productMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:productMessage.html";
    }

    @RequestMapping("/productMessageInfo.html")
    public ModelAndView fixedProductInfo(String id) {
        ModelAndView mav = getModelAndView();
        try {
            if (StringUtils.isNotBlank(id)) {
                ProductMessage productMessage = productMessageService.findProduct(id);
                mav.addObject("prod", productMessage);
            }
            mav.setViewName("productMessage/productMessageInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }


}
