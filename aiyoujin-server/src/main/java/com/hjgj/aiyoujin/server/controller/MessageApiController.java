package com.hjgj.aiyoujin.server.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hjgj.aiyoujin.core.model.OrderMessage;
import com.hjgj.aiyoujin.core.model.ProductMessage;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.OrderMessageService;
import com.hjgj.aiyoujin.core.service.ProductMessageService;
import com.hjgj.aiyoujin.core.vo.OrderMessageVo;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;

import io.swagger.annotations.ApiOperation;


/**
 * Created by Lin on 2017/9/5.
 */
@RestController
@RequestMapping("/api/message")
public class MessageApiController{
	
	protected final Logger logger = LoggerFactory.getLogger(MessageApiController.class);


    @Autowired
    private ProductMessageService productMessageService;
    
    @Autowired
    private OrderMessageService orderMessageService;
    
    @ApiOperation(value = "查询留言模板")
	@RequestMapping(value = "queryProductMessage", method = RequestMethod.GET)
	public ResultModel queryGoodsDetail(String id) {
		try {
			Page<ProductMessage> data = productMessageService.queryPageProductMessage(new ProductMessage(),1, 100);
			List<ProductMessage> list =data.getList();
			int index=(int)(Math.random()*(list.size()-1));
			ProductMessage p=list.get(index);
			while(p.getId().equals(id)&&list.size()>1)
				p=list.get((int)(Math.random()*(list.size()-1)));
			return ResultModel.ok(p);
		} catch (Exception e) {
			logger.error("查询商品列表接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}
	}
    @ApiOperation(value = "根据订单查询留言")
   	@RequestMapping(value = "queryOrderMessage", method = RequestMethod.GET)
   	public ResultModel queryOrderMessage(String orderId) {
   		try {
   			OrderMessage om=orderMessageService.queryMessage(orderId);
   			OrderMessageVo omVo= new OrderMessageVo();
   			omVo.setTitle(om.getTitle());
   			omVo.setContent(om.getContent());
   			omVo.setImageUrl(om.getImageUrl());
   			omVo.setVideoUrl(om.getVideoUrl());
   			return ResultModel.ok(omVo);
   		} catch (Exception e) {
   			logger.error("查询商品列表接口异常,e:{}", e);
   			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
   		}
   	}
}
