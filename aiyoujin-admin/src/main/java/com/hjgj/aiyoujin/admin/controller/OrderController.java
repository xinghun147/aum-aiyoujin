package com.hjgj.aiyoujin.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hjgj.aiyoujin.admin.common.utils.HttpUtil;
import com.hjgj.aiyoujin.core.model.Express;
import com.hjgj.aiyoujin.core.model.vo.OrderRequestVo;
import com.hjgj.aiyoujin.core.model.vo.OrderVO;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.AdminOrderService;
import com.hjgj.aiyoujin.core.service.ExpressService;
import com.hjgj.aiyoujin.core.vo.ExpressResultVo;
import com.hjgj.aiyoujin.core.vo.ExpressVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "order")
public class OrderController extends BaseController {
	

    @Value("${express.address}")
	private String expressAddress;

	@Value("${aliyun.market.appcode}")
	private String appcode;
	
    @Autowired
    private AdminOrderService adminOrderService;
    @Autowired
    private ExpressService expressService;

    @RequestMapping("/entry.html")
    public String allUsers() {
        return "user/allUsers";
    }


    /**
     * 爱有金 买入订单
     *
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("buyOrderEntry.html")
    public ModelAndView getBuyOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getBuyOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/buyOrder/entry");
        modelAndView.addObject("userName", requestVo.getUserName());
        modelAndView.addObject("orderNo", requestVo.getOrderNo());
        modelAndView.addObject("productName", requestVo.getProductName());
        modelAndView.addObject("page", orderVOMap);
        return modelAndView;
    }

    /**
     * TODO 爱有金 变现订单
     *
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("sellOrderEntry.html")
    public ModelAndView getSellOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getSellOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/sellOrder/entry");
        modelAndView.addObject("vo", requestVo);
        modelAndView.addObject("page", orderVOMap);
        return modelAndView;
    }

    /**
     * TODO 爱有金 转送订单
     *
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("sendOrderEntry.html")
    public ModelAndView getSendOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {

        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getSendOrderAllMap(requestVo, pageNum, pageSize);
        modelAndView.setViewName("order/sendOrder/entry");
        modelAndView.addObject("vo", requestVo);
        modelAndView.addObject("page", orderVOMap);
        return modelAndView;
    }


    /**
     * TODO 爱有金 提货订单
     *
     * @param requestVo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("pickOrderEntry.html")
    public ModelAndView getPickOrderAll(OrderRequestVo requestVo, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : pageSize;
        ModelAndView modelAndView = new ModelAndView();
        Page<OrderVO> orderVOMap = adminOrderService.getPickOrderAllMap(requestVo, pageNum, pageSize);
        List<Express> express = expressService.findExpressName();
        modelAndView.addObject("vo", requestVo);
        modelAndView.addObject("express", express);
        modelAndView.addObject("page", orderVOMap);
        modelAndView.setViewName("order/pickOrder/entry");
        return modelAndView;
    }

    /**
     * 添加快递信息信息
     *
     * @param request
     * @param orderVO
     * @return
     */
    @ResponseBody
    @PostMapping("/addExpress.html")
    public String addExpress(HttpServletRequest request, OrderVO orderVO) {
    	String code = "200";
        try {
            adminOrderService.addExpressToOrder(orderVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(code);
    }
    /**
	 * 查看物流
	 * @param id
	 * @return
	 */
	@RequestMapping("/expressView.html")
	public ModelAndView expressView(String expressNo,String expressCode){
		ExpressResultVo expressResultVo = new ExpressResultVo();
		String status = null;
		String message = null;
		if (StringUtils.isNotBlank(expressNo)) {
			JSONObject jsonObject = getExpressDetail(expressNo,expressCode);
			status = jsonObject.getString("status");
			message = jsonObject.getString("message");
			if (("0").equals(status)) {//请求成功
				expressResultVo = JSONObject.toJavaObject(jsonObject.getJSONObject("result"), ExpressResultVo.class);
			}else {
				expressResultVo.setType(getExpressNameByCode(expressCode));
				expressResultVo.setNumber(expressNo);
				expressResultVo.setDeliverystatus("未知");
			}
		}else {
			status = "-1";
			message = "物流单号不存在";
		}
		logger.info("提金物流查询===status:{}===message:{}===detail:{}===>",status,message,expressResultVo);
		ModelAndView mav = getModelAndView();
		mav.addObject("status", status);
		mav.addObject("message", message);
		mav.addObject("vo", expressResultVo);
		mav.setViewName("order/pickOrder/orderExpressView");
		return mav;
	}
	/**
	 * 物流/快递
	 * 根据公司代码查询公司名称
	 * @param code
	 * @return
	 */
	public String getExpressNameByCode (String code) {
		ExpressVo vo = new ExpressVo();
		vo.setCode(code.toUpperCase());
		Express express = expressService.queryOneExpress(vo);
		if (null!=express) {
			return express.getName();
		}
		return code;
	}
	/**
     * 快递接口
     * @param expressNo
     * @param expressCode
     * @return
     */
	public JSONObject getExpressDetail(String expressNo, String expressCode) {
  	  Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("no",expressNo.trim());
	    querys.put("type",StringUtils.isNotBlank(expressCode) ? expressCode : "auto");
	  	  try {
	  		/*  状态码       说明
			        201 	快递单号为空
					202 	快递公司为空
					203		快递公司不存在
					204		快递公司识别失败
					205		没有信息；单号错误 (正确单号应是扫描入库的正规单号)
					207		该单号被限制，错误单号；一个单号对应多个快递公司，请求须指定快递公司	*/
	  	logger.info("调用第三方接口【快递查询】请求参数,expressNo:{},type:{}",expressNo,expressCode);
	  	String res =  HttpUtil.getRequest(expressAddress, querys, headers);
	  	logger.info("调用第三方接口【快递查询】返回数据 {}",res);
	  	JSONObject obj = JSONObject.parseObject(res);
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