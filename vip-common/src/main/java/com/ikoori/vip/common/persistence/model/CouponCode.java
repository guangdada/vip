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
 * 现金券券号管理
 * </p>
 *
 * @author chengxg
 * @since 2017-10-13
 */
@TableName("v_coupon_code")
public class CouponCode extends Model<CouponCode> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
	
    /**
     * 批次号
     */
	@TableField("batch_no")
	private String batchNo;
	
    /**
     * 卡号
     */
	@TableField("verify_no")
	private String verifyNo;
    /**
     * 券号
     */
	@TableField("verify_code")
	private String verifyCode;
    /**
     * 现金券id
     */
	@TableField("coupon_id")
	private Long couponId;
	
	/**
	 * 现金券名称
	 */
	//@TableField(exist = false)
	//private String couponName;
    /**
     * 使用状态（0：已生成、1：已制卡、2：已发行、3：已激活）
     */
	@TableField("use_status")
	private Integer useStatus;
	
	/**
	 * 状态字符串
	 */
	//@TableField(exist = false)
	//private String useStatusStr;
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
     * 删除状态
     */
	@TableLogic
	private Integer status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getVerifyNo() {
		return verifyNo;
	}

	public void setVerifyNo(String verifyNo) {
		this.verifyNo = verifyNo;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
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
	
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	

	/*public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getUseStatusStr() {
		return useStatusStr;
	}

	public void setUseStatusStr(String useStatusStr) {
		this.useStatusStr = useStatusStr;
	}*/

	@Override
	public String toString() {
		return "CouponCode{" +
			"id=" + id +
			", merchantId=" + merchantId +
			", verifyNo=" + verifyNo +
			", verifyCode=" + verifyCode +
			", couponId=" + couponId +
			", useStatus=" + useStatus +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
