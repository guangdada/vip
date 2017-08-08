package com.ikoori.vip.common.constant.state;

/**
 * 会员卡类型
 * @author chengxg
 *
 */
public enum CouponType {
	YHQ(0, "优惠券"),
	XJQ(1, "现金券");

    int code;
    String message;

    CouponType(int code, String message) {
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
            for (CouponType s : CouponType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
