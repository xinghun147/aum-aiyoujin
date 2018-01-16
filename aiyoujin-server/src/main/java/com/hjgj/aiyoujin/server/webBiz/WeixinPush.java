package com.hjgj.aiyoujin.server.webBiz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.MessageToken;
import com.hjgj.aiyoujin.core.service.MessageTokenService;
import com.hjgj.aiyoujin.server.config.WeiXinProperty;

import weixin.popular.api.MessageAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.WxopenTemplateMessage;
import weixin.popular.bean.token.Token;

@Component
public class WeixinPush {

    @Autowired
    private WeiXinProperty weiXinProperty;

    @Autowired
    private MessageTokenService messageTokenService;

    /**
     * 微信支付成功通知
     * @param prodName
     * @param payMoney
     * @param payTime
     * @param prePayId
     * @param openId
     */
    @Async
    public void payResultNotify(String prodName,String payMoney,Date payTime,String prePayId,String openId) {
        String weixinToken = null;
        while (weixinToken == null){
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");
        
        // 付款金额
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(payMoney+"元");
        keyword2.setColor("#333333");

        // 付款时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateToString(payTime));
        keyword3.setColor("#333333");

        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(openId);
        wxopenTemplateMessage.setData(linkedHashMap);
        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(prePayId);
        wxopenTemplateMessage.setTemplate_id("xeZ-PIsudhp2uxzIng9iD6rIzBCwq7zg093tlpJ3Ev4");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);

    }
    /**
     * 微信支付失败通知
     * @param prodName
     * @param payMoney
     * @param failCause
     * @param payTime
     * @param prePayId
     * @param openId
     */
    @Async
    public void payResultNotifyFail(String prodName,String payMoney,String failCause,Date payTime,String prePayId,String openId) {
        String weixinToken = null;
        while (weixinToken == null){
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");

        // 付款金额
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(payMoney+"元");
        keyword2.setColor("#333333");

        // 付款时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateToString(payTime));
        keyword3.setColor("#333333");
        //失败原因
        TemplateMessageItem keyword4 = new TemplateMessageItem();
        keyword3.setValue(failCause);
        keyword3.setColor("#333333");
        
        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        linkedHashMap.put("keyword4", keyword4);
        
        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(openId);
        wxopenTemplateMessage.setData(linkedHashMap);
        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(prePayId);
        wxopenTemplateMessage.setTemplate_id("okd7M98Q7ZoqLGAVDwaxB9co9SxKLthkDg7kQ76XAVI");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);

    }
    /**
     * 卖出资金到账通知
     * @param amount
     * @param arrivalType
     * @param arrivalTime
     * @param prePayId
     * @param openId
     */
    @Async
    public void payResultNotifySell(String amount,String arrivalType,Date arrivalTime,String prePayId,String openId) {
        String weixinToken = null;
        while (weixinToken == null){
            weixinToken = getRightAccessToken();
        }
        // 到账金额
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(amount);
        keyword1.setColor("#333333");

        // 到账方式
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(arrivalType+"元");
        keyword2.setColor("#333333");

        // 到账时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateToString(arrivalTime));
        keyword3.setColor("#333333");
        
        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        
        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(openId);
        wxopenTemplateMessage.setData(linkedHashMap);
        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(prePayId);
        wxopenTemplateMessage.setTemplate_id("fAdgHURICCzNoYz8HbTA_fbLW-JUhjbicPSqofWNqwc");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);

    }
    
    /**
     * 提货通知
     * @param prodName
     * @param userName
     * @param phoneNumber
     * @param address
     * @param prePayId
     * @param openId
     */
    @Async
    public void giftResultNotifyCarry(String prodName,String userName,String phoneNumber,String address,String prePayId,String openId) {
        String weixinToken = null;
        while (weixinToken == null){
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");

        // 收货人
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(userName);
        keyword2.setColor("#333333");

        // 手机号
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(phoneNumber);
        keyword3.setColor("#333333");
        
        //收货地址
        TemplateMessageItem keyword4 = new TemplateMessageItem();
        keyword3.setValue(address);
        keyword3.setColor("#333333");
        
        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        linkedHashMap.put("keyword4", keyword4);
        
        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(openId);
        wxopenTemplateMessage.setData(linkedHashMap);
        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(prePayId);
        wxopenTemplateMessage.setTemplate_id("0P9JoND8UHherQDUJKPXjXR5s_xUTYxXYdlDCRbwPTk");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);

    }
   
   
    @Async
    public void giftResultNotifyFail(String prodName,String number,Date receiveTime,String userName,String prePayId,String openId) {
        String weixinToken = null;
        while (weixinToken == null){
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");

        // 礼品数量
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(number+"份");
        keyword2.setColor("#333333");

        // 领取时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateToString(receiveTime));
        keyword3.setColor("#333333");
        
        //收礼人
        TemplateMessageItem keyword4 = new TemplateMessageItem();
        keyword3.setValue(userName);
        keyword3.setColor("#333333");
        
        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        linkedHashMap.put("keyword4", keyword4);
        
        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(openId);
        wxopenTemplateMessage.setData(linkedHashMap);
        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(prePayId);
        wxopenTemplateMessage.setTemplate_id("iibwNplUljRfoYjSJ6SLxg1KhPCqMMlDiJNETBoA2_k");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);

    }
    
    private String  dateToString(Date date){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");  
    	String str=sdf.format(date);
    	return str;
    }
    public void test(){

    }

    /**
     * 获取可用的AccessToken
     * @return
     */
    private String getRightAccessToken(){
        Date nowTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowTime);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 2);

        Date beforeTime = calendar.getTime();
        MessageToken latestToken = messageTokenService.getLatestToken(beforeTime, nowTime);

        String weixinToken = null;
        if (latestToken != null) {
            String tokenAccessToken = latestToken.getAccessToken();
            weixinToken = tokenAccessToken;
        } else {
            Token token = TokenAPI.token(weiXinProperty.getAppid(), weiXinProperty.getAppSecret());
            String access_token = token.getAccess_token();
            String errcode = token.getErrcode();
            int expires_in = token.getExpires_in();
            String generateID = UUIDGenerator.generate();
            MessageToken messageToken = new MessageToken();
            messageToken.setId(generateID);
            messageToken.setAccessToken(access_token);
            messageToken.setRequireDate(new Date());
            messageToken.setExpiresIn(expires_in);
            messageToken.setScope("client_credential");
            messageToken.setRemark("OK");
            messageToken.setTokenType(3); // 3 小程序 1 普通
            int insertLatestToken = messageTokenService.insertLatestToken(messageToken);
            if(insertLatestToken>0){
                weixinToken = access_token;
            }
        }
        return weixinToken;
    }
}
