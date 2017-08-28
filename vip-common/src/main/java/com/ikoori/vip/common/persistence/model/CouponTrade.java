package com.ikoori.vip.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 优惠券使用记录
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_coupon_trade")
public class CouponTrade extends Model<CouponTrade> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	
	 /**
     * 商户Id
     */
	@TableField("merchant_id")
	private Long merchantId;
	
    /**
     * 店铺Id
     */
	@TableField("store_id")
	private Long storeId;
    /**
     * 优惠券id
     */
	@TableField("coupon_id")
	private Long couponId;
    /**
     * 会员id
     */
	@TableField("member_id")
	private Long memberId;
    /**
     * 使用金额(单位:分)
     */
	@TableField("used_value")
	private Integer usedValue;
    /**
     * 使用时间
     */
	@TableField("used_time")
	private Date usedTime;
    /**
     * 订单号
     */
	@TableField("used_order_no")
	private String usedOrderNo;
	
	/**
	 * 订单id
	 */
	@TableField("order_id")
	private Long usedOrderId;
    /**
     * 券码
     */
	@TableField("verify_code")
	private String verifyCode;
    /**
     * 微信用户id
     */
	@TableField("wx_user_id")
	private Long wxUserId;
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
	@TableLogic
	private Integer status;


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

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getUsedValue() {
		return usedValue;
	}

	public void setUsedValue(Integer usedValue) {
		this.usedValue = usedValue;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public String getUsedOrderNo() {
		return usedOrderNo;
	}

	public void setUsedOrderNo(String usedOrderNo) {
		this.usedOrderNo = usedOrderNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Long getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(Long wxUserId) {
		this.wxUserId = wxUserId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public Long getUsedOrderId() {
		return usedOrderId;
	}

	public void setUsedOrderId(Long usedOrderId) {
		this.usedOrderId = usedOrderId;
	}

	@Override
	public String toString() {
		return "CouponTrade{" +
			"id=" + id +
			", storeId=" + storeId +
			", couponId=" + couponId +
			", memberId=" + memberId +
			", usedValue=" + usedValue +
			", usedTime=" + usedTime +
			", usedOrderNo=" + usedOrderNo +
			", verifyCode=" + verifyCode +
			", wxUserId=" + wxUserId +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
