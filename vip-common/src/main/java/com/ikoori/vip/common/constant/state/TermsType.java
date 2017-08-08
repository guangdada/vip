package com.ikoori.vip.common.constant.state;

/**
 * 会员期限类型
 * @author chengxg
 *
 */
public enum TermsType {
	INFINITE(0, "无期限"),
	DAYS(1, "天"),
	RANGE(2, "时间段");

    int code;
    String message;

    TermsType(int code, String message) {
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
            for (TermsType s : TermsType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
