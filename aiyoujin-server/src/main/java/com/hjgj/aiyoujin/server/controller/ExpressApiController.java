package com.hjgj.aiyoujin.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hjgj.aiyoujin.core.model.Express;
import com.hjgj.aiyoujin.core.model.Order;
import com.hjgj.aiyoujin.core.model.ProductPicture;
import com.hjgj.aiyoujin.core.service.ExpressService;
import com.hjgj.aiyoujin.core.service.ProductPictureService;
import com.hjgj.aiyoujin.core.service.UserOrderService;
import com.hjgj.aiyoujin.core.vo.ExpressVo;
import com.hjgj.aiyoujin.server.util.HttpUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/express/")
public class ExpressApiController {
    protected final Logger logger = LoggerFactory.getLogger(ExpressApiController.class);
    /**
     * 快递接口
     * @param expressNo
     * @param expressCode
     * @return
     */
    @Value("${aliyun.market.appcode}")
	private String appcode;
    @Value("${express.address}")
  	private String expressAddress;
    @Autowired
    private ExpressService expressService;
    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private ProductPictureService productPictureService ;
    @ApiOperation(value = "快递接口")
    @RequestMapping(value = "getExpressDetail", method = RequestMethod.POST)
    public JSONObject getExpressDetail(String orderId) {
    	Order order =userOrderService.getOrderById(orderId);
    	if(order==null){
    		JSONObject obj = new JSONObject();
    		obj.put("mes", "该订单不存在");
    		return obj;
    	}
    	Map<String, String> headers = new HashMap<String, String>();
  	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
  	    headers.put("Authorization", "APPCODE " + appcode);
  	    Map<String, String> querys = new HashMap<String, String>();
  	    querys.put("no",order.getExpressNo().trim());
  	    String expressCode="";
  	    querys.put("type",StringUtils.isNotBlank(expressCode) ? expressCode : "auto");
	  	  try {
	  		/*  状态码       说明
			        201 	快递单号为空
					202 	快递公司为空
					203		快递公司不存在
					204		快递公司识别失败
					205		没有信息；单号错误 (正确单号应是扫描入库的正规单号)
					207		该单号被限制，错误单号；一个单号对应多个快递公司，请求须指定快递公司	*/
	  	logger.info("调用第三方接口【快递查询】请求参数,expressNo:{},type:{}",order.getExpressNo(),order.getExpressCompany());
	  	expressCode=order.getExpressCompany();
	  	String res =  HttpUtil.getRequest(expressAddress, querys, headers);
	  	logger.info("调用第三方接口【快递查询】返回数据 {}",res);
	  	JSONObject obj = JSONObject.parseObject(res);
	  	List<ProductPicture> list =productPictureService.queryProductPicture(order.getProductId());
    	for(int i=0;i<list.size();i++){
    		ProductPicture p=list.get(i);
    		if(p.getType()==0){
    			obj.put("path",p.getPath());
    			break;
    		}
    	}
	  	String status = obj.getString("status");
			if (("0").equals(status)) {//请求成功
	  			JSONObject result = obj.getJSONObject("result");
	  			if (null != result) {
	  				expressCode = result.getString("type");//快递公司编码
	  				if (StringUtils.isNotBlank(expressCode)) {
	  					ExpressVo vo = new ExpressVo();
	  					vo.setCode(expressCode.toUpperCase());
	  					String deliveryStatus =  result.getString("deliverystatus");//送货状态
	  					if (("1").equals(deliveryStatus)) {
	  						deliveryStatus = "在途中";
						} else if (("2").equals(deliveryStatus)) {
							deliveryStatus = "正在派件";
						}else if (("3").equals(deliveryStatus)) {
							deliveryStatus = "已签收";
						}else if (("4").equals(deliveryStatus)) {
							deliveryStatus = "派送失败";
						}else {
							deliveryStatus = "未知状态";
						}
	  					obj.getJSONObject("result").put("deliverystatus", deliveryStatus);
	  					Express express = expressService.queryOneExpress(vo);
	  					if (null!=express) {
	  						obj.getJSONObject("result").put("type", express.getName());
	  						obj.getJSONObject("result").put("ophone", express.getOfficialPhone());
							}
						}
					}
			}
			return obj;
	  } catch (Exception e) {
	  	logger.error("调用快递查询接口异常,e:{}", e);
		return null;
	  }
    }


}
