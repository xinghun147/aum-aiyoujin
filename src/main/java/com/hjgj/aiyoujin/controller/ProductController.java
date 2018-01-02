package com.hjgj.aiyoujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hjgj.aiyoujin.common.utils.SessionUtil;
import com.hjgj.aiyoujin.model.Product;
import com.hjgj.aiyoujin.model.vo.Page;
import com.hjgj.aiyoujin.service.ProductService;
import com.hjgj.permissions.model.User;

/**
 * Created by Lin on 2017/9/5.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;

    /**
     * 活期产品列表
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/product.html")
    public ModelAndView queryProduct(ModelMap modelMap , Product product, Integer pageNum , Integer pageSize) throws Exception{
        pageNum = pageNum == null ? super.pageNum:pageNum;
        pageSize = pageSize == null ? super.pageSize:pageSize;
        Page<Product> page  = productService.queryPageProduct(product, pageNum, pageSize);
        ModelAndView mav = getModelAndView();
        mav.addObject("page", page);
        mav.addObject("product",product);
        mav.setViewName("product/product");
        return mav;
    }

    /**
     * 跳转添加产品页面
     * @param id
     * @param version
     * @return
     */
    @RequestMapping("/productAdd.html")
    public ModelAndView addProduct(String id){
    	ModelAndView mav = getModelAndView();
        try {
        	if(StringUtils.isNotBlank(id)){
        		Product product = productService.findProduct(id);
        		mav.addObject("descriptionStr",new String(product.getDescription()));
        		mav.addObject("prod",product);
        	}
        	 mav.setViewName("product/productEdit");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return mav;
    }
    
    /**
     * 修改产品数据
     * @param request
     * @param modelMap
     * @param recycleProduct
     * @param product
     * @return
     */
    @RequestMapping("/productEdit.html")
    public String fixedProductEdit(HttpServletRequest request,ModelMap modelMap ,Product product){
        User user = SessionUtil.getSessionUser(request);
        try {
        	if(StringUtils.isBlank(product.getId())){
        		product.setCreateBy(user.getFullname());
        	}
        	product.setUpdateBy(user.getFullname());
        	productService.insertOrUpdate(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:product.html";
    }
    
    @RequestMapping("/productInfo.html")
    public ModelAndView fixedProductInfo(String id){
    	ModelAndView mav = getModelAndView();
        try {
        	if(StringUtils.isNotBlank(id)){
        		Product product = productService.findProduct(id);
        		mav.addObject("descriptionStr",new String(product.getDescription()));
        		mav.addObject("prod",product);
        	}
        	 mav.setViewName("product/productInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return mav;
    }


    /**
     * 修改产品状态并更新页面   0草稿未上架、1上架未开售、2正在销售中、3售磬未下架、4下架
     * @param id
     * @param status
     * @return
     */
    @RequestMapping("/updateProductStatus")
    public String updateProductStatus(HttpServletRequest request,String id, Integer status){
        try{
        	User user = SessionUtil.getSessionUser(request);
        	productService.updateProductStatus(id, status, user.getFullname());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:product.html";
    }
}
