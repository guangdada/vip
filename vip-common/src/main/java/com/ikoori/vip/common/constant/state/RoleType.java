package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * @author chengxg
 *
 */
public enum RoleType {
	/*NO_RULE(0, "关注微信"),
	SUB_WX(1, "无门槛"),
    RULE(2, "按规则");*/
	/*administrator(0,"超级管理员"),
	temp(1,"临时"),
	merchant(2,"商户");*/
	shop(1,"店员"),
	shopManager(2,"店长");
    int code;
    String message;

    RoleType(int code, String message) {
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
            for (RoleType s : RoleType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
