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
 * 会员卡权益
 * </p>
 *
 * @author chengxg
 * @since 2017-07-31
 */
@TableName("v_card_right")
public class CardRight extends Model<CardRight> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 会员卡id
     */
	@TableField("card_id")
	private Long cardId;
    /**
     * 权益类型id
     */
	@TableField("right_type_id")
	private Long rightTypeId;
	
	@TableField("right_type")
	private String rightType;
    /**
     * 优惠券id
     */
	@TableField("coupon_id")
	private Long couponId;
    /**
     * 优惠券数量
     */
	private Integer number;
    /**
     * 积分
     */
	private Integer points;
    /**
     * 折扣(两位整数)
     */
	private Integer discount;
    /**
     * 是否生效
     */
	@TableField("is_available")
	private Boolean isAvailable;
    /**
     * 权益描述
     */
	private String tags;
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

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getRightTypeId() {
		return rightTypeId;
	}

	public void setRightTypeId(Long rightTypeId) {
		this.rightTypeId = rightTypeId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Boolean isIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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
	

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	@Override
	public String toString() {
		return "CardRight{" +
			"id=" + id +
			", cardId=" + cardId +
			", rightTypeId=" + rightTypeId +
			", couponId=" + couponId +
			", number=" + number +
			", points=" + points +
			", discount=" + discount +
			", isAvailable=" + isAvailable +
			", tags=" + tags +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			"}";
	}
}
