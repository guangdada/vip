package com.ikoori.vip.common.constant.state;

/**
 * 店铺类型
 * 
 * @author chengxg
 *
 */
public enum StoreType {
	online(0, "线上店铺"), offline(1, "线下店铺");

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
