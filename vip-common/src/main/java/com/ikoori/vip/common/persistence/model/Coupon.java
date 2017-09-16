package com.ikoori.vip.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import java.io.Serializable;

/**
 * <p>
 * 优惠券/现金券 
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_coupon")
public class Coupon extends Model<Coupon> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 创建用户id
     */
	@TableField("create_user_id")
	private Long createUserId;
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
    /**
     * 店铺Id
     */
	@TableField("store_id")
	private Long storeId;
    /**
     * 优惠券名称
     */
	private String name;
    /**
     * 优惠券类型(0:优惠券;1:现金券)
     */
	private Integer type;
    /**
     * 优惠方式(0:指定金额,1:折扣)
     */
	@TableField("preferential_type")
	private Integer preferentialType;
    /**
     * 折扣(9折:90)
     */
	private Integer discount;
    /**
     * 实际折扣(9折:90)
     */
	@TableField("origin_discount")
	private Integer originDiscount;
    /**
     * 别名
     */
	private String alias;
    /**
     * 面值
     */
	private Integer value;
    /**
     * 面值(单位:分)
     */
	@TableField("origin_value")
	private Integer originValue;
    /**
     * 每人限领(单位:张)
     */
	private Integer quota;
    /**
     * 使用门槛(单位:元)
     */
	@TableField("at_least")
	private Integer atLeast;
    /**
     * 使用门槛(单位:分)
     */
	@TableField("origin_at_least")
	private Integer originAtLeast;
    /**
     * 使用门槛限制
     */
	@TableField("is_at_least")
	private Boolean isAtLeast;
    /**
     * 发放总量
     */
	private Integer total;
    /**
     * 剩余张数
     */
	private Integer stock;
    /**
     * 生效时间
     */
	@TableField("start_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startAt;
	
    /**
     * 生效时间(long值)
     */
	@TableField("start_time")
	private Long startTime;
    /**
     * 失效时间
     */
	@TableField("end_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endAt;
	
	
    /**
     * 失效时间(long值)
     */
	@TableField("end_time")
	private Long endTime;
    /**
     * 到期提醒
     */
	@TableField("expire_notice")
	private Boolean expireNotice;
    /**
     * 使用说明
     */
	private String description;
    /**
     * 可领取的会员等级
     */
	@TableField("card_id")
	private Long cardId;
    /**
     * 同步至微信卡包
     */
	@TableField("is_sync_weixin")
	private Boolean isSyncWeixin;
    /**
     * 是否有效
     */
	@TableField("is_invalid")
	private Boolean isInvalid;
    /**
     * 是否过期
     */
	@TableField("is_expired")
	private Boolean isExpired;
    /**
     * 是否允许分享领取链接
     */
	@TableField("is_share")
	private Boolean isShare;
    /**
     * 领取链接
     */
	private String url;
    /**
     * 使用数量
     */
	@TableField("use_count")
	private Integer useCount;
    /**
     * 领取数量
     */
	@TableField("get_count")
	private Integer getCount;
    /**
     * 领取人数
     */
	@TableField("get_count_user")
	private Integer getCountUser;
    /**
     * 使用条件描述
     */
	@TableField("use_condition")
	private String useCondition;
    /**
     * 卡券标题
     */
	@TableField("weixin_title")
	private String weixinTitle;
    /**
     * 卡券副标题
     */
	@TableField("weixin_sub_title")
	private String weixinSubTitle;
    /**
     * 客服电话
     */
	@TableField("service_phone")
	private String servicePhone;
    /**
     * 卡券颜色
     */
	@TableField("color_id")
	private Long colorId;
    /**
     * 可否赠送
     */
	@TableField("can_give_friend")
	private Boolean canGiveFriend;
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
	 * 乐观锁
	 */
	@Version
    private Integer version;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPreferentialType() {
		return preferentialType;
	}

	public void setPreferentialType(Integer preferentialType) {
		this.preferentialType = preferentialType;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getOriginDiscount() {
		return originDiscount;
	}

	public void setOriginDiscount(Integer originDiscount) {
		this.originDiscount = originDiscount;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getOriginValue() {
		return originValue;
	}

	public void setOriginValue(Integer originValue) {
		this.originValue = originValue;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {
		this.quota = quota;
	}

	public Integer getAtLeast() {
		return atLeast;
	}

	public void setAtLeast(Integer atLeast) {
		this.atLeast = atLeast;
	}

	public Integer getOriginAtLeast() {
		return originAtLeast;
	}

	public void setOriginAtLeast(Integer originAtLeast) {
		this.originAtLeast = originAtLeast;
	}

	public Boolean isIsAtLeast() {
		return isAtLeast;
	}

	public void setIsAtLeast(Boolean isAtLeast) {
		this.isAtLeast = isAtLeast;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Boolean isExpireNotice() {
		return expireNotice;
	}

	public void setExpireNotice(Boolean expireNotice) {
		this.expireNotice = expireNotice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Boolean isIsSyncWeixin() {
		return isSyncWeixin;
	}

	public void setIsSyncWeixin(Boolean isSyncWeixin) {
		this.isSyncWeixin = isSyncWeixin;
	}

	public Boolean isIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public Boolean isIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public Boolean isIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Integer getGetCount() {
		return getCount;
	}

	public void setGetCount(Integer getCount) {
		this.getCount = getCount;
	}

	public Integer getGetCountUser() {
		return getCountUser;
	}

	public void setGetCountUser(Integer getCountUser) {
		this.getCountUser = getCountUser;
	}

	public String getUseCondition() {
		return useCondition;
	}

	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}

	public String getWeixinTitle() {
		return weixinTitle;
	}

	public void setWeixinTitle(String weixinTitle) {
		this.weixinTitle = weixinTitle;
	}

	public String getWeixinSubTitle() {
		return weixinSubTitle;
	}

	public void setWeixinSubTitle(String weixinSubTitle) {
		this.weixinSubTitle = weixinSubTitle;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public Boolean isCanGiveFriend() {
		return canGiveFriend;
	}

	public void setCanGiveFriend(Boolean canGiveFriend) {
		this.canGiveFriend = canGiveFriend;
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
	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Coupon{" +
			"id=" + id +
			", createUserId=" + createUserId +
			", merchantId=" + merchantId +
			", storeId=" + storeId +
			", name=" + name +
			", type=" + type +
			", preferentialType=" + preferentialType +
			", discount=" + discount +
			", originDiscount=" + originDiscount +
			", alias=" + alias +
			", value=" + value +
			", originValue=" + originValue +
			", quota=" + quota +
			", atLeast=" + atLeast +
			", originAtLeast=" + originAtLeast +
			", isAtLeast=" + isAtLeast +
			", total=" + total +
			", stock=" + stock +
			", startAt=" + startAt +
			", startTime=" + startTime +
			", endAt=" + endAt +
			", endTime=" + endTime +
			", expireNotice=" + expireNotice +
			", description=" + description +
			", cardId=" + cardId +
			", isSyncWeixin=" + isSyncWeixin +
			", isInvalid=" + isInvalid +
			", isExpired=" + isExpired +
			", isShare=" + isShare +
			", url=" + url +
			", useCount=" + useCount +
			", getCount=" + getCount +
			", getCountUser=" + getCountUser +
			", useCondition=" + useCondition +
			", weixinTitle=" + weixinTitle +
			", weixinSubTitle=" + weixinSubTitle +
			", servicePhone=" + servicePhone +
			", colorId=" + colorId +
			", canGiveFriend=" + canGiveFriend +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
