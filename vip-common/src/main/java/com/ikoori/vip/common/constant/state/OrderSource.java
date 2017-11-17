package com.ikoori.vip.common.constant.state;

/**
 * 订单来源
 * @ClassName:  OrderSource
 * @author: chengxg
 * @date:   2017年11月17日 上午9:43:08
 */
public enum OrderSource {
	online(0, "线上"),
	offline(1, "门店"),
	gw(2, "官网");

    int code;
    String message;

    OrderSource(int code, String message) {
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
            for (OrderSource s : OrderSource.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
