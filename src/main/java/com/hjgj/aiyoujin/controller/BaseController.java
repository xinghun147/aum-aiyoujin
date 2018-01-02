package com.hjgj.aiyoujin.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hjgj.aiyoujin.common.utils.CustomerDateEditor;
import com.hjgj.aiyoujin.common.utils.FtpUtils;

public class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(super.getClass().getName());
    
	@Autowired
    private AtomicInteger atomicInteger;

    @Value("${ftp.ip}")
    protected String ftpIp;

    @Value("${ftp.port}")
    protected Integer ftpPort;

    @Value("${ftp.user}")
    protected String ftpUser;

    @Value("${ftp.pass}")
    protected String ftpPass;
    
    @Value("${express.address}")
	private String expressAddress;

	@Value("${aliyun.market.appcode}")
	private String appcode;

	
    protected int pageNum = 1;
    protected int pageSize = 10;

    protected String SUCCESS = "succ";
    protected String FAIL = "fail";

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        // 对于需要转换为Date类型的属性，使用DateEditor进行处理
        binder.registerCustomEditor(Date.class, new CustomerDateEditor());
    }

    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 过滤最后一位字符串
     *
     * @param str
     * @return
     */
    protected String characterFilter(String str) {
        if (null != str && !("").equals(str)) {
            if (str.lastIndexOf("|") != (str.length() - 1)) {
                return str;
            } else {
                return str.substring(0, (str.length() - 1));
            }
        } else {
            return "";
        }
    }

    public String uploadFiles(MultipartFile file) throws IOException {
        Assert.notNull(file, "file can not be empty");
        FtpUtils ftpUtils = new FtpUtils();
        /*if(StringUtils.isEmpty(FileTypeUtil.getFileTypeByStream(file.getBytes()))){//校验图片是否合法
            return null;
        }*/
        String fileType = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        String fullPath = "/aiyoujin/" + generateFileName(fileType);
        String fileName = StringUtils.substringAfterLast(fullPath, "/");
        String filePath = StringUtils.substringBeforeLast(fullPath, "/");
        try {
            ftpUtils.connectServer(ftpIp, ftpPort, ftpUser, ftpPass, "");
            ftpUtils.createDir(filePath);
            ftpUtils.upload(file.getInputStream(), fileName);
            return fullPath;
        } catch (Exception e) {
            logger.error("上传那文件接口异常,e:{}", e);
        } finally {
            ftpUtils.closeConnect();
        }
        return null;
    }

    public String generateFileName(String fileType) {
        // 以每分钟为key
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        // hourOfYear 4位数
        // int
        // hourOfYear=9999-(calendar.get(Calendar.DAY_OF_YEAR)*24+calendar.get(Calendar.HOUR_OF_DAY));
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append(calendar.get(Calendar.MONTH) + 1);
        sb.append("/");
        sb.append(calendar.get(Calendar.DAY_OF_MONTH));
        sb.append("-");
        sb.append(calendar.get(Calendar.HOUR_OF_DAY));// 1位
        sb.append(calendar.get(Calendar.MINUTE));// 1位
        sb.append(calendar.get(Calendar.SECOND));// 1位
        sb.append("-");
        sb.append(atomicInteger.getAndIncrement());
        sb.append(RandomStringUtils.randomAlphanumeric(5));
        sb.append(".");
        sb.append(fileType);
        return sb.toString();
    }
    
    /**
     * 快递接口
     * @param expressNo
     * @param expressCode
     * @return
     */
//    public JSONObject getExpressDetail(String expressNo, String expressCode) {
//    	  Map<String, String> headers = new HashMap<String, String>();
//  	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//  	    headers.put("Authorization", "APPCODE " + appcode);
//  	    Map<String, String> querys = new HashMap<String, String>();
//  	    querys.put("no",expressNo.trim());
//  	    querys.put("type",StringUtils.isNotBlank(expressCode) ? expressCode : "auto");
//	  	  try {
//	  		/*  状态码       说明
//			        201 	快递单号为空
//					202 	快递公司为空
//					203		快递公司不存在
//					204		快递公司识别失败
//					205		没有信息；单号错误 (正确单号应是扫描入库的正规单号)
//					207		该单号被限制，错误单号；一个单号对应多个快递公司，请求须指定快递公司	*/
//	  	logger.info("调用第三方接口【快递查询】请求参数,expressNo:{},type:{}",expressNo,expressCode);
//	  	String res =  HttpUtil.getRequest(expressAddress, querys, headers);
//	  	logger.info("调用第三方接口【快递查询】返回数据 {}",res);
//	  	JSONObject obj = JSONObject.parseObject(res);
//	  	String status = obj.getString("status");
//			if (("0").equals(status)) {//请求成功
//	  			JSONObject result = obj.getJSONObject("result");
//	  			if (null != result) {
//	  				expressCode = result.getString("type");//快递公司编码
//	  				if (StringUtils.isNotBlank(expressCode)) {
//	  					ExpressVo vo = new ExpressVo();
//	  					vo.setCode(expressCode.toUpperCase());
//	  					String deliveryStatus =  result.getString("deliverystatus");//送货状态
//	  					if (("1").equals(deliveryStatus)) {
//	  						deliveryStatus = "在途中";
//						} else if (("2").equals(deliveryStatus)) {
//							deliveryStatus = "正在派件";
//						}else if (("3").equals(deliveryStatus)) {
//							deliveryStatus = "已签收";
//						}else if (("4").equals(deliveryStatus)) {
//							deliveryStatus = "派送失败";
//						}else {
//							deliveryStatus = "未知状态";
//						}
//	  					obj.getJSONObject("result").put("deliverystatus", deliveryStatus);
//	  					Express express = expressApi.queryOneExpress(vo);
//	  					if (null!=express) {
//	  						obj.getJSONObject("result").put("type", express.getName());
//	  						obj.getJSONObject("result").put("ophone", express.getOfficialPhone());
//							}
//						}
//					}
//			}
//			return obj;
//	  } catch (Exception e) {
//	  	logger.error("调用快递查询接口异常,e:{}", e);
//		return null;
//	  }
//    }
}
