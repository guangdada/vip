package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * 
 * @author chengxg
 *
 */
public enum PackType {
	re(0, "注册红包"),
	gm(1,"购买红包");
	int code;
	String message;

	PackType(int code, String message) {
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
			for (PackType s : PackType.values()) {
				if (s.getCode() == type) {
					return s.getMessage();
				}
			}
			return "";
		}
	}
}
