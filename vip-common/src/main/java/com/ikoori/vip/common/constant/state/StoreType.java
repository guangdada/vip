package com.ikoori.vip.common.constant.state;

/**
 * 店铺类型
 * 
 * @author chengxg
 *
 */
public enum StoreType {
	tb(0, "淘宝"), tm(1, "天猫"),jd(2, "京东"),st(3, "实体"),gw(4, "官网");

	int code;
	String message;

	StoreType(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String valueOf(Integer status) {
		if (status == null) {
			return "";
		} else {
			for (StoreType s : StoreType.values()) {
				if (s.getCode() == status) {
					return s.getMessage();
				}
			}
			return "";
		}
	}
}
