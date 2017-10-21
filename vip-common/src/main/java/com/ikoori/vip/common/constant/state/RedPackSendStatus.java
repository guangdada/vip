package com.ikoori.vip.common.constant.state;

/**
 * 红包发送状态 0:发放成功 1:发放失败 2:已领取 3:已退款
 * @ClassName:  RedPackSendStatus
 * @author: chengxg
 * @date:   2017年10月19日 下午4:47:42
 */
public enum RedPackSendStatus {
	SENDING(0, "发放成功"),
	FAILED(1, "发放失败"),
	RECEIVED(2, "已领取"),
	REFUND(3, "已退款");

    int code;
    String message;

    RedPackSendStatus(int code, String message) {
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
    
	public static Integer getCode(String message) {
		if (message == null) {
			return null;
		} else {
			for (RedPackSendStatus s : RedPackSendStatus.values()) {
				if (s.name().equals(message)) {
					return s.getCode();
				}
			}
			return null;
		}
	}

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (RedPackSendStatus s : RedPackSendStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
