package com.hjgj.aiyoujin.common.utils;

import java.math.BigDecimal;

/**
 * double类型运算帮助类
 *
 */
public class CalculateUtils {

	/**
	 * 格式化double到小数点后几位
	 * @param x
	 * @param number
	 * @return
	 */
	public static double doubleFormat(double x, int number) {
		return new BigDecimal(x).divide(BigDecimal.ONE, number, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
