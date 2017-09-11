package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * @author chengxg
 *
 */
public enum GrantType {
	SUB_WX(0, "关注微信"),
	/*NO_RULE(1, "无门槛"),*/
    RULE(2, "按规则");

    int code;
    String message;

    GrantType(int code, String message) {
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
            for (GrantType s : GrantType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
