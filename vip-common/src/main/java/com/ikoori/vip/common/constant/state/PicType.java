package com.ikoori.vip.common.constant.state;

/**
 * 图片类型
 * 
 * @author chengxg
 *
 */
public enum PicType {
	STORE(1, "门店图片"), CARD(2, "会员卡背景图片"), LOGO(3, "logo图片");
	Integer code;
	String message;

	PicType(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static String valueOf(Integer roleId) {
		if (roleId == null) {
			return "";
		} else {
			for (PicType s : PicType.values()) {
				if (s.getCode().toString().equals(roleId.toString())) {
					return s.getMessage();
				}
			}
			return "";
		}
	}
}
