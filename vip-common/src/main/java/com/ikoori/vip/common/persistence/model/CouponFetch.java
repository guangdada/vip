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
 * 优惠券领取记录
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_coupon_fetch")
public class CouponFetch extends Model<CouponFetch> {

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
	
	@TableField(exist =false)
	private Merchant merchant;
	
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
     * 是否使用(0:未使用;1:已使用;2:部分使用)
     */
	@TableField("is_used")
	private Integer isUsed;
    /**
     * 使用金额(单位:分)
     */
	@TableField("used_value")
	private Integer usedValue;
    /**
     * 面值(单位:分)
     */
	private Integer value;
    /**
     * 可用金额(单位:分)
     */
	@TableField("available_value")
	private Integer availableValue;
    /**
     * 会员id
     */
	@TableField("member_id")
	private Long memberId;
    /**
     * 是否有效(0:无效;1有效)
     */
	@TableField("is_invalid")
	private Boolean isInvalid;
    /**
     * 券码
     */
	@TableField("verify_code")
	private String verifyCode;
    /**
     * 生效时间
     */
	@TableField("valid_time")
	private Date validTime;
    /**
     * 领取标题
     */
	private String message;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 微信用户id
     */
	@TableField("wx_user_id")
	private Long wxUserId;
    /**
     * 失效时间
     */
	@TableField("expire_time")
	private Date expireTime;
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

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public Integer getUsedValue() {
		return usedValue;
	}

	public void setUsedValue(Integer usedValue) {
		this.usedValue = usedValue;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getAvailableValue() {
		return availableValue;
	}

	public void setAvailableValue(Integer availableValue) {
		this.availableValue = availableValue;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Boolean getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(Long wxUserId) {
		this.wxUserId = wxUserId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
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

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return "CouponFetch{" +
			"id=" + id +
			", storeId=" + storeId +
			", couponId=" + couponId +
			", isUsed=" + isUsed +
			", usedValue=" + usedValue +
			", value=" + value +
			", availableValue=" + availableValue +
			", memberId=" + memberId +
			", isInvalid=" + isInvalid +
			", verifyCode=" + verifyCode +
			", validTime=" + validTime +
			", message=" + message +
			", mobile=" + mobile +
			", wxUserId=" + wxUserId +
			", expireTime=" + expireTime +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
