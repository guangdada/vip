package com.ikoori.vip.common.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";
	public static final int coupon_code_length = 16; // 优惠券券号长度
	// 优惠券正则验证
	public static final String coupon_code_matches = "[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}"; 

	/**
	 * 返回一个定长的随机字符串(数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateOnlyNumber(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(LETTERCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * 
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 每次生成的len位数都不相同
	 * 
	 * @param param
	 * @return 定长的数字
	 */
	public static int getNotSimple(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}
	
	
	/**
	 * 返回16位优惠券串码
	 * 
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateCouponCode() {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < coupon_code_length; i++) {
			if(i !=0 && i % 4 == 0){
				sb.append("-");
			}
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString().toUpperCase();
	}
	
	/**
	 * 返回10位会员卡号
	 * @param prefix 店铺前缀
	 * @return
	 */
	public static String generateCardNum(String prefix){
		return prefix + generateOnlyNumber(8) + "88";
	}
	
	/**
	 * 返回店铺编号
	 * @return
	 */
	public static String generateStoreNum(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public static String generateOrderNo(){
		//Random random = new Random();
		//int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
		//return DateUtil.getMsAllTime() +rannum;
		return DateUtil.getMsAllTime();
	}

	public static void main(String[] args) {
		//System.out.println("返回一个定长的随机字符串(只包含大小写字母、数字):" + generateCardNum("KR"));
		
		System.out.println(generateCouponCode());
		String verifyCode = generateCouponCode();
		System.out.println(verifyCode.matches("[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}"));
	}
}
