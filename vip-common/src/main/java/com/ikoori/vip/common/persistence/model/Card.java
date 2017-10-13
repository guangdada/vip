package com.ikoori.vip.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 会员卡
 * </p>
 *
 * @author chengxg
 * @since 2017-08-04
 */
@TableName("v_card")
public class Card extends Model<Card> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 会员卡名称
     */
	private String name;
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
	 * 卡片封面
	 */
	@TableField("cover_type")
	private Integer coverType;
    /**
     * 背景颜色id
     */
	@TableField("color_id")
	private Long colorId;
    /**
     * 颜色RGB值
     */
	@TableField("color_code")
	private String colorCode;
    /**
     * 背景图片url
     */
	@TableField("cover_pic")
	private String coverPic;
    /**
     * 等级
     */
	@TableField("card_level")
	private Integer cardLevel;
    /**
     * 卡类型
     * NO_RULE(0, "关注微信"),
	   SUB_WX(1, "无门槛"),
       RULE(2, "按规则");
     */
	@TableField("grant_type")
	private Integer grantType;
	
	/**
	 * 会员期限类型
	 */
	@TableField("term_type")
	private Integer termType;
	
	/**
	 * 服务电话
	 */
	@TableField("service_phone")
	private String servicePhone;
    /**
     * 是否需要激活
     */
	@TableField("is_need_activate")
	private Boolean isNeedActivate;
    /**
     * 是否同步至微信卡包
     */
	@TableField("is_sync_weixin")
	private Boolean isSyncWeixin;
    /**
     * 是否可用
     */
	@TableField("is_available")
	private Boolean isAvailable;
    /**
     * 发布微信卡包状态
     */
	@TableField("sync_weixin_state")
	private Boolean syncWeixinState;
    /**
     * 微信卡包id
     */
	@TableField("weixin_card_id")
	private String weixinCardId;
    /**
     * 会员卡号前缀
     */
	@TableField("card_number_prefix")
	private String cardNumberPrefix;
    
	 /**
     * 每个月按内购价格可以购买的额度（单位：分）
     */
	@TableField("trade_amount_limit")
	private Integer tradeAmountLimit;
	
	/**
     * 描述
     */
	private String description;
    /**
     * 是否允许会员分享
     */
	@TableField("is_allow_share")
	private Boolean isAllowShare;
    /**
     * 有效天数
     */
	@TableField("term_days")
	private Integer termDays;
    /**
     * 生效时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@TableField("term_start_at")
	private Date termStartAt;
	
    /**
     * 失效时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@TableField("term_end_at")
	private Date termEndAt;
	
    /**
     * 过期后调整至会员卡
     */
	@TableField("term_to_card_id")
	private Long termToCardId;
    /**
     * 激活条件json
     */
	@TableField("activation_condition")
	private String activationCondition;
    /**
     * 领取条件json
     */
	@TableField("grant_condition")
	private String grantCondition;
    /**
     * 显示顺序
     */
	@TableField("display_order")
	private Integer displayOrder;
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
	 * 累计支付成功
	 */
	@TableField("trade_limit")
	private Integer tradeLimit;
	
	/**
	 * 累计积分达到
	 */
	@TableField("points_limit")
	private Integer pointsLimit;
	
	/**
	 * 累计消费金额
	 */
	@TableField("amount_limit")
	private Integer amountLimit;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public Integer getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(Integer cardLevel) {
		this.cardLevel = cardLevel;
	}

	public Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}

	public Boolean isIsNeedActivate() {
		return isNeedActivate;
	}

	public void setIsNeedActivate(Boolean isNeedActivate) {
		this.isNeedActivate = isNeedActivate;
	}

	public Boolean isIsSyncWeixin() {
		return isSyncWeixin;
	}

	public void setIsSyncWeixin(Boolean isSyncWeixin) {
		this.isSyncWeixin = isSyncWeixin;
	}

	public Boolean isIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Boolean isSyncWeixinState() {
		return syncWeixinState;
	}

	public void setSyncWeixinState(Boolean syncWeixinState) {
		this.syncWeixinState = syncWeixinState;
	}

	public String getWeixinCardId() {
		return weixinCardId;
	}

	public void setWeixinCardId(String weixinCardId) {
		this.weixinCardId = weixinCardId;
	}

	public String getCardNumberPrefix() {
		return cardNumberPrefix;
	}

	public void setCardNumberPrefix(String cardNumberPrefix) {
		this.cardNumberPrefix = cardNumberPrefix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isIsAllowShare() {
		return isAllowShare;
	}

	public void setIsAllowShare(Boolean isAllowShare) {
		this.isAllowShare = isAllowShare;
	}

	public Integer getTermDays() {
		return termDays;
	}

	public void setTermDays(Integer termDays) {
		this.termDays = termDays;
	}

	public Date getTermStartAt() {
		return termStartAt;
	}

	public void setTermStartAt(Date termStartAt) {
		this.termStartAt = termStartAt;
	}

	public Date getTermEndAt() {
		return termEndAt;
	}

	public void setTermEndAt(Date termEndAt) {
		this.termEndAt = termEndAt;
	}

	public Long getTermToCardId() {
		return termToCardId;
	}

	public void setTermToCardId(Long termToCardId) {
		this.termToCardId = termToCardId;
	}

	public String getActivationCondition() {
		return activationCondition;
	}

	public void setActivationCondition(String activationCondition) {
		this.activationCondition = activationCondition;
	}

	public String getGrantCondition() {
		return grantCondition;
	}

	public void setGrantCondition(String grantCondition) {
		this.grantCondition = grantCondition;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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
	
	
	public Integer getTradeLimit() {
		return tradeLimit;
	}

	public void setTradeLimit(Integer tradeLimit) {
		this.tradeLimit = tradeLimit;
	}

	public Integer getPointsLimit() {
		return pointsLimit;
	}

	public void setPointsLimit(Integer pointsLimit) {
		this.pointsLimit = pointsLimit;
	}

	public Integer getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(Integer amountLimit) {
		this.amountLimit = amountLimit;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	
	public Integer getTermType() {
		return termType;
	}

	public void setTermType(Integer termType) {
		this.termType = termType;
	}
	
	
	public Integer getCoverType() {
		return coverType;
	}

	public void setCoverType(Integer coverType) {
		this.coverType = coverType;
	}
	
	public Integer getTradeAmountLimit() {
		return tradeAmountLimit;
	}

	public void setTradeAmountLimit(Integer tradeAmountLimit) {
		this.tradeAmountLimit = tradeAmountLimit;
	}

	@Override
	public String toString() {
		return "Card{" +
			"id=" + id +
			", name=" + name +
			", createUserId=" + createUserId +
			", merchantId=" + merchantId +
			", colorId=" + colorId +
			", colorCode=" + colorCode +
			", coverPic=" + coverPic +
			", cardLevel=" + cardLevel +
			", grantType=" + grantType +
			", isNeedActivate=" + isNeedActivate +
			", isSyncWeixin=" + isSyncWeixin +
			", isAvailable=" + isAvailable +
			", syncWeixinState=" + syncWeixinState +
			", weixinCardId=" + weixinCardId +
			", cardNumberPrefix=" + cardNumberPrefix +
			", tradeAmountLimit=" + tradeAmountLimit +
			", description=" + description +
			", isAllowShare=" + isAllowShare +
			", termDays=" + termDays +
			", termStartAt=" + termStartAt +
			", termEndAt=" + termEndAt +
			", termToCardId=" + termToCardId +
			", activationCondition=" + activationCondition +
			", grantCondition=" + grantCondition +
			", displayOrder=" + displayOrder +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}

}
