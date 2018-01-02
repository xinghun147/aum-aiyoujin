package com.hjgj.aiyoujin.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hjgj.aiyoujin.common.ResultStatus;
import com.hjgj.aiyoujin.model.Product;
import com.hjgj.aiyoujin.model.ResultModel;
import com.hjgj.aiyoujin.model.vo.Page;
import com.hjgj.aiyoujin.model.vo.ProductVo;
import com.hjgj.aiyoujin.service.ProductService;


/**
 * Created by Lin on 2017/9/5.
 */
@RestController
@RequestMapping("/api/product")
public class ProductApiController{
	
	protected final Logger logger = LoggerFactory.getLogger(ProductApiController.class);


    @Autowired
    private ProductService productService;
    
    
	@RequestMapping(value = "queryGoodsDetail", method = RequestMethod.GET)
	public ResultModel queryGoodsDetail(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
		try {
			Page<ProductVo> data = productService.queryPageGoods(new Product(), pageNum, pageSize);
			return ResultModel.ok(data);
		} catch (Exception e) {
			logger.error("查询商品列表接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
	
	
	@RequestMapping(value = "queryPageGoods", method = RequestMethod.GET)
	public ResultModel queryAddress(String id) {
		try {
			ProductVo data = productService.queryGoodsDetail(id);
			return ResultModel.ok(data);
		} catch (Exception e) {
			logger.error("查询商品明细接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
    
}
