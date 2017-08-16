package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * 
 * @author chengxg
 *
 */
public enum SpecType {
	small(1, "58mm"), big(2, "80mm");
	int code;
	String message;

	SpecType(int code, String message) {
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

	public static String valueOf(Integer specType) {
		if (specType == null) {
			return "";
		} else {
			for (SpecType s : SpecType.values()) {
				if (s.getCode() == specType) {
					return s.getMessage();
				}
			}
			return "";
		}
	}
}
