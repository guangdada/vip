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
 * 店铺交易明细
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_trade")
public class Trade extends Model<Trade> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 金额 (单位：分)
     */
	private Integer amount;
    /**
     * 交易类型(0:交易;1:充值;2:提现;3:手续费)
     */
	@TableField("trade_type")
	private Integer tradeType;
    /**
     * 店铺id
     */
	@TableField("store_id")
	private Long storeId;
    /**
     * 支付流水号(内部)
     */
	@TableField("flow_no")
	private String flowNo;
    /**
     * 订单Id
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 订单号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 交易状态(0:成功;1:失败)
     */
	private Integer state;
    /**
     * 1支出/0收入
     */
	@TableField("in_out")
	private Boolean inOut;
    /**
     * 支付时间
     */
	@TableField("pay_time")
	private Date payTime;
    /**
     * 完成时间(接收到微信支付结果的时间)
     */
	@TableField("finish_time")
	private Date finishTime;
    /**
     * 交易描述
     */
	private String tag;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Boolean isInOut() {
		return inOut;
	}

	public void setInOut(Boolean inOut) {
		this.inOut = inOut;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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
		return "Trade{" +
			"id=" + id +
			", amount=" + amount +
			", tradeType=" + tradeType +
			", storeId=" + storeId +
			", flowNo=" + flowNo +
			", orderId=" + orderId +
			", orderNo=" + orderNo +
			", state=" + state +
			", inOut=" + inOut +
			", payTime=" + payTime +
			", finishTime=" + finishTime +
			", tag=" + tag +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
