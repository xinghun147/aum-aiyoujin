package com.hjgj.aiyoujin.core.common.utils;

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
}