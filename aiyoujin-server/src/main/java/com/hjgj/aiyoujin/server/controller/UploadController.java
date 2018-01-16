package com.hjgj.aiyoujin.server.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hjgj.aiyoujin.core.common.utils.FileTypeUtil;
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
	public ResultModel upload(MultipartFile file) throws IOException {
		Assert.notNull(file, "file can not be empty");
		FtpUtils ftpUtils = new FtpUtils();
//		if(StringUtils.isEmpty(FileTypeUtil.getFileTypeByStream(file.getBytes()))){//校验图片是否合法
//			return ResultModel.error(ResultStatus.UPLOAD_NOT_IMAGE);
//		}
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
	
	private  void upload(HttpServletRequest request){
		  //获取文件需要上传到的路径
//        String path = request.getRealPath("/upload") + "/";
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.mkdir();
//        }
//        logger.debug("path=" + path);
//
//        request.setCharacterEncoding("utf-8");  //设置编码
//        //获得磁盘文件条目工厂
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        //如果没以下两行设置的话,上传大的文件会占用很多内存，
//        //设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
//        /**
//         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
//         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
//         * 然后再将其真正写到对应目录的硬盘上
//         */
//        factory.setRepository(dir);
//        //设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
//        factory.setSizeThreshold(1024 * 1024);
//        //高水平的API文件上传处理
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        try {
//            List<FileItem> list = upload.parseRequest(request);
//            FileItem picture = null;
//            for (FileItem item : list) {
//                //获取表单的属性名字
//                String name = item.getFieldName();
//                //如果获取的表单信息是普通的 文本 信息
//                if (item.isFormField()) {
//                    //获取用户具体输入的字符串
//                    String value = item.getString();
//                    request.setAttribute(name, value);
//                    logger.debug("name=" + name + ",value=" + value);
//                } else {
//                    picture = item;
//                }
//            }
//
//            //自定义上传图片的名字为userId.jpg
//            String fileName = request.getAttribute("userId") + ".jpg";
//            String destPath = path + fileName;
//            logger.debug("destPath=" + destPath);
//
//            //真正写到磁盘上
//            File file = new File(destPath);
//            OutputStream out = new FileOutputStream(file);
//            InputStream in = picture.getInputStream();
//            int length = 0;
//            byte[] buf = new byte[1024];
//            // in.read(buf) 每次读到的数据存放在buf 数组中
//            while ((length = in.read(buf)) != -1) {
//                //在buf数组中取出数据写到（输出流）磁盘上
//                out.write(buf, 0, length);
//            }
//            in.close();
//            out.close();
//        } catch (FileUploadException e1) {
//            logger.error("", e1);
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//
//
//        PrintWriter printWriter = response.getWriter();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        HashMap<String, Object> res = new HashMap<String, Object>();
//        res.put("success", true);
//        printWriter.write(JSON.toJSONString(res));
//        printWriter.flush();
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
