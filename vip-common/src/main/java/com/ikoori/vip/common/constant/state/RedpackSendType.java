package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * 
 * @author chengxg
 *
 */
public enum RedpackSendType {
	fixed(0, "固定金额"), random(1, "随机金额");
	int code;
	String message;

	RedpackSendType(int code, String message) {
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

	public static String valueOf(Integer type) {
		if (type == null) {
			return "";
		} else {
			for (RedpackSendType s : RedpackSendType.values()) {
				if (s.getCode() == type) {
					return s.getMessage();
				}
			}
			return "";
		}
	}
}
