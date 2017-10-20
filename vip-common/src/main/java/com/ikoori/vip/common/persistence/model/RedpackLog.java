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
 * 红包发送记录
 * </p>
 *
 * @author chengxg
 * @since 2017-10-19
 */
@TableName("v_redpack_log")
public class RedpackLog extends Model<RedpackLog> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 红包规则id
     */
	@TableField("redpack_id")
	private Long redpackId;
    /**
     * 红包状态 0:发放成功 1:发放失败 2:已领取 3:已退款
     */
	@TableField("send_status")
	private Integer sendStatus;
    /**
     * 付款金额
     */
	@TableField("send_amount")
	private Integer sendAmount;
    /**
     * 用户openid
     */
	private String openid;
	/**
     * 用户ip
     */
	private String ip;
    /**
     * 商户订单号
     */
	private String billno;
    /**
     * 失败原因
     */
	private String reason;
    /**
     * 红包退款时间
     */
	@TableField("refund_time")
	private Date refundTime;
    /**
     * 红包领取时间
     */
	@TableField("rcv_time")
	private Date rcvTime;
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

	public Long getRedpackId() {
		return redpackId;
	}

	public void setRedpackId(Long redpackId) {
		this.redpackId = redpackId;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(Integer sendAmount) {
		this.sendAmount = sendAmount;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getRcvTime() {
		return rcvTime;
	}

	public void setRcvTime(Date rcvTime) {
		this.rcvTime = rcvTime;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "RedpackLog{" +
			"id=" + id +
			", merchantId=" + merchantId +
			", redpackId=" + redpackId +
			", sendStatus=" + sendStatus +
			", sendAmount=" + sendAmount +
			", openid=" + openid +
			", billno=" + billno +
			", reason=" + reason +
			", refundTime=" + refundTime +
			", rcvTime=" + rcvTime +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
