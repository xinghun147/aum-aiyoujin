package com.hjgj.aiyoujin.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hjgj.aiyoujin.admin.common.utils.FtpUtils;
import com.hjgj.aiyoujin.admin.common.utils.HttpUtil;
import com.hjgj.aiyoujin.admin.common.utils.SessionUtil;
import com.hjgj.aiyoujin.core.common.Constants;
import com.hjgj.aiyoujin.core.model.MiniQrcode;
import com.hjgj.aiyoujin.core.model.vo.Page;
import com.hjgj.aiyoujin.core.service.MiniQrcodeService;
import com.hjgj.permissions.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by Lin on 2017/9/5.
 */
@Controller
@RequestMapping(value = "qrcode")
public class MiniQrcodeController extends BaseController {

    @Autowired
    private MiniQrcodeService miniQrcodeService;

    @Value("${coverimg.urlprefix}")
    private String baseUrl;

    /**
     * 活期产品列表
     *
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/miniQrcode.html")
    public ModelAndView queryProduct(ModelMap modelMap, MiniQrcode miniQrcode, Integer pageNum, Integer pageSize) throws Exception {
        pageNum = pageNum == null ? super.pageNum : pageNum;
        pageSize = pageSize == null ? super.pageSize : pageSize;
        Page<MiniQrcode> page = miniQrcodeService.queryPageMiniQrcode(miniQrcode, pageNum, pageSize);
        ModelAndView mav = getModelAndView();
        mav.addObject("page", page);
        mav.addObject("miniQrcode", miniQrcode);
        mav.setViewName("qrcode/miniQrcode");
        return mav;
    }

    @RequestMapping("/miniQrcodeAdd.html")
    public ModelAndView addProduct(String id) {
        ModelAndView mav = getModelAndView();
        try {
            if (StringUtils.isNotBlank(id)) {
                MiniQrcode miniQrcode = miniQrcodeService.getMiniQrcodeById(id);
                mav.addObject("miniQrcode", miniQrcode);
            }
            mav.setViewName("qrcode/miniQrcodeEdit");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }


    @RequestMapping("/miniQrcodeEdit.html")
    public String fixedProductEdit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, MiniQrcode miniQrcode) {
        User user = SessionUtil.getSessionUser(request);
        try {

            //生成图片二维码
            String qequrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbad011f34af54919"+
                    "&secret=8c998dd4511ad0c2b23e40a0cfebe1a1";
            String res = HttpUtil.getRequest(qequrl);
            JSONObject obj = JSONObject.parseObject(res);
            String accessToken = obj.get("access_token").toString();
            String qrcodeImg ="";
            OutputStream out = null; //写
            JSONObject param = new JSONObject();
            param.put("scene",miniQrcode.getCode());
            param.put("page",miniQrcode.getPages());
            try {
                URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                //一定要设置 Content-Type 要不然服务端接收不到参数
                conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
                //指示应用程序要将数据写入URL连接,其值默认为false（是否传参）
                conn.setDoOutput(true);
                conn.setConnectTimeout(5 * 1000);
                //获取输出流
                out = conn.getOutputStream();
                //输出流里写入POST参数
                out.write(param.toJSONString().getBytes());
                out.flush();
                out.close();

                InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
/*                byte data[] = readInputStream(inStream);
                inStream.read(data);  //读数据
                inStream.close();
                response.setContentType("image/jpg"); //设置返回的文件类型
                OutputStream os = response.getOutputStream();*/
                String fullPath = "/aiyoujin/qrcode/"+miniQrcode.getCode()+"_qrcode.png";
                FtpUtils ftpUtils = new FtpUtils();
                ftpUtils.connectServer(ftpIp, ftpPort, ftpUser, ftpPass, "");
                ftpUtils.createDir("/aiyoujin/qrcode/");
                ftpUtils.upload(inStream,fullPath);
                miniQrcode.setImgUrl(baseUrl +fullPath);
    /*            os.write(data);
                os.flush();
                os.close();*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (StringUtils.isNotBlank(miniQrcode.getId())){
                miniQrcodeService.updateMiniQrcode(miniQrcode);
            }else{
                miniQrcodeService.insert(miniQrcode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:miniQrcode.html";
    }


    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    @RequestMapping("/miniQrcodeInfo.html")
    public ModelAndView fixedProductInfo(String id) {
        ModelAndView mav = getModelAndView();
        try {
            if (StringUtils.isNotBlank(id)) {
                MiniQrcode miniQrcode = miniQrcodeService.getMiniQrcodeById(id);
                mav.addObject("miniQrcode", miniQrcode);
            }
            mav.setViewName("qrcode/miniQrcodeInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }


}
