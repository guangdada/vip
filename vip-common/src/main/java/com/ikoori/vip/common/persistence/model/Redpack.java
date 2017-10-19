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
 * 红包规则
 * </p>
 *
 * @author chengxg
 * @since 2017-10-19
 */
@TableName("v_redpack")
public class Redpack extends Model<Redpack> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 红包名称
     */
	private String name;
    /**
     * 红包类型 0:注册红包
     */
	@TableField("pack_type")
	private Integer packType;
    /**
     * 发送方式 0:固定金额 1:随机金额
     */
	@TableField("send_type")
	private Integer sendType;
    /**
     * 面额
     */
	private Integer amount;
    /**
     * 最小金额
     */
	@TableField("min_amount")
	private Integer minAmount;
    /**
     * 最大金额
     */
	@TableField("max_amount")
	private Integer maxAmount;
    /**
     * 发放量
     */
	@TableField("send_count")
	private Integer sendCount;
    /**
     * 发放金额
     */
	@TableField("send_amount")
	private Integer sendAmount;
    /**
     * 领取量
     */
	@TableField("receive_count")
	private Integer receiveCount;
    /**
     * 领取金额
     */
	@TableField("receive_amount")
	private Integer receiveAmount;
    /**
     * 活动名称
     */
	@TableField("act_name")
	private String actName;
    /**
     * 红包祝福语
     */
	private String wishing;
    /**
     * 备注
     */
	private String remark;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPackType() {
		return packType;
	}

	public void setPackType(Integer packType) {
		this.packType = packType;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}

	public Integer getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Integer maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(Integer sendAmount) {
		this.sendAmount = sendAmount;
	}

	public Integer getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	public Integer getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Integer receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getWishing() {
		return wishing;
	}

	public void setWishing(String wishing) {
		this.wishing = wishing;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Redpack{" +
			"id=" + id +
			", merchantId=" + merchantId +
			", name=" + name +
			", packType=" + packType +
			", sendType=" + sendType +
			", amount=" + amount +
			", minAmount=" + minAmount +
			", maxAmount=" + maxAmount +
			", sendCount=" + sendCount +
			", sendAmount=" + sendAmount +
			", receiveCount=" + receiveCount +
			", receiveAmount=" + receiveAmount +
			", actName=" + actName +
			", wishing=" + wishing +
			", remark=" + remark +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
