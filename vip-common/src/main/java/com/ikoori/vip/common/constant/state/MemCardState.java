package com.ikoori.vip.common.constant.state;

/**
 * 会员卡使用状态
 * @author chengxg
 *
 */
public enum MemCardState {
	USED(0, "使用中"),
	UN_USED(1, "未生效"),
    EXPIRED(2, "已过期");

    int code;
    String message;

    MemCardState(int code, String message) {
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
            for (MemCardState s : MemCardState.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
