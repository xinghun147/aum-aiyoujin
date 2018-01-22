package com.hjgj.aiyoujin.server.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hjgj.aiyoujin.core.common.utils.FtpUtils;
import com.hjgj.aiyoujin.server.common.ResultModel;
import com.hjgj.aiyoujin.server.common.ResultStatus;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/upload")
public class UploadController  {
	
	protected final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private AtomicInteger atomicInteger;

	@Value("${coverimg.urlprefix}")
	private String imgUrlPrefix;

	@Value("${ftp.ip}")
	private String ftpIp;

	@Value("${ftp.port}")
	private Integer ftpPort;

	@Value("${ftp.user}")
	private String ftpUser;

	@Value("${ftp.pass}")
	private String ftpPass;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ApiOperation(value = "文件上传接口")
	public ResultModel upload(HttpServletRequest request,HttpServletResponse response) throws IOException {
		FtpUtils ftpUtils = new FtpUtils();
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    MultipartFile file = multipartRequest.getFile("file");  
	    logger.info("上传文件开始,fileName:{}",file.getOriginalFilename());
//		if(StringUtils.isEmpty(FileTypeUtil.getFileTypeByStream(file.getBytes()))){//校验图片是否合法
//		return ResultModel.error(ResultStatus.UPLOAD_NOT_IMAGE);
//	}
		String fileType = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
		String fullPath = "/aiyoujin/"+generateFileName(fileType);
		String fileName = StringUtils.substringAfterLast(fullPath, "/");
		String filePath= StringUtils.substringBeforeLast(fullPath, "/");
		try {
			ftpUtils.connectServer(ftpIp, ftpPort, ftpUser, ftpPass, "");
			ftpUtils.createDir(filePath);
			ftpUtils.upload(file.getInputStream(), fileName);
			return ResultModel.ok(imgUrlPrefix.concat(fullPath));
		} catch (Exception e) {
			logger.error("上传那文件接口异常,e:{}", e);
			return ResultModel.error(ResultStatus.SYSTEM_ERROR);
		}finally {
			ftpUtils.closeConnect();
		}
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
		sb.append(calendar.get(Calendar.MONTH)+1);
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
}
