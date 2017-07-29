package com.ikoori.vip.common.constant.state;

/**
 * 管理员的状态
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午9:54:13
 */
public enum MerchantState {

    NO(0, "未验证"), YES(1, "已验证"),FAIL(2, "验证不通过");

    int code;
    String message;

    MerchantState(int code, String message) {
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

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (MerchantState ms : MerchantState.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
