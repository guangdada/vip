package com.ikoori.vip.common.constant.state;

/**
 * 积分获得方式
 * @author chengxg
 *
 */
public enum PointTradeType {

	SUBSCRIBE_WX(0, "关注微信"),
	ORDER_COUNT(1, "交易笔数"),
    ORDER_MONEY(2, "交易金额"),
    GIVE(3, "商家赠送"),
    MARK(4, "签到获得"),
    PAY_ORDER(5, "订单消费"),
    CARD(6, "开卡赠送"),
    SHARE(7, "分享好友");

    int code;
    String message;

    PointTradeType(int code, String message) {
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
            for (PointTradeType s : PointTradeType.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
