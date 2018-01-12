package com.hjgj.aiyoujin.server.util;

import org.junit.Test;

import java.security.MessageDigest;

/**
 * MD5加密工具(是基于hash算法实现,不可逆)
 */
public class MD5Util {
    /**
     * 16进制的字符数组
     */
    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字符串
     *
     * @param b 字节数组
     * @return
     */

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }


    /**
     * 转换byte到16进制
     *
     * @param b 要转换的byte
     * @return 16进制对应的字符
     */

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }


    /**
     * @param origin      需要加密的原字符串
     * @param charsetname 指定编码类型
     * @return
     */

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    //@Test
    public void testa() {
//        String string = "appId=wxd678efh567hg6787&nonceStr=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&package=prepay_id=wx2017033010242291fcfe0db70013231072&signType=MD5&timeStamp=1490840662&key=qazwsxedcrfvtgbyhnujmikolp111111";
//        String upperCase = MD5Util.MD5Encode(string, "UTF-8").toUpperCase();
//        System.out.println(upperCase);

        String st = "appid=wxbad011f34af54919&nonceStr=xHFSkX8DjRK83KNz77tC38GFnQQknXE5&package=prepay_id=wx2018011115273630baf302470575496196&signType=MD5&timeStamp=1515655655703&key=HuangJinGuanJia2ddc2DaoDian13509";
        String upperCase1 = MD5Util.MD5Encode(st, "UTF-8").toUpperCase();
        System.out.println(upperCase1);

    }
}
