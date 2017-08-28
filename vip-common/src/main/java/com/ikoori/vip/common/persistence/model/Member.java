package com.ikoori.vip.common.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 会员
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_member")
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 是否激活
     */
	@TableField("is_active")
	private Boolean isActive;
    /**
     * 会员名称
     */
	private String name;
    /**
     * 微信号
     */
	@TableField("wx_code")
	private String wxCode;
    /**
     * 微信用户id
     */
	@TableField("wx_user_id")
	private Long wxUserId;
	
	/**
	 * 微信用户openId
	 */
	@TableField("open_id")
	private String openId;
    /**
     * 性别
     */
	private Integer sex;
	/**
     * 生日
     */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	
	/**
	 * 生日(接收参数)
	 */
	@TableField(exist=false)
	private String birthdayStr;
    /**
     * 来源(0:关注微信;1:导入)
     */
	@TableField("source_type")
	private Integer sourceType;
    /**
     * 积分
     */
	private Integer points;
    /**
     * 余额
     */
	private BigDecimal balance;
    /**
     * 购买金额
     */
	@TableField("trade_amount")
	private BigDecimal tradeAmount;
    /**
     * 最后购买时间
     */
	@TableField("last_trade_time")
	private Date lastTradeTime;
    /**
     * 收货地址
     */
	private String address;
    /**
     * 购买次数
     */
	@TableField("trade_count")
	private Integer tradeCount;
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
	/**
     * 备注
     */
	private String tips;
	
	/**
	 * 乐观锁
	 */
	@Version
    private Integer version;
    

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Boolean isIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWxCode() {
		return wxCode;
	}

	public void setWxCode(String wxCode) {
		this.wxCode = wxCode;
	}

	public Long getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(Long wxUserId) {
		this.wxUserId = wxUserId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Date getLastTradeTime() {
		return lastTradeTime;
	}

	public void setLastTradeTime(Date lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
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
	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", mobile=" + mobile +
			", merchantId=" + merchantId +
			", isActive=" + isActive +
			", name=" + name +
			", wxCode=" + wxCode +
			", wxUserId=" + wxUserId +
			", sex=" + sex +
			", birthday=" + birthday +
			", sourceType=" + sourceType +
			", points=" + points +
			", balance=" + balance +
			", tradeAmount=" + tradeAmount +
			", lastTradeTime=" + lastTradeTime +
			", address=" + address +
			", tradeCount=" + tradeCount +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
