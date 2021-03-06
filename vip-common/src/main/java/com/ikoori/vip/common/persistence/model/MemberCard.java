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
 * 会员卡领取记录
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_member_card")
public class MemberCard extends Model<MemberCard> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 会员id
     */
	@TableField("member_id")
	private Long memberId;
	
    /**
     * 商户id
     */
	@TableField("merchant_id")
	private Long merchantId;
	
    /**
     * 会员卡id
     */
	@TableField("card_id")
	private Long cardId;
	
	
    /**
     * 卡号
     */
	@TableField("card_number")
	private String cardNumber;
    /**
     * 状态(0:使用中;1:未激活;2:已过期)
     */
	private Integer state;
    /**
     * 是否为默认卡
     */
	@TableField("is_default")
	private Boolean isDefault;
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
	 * 关联查询对象
	 */
	@TableField(exist=false)
	private Member member;
	@TableField(exist=false)
	private Card card;
	@TableField(exist=false)
	private Merchant merchant;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Boolean isIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	
	@Override
	public String toString() {
		return "MemberCard{" +
			"id=" + id +
			", memberId=" + memberId +
			", merchantId=" + merchantId +
			", cardId=" + cardId +
			", cardNumber=" + cardNumber +
			", state=" + state +
			", isDefault=" + isDefault +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
