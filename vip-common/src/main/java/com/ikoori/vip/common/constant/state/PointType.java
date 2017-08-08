package com.ikoori.vip.common.constant.state;

/**
 * 积分规则类型
 * @author chengxg
 *
 */
public enum PointType {

	SUBSCRIBE_WX(0, "关注微信"),
    PAY_ORDER(1, "每成功交易"),
    PAY_MONEY(2, "每购买金额");

    int code;
    String message;

    PointType(int code, String message) {
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
            for (PointType s : PointType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
