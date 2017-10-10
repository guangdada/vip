package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * 
 * @author chengxg
 *
 */
public enum SexType {
	boy(1, "男"), girl(0, "女");
	int code;
	String message;

	SexType(int code, String message) {
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

	public static String valueOf(Integer sex) {
		if (sex == null) {
			return "";
		} else {
			for (SexType s : SexType.values()) {
				if (s.getCode() == sex) {
					return s.getMessage();
				}
			}
			return "";
		}
	}
}
