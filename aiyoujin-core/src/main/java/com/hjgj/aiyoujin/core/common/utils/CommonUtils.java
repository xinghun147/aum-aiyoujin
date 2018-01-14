package com.hjgj.aiyoujin.core.common.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 
* @ClassName: CommonUtils
* @Description: 通用工具类
* @author Gaoguanjun gaoguanjun@32gold.com
* @date 2017年11月24日 下午9:20:01
*
 */
public class CommonUtils {
	/**
	 * 将手机号信息模糊化显示
	 */
	public String getMakePhoneAmbiguous(String phone) {
		return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
	}
	
	/**
	 * 将开户名信息模糊化显示
	 */
	public String getMakeUserNameAmbiguous(String userName) {
		return "*" + userName.substring(userName.length() - 1);
	}
	
	/**
	 * 将银行卡信息模糊化显示
	 */
	public String makeUserBankAmbiguous(String bankNo) {
		return "**** **** **** "+bankNo.substring(bankNo.length() - 4);
	}
	/**
	 * 将开户名信息模糊化显示
	 */
	public static String makeRealNameAmbiguous(String userName) {
		return "*" + userName.substring(userName.length() - 1);
	}
	/**
	 * 将身份证号信息模糊化显示
	 */
	public static String makeIdNoAmbiguous(String idNo) {
		return idNo.substring(0, 3)+"***********"+idNo.substring(idNo.length() - 4);
	}
	/**
	 * 将银行卡信息模糊化显示
	 */
	public static String makeBankNoAmbiguous(String bankNo) {
		return bankNo.substring(0, 4)+"*********"+bankNo.substring(bankNo.length() - 4);
	}
	/**
	 * 将银行卡信息模糊化显示
	 */
	public static String makeBankNoAmbiguous(String bankNo, int prefix, int suffix) {
		return bankNo.substring(0, prefix)+" **** **** "+bankNo.substring(bankNo.length() - suffix);
	}

	/**
	 * 生成随机订单号
	 * 总长度 32
	 * @return
	 */
	public static String generateOrderNo(String prefix){
		int machineId = 1;//最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {//有可能是负数
			hashCodeV = - hashCodeV;
		}
		String orderNo = machineId+String.format("%015d", hashCodeV);
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		orderNo = prefix + format.format(new Date())+orderNo.substring(10,orderNo.length());
		//32位长度保证
		if (orderNo.length()>32) {
			orderNo = orderNo.substring(0,32);
		}
		return orderNo;
	}

	/**
	 * 16进制的字符数组
	 */
	private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

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
}