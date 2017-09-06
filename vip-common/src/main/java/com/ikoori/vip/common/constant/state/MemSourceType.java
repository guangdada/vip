package com.ikoori.vip.common.constant.state;

/**
 * 会员来源类型
 *
 * @author chengxg
 */
public enum MemSourceType {
	add(0, "导入"), wechat(1, "关注微信");

	int code;
	String message;

	MemSourceType(int code, String message) {
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

	public static String valueOf(Integer value) {
		if (value == null) {
			return "";
		} else {
			for (MemSourceType ms : MemSourceType.values()) {
				if (ms.getCode() == value) {
					return ms.getMessage();
				}
			}
			return "";
		}
	}
}
