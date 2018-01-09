package com.hjgj.aiyoujin.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hjgj.aiyoujin.admin.common.utils.FileTypeUtil;
import com.hjgj.aiyoujin.core.model.Product;
import com.hjgj.aiyoujin.core.model.ProductPicture;
import com.hjgj.aiyoujin.core.service.ProductPictureService;
import com.hjgj.aiyoujin.core.service.ProductService;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lin on 2017/9/15.
 */
@Controller
@RequestMapping("/pic")
public class ProductPictureController extends BaseController{

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPictureService productPictureService;
    
    @Value("${coverimg.urlprefix}")
    private String baseUrl;

    @RequestMapping("/picManage")
    public ModelAndView toPage(String id){
        ModelAndView mav = getModelAndView();
        mav.addObject("productId", id);
        mav.setViewName("product/picture/picManage");
        return mav;
    }

    @RequestMapping("/picEdit")
    public ModelAndView picEdit(String id){
        ProductPicture productPicture = productPictureService.queryProductPictureById(id);
        ModelAndView mav = getModelAndView();
        mav.addObject("data",productPicture);
        mav.setViewName("product/picture/picEdit");
        return mav;
    }

    @ResponseBody
    @RequestMapping("/toEdit")
    public String toEdit(ProductPicture productPicture){
    	productPictureService.addOrUpdateProductPic(productPicture);
        return SUCCESS;
    }

    @ResponseBody
    @RequestMapping("/queryProductList")
    public List<Product> queryProductList(){
        return productService.queryAll();
    }

    @ResponseBody
    @RequestMapping("/queryProductPictures")
    public List<ProductPicture> queryProductPictureById(String productId){
        return productPictureService.queryProductPicture(productId);
    }

    @ResponseBody
    @RequestMapping("/uploadPicture")
    public int uploadPicture(MultipartFile file,String productId) throws IOException{
        if(StringUtils.isEmpty(FileTypeUtil.getFileTypeByStream(file.getBytes()))){//校验图片是否合法
            return 1;
        }
        if(productId == null || productId.equals("")){
            return 1;
        }
        String path = uploadFiles(file);
        if(path == null || path.equals("")){
            return 1;
        }
        ProductPicture productPicture = new ProductPicture();
        productPicture.setProductId(productId);
        productPicture.setPath(baseUrl.concat(path));
        productPictureService.addOrUpdateProductPic(productPicture);
        return 0;
    }

    /**
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delProductPic")
    public int delProductPic(@RequestParam("ids[]") List<String> ids){
        System.out.println(ids);
        if(ids != null){
        	productPictureService.delProductPicture(ids);
            return 0;
        }
        return 1;
    }

}
