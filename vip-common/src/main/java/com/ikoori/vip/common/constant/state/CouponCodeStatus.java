package com.ikoori.vip.common.constant.state;

/**
 * 券码使用状态
 * @ClassName:  CouponCodeStatus
 * @author: chengxg
 * @date:   2017年10月13日 下午3:47:01
 */
public enum CouponCodeStatus {
	create(0, "已生成"),
	madecard(1, "已制卡"),
	publish(2, "已发行"),
    active(3, "已激活");

    int code;
    String message;

    CouponCodeStatus(int code, String message) {
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
            for (CouponCodeStatus s : CouponCodeStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
