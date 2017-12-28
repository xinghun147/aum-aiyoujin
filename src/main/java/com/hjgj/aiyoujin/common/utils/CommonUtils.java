package com.hjgj.aiyoujin.common.utils;


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
}