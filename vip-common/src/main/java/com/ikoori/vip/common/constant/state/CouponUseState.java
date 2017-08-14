package com.ikoori.vip.common.constant.state;

/**
 * 优惠券使用状态
 * @author chengxg
 *
 */
public enum CouponUseState {
	NO_USED(0, "未使用"),
	USED(1, "已使用"),
    PART_USED(2, "部分使用");

    int code;
    String message;

    CouponUseState(int code, String message) {
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
            for (CouponUseState s : CouponUseState.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
