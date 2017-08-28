package com.ikoori.vip.common.dto;

import java.util.List;

public class OrderPayDo {
	private String orderNo;
	private String mobile;
	private String storeNo;
	private Integer balanceDue;
	private Integer payment;
	private Integer discount;
	private Integer point;
	private List<CouponPayDo> coupons;
	private List<OrderItemPayDo> orderItems;
	

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Integer getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(Integer balanceDue) {
		this.balanceDue = balanceDue;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public List<CouponPayDo> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponPayDo> coupons) {
		this.coupons = coupons;
	}

	public List<OrderItemPayDo> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemPayDo> orderItems) {
		this.orderItems = orderItems;
	}

}
