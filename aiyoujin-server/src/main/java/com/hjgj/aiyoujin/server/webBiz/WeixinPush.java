package com.hjgj.aiyoujin.server.webBiz;

import com.hjgj.aiyoujin.core.common.utils.UUIDGenerator;
import com.hjgj.aiyoujin.core.model.MessageToken;
import com.hjgj.aiyoujin.core.service.MessageTokenService;
import com.hjgj.aiyoujin.server.config.WeiXinProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import weixin.popular.api.MessageAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.WxopenTemplateMessage;
import weixin.popular.bean.token.Token;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class WeixinPush {

    @Autowired
    private WeiXinProperty weiXinProperty;

    @Autowired
    private MessageTokenService messageTokenService;

    /**
     * 微信支付成功通知
     *
     * @param prodName
     * @param payMoney
     * @param payTime
     * @param prePayId
     * @param openId
     */
    @Async
    public Map payResultNotify(String prodName, String payMoney, Date payTime, String prePayId, String openId, String orderId) {
        Map<String, Object> resultMap = new HashMap<>();
        String weixinToken = null;
        while (weixinToken == null) {
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");
        resultMap.put("prodName", prodName);

        // 付款金额
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(payMoney + "元");
        keyword2.setColor("#333333");
        resultMap.put("payMoney", payMoney);


        // 付款时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateToString(payTime));
        keyword3.setColor("#333333");
        resultMap.put("payTime", dateToString(payTime));


        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(openId);
        wxopenTemplateMessage.setData(linkedHashMap);
//        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(prePayId);
        wxopenTemplateMessage.setTemplate_id("xeZ-PIsudhp2uxzIng9iD6rIzBCwq7zg093tlpJ3Ev4");
        wxopenTemplateMessage.setPage("pages/index/index");
        resultMap.put("tempId", "xeZ-PIsudhp2uxzIng9iD6rIzBCwq7zg093tlpJ3Ev4");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);
        String errcode = baseResult.getErrcode();
        if (errcode != null && "0".equals(errcode)) {
            resultMap.put("code", Integer.valueOf(0));
        } else {
            resultMap.put("code", Integer.valueOf(0));
        }
        return resultMap;
    }

    /**
     * 微信支付失败通知
     *
     * @param prodName
     * @param payMoney
     * @param failCause
     * @param payTime
     * @param prePayId
     * @param openId
     */
    @Async
    public Map payResultNotifyFail(String prodName, String payMoney, String failCause, Date payTime, String prePayId, String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        String weixinToken = null;
        while (weixinToken == null) {
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");
        resultMap.put("prodName", prodName);

        // 付款金额
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(payMoney + "元");
        keyword2.setColor("#333333");
        resultMap.put("payMoney", payMoney);

        // 付款时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateToString(payTime));
        keyword3.setColor("#333333");
        resultMap.put("payTime", dateToString(payTime));
        // 失败原因
        TemplateMessageItem keyword4 = new TemplateMessageItem();
        keyword4.setValue(failCause);
        keyword4.setColor("#333333");
        resultMap.put("failCause", failCause);

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
        wxopenTemplateMessage.setPage("pages/index/index");

        resultMap.put("tempId", "okd7M98Q7ZoqLGAVDwaxB9co9SxKLthkDg7kQ76XAVI");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);
        String errcode = baseResult.getErrcode();
        if (errcode != null && "0".equals(errcode)) {
            resultMap.put("code", Integer.valueOf(0));
        } else {
            resultMap.put("code", Integer.valueOf(1));
        }
        return resultMap;
    }

    /**
     * 卖出资金到账通知
     *
     * @param amount
     * @param arrivalType
     * @param arrivalTime
     * @param prePayId
     * @param openId
     */
    @Async
    public Map payResultNotifySell(String amount, String arrivalType, Date arrivalTime, String prePayId, String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        String weixinToken = null;
        while (weixinToken == null) {
            weixinToken = getRightAccessToken();
        }
        // 到账金额
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue("¥" + amount);
        keyword1.setColor("#333333");

        // 到账方式
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(arrivalType);
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
        wxopenTemplateMessage.setPage("pages/index/index");
        resultMap.put("tempId", "fAdgHURICCzNoYz8HbTA_fbLW-JUhjbicPSqofWNqwc");

        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);
        String errcode = baseResult.getErrcode();
        if (errcode != null && "0".equals(errcode)) {
            resultMap.put("code", Integer.valueOf(0));
        } else {
            resultMap.put("code", Integer.valueOf(1));
        }
        return resultMap;
    }

    /**
     * 提货通知
     *
     * @param prodName
     * @param userName
     * @param phoneNumber
     * @param address
     * @param prePayId
     * @param openId
     */
    @Async
    public Map<String, Object> giftResultNotifyCarry(String prodName, String userName, String phoneNumber, String address, String prePayId, String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        String weixinToken = null;
        while (weixinToken == null) {
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
        keyword4.setValue(address);
        keyword4.setColor("#333333");

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
        wxopenTemplateMessage.setPage("pages/index/index");
        resultMap.put("tempId", "0P9JoND8UHherQDUJKPXjXR5s_xUTYxXYdlDCRbwPTk");
        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);
        String errcode = baseResult.getErrcode();
        if (errcode != null && "0".equals(errcode)) {
            resultMap.put("code", Integer.valueOf(0));
        } else {
            resultMap.put("code", Integer.valueOf(1));
        }
        return resultMap;
    }


    @Async
    public Map giftResultNotifyFail(String prodName, String number, Date receiveTime, String userName, String prePayId, String openId) {
        Map<String, Object> resultMap = new HashMap<>();
        String weixinToken = null;
        while (weixinToken == null) {
            weixinToken = getRightAccessToken();
        }
        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(prodName);
        keyword1.setColor("#333333");

        // 礼品数量
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(number + "份");
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
        wxopenTemplateMessage.setPage("pages/index/index");
        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);
        String errcode = baseResult.getErrcode();
        if (errcode != null && "0".equals(errcode)) {
            resultMap.put("code", Integer.valueOf(0));
        } else {
            resultMap.put("code", Integer.valueOf(1));
        }
        return resultMap;
    }

    /**
     * 礼物领取通知
     *
     * @param productName
     * @param formId
     * @param number
     * @param receiveDate
     * @param receiveName
     * @param sourceOpenId
     * @return
     */
    @Async
    public Map giftReceiveNotify(String productName, String formId, Integer number, Date receiveDate, String receiveName, String sourceOpenId) {
        Map<String, Object> resultMap = new HashMap<>();
        String weixinToken = null;
        while (weixinToken == null) {
            weixinToken = getRightAccessToken();
        }

        // 物品名称
        TemplateMessageItem keyword1 = new TemplateMessageItem();
        keyword1.setValue(productName);
        keyword1.setColor("#333333");

        // 礼品数量
        TemplateMessageItem keyword2 = new TemplateMessageItem();
        keyword2.setValue(number + "份");
        keyword2.setColor("#333333");

        //收礼人
        TemplateMessageItem keyword4 = new TemplateMessageItem();
        keyword4.setValue(receiveName);
        keyword4.setColor("#333333");

        // 领取时间
        TemplateMessageItem keyword3 = new TemplateMessageItem();
        keyword3.setValue(dateTimeToString(receiveDate));
        keyword3.setColor("#333333");

        LinkedHashMap<String, TemplateMessageItem> linkedHashMap = new LinkedHashMap<String, TemplateMessageItem>();
        linkedHashMap.put("keyword1", keyword1);
        linkedHashMap.put("keyword2", keyword2);
        linkedHashMap.put("keyword3", keyword3);
        linkedHashMap.put("keyword4", keyword4);

        WxopenTemplateMessage wxopenTemplateMessage = new WxopenTemplateMessage();
        wxopenTemplateMessage.setTouser(sourceOpenId);
        wxopenTemplateMessage.setData(linkedHashMap);
        wxopenTemplateMessage.setEmphasis_keyword("keyword1.DATA");
        wxopenTemplateMessage.setForm_id(formId);
        wxopenTemplateMessage.setTemplate_id("iibwNplUljRfoYjSJ6SLxg1KhPCqMMlDiJNETBoA2_k");
        wxopenTemplateMessage.setPage("pages/index/index");
        BaseResult baseResult = MessageAPI.messageWxopenTemplateSend(weixinToken, wxopenTemplateMessage);
        String errcode = baseResult.getErrcode();
        if (errcode != null && "0".equals(errcode)) {
            resultMap.put("code", Integer.valueOf(0));
        } else {
            resultMap.put("code", Integer.valueOf(1));
        }
        return resultMap;
    }

    private String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String str = sdf.format(date);
        return str;
    }

    private String dateTimeToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }


    /**
     * 获取可用的AccessToken
     *
     * @return
     */
    public String getRightAccessToken() {
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
            if (insertLatestToken > 0) {
                weixinToken = access_token;
            }
        }
        return weixinToken;
    }
}
