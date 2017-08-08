package com.ikoori.vip.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 店铺Id
     */
	@TableField("store_id")
	private Long storeId;
    /**
     * 订单号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 退货状态
     */
	@TableField("return_status")
	private Integer returnStatus;
    /**
     * 订单状态
     */
	@TableField("pay_status")
	private Integer payStatus;
    /**
     * 支付方式(0:微信;1:支付宝;2:现金)
     */
	@TableField("pay_type")
	private Integer payType;
    /**
     * 收款方式(0:代收;1:记账)
     */
	@TableField("received_type")
	private Integer receivedType;
    /**
     * 会员id
     */
	@TableField("member_id")
	private Long memberId;
    /**
     * 收银员id
     */
	@TableField("cashier_user_id")
	private Long cashierUserId;
    /**
     * 收银员名称
     */
	@TableField("cashier_name")
	private String cashierName;
    /**
     * 支付现金(现金支付，单位:分)
     */
	@TableField("customer_money")
	private Integer customerMoney;
    /**
     * 找零（现金支付, 单位:分）
     */
	@TableField("change_money")
	private Integer changeMoney;
    /**
     * 实收金额(单位:分)
     */
	@TableField("rounded_to")
	private Integer roundedTo;
    /**
     * 支付金额(单位:分)
     */
	private Integer payment;
    /**
     * 优惠金额(单位:分)
     */
	private Integer discount;
    /**
     * 总合计（没使用任何优惠券的金额，单位:分）
     */
	@TableField("balance_due")
	private Integer balanceDue;
    /**
     * 商品数量
     */
	@TableField("product_num")
	private Integer productNum;
    /**
     * 优惠方式描述
     */
	@TableField("discount_info")
	private String discountInfo;
    /**
     * 支付流水号(内部)
     */
	@TableField("flow_no")
	private String flowNo;
    /**
     * 支付流水号(外部)
     */
	@TableField("outFlow_no")
	private String outFlowNo;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 状态
     */
	private Boolean status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getReceivedType() {
		return receivedType;
	}

	public void setReceivedType(Integer receivedType) {
		this.receivedType = receivedType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getCashierUserId() {
		return cashierUserId;
	}

	public void setCashierUserId(Long cashierUserId) {
		this.cashierUserId = cashierUserId;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public Integer getCustomerMoney() {
		return customerMoney;
	}

	public void setCustomerMoney(Integer customerMoney) {
		this.customerMoney = customerMoney;
	}

	public Integer getChangeMoney() {
		return changeMoney;
	}

	public void setChangeMoney(Integer changeMoney) {
		this.changeMoney = changeMoney;
	}

	public Integer getRoundedTo() {
		return roundedTo;
	}

	public void setRoundedTo(Integer roundedTo) {
		this.roundedTo = roundedTo;
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

	public Integer getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(Integer balanceDue) {
		this.balanceDue = balanceDue;
	}

	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}

	public String getDiscountInfo() {
		return discountInfo;
	}

	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getOutFlowNo() {
		return outFlowNo;
	}

	public void setOutFlowNo(String outFlowNo) {
		this.outFlowNo = outFlowNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Order{" +
			"id=" + id +
			", storeId=" + storeId +
			", orderNo=" + orderNo +
			", returnStatus=" + returnStatus +
			", payStatus=" + payStatus +
			", payType=" + payType +
			", receivedType=" + receivedType +
			", memberId=" + memberId +
			", cashierUserId=" + cashierUserId +
			", cashierName=" + cashierName +
			", customerMoney=" + customerMoney +
			", changeMoney=" + changeMoney +
			", roundedTo=" + roundedTo +
			", payment=" + payment +
			", discount=" + discount +
			", balanceDue=" + balanceDue +
			", productNum=" + productNum +
			", discountInfo=" + discountInfo +
			", flowNo=" + flowNo +
			", outFlowNo=" + outFlowNo +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
