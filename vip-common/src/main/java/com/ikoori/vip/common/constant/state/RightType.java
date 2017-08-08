package com.ikoori.vip.common.constant.state;

/**
 * 权益类型
 *
 * @author chegnxg
 * @Date 2017年1月22日 下午12:14:59
 */
public enum RightType {

	COUPON("coupon", "优惠券"), DISCOUNT("discount", "折扣"), POINTS("points", "积分"), POSTAGE("postage", "免邮");

	String code;
	String message;

	RightType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String vf(String value) {
		if (value == null) {
			return "";
		} else {
			for (RightType ms : RightType.values()) {
				if (ms.getCode() == value) {
					return ms.getMessage();
				}
			}
			return "";
		}
	}
}
