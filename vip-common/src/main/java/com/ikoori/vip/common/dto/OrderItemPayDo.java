package com.ikoori.vip.common.dto;

public class OrderItemPayDo {
	private String goodsName;
	private Integer quantity;
	private String goodsNo;
	private String model;
	private Integer casePrice;
	private Integer originalPrice;

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCasePrice() {
		return casePrice;
	}

	public void setCasePrice(Integer casePrice) {
		this.casePrice = casePrice;
	}

	public Integer getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Integer originalPrice) {
		this.originalPrice = originalPrice;
	}

}
