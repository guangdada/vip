package com.ikoori.vip.common.constant.state;

/**
 * 颜色类型
 *
 * @author chengxg
 * @Date 2017年1月10日 下午9:54:13
 */
public enum ColorType {
	Color010("Color010", "#55bd47"), Color020("Color020", "#10ad61"),Color030("Color030", "#35a4de"),
	Color040("Color040", "#3d78da"), Color050("Color050", "#9058cb"),Color060("Color060", "#de9c33"),
	Color070("Color070", "#ebac16"), Color080("Color080", "#f9861f"),Color081("Color081", "#f08500"),
	Color090("Color090", "#e75735"), Color100("Color100", "#d54036"),Color101("Color101", "#cf3e36");

    String code;
    String color;

    ColorType(String code, String color) {
        this.code = code;
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static String valOf(String value) {
        if (value == null) {
            return "";
        } else {
            for (ColorType ms : ColorType.values()) {
                if (ms.getCode().equals(value)) {
                    return ms.getColor();
                }
            }
            return "";
        }
    }
}
