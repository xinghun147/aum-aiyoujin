package com.hjgj.aiyoujin.server.controller;

import com.hjgj.aiyoujin.core.model.Product;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.model.vo.ProductVo;
import com.hjgj.aiyoujin.core.service.ProductService;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Lin on 2017/9/5.
 */
@RestController
@RequestMapping("/api/product")
public class ProductApiController{
	
	protected final Logger logger = LoggerFactory.getLogger(ProductApiController.class);


    @Autowired
    private ProductService productService;
    
    
    @ApiOperation(value = "查询商品列表")
	@RequestMapping(value = "queryPageGoods", method = RequestMethod.GET)
	public ResultModel queryGoodsDetail(@ApiParam(value = "第多少页", required = true)  @RequestParam Integer pageNum,
										@ApiParam(value = "每页多少", required = true) @RequestParam Integer pageSize) {
    	Assert.notNull(pageNum, "页码 can not be empty");
    	Assert.notNull(pageSize, "每页条数 can not be empty");
		try {
            Page<ProductVo> data = productService.queryPageGoods(new Product(), pageNum, 100);
			return ResultModel.ok(data);
		} catch (Exception e) {
			logger.error("查询商品列表接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
	
    
    @ApiOperation(value = "查询商品详情")
	@RequestMapping(value = "queryGoodsDetail", method = RequestMethod.GET)
	public ResultModel queryAddress(@ApiParam(value = "商品id", required = true) @RequestParam String id) {
		try {
			ProductVo data = productService.queryGoodsDetail(id);
			return ResultModel.ok(data);
		} catch (Exception e) {
			logger.error("查询商品明细接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
    
}
